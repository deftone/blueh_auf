package de.deftone.blueh_auf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/biotopvernetzungUI").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf()
                .and()
                .formLogin()
                //wenn man es customizen moechte:
                .loginPage("/login");


    }

    //passt hier am besten hin, gehoert zu security und eine bean in eine config klasse
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

}