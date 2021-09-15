package com.example.demo.model;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "documentation.swagger.services")
@Component
public class Resource implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
    private String url;
    private String version;
}
