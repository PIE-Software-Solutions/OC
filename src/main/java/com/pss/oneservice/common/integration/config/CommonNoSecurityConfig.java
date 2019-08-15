package com.pss.oneservice.common.integration.config;

import static com.pss.oneservice.common.integration.util.CommonConstants.ALLOWED_SERVICE_PATHS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.pss.oneservice.common.integration.util.AppLogger;
import com.pss.oneservice.common.integration.util.NoDataBaseCondition;

/**
 * Common class to configure the authentication security for the users accessing
 * the service
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author Kiran
 */
@Conditional(NoDataBaseCondition.class)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Configuration
public class CommonNoSecurityConfig extends WebSecurityConfigurerAdapter {

	private final AppLogger LOGGER = new AppLogger(CommonNoSecurityConfig.class.getName());
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
		//
	}

	@Override
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers(SPRING_ACTUATOR_PATHS).permitAll().and().authorizeRequests()
				.antMatchers(ALLOWED_SERVICE_PATHS).permitAll().and().authorizeRequests().antMatchers("/shutdown")
				.authenticated().anyRequest().access("hasRole('ROLE_ADMIN')");
		httpSecurity.csrf().disable();
		httpSecurity.httpBasic();
	}
	
}
