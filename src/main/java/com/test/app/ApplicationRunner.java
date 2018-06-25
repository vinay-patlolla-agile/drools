package com.test.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.test.configuration.KnowledgeBaseEnvironment;

@SpringBootApplication
@ComponentScan("com.test")
public class ApplicationRunner {

   public static void main(String[] args) {
      SpringApplication.run(ApplicationRunner.class, args);
      System.setProperty("java.naming.factory.initial",
            "bitronix.tm.jndi.BitronixInitialContextFactory");

      KnowledgeBaseEnvironment.initiateNewEnvironment();
      // System.setProperty("java.naming.provider.url","rmi://localhost:1099");
   }

}