package br.com.inventory.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.inventory.*"})
public class InventoryWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryWebApplication.class,args);
    }
}
