package com.kj.oneservice.common.integration.config;

import static com.kj.oneservice.common.integration.util.CommonConstants.ALLOWED_SERVICE_PATHS;
import static com.kj.oneservice.common.integration.util.CommonConstants.ONESERVICE_DATA_SOURCE;
import static com.kj.oneservice.common.integration.util.CommonConstants.SERVICE_NAME;
import static com.kj.oneservice.common.integration.util.CommonConstants.YES;
import static com.kj.oneservice.common.integration.util.SQLQueryConstants.GET_USERS;
import static com.kj.oneservice.common.integration.util.SQLQueryConstants.GET_USER_ROLES;
import static org.apache.commons.lang.StringUtils.isBlank;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kj.oneservice.common.integration.util.AppLogger;

/**
 * Common class to configure the authentication security for the users accessing
 * the service
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author Kiran
 */
@Configuration
public class CommonSecurityConfig extends WebSecurityConfigurerAdapter {

	private final AppLogger LOGGER = new AppLogger(CommonSecurityConfig.class.getName());
	/**
	 * Spring Actuator paths
	 */
	private static final String[] SPRING_ACTUATOR_PATHS = new String[] { "/health", "/metrics", "/monitoring",
			"/" + SERVICE_NAME + "/swagger-ui.html", "/" + SERVICE_NAME + "/webjars/**",
			"/" + SERVICE_NAME + "/swagger-resources/**", "/" + SERVICE_NAME + "/v2/api-docs" };

	@Autowired
	@Qualifier(ONESERVICE_DATA_SOURCE)
	private DataSource oneserviceDataSource;

	/**
	 * To configure authentication based on user level access
	 * 
	 * @param authBuilder
	 * @throws Exception
	 */
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		final String METHOD_NAME = "configAuthentication";
		authBuilder.jdbcAuthentication().dataSource(oneserviceDataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery(GET_USERS).authoritiesByUsernameQuery(GET_USER_ROLES);
	}

	@Override
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		final String METHOD_NAME = "configure";

		httpSecurity.authorizeRequests().antMatchers(SPRING_ACTUATOR_PATHS).permitAll().and().authorizeRequests()
				.antMatchers(ALLOWED_SERVICE_PATHS).authenticated().anyRequest().access("hasRole('ROLE_USER')")
				.and().authorizeRequests().antMatchers("/shutdown").authenticated().anyRequest()
				.access("hasRole('ROLE_ADMIN')");
		httpSecurity.csrf().disable();
		httpSecurity.httpBasic();
		
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
