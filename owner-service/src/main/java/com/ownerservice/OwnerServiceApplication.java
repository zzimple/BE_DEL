package com.ownerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
    "com.ownerservice",
    "com.commonservice"
})
@SpringBootApplication
public class OwnerServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(OwnerServiceApplication.class, args);
  }

}
