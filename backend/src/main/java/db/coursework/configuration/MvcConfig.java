package db.coursework.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/static/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/static/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/static/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/static/index.html");
    }
}
