package com.test.configuration;

import javax.persistence.Persistence;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.context.annotation.Configuration;

import bitronix.tm.TransactionManagerServices;

@Configuration
public class KnowledgeBaseEnvironment {

   private static Environment environment;

   private static StatefulKnowledgeSession kieSession;

   public static void initiateNewEnvironment() {

      if (null == environment) {
         environment = KnowledgeBaseFactory.newEnvironment();
         environment.set(EnvironmentName.ENTITY_MANAGER_FACTORY,
               Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa"));
         environment.set(EnvironmentName.TRANSACTION_MANAGER,
               TransactionManagerServices.getTransactionManager());
      }

      KieBase kbase = kieContainer().getKieBase("rules");

      kieSession = JPAKnowledgeService.loadStatefulKnowledgeSession(1, kbase, null, environment);

   }

   private static KieContainer kieContainer() {
      return KieServices.Factory.get().newKieClasspathContainer();
   }

   public StatefulKnowledgeSession getKieSession() {
      return kieSession;
   }

}
