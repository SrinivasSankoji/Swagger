package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SwaggerConfiguration;
import com.example.demo.model.Resource;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Component
@Primary
@EnableAutoConfiguration
@CrossOrigin(maxAge = 6000)
@RestController
public class DocumentationController implements SwaggerResourcesProvider {

	private SwaggerConfiguration swaggerConfiguration;

    @Autowired
    public DocumentationController(SwaggerConfiguration swaggerConfiguration){
        this.swaggerConfiguration = swaggerConfiguration;
    }

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		for (Resource resource : this.swaggerConfiguration.getResources()) {
			resources.add(createSwaggerResource(resource));
		}
		return resources;
	}

	private SwaggerResource createSwaggerResource(Resource resource) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(resource.getName());
		swaggerResource.setUrl(resource.getUrl());
		swaggerResource.setSwaggerVersion(resource.getVersion());
		return swaggerResource;
	}
}
