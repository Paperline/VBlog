package org.sang.config;

import org.sang.config.CrossInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport {

    private final CrossInterceptor crossInterceptor;


    @Autowired
    public MvcInterceptorConfig(CrossInterceptor crossInterceptor) {
        this.crossInterceptor = crossInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        int order = 0;
        // 跨域拦截
        registry.addInterceptor(crossInterceptor).order(order++).addPathPatterns("/**/*");
        super.addInterceptors(registry);
    }

}