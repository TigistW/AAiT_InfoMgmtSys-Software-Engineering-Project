package com.aait.aaitims.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/school").setViewName("school");

        registry.addViewController("/site").setViewName("SiteDetails");
        registry.addViewController("/chemical").setViewName("sccedetails");
        registry.addViewController("/mechanical").setViewName("sbmeDetials");
        registry.addViewController("/civil").setViewName("scedetails");
        registry.addViewController("/biomedical").setViewName("smedetails");
        registry.addViewController("/electrical").setViewName("seceDetails");
        registry.addViewController("/profile").setViewName("profile");
        // registry.addViewController("/").setViewName("profile");
        registry.addViewController("/archiveViewUser").setViewName("archiveView");
        registry.addViewController("/archiveView2").setViewName("archiveView2");
        registry.addViewController("/login");
    }

}
