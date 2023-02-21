/** package com.kev.coop.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args ->{
            User lilkev = new User(
                    "Kevin",
                    "klang021@gmail.com",
                    LocalDate.of(2000, Month.MAY, 9)
            );
            User lauren = new User(
                    "Lauren",
                    "lrive021@gmail.com",
                    LocalDate.of(2003, Month.MARCH, 4)
            );
            userRepository.saveAll(
                    List.of(lilkev, lauren)
            );
        };
    }
}
**/