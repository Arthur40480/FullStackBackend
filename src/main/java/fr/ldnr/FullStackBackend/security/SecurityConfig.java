package fr.ldnr.FullStackBackend.security;

import fr.ldnr.FullStackBackend.security.entities.AppUser;
import fr.ldnr.FullStackBackend.security.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;

    public SecurityConfig(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.GET,"/hotels").permitAll()
                .antMatchers(HttpMethod.GET,"/hotels/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/hotel/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/hotel").permitAll() // .hasAnyAuthority("ADMIN", "HOTEL_MANAGER")
                .antMatchers(HttpMethod.DELETE,"/hotel/{id}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/manager/{id}/hotels").permitAll() //.hasAnyAuthority("ADMIN", "HOTEL_MANAGER")
                .antMatchers(HttpMethod.GET,"/cities").permitAll()
                .antMatchers(HttpMethod.GET,"/city/{id}").permitAll() //.hasAnyAuthority("ADMIN", "HOTEL_MANAGER")
                .antMatchers(HttpMethod.POST,"/city").permitAll()
                .antMatchers(HttpMethod.DELETE,"/city/{id}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/users").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/user/{id}").permitAll()  //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/managers").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/managers/{id}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/users").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/username").permitAll()
                .antMatchers(HttpMethod.GET,"/user/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/assign/{idUser}/{idHotel}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/remove/{idUser}/{idHotel}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/manager").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/manager/{id}").permitAll() //.hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/manager/{id}").permitAll(); //.hasAuthority("ADMIN");

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

