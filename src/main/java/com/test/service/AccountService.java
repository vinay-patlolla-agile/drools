package com.test.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbpm.process.audit.AbstractAuditLogger;
import org.jbpm.process.audit.AuditLoggerFactory;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.configuration.KnowledgeBaseEnvironment;
import com.test.model.Account;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {

   @Autowired
   KnowledgeBaseEnvironment knowledgeBaseEnvironment;

   public List<Account> getAccounts() {

      return new ArrayList<Account>();

   }

   public void saveAccount(Account account) {

      StatefulKnowledgeSession kieSession = knowledgeBaseEnvironment.getKieSession();

      FactHandle factHandle = kieSession.insert(account);
      System.out.println(factHandle.toExternalForm());

      int fired = kieSession.fireAllRules();

      System.out.println("Fired ? " + fired + "");

      AbstractAuditLogger auditLogger =
            AuditLoggerFactory.newJPAInstance(kieSession.getEnvironment());

      kieSession.addEventListener(auditLogger);

      Collection<FactHandle> factHandleCollection = kieSession.getFactHandles();
      System.out.println(
            "Number of facts stored in the Working Memory {} " + factHandleCollection.size());
   }

}
