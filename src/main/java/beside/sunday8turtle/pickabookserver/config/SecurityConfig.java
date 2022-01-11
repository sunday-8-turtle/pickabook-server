package beside.sunday8turtle.pickabookserver.config;

import beside.sunday8turtle.pickabookserver.common.security.CustomAccessDeniedHandler;
import beside.sunday8turtle.pickabookserver.common.security.CustomAuthenticationEntryPoint;
import beside.sunday8turtle.pickabookserver.common.util.RedisUtil;
import beside.sunday8turtle.pickabookserver.config.jwt.JwtAuthenticationFilter;
import beside.sunday8turtle.pickabookserver.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
//                        .antMatchers("/test", "/test2").hasRole("USER")
//                        .antMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().permitAll()
                .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,redisUtil), UsernamePasswordAuthenticationFilter.class);
    }

}