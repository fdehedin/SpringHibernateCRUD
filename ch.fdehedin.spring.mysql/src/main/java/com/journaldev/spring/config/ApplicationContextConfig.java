package com.journaldev.spring.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.journaldev.spring.dao.PersonDAO;
import com.journaldev.spring.dao.PersonDAOImpl;

@Configuration
@ComponentScan("com.journaldev.spring")
@EnableTransactionManagement
public class ApplicationContextConfig {

	private static final String driverClassName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://192.168.80.2:3306/Appointments";
	private static final String dbUsername = "fdn";
	private static final String dbPassword = "fdn+2016";

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		System.out.println("inside view resolver");
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean(name = "dataSource")
	public static BasicDataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(dbUsername);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		/*
		 * properties.put("hibernate.c3p0.min_size", "5");
		 * properties.put("hibernate.c3p0.max_size", "20");
		 * properties.put("hibernate.c3p0.timeout", "300");
		 * properties.put("hibernate.c3p0.max_statements", "50");
		 * properties.put("hibernate.c3p0.idle_test_period", "3000");
		 */
		return properties;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(final DataSource dataSource) {

		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(dataSource);
		lsfb.setPackagesToScan("com.journaldev.spring.model");
		lsfb.setHibernateProperties(this.getHibernateProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(final SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	@Autowired
	@Bean(name = "personDAO")
	public PersonDAO getPersonDao(final SessionFactory sessionFactory) {
		return new PersonDAOImpl(sessionFactory);
	}

}
