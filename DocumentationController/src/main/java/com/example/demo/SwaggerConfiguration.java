package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.example.demo.model.Resource;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "documentation.swagger")
@EnableSwagger2
public class SwaggerConfiguration {

	@Autowired
	List<Resource> resources;
	
	public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
    
    @Bean
	public Docket productAPi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaInfo()).select()
				.apis(Predicates.not(RequestHandlerSelectors.basePackage("com.example.demo.swagger.controller")))
						.build();
	}

	private ApiInfo metaInfo() {

		Contact contact = new Contact("Srinivas.Sankoji", "https://youtube.com", "Srinivas.Sankoji@ril.com");
		return new ApiInfoBuilder().title("Micro Service Documentation").description("Documentation").version("1.0")
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").contact(contact)
				.build();
	}
}
