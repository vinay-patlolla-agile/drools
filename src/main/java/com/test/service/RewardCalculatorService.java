package com.test.service;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.configuration.KnowledgeBaseEnvironment;
import com.test.model.Reward;
import com.test.model.RewardPolicy;
import com.test.model.UserActivity;

import bitronix.tm.resource.jdbc.PoolingDataSource;

@Service
public class RewardCalculatorService {

   @Autowired
   KnowledgeBaseEnvironment knowledgeBaseEnvironment;

   private final Map<Object, FactHandle> fc = new HashMap<Object, FactHandle>();
   private final Map<Object, Object> factsMap = new HashMap<Object, Object>();

   @Autowired
   public RewardCalculatorService(KieContainer kieContainer) {

      PoolingDataSource ds = new PoolingDataSource();
      ds.setUniqueName("jdbc/BitronixJTADataSource");
      ds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
      ds.setMaxPoolSize(3);
      ds.setAllowLocalTransactions(true);
      ds.getDriverProperties().put("user", "imfilmy");
      ds.getDriverProperties().put("password", "imfilmy");
      ds.getDriverProperties().put("url", "jdbc:mysql://18.220.233.190:3306/testdb");
      ds.getDriverProperties().put("driverClassName", "com.mysql.jdbc.Driver");
      // ds.getDriverProperties().put("URL", "jdbc:h2:mem:mydb");
      ds.init();

   }

   public Reward makeRewardPolicy(RewardPolicy rp, UserActivity ua) {

      StatefulKnowledgeSession kieSession = knowledgeBaseEnvironment.getKieSession();

      System.out.println("kieSession" + kieSession);
      Reward reward = new Reward();
      kieSession.setGlobal("reward", reward);
      kieSession.insert(rp);

      System.out.println("factsMap starting" + factsMap);
      if (factsMap.containsKey(ua.getUserName())) {
         System.out.println("Fact already present");
         FactHandle fh = kieSession.getFactHandle(ua);
         kieSession.update(fh, ua);
      } else {
         FactHandle fh = kieSession.insert(ua);
         fc.put(ua.getUserName(), fh);
         factsMap.put(ua.getUserName(), ua);
      }
      System.out.println("factsMap" + factsMap.toString());
      kieSession.fireAllRules();
      // kieSession.dispose();
      return reward;
   }

}
