package com.rongxinkj.alexmessage;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AlexmessageApplication {
    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) {

        //SpringApplication.run(AlexmessageApplication.class, args);

        String mode = args != null && args.length > 0 ? args[0] : null;

        if (applicationContext != null && mode != null && "stop".equals(mode)) {
            System.exit(SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
                @Override
                public int getExitCode() {
                    return 0;
                }
            }));
        }
        else {
            SpringApplication app = new SpringApplication(AlexmessageApplication.class);
            applicationContext = app.run(args);
        }

    }

}
