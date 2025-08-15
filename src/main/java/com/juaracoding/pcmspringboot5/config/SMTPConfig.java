package com.juaracoding.pcmspringboot5.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:smtpconfig.properties")
public class SMTPConfig {

    private static String emailUsername;
    private static String emailPassword;

    public static String getEmailUsername() {
        return emailUsername;
    }

    @Value("${email.username}")
    private void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public static String getEmailPassword() {
        return emailPassword;
    }

    @Value("${email.password}")
    private void setEmailPassword(String emailPassword) {
//        this.emailPassword = Crypto.decript(emailPassword);
        this.emailPassword = emailPassword;
    }
}
