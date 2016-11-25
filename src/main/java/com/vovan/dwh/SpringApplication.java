package com.vovan.dwh;

import com.vovan.dwh.data_population.SocketServerPopulator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringApplication {

    public static void main(String[] args) {
        ApplicationContext context = org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
        launchSocketServer(context);
    }

    private static void launchSocketServer(ApplicationContext context) {
        SocketServerPopulator socketServer = context.getBean(SocketServerPopulator.class);
        Thread thread = new Thread(socketServer);
        thread.start();
    }
}
