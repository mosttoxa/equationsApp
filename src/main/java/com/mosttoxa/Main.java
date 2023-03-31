package com.mosttoxa;

import java.util.Date;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration(exclude = {EmbeddedMongoAutoConfiguration.class})
@ComponentScan
public class Main implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Autowired
    EquationsService es;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("starting equations at " + new Date().toString());
        try (Scanner scan = new Scanner(System.in)) {
            while (scan.hasNext()) {
                String input = scan.nextLine();
                // break app
                if (input.equals("exit")) {
                    break;
                // solve equation and add it to db (input form: solve 2x=4)
                } else if (input.startsWith("solve")) {
                    String equation = input.substring("solve".length());
                    es.solve(equation);
                // check if supposed number is correct root of equation (input form: check 2x=4 2)
                } else if (input.startsWith("check")) {
                    input = input.substring("check".length());
                    String equation = (input.contains(" ")) ? input.substring(0, input.lastIndexOf(" ")) : "";
                    String root = (input.contains(" ")) ? input.substring(input.lastIndexOf(" ")) : "";
                    es.check(equation, root);
                // find all equations from db with given root (input form: find 2)
                } else if (input.startsWith("find")) {
                    String root = input.substring("find".length());
                    es.findByRoot(root);
                } else {
                    LOG.info("Wrong input. Please enter correct command");
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}