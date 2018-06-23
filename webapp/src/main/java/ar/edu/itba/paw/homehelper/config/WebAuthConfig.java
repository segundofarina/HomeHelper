package ar.edu.itba.paw.homehelper.config;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.auth.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.homehelper.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HHUserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        File file;
        /* Default key */
        String key = "8EE76CDB9A8876AAE26A81FAE178FC387382830D6DD81F90C064CEDCEEAE7CDD";
        try {
             file = ResourceUtils.getFile("classpath:keyL.pem");
        } catch (FileNotFoundException e) {
            // Handle remeber me key error loading file
            file = null;
        }

        if(file != null && file.exists()) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                key = content;
            } catch (IOException e) {
                // Unable to read file
            }
        }

        http.userDetailsService(userDetailsService).sessionManagement()
                .invalidSessionUrl("/")
                .and().authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/sprovider/**").hasRole("PROVIDER")
                    .antMatchers("/client/**").hasRole("USER")
                    .antMatchers("/unverified/**").hasRole("UNVERIFIED_USER")
                    .antMatchers("/**").permitAll()
                .and().formLogin()
                    .usernameParameter("username").passwordParameter("password")
                    .successHandler(successHandler())
                    .loginPage("/login")
                    .failureUrl("/login?error=y")
                .and().rememberMe()
                    .userDetailsService(userDetailsService)
                    .rememberMeParameter("rememberme")
                    .key(key)
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/error/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403", "/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler("/");
    }

}

