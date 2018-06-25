package com.test.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;

import org.jbpm.process.audit.AbstractAuditLogger;
import org.jbpm.process.audit.AuditLoggerFactory;
import org.jbpm.process.audit.AuditLoggerFactory.Type;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.configuration.KnowledgeBaseEnvironment;
import com.test.model.UserActivity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FactService {

   @Autowired
   KnowledgeBaseEnvironment knowledgeBaseEnvironment;

   public void saveFacts(UserActivity userActivity) {

      StatefulKnowledgeSession kieSession = knowledgeBaseEnvironment.getKieSession();

      FactHandle factHandle = kieSession.insert(userActivity);
      log.debug(factHandle.toExternalForm());

      AbstractAuditLogger auditLogger =
            AuditLoggerFactory.newJPAInstance(kieSession.getEnvironment());

      kieSession.addEventListener(auditLogger);

      Collection<FactHandle> factHandleCollection = kieSession.getFactHandles();
      log.debug("Number of facts stored in the Working Memory {} ", factHandleCollection.size());
   }

   public List<UserActivity> getFacts() {

      StatefulKnowledgeSession kieSession = knowledgeBaseEnvironment.getKieSession();

      Collection<FactHandle> factHandleCollection = kieSession.getFactHandles();
      log.debug("Number of facts stored in the Working Memory {} ", factHandleCollection.size());

      return new ArrayList<UserActivity>();

   }

   @SuppressWarnings("unused")
   private AbstractAuditLogger getJMSAuditLogger(StatefulKnowledgeSession kieSession) {
      AbstractAuditLogger auditLogger = null;

      try {

         InitialContext ctx = new InitialContext();
         ConnectionFactory factory =
               (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
         Queue queue = (Queue) ctx.lookup("myQueue");

         Map<String, Object> jmsProps = new HashMap<String, Object>();

         jmsProps.put("jbpm.audit.jms.transacted", true);
         jmsProps.put("jbpm.audit.jms.connection.factory", factory);
         jmsProps.put("jbpm.audit.jms.queue", queue);
         AuditLoggerFactory.newInstance(Type.JMS, kieSession, jmsProps);

      } catch (Exception e) {
         log.error("Error creating a JMS Audit logger {}", e);
      }

      return auditLogger;

   }

}
