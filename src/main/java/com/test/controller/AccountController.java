package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.Account;
import com.test.service.AccountService;

@RestController
public class AccountController {

   @Autowired
   AccountService accountService;

   @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = "application/json")
   public List<Account> getAccounts() {

      return accountService.getAccounts();

   }

   @RequestMapping(value = "/account/save", method = RequestMethod.POST,
         consumes = "application/json")
   public void saveAccount(@RequestBody Account account) {

      accountService.saveAccount(account);

   }

}
