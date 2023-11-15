package ua.holovchenko.dopomoga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("master")
                .password(encoder().encode("master"))
                .authorities("CIVUC_READ","CIVREQ_READ","CIVUC_CREATE","CIVREQ_CREATE","CIVUC_EDIT","CIVREQ_EDIT")
                .build());
        manager.createUser(User.withUsername("reader")
                .password(encoder().encode("reader"))
                .authorities("CIVUC_READ","CIVREQ_READ")
                .build());
        manager.createUser(User.withUsername("registrator")
                .password(encoder().encode("registrator "))
                .authorities("CIVUC_READ","CIVREQ_READ","CIVUC_CREATE","CIVREQ_CREATE")
                .build());
        manager.createUser(User.withUsername("editor")
                .password(encoder().encode("editor"))
                .authorities("CIVUC_READ","CIVREQ_READ","CIVUC_EDIT","CIVREQ_EDIT")
                .build());
        return manager;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers("*/civil-undercared").hasAuthority("CIVUC_READ")
                                .requestMatchers("*/civil-requests").hasAuthority("CIVREQ_READ")
                                .requestMatchers("*/civil-undercared/new").hasAuthority("CIVUC_CREATE")
                                .requestMatchers("*/civil-requests/new").hasAuthority("CIVREQ_CREATE")
                                .requestMatchers("*/civil-undercared/*/edit").hasAuthority("CIVUC_EDIT")
                                .requestMatchers("*/civil-requests/*/edit").hasAuthority("CIVREQ_EDIT")
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }
}
