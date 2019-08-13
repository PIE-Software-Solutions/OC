package com.pss.oneservice.common.integration.config;

import static com.pss.oneservice.common.integration.util.CommonConstants.ALLOWED_SERVICE_PATHS;
import static com.pss.oneservice.common.integration.util.CommonConstants.SEC_REQ;
import static com.pss.oneservice.common.integration.util.CommonConstants.YES;

import org.springframework.beans.factory.annotation.Autowired;
import static org.apache.commons.lang3.StringUtils.isBlank;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pss.oneservice.common.integration.util.AppLogger;

/**
 * Common class to configure the authentication security for the users accessing
 * the service
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author Kiran
 */
@Profile("nodbsecurity")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class CommonSecurityNoDbConfig extends WebSecurityConfigurerAdapter {

	private final AppLogger LOGGER = new AppLogger(CommonSecurityNoDbConfig.class.getName());
	/**
	 * Spring Actuator paths
	 */
	private static final String[] SPRING_ACTUATOR_PATHS = new String[] { 	"/actuator/health", 
																			"/actuator/metrics", 
																			"/actuator/monitoring",
																			"/swagger-ui.html", "/webjars/**",
																			"/swagger-resources/**", 
																			"/v2/api-docs"};


	/**
	 * To configure authentication based on user level access
	 * 
	 * @param authBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder
        .inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
	}

	@Override
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		final String METHOD_NAME = "configure";
		if (!isBlank(SEC_REQ) && SEC_REQ.equals(YES)) {
			LOGGER.info(METHOD_NAME, "Checking Security : ");
			httpSecurity.authorizeRequests().antMatchers(SPRING_ACTUATOR_PATHS).permitAll().and().authorizeRequests()
					.antMatchers(ALLOWED_SERVICE_PATHS).authenticated().anyRequest().access("hasRole('ROLE_USER')")
					.and().authorizeRequests().antMatchers("/shutdown").authenticated().anyRequest()
					.access("hasRole('ROLE_ADMIN')");
			httpSecurity.csrf().disable();
			httpSecurity.httpBasic();
		} else {
			httpSecurity.authorizeRequests().antMatchers(SPRING_ACTUATOR_PATHS).permitAll().and().authorizeRequests()
					.antMatchers(ALLOWED_SERVICE_PATHS).permitAll().and().authorizeRequests().antMatchers("/shutdown")
					.authenticated().anyRequest().access("hasRole('ROLE_ADMIN')");
			httpSecurity.csrf().disable();
			httpSecurity.httpBasic();
		}
	}

	/**
	 * Method to get the password bcrypt encoder
	 * 
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
