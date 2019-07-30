package com.kj.oneservice.common.integration.config;

import static com.kj.oneservice.common.integration.util.CommonConstants.ONESERVICE_DATA_SOURCE;
import static com.kj.oneservice.common.integration.util.CommonConstants.ONESERVICE_JDBC_TEMPLATE;

import java.util.Arrays;
import java.util.Base64;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Configuration class for JDBC template
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author Kiran
 */
@Configuration
@PropertySource("file:${app.home}/${app.prop}.properties")
public class DatabaseConfig {

	/**
	 * Method to build the readonly data source with supplied DB details
	 * 
	 * @param passwd
	 * @return DataSource
	 */
	@Bean(name = ONESERVICE_DATA_SOURCE)
	@Primary
	@Profile("readonly")
	@ConfigurationProperties(prefix = "readonly.datasource")
	public DataSource readonlyOneserviceDataSource(
			@Value("${readonly.datasource.passwd:readonly.datasource.passwd}") byte[] passwd) {
		DataSource dataSource = createDataSource(passwd);
		return dataSource;
	}

	/**
	 * Method to build the readwrite data source with supplied DB details
	 * 
	 * @param passwd
	 * @return DataSource
	 */
	@Bean(name = ONESERVICE_DATA_SOURCE)
	@Primary
	@Profile("readwrite")
	@ConfigurationProperties(prefix = "readwrite.datasource")
	public DataSource readwriteOneserviceDataSource(
			@Value("${readwrite.datasource.passwd:readwrite.datasource.passwd}") byte[] passwd) {
		DataSource dataSource = createDataSource(passwd);
		return dataSource;
	}

	/**
	 * Method to build the master data source with supplied DB details
	 * 
	 * @param passwd
	 * @return DataSource
	 */
	@Bean(name = ONESERVICE_DATA_SOURCE)
	@Primary
	@Profile("master")
	@ConfigurationProperties(prefix = "master.datasource")
	public DataSource masterOneserviceDataSource(
			@Value("${master.datasource.passwd:master.datasource.passwd}") byte[] passwd) {
		DataSource dataSource = createDataSource(passwd);
		return dataSource;
	}

	/**
	 * Creates datasource with supplied passwd
	 * 
	 * @param passwd
	 * @return
	 */
	private DataSource createDataSource(byte[] passwd) {
		Base64.Decoder decoder = Base64.getDecoder();
		String passwdStr = new String(decoder.decode(passwd));

		DataSource dataSource = DataSourceBuilder.create().password(passwdStr).build();

		// Nullify and overwrite the password in the memory
		passwdStr = null;
		Arrays.fill(passwd, (byte) 0);

		return dataSource;
	}

	/**
	 * Method to instantiate JdbcTemplate with build dataSource
	 * 
	 * @param oneserviceDataSource
	 * @return JdbcTemplate
	 */
	@Bean(name = ONESERVICE_JDBC_TEMPLATE)
	public JdbcTemplate oneserviceJdbcTemplate(@Qualifier(ONESERVICE_DATA_SOURCE) DataSource oneserviceDataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(oneserviceDataSource);

		return jdbcTemplate;
	}
}