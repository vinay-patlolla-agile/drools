package com.test.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Reward;
import com.test.model.RewardPolicy;
import com.test.model.UserActivity;
import com.test.service.RewardCalculatorService;

@RestController

public class RewardServiceController {

   @Autowired
   private RewardCalculatorService rewardCalculatorService;

   @RequestMapping(value = "/checkRewards", method = RequestMethod.POST,
         consumes = "application/json", produces = "application/json")
   public Reward checkRewards(@RequestBody Map<String, Object> requestMap) {
      System.out.println("Hi I got a request Object");
      System.out.println(requestMap.toString());

      // Populating Reward Policy Object
      RewardPolicy rp = new RewardPolicy();
      Map<String, Object> rewardMap = (Map<String, Object>) requestMap.get("rewardPolicy");
      // rp.setPointsAwarded((Integer) rewardMap.get("pointsTobeAwarded"));
      String dateString = (String) rewardMap.get("lastDateOfChallenge");
      String dateStringStart = (String) rewardMap.get("startDateOfChallenge");
      DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
      Date lastDateOfChallenge = null;
      Date startDateOfChallenge = null;
      try {
         lastDateOfChallenge = format.parse(dateString);
         startDateOfChallenge = format.parse(dateStringStart);
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      rp.setLastDateOfChallenge(lastDateOfChallenge);
      rp.setStartDateOfChallenge(startDateOfChallenge);
      rp.setItemsForOffer((List<String>) rewardMap.get("itemsForOffer"));
      rp.setChallengeType((String) rewardMap.get("challengeType"));

      // Populating UserActivity
      UserActivity ua = new UserActivity();
      Map<String, Object> userMap = (Map<String, Object>) requestMap.get("userActivity");
      ua.setItems((List<String>) userMap.get("itemsPurchased"));
      String dateStringlastActivityBuying = (String) userMap.get("lastActivityOfBuying");
      Date lastActivityOfBuying = null;
      try {
         lastActivityOfBuying = format.parse(dateStringlastActivityBuying);
      } catch (ParseException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      ua.setLastActivityOfBuying(lastActivityOfBuying);
      ua.setUserName((String) userMap.get("userName"));
      return rewardCalculatorService.makeRewardPolicy(rp, ua);
   }

   @RequestMapping(value = "/getDiscount", method = RequestMethod.GET,
         produces = "application/json")
   public Reward getQuestions(@RequestParam(required = true) String type) {
      Reward reward = new Reward();
      return reward;
   }

   @Bean
   public RewardCalculatorService rewardCalculatorService() {
      return new RewardCalculatorService(kieContainer());
   }

   @Bean
   public KieContainer kieContainer() {
      return KieServices.Factory.get().newKieClasspathContainer();
   }
}
