package edu.matyas94k.s3practice;

import edu.matyas94k.s3practice.config.AwsUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    private static CommandLineRunner runner() {
        return args -> AwsUtils.configure(args[0], args[1]);
    }

}
