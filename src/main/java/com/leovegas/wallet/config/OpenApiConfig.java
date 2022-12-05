package com.leovegas.wallet.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author volkanozturk
 */
@Configuration
public class OpenApiConfig {

	/**
	 * Bean declaration for Open API documentation details.
	 *
	 * @return OpenAPI. Please, see the {@link io.swagger.v3.oas.models.OpenAPI} class for details.
	 */
	@Bean
	public OpenAPI openApiDocumentation() {
		Contact contact = new Contact();
		contact.setName("LeoVegas");
		contact.setEmail("info@leovegas.com");
		contact.setUrl("https://www.leovegas.com/");
		return new OpenAPI()
				.info(new Info().title("Swagger UI")
						.description("LeoVegas")
						.version("1.0.0")
						.contact(contact)
						.license(new License().name("Apache 2.0").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation()
						.description("LeoVegas Assessment"));
	}
}

