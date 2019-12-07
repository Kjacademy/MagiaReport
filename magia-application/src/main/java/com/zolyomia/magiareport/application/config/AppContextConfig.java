package com.zolyomia.magiareport.application.config;

import java.util.HashMap;
import java.util.Map;

import com.zolyomia.magiareport.application.scope.ScreenScope;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.zolyomia.magiareport.*")
public class AppContextConfig {

    @Bean
    public CustomScopeConfigurer getCustomScopeConfigurer(ScreenScope screenScope){
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        final Map<String, Object> scopeMap = new HashMap<>();
        scopeMap.put("screen", screenScope);
        configurer.setScopes(scopeMap);
        return configurer;
    }

    @Bean
    public ScreenScope screenScope(){
        return new ScreenScope();
    }
}
