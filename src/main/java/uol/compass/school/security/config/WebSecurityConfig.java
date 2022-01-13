package uol.compass.school.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private static final String STUDENT_API_URL = "/api/v1/students/**";

    private static final String SWAGGER_URL = "/swagger-ui.html";

    private static final String H2_CONSOLE_URL = "/h2-console/**";

    private static final String ROLE_ADMIN = Role.ADMIN.getDescription();
    private static final String ROLE_EDUCATOR = Role.EDUCATOR.getDescription();
    private static final String ROLE_RESPONSIBLE = Role.RESPONSIBLE.getDescription();

    private static final String[] SWAGGER_RESOURCES = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
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
                .authorizeRequests().antMatchers(USERS_API_URL, SWAGGER_URL).permitAll()
                .antMatchers(CLASSROOMS_API_URL, COURSES_API_URL, EDUCATORS_API_URL, OCCURRENCES_API_URL, RESPONSIBLE_API_URL, STUDENT_API_URL).hasAnyRole(ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }
}
