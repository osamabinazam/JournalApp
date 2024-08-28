package pk.smartq.journalApp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http ) throws Exception{
        http.authorizeRequests(requests -> requests
                .requestMatchers("/public/api/**")
                .permitAll()
                .anyRequest().authenticated());

            http.httpBasic(Customizer.withDefaults());
            http.csrf(AbstractHttpConfigurer::disable);
           return http.build();
    }

}
