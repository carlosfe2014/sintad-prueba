package net.carlosfe.tests.sintad;

import net.carlosfe.tests.sintad.global.configs.JwtConfig;
import net.carlosfe.tests.sintad.global.configs.SintadConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfig.class, SintadConfig.class})
public class SintadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SintadApplication.class, args);
	}

}
