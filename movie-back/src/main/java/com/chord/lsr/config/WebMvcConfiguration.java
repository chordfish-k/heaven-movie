package com.chord.lsr.config;

import com.chord.lsr.interceptor.JwtTokenInterceptor;
import com.chord.lsr.json.JacksonObjectMapper;
import com.chord.lsr.properties.MediaProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private MediaProperties mediaProperties;
    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 跨域处理
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //允许跨域访问的路径
                .allowedOriginPatterns("*") //允许访问的源
                .allowedMethods("POST","GET","PUT","DELETE") //允许的请求方式
                .maxAge(16800) //允许的检测间隔时间
                .allowedHeaders("*") //允许的请求头
                .allowCredentials(true); //允许发送cookie
    }

    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/user/user/privilege/**")
                .addPathPatterns("/user/history")
                .addPathPatterns("/user/video/getUrl/**")
                .addPathPatterns("/user/like/**")
                .addPathPatterns("/user/movie/view/**");
    }


    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 接口文档地址: localhost:8080/doc.html
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + mediaProperties.imagePath);
        registry.addResourceHandler("/v/**")
                .addResourceLocations("file:" + mediaProperties.videoPath);
    }

    /**
     * 扩展Spring MVC框架的消息转化器
     * @param converters
     */
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自己的消息转换器加入容器中，排在首位
        converters.add(0, converter);
    }
}
