package com.center.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@EnableAspectJAutoProxy
@SpringBootApplication
//@ComponentScan(basePackages = {"com.center.*"})
public class CenterSSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(CenterSSOApplication.class,args);
    }
}
