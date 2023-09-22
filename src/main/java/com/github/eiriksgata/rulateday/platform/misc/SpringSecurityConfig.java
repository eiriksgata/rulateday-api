package com.github.eiriksgata.rulateday.platform.misc;

import com.github.eiriksgata.rulateday.platform.provider.CustomAuthenticationProvider;
import com.github.eiriksgata.rulateday.platform.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity and = web.ignoring().and();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, method) -> {
            // 带IgnoreAuth注解的方法直接放行
            if (method.getMethodAnnotation(IgnoreAuthentication.class) != null) {
                // 根据请求类型做不同的处理
                info.getMethodsCondition().getMethods().forEach(requestMethod -> {
                    switch (requestMethod) {
                        case GET:
                            // getPatternsCondition得到请求url数组，遍历处理
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.GET, pattern);
                            });
                            break;
                        case POST:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.POST, pattern);
                            });
                            break;
                        case DELETE:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.DELETE, pattern);
                            });
                            break;
                        case PUT:
                            info.getPatternsCondition().getPatterns().forEach(pattern -> {
                                and.ignoring().antMatchers(HttpMethod.PUT, pattern);
                            });
                            break;
                        default:
                            break;
                    }
                });
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 正常配置其他安全相关的内容
        http.authorizeRequests()
                // 放行所有OPTIONS请求

                //文档
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                // 其他请求都需要认证后才能访问
                .anyRequest().authenticated()
                // 使用自定义的 accessDecisionManager
                .accessDecisionManager(accessDecisionManager())
                .and()
                // 添加未登录与权限不足异常处理器
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                // 将自定义的JWT过滤器放到过滤链中
                .addFilterBefore(
                        jwtAuthenticationTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class)

                // 打开Spring Security的跨域
                .cors()
                .and()
                // 关闭CSRF
                .csrf().disable()

                // 关闭Session机制
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AccessDecisionVoter<FilterInvocation> accessDecisionProcessor() {
        return new AccessDecisionProcessor();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                new WebExpressionVoter(), accessDecisionProcessor());
        return new UnanimousBased(decisionVoters);
    }


}
