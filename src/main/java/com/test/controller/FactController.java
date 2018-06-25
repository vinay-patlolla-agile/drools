package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.UserActivity;
import com.test.service.FactService;

@RestController
public class FactController {

   @Autowired
   private FactService factService;

   @RequestMapping(value = "/facts", method = RequestMethod.GET, produces = "application/json")
   public List<UserActivity> getFacts() {

      return factService.getFacts();

   }

   @RequestMapping(value = "/facts/save", method = RequestMethod.POST,
         consumes = "application/json")
   public void saveFacts(@RequestBody UserActivity userActivity) {

      factService.saveFacts(userActivity);

   }
}
