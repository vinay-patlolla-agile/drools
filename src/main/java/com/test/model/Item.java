package com.test.model;

import java.util.Date;

import lombok.Data;

@Data
public class Item {
   private String name;
   private float price;
   private Date dateOfBuying;
}
