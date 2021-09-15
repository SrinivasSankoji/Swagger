package com.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket productAPi() {
		return new Docket(DocumentationType.SWAGGER_2)
				//.ignoredParameterTypes(User.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.swagger.techprimers.controller"))
				.paths(Predicates.not(PathSelectors.regex("/error.*"))).build()
				.groupName("MicroService")
				.apiInfo(metaInfo());
	}

	/**
	 * private Predicate<String> postPaths() { return
	 * or(PathSelectors.regex("/api/posts.*"),
	 * PathSelectors.regex("/api/javainuse.*")); }
	 **/

	private ApiInfo metaInfo() {

		Contact contact = new Contact("Srinivas.Sankoji", "https://youtube.com", "Srinivas.Sankoji@ril.com");
		return new ApiInfoBuilder().title("Rest API Documentation").description("Service Dependency").version("1.0")
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").contact(contact)
				.build();

		/**
		 * return new ApiInfo("Rest API Documentation", "Service Dependency", "1.0",
		 * "Terms of Service", new Contact("JAM", "https://www.youtube.com/",
		 * "srinivas.sankoji@ril.com"), "Apache License Version 2.0",
		 * "https://www.apache.org/licesen.html",Arrays.asList());
		 **/

	}

}
