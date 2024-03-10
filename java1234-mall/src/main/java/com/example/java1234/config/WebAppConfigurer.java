package com.example.java1234.config;

import com.example.java1234.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//web项目配置类
@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\swiper\\");
        registry.addResourceHandler("/image/bigType/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\bigTypeImgs\\");
        registry.addResourceHandler("/image/product/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\productImgs\\");
        registry.addResourceHandler("/image/productSwiperImgs/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\productSwiperImgs\\");
        registry.addResourceHandler("/image/productIntroImgs/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\productIntroImgs\\");
        registry.addResourceHandler("/image/productParaImgs/**").addResourceLocations("file:C:\\Users\\zzr\\Desktop\\images\\java1234\\productParaImgs\\");

    }

    @Bean
    public SysInterceptor sysInterceptor() {
        return new SysInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        String[] patterns=new String[]{
                "/adminLogin","/product/**","/bigType/**","/user/wxlogin","/weixinpay/**"
        };
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
