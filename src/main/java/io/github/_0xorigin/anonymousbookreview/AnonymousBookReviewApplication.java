package io.github._0xorigin.anonymousbookreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
@EnableTransactionManagement
@EnableJpaRepositories
@EnableScheduling
public class AnonymousBookReviewApplication {
    static void main(String[] args) {
        new SpringApplication(AnonymousBookReviewApplication.class).run(args);
    }
}
