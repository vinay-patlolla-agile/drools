package com.test.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class Transaction {

   private Customer customer;
   private List<Item> items;
   private Timestamp transactionDateTime;

}
