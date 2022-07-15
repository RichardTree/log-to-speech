package com.rickyt.logtospeech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableWebMvc
//@EnableSwagger2
@SpringBootApplication
public class LogToSpeechApplication {

//    @Bean
//    public InternalResourceViewResolver defaultViewResolver() {
//        return new InternalResourceViewResolver();
//    }

    public static void main(String[] args) {
        SpringApplication.run(LogToSpeechApplication.class, args);
    }

}
