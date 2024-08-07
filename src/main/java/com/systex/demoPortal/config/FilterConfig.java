package com.systex.demoPortal.config;

import com.systex.demoPortal.filters.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<LoginFilter> urlFilter(final LoginFilter loginFilter){
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loginFilter);
        registrationBean.addUrlPatterns("/login","/ajaxlogin");
        return registrationBean;
    }
}
