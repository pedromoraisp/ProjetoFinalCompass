package uol.compass.school.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uol.compass.school.enums.Role;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USERS_API_URL = "/api/v1/users/**";
    private static final String CLASSROOMS_API_URL = "/api/v1/classrooms/**";
    private static final String COURSES_API_URL = "/api/v1/courses/**";
    private static final String EDUCATORS_API_URL = "/api/v1/educators/**";
    private static final String OCCURRENCES_API_URL = "/api/v1/occurrences/**";
    private static final String RESPONSIBLE_API_URL = "/api/v1/responsible/**";
    private static final String COORDINATORS_API_URL = "/api/v1/coordinators/**";

    private static final String H2_CONSOLE_URL = "/h2-console/**";

    private static final String ROLE_ADMIN = Role.ADMIN.getDescription();
    private static final String ROLE_RESPONSIBLE = Role.RESPONSIBLE.getDescription();

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Return AuthenticationManager implementation
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/api/v1/users/authenticate").permitAll()
                .antMatchers(H2_CONSOLE_URL).permitAll()
                .antMatchers(CLASSROOMS_API_URL, COURSES_API_URL, EDUCATORS_API_URL, OCCURRENCES_API_URL, RESPONSIBLE_API_URL, COORDINATORS_API_URL).hasRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
