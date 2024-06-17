package fr.ldnr.FullStackBackend.security;

import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests().anyRequest().authenticated();
        // Toute les ressources nécessitent dorénavant une authentification
        // * Active le formulaire d'authentification
//        http.formLogin();
        // ajout filtre authentification

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/customers").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/orders/{id}/status").hasAnyAuthority("ADMIN", "ORDER_MANAGER")
                .antMatchers(HttpMethod.POST,"/download").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/download/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/categoryy").permitAll()
                .antMatchers(HttpMethod.DELETE,"/ordersitems/{id}").permitAll();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // c'est a nous de lui indiquer comment chercher un user
                AppUser user = accountService.findUserByUsername(username);
                System.out.println("username = " + username);
                Collection<GrantedAuthority> authorities = new ArrayList<>();

                user.getRoles().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getRolename()));
                });
                System.out.println("authorities = " + authorities);
                System.out.println(new User(user.getUsername(), user.getPassword(), authorities));
                return new User(user.getUsername(), user.getPassword(), authorities);
            }
        });
    }
}
