package com.websystique.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.websystique.api.model.User;

@Configuration
public class WebMystiqueRestMvcConfiguration extends RepositoryRestConfigurerAdapter{
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    	config.setBasePath("/api");
    	config.exposeIdsFor(User.class);
	}
}
