package org.humanResources.security;

import org.humanResources.security.jwt.JwtAuthEntryPoint;
import org.humanResources.security.jwt.JwtAuthTokenFilter;
import org.humanResources.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig /*extends WebSecurityConfigurerAdapter*/ {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }*/

    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                //Begin Swagger
                .antMatchers("/v2/api-docs", "/swagger-ui.html", "/configuration/**", "/swagger**", "/webjars/**")
                .permitAll()
                //End Swagger
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(c->{
                            /*CorsConfigurationSource source = request -> {
                                // define the CORS configuration. Within it, we create
                                // a CorsConfiguration object where we set
                                // the allowed origins and methods.
                                CorsConfiguration config = new CorsConfiguration();
                                //config.setAllowedOrigins(List.of("example.com", "example.org"));
                                config.setAllowCredentials(true);
                                config.addAllowedOriginPattern("*");
                                config.addAllowedHeader("*");
                                config.setAllowedMethods(
                                        List.of("OPTIONS","GET", "POST", "PUT", "DELETE", "PATCH"));
                                return config;

                            };*/
                            //c.configurationSource(source);
                            c.configurationSource(corsConfigurationSource());

                    }
                )/*.and()*/
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()
                //Begin Swagger
                .requestMatchers("/v2/api-docs", "/swagger-ui.html", "/configuration/**", "/swagger*/**",
                        "/webjars/**",
                        "/error")
                .permitAll()
                //End Swagger
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(exceptions ->exceptions.authenticationEntryPoint(unauthorizedHandler))
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        /*


        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
                new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedHeaders(
                Arrays.asList("Authorization", "Content-Type"));
        corsConfiguration.setAllowedMethods(
                Arrays.asList(
                        "DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
        corsConfiguration.setAllowedOrigins(
                Stream.of(
                        _dxpDomains.split("\\s*[,\n]\\s*")
                ).map(
                        String::trim
                ).flatMap(
                        domain -> Stream.of(
                                "http://", "https://"
                        ).map(
                                scheme -> scheme.concat(domain)
                        )
                ).collect(
                        Collectors.toList()
                ));

        urlBasedCorsConfigurationSource.registerCorsConfiguration(
                "/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;*/

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //config.addAllowedOrigin("*");
        config.addAllowedOriginPattern( "*"); // SpringBoot2.4.0 [allowedOriginPatterns] instead of [allowedOrigins]
        config.addAllowedHeader("*");
        config.setAllowedMethods(List.of("DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}