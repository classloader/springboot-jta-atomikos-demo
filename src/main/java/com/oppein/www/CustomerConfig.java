package com.oppein.www;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.oppein.www.repository.customer.CustomerDatasourceProperties;
//import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.oppein.www.repository.customer", entityManagerFactoryRef = "customerEntityManager", transactionManagerRef = "transactionManager")
@EnableConfigurationProperties(CustomerDatasourceProperties.class)
public class CustomerConfig {

	@Autowired
	private JpaVendorAdapter jpaVendorAdapter;

	@Autowired
	private CustomerDatasourceProperties customerDatasourceProperties;

	@Primary
	@Bean(name = "customerDataSource", initMethod = "init", destroyMethod = "close")
//	@Qualifier("customerDataSource")
	public DataSource customerDataSource() throws SQLException {
//		MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
		MariaDbDataSource mysqlXaDataSource = new MariaDbDataSource();
		mysqlXaDataSource.setUrl(customerDatasourceProperties.getUrl());
//		mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
		mysqlXaDataSource.setPassword(customerDatasourceProperties.getPassword());
		mysqlXaDataSource.setUser(customerDatasourceProperties.getUsername());
		AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("xads1");
		xaDataSource.setBorrowConnectionTimeout(60);
		xaDataSource.setMaxPoolSize(20);
		return xaDataSource;

	}

	@Primary
	@Bean(name = "customerEntityManager")
	@DependsOn("transactionManager")
	public LocalContainerEntityManagerFactoryBean customerEntityManager() throws Throwable {

		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
		properties.put("javax.persistence.transactionType", "JTA");

		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setJtaDataSource(customerDataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter);
		entityManager.setPackagesToScan("com.oppein.www.domain.customer");
		entityManager.setPersistenceUnitName("customerPersistenceUnit");
		entityManager.setJpaPropertyMap(properties);
		return entityManager;
	}

}
