package com.qhx.back.config;

import com.qhx.back.interceptor.AddressInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 跨域配置
 *
 * @author: qhx20040819
 * @date: 2023-08-30 17:03
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer
{

    @Autowired
    private AddressInterceptor addressInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        // 注册 CORS 配置
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 注意就是要请求方式上，要改成全部
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addressInterceptor).addPathPatterns("/**");
    }

}