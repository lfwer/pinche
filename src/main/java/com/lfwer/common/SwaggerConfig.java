package com.lfwer.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan(basePackages = { "com.lfwer.controller" })
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	private SpringSwaggerConfig springSwaggerConfig;

	@Autowired
	public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
		if (null == springSwaggerConfig) {
			springSwaggerConfig = new SpringSwaggerConfig();
		}
		this.springSwaggerConfig = springSwaggerConfig;
	}

	@Bean
	public SwaggerSpringMvcPlugin customImplementation() {
		return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(apiInfo()).includePatterns(".*")
				.useDefaultResponseMessages(false)
				.apiVersion("1.0").swaggerGroup("user");
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("邯郸拼车", "My Apps API Description", "My Apps API terms of service",
				"My Apps API Contact Email", "My Apps API Licence Type", "My Apps API License URL");
		return apiInfo;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	 
}