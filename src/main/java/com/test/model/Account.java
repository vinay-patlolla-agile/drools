package com.test.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String type;
   private String name;

}
