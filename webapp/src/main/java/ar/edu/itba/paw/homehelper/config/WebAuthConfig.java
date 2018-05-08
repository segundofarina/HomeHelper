package ar.edu.itba.paw.homehelper.config;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.homehelper.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HHUserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService).sessionManagement()
                .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/sprovider/**").hasRole("PROVIDER")
                    .antMatchers("/client/**").hasRole("USER")
                    .antMatchers("/**").permitAll()
                .and().formLogin()
                    .usernameParameter("username").passwordParameter("password")
                    .defaultSuccessUrl("/", false).loginPage("/login")
                .and().rememberMe()
                    .userDetailsService(userDetailsService)
                    .rememberMeParameter("rememberme")
                    .key("mysupersecretketthatnobodyknowsabout")
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403", "/resources/**");
    }

}

