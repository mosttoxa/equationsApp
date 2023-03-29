package org.example;

import java.util.Date;
import java.util.Scanner;

import org.mariuszgromada.math.mxparser.License;
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
                if (input.equals("exit")) {
                    break;
                } else if (input.startsWith("solve")) {
                    input = input.substring("solve".length());
                    LOG.info(input);
                    es.solve(input);
                } else if (input.startsWith("check")) {
                    input = input.substring("check".length());
                    LOG.info(input);
                    String root = input.substring(input.lastIndexOf(" "));
                    es.check(input.substring(0, input.lastIndexOf(" ")), root);
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