package com.systex.demoPortal.config;

import com.systex.demoPortal.filters.UrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    public FilterRegistrationBean<UrlFilter> urlFilter(){
        FilterRegistrationBean<UrlFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UrlFilter());
        registrationBean.addUrlPatterns("/employee");
        return registrationBean;
    }
}
