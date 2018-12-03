package ar.edu.itba.paw.homehelper.config;

import ar.edu.itba.paw.homehelper.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.homehelper.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HHUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private StatelessLoginSuccessHandler statelessLoginSuccessHandler;

    @Autowired
    private StatelessAuthenticationFilter statelessAuthenticationFilter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService).sessionManagement()
                .and()
                    .csrf().disable()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().authorizeRequests()
                    .antMatchers("/api/login").permitAll()
                    .antMatchers("/api/**").authenticated()
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .formLogin().usernameParameter("username").passwordParameter("password").loginProcessingUrl("/api/login")
                    .successHandler(statelessLoginSuccessHandler)
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                    .addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/error/*", "/resources/**", "/websocket/**");
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
    public String tokenKey() {
         String key = "8EE76CDB9A8876AAE26A81FAE178FC387382830D6DD81F90C064CEDCEEAE7CDD";
         File file;
         try {
             file = ResourceUtils.getFile("classpath:keyL.pem");
         }catch (FileNotFoundException e) {
             file = null;
         }

         if (file != null && file.exists()) {
             try {
                 key = new String(Files.readAllBytes(file.toPath()));
             } catch (IOException e) {
                 // Unable to read file
             }
         }

         return key;
    }

}

