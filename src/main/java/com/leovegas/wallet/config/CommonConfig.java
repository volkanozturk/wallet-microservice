package com.leovegas.wallet.config;

import com.leovegas.wallet.mapper.CustomObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author volkanozturk
 */
@Configuration
public class CommonConfig {

	@Bean(name = "customObjectMapper")
	public CustomObjectMapper customObjectMapper() {
		return new CustomObjectMapper();
	}
}
