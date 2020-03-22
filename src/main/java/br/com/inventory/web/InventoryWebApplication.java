package br.com.inventory.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.inventory.*"})
@EntityScan(basePackages = {"br.com.inventory.adapters.persistence.entity"})
@EnableJpaRepositories(basePackages = {"br.com.inventory.adapters.persistence.repository"})
public class InventoryWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryWebApplication.class,args);
    }
}
