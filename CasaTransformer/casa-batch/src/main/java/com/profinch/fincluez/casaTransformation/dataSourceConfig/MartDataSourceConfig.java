/*-----------------------------------------------------------------------------------------
This source is part of the Profinch Software Product. 
Copyright © 2018. All rights reserved. 

No part of this work may be reproduced, stored in a retrieval system, 
adopted or transmitted in any form or by any means, electronic, mechanical, 
photographic, graphic, optic recording or otherwise, translated in any language 
or computer language, without the prior written permission of Profinch Solutions Pvt Ltd. 

Profinch Solutions Pvt Ltd.
Wings of Eagles, SS Commercial Estate,
C V Raman Nagar, Bengaluru, Karnataka 560093

Modification History:
====================
Date		Version		Author			Description
----------	-----------	--------------- --------------------------------------------------------
11-08-2018	1.0			Naveen M D			Initial Version
------------------------------------------------------------------------------------------------*/
package com.profinch.fincluez.casaTransformation.dataSourceConfig;


import com.profinch.fincluez.casaTransformation.config.YamlPropertyLoaderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EntityScan({"com.profinch.fincluez.casaTransformation.mart"})
@EnableJpaRepositories(basePackages = "com.profinch.fincluez.casaTransformation.repoMart",
        entityManagerFactoryRef = "martEntityManagerFactory",
        transactionManagerRef = "transactionManager")
@PropertySource(
		value = "file:${PROPERTY_PATH}", 
		factory = YamlPropertyLoaderFactory.class)
 public class MartDataSourceConfig {
 
	
	@Value("${fincluez.mart.datasource.url}")
	public String dataSourceUrl;
	
	@Value("${fincluez.mart.datasource.username}")
	public String dataSourceUsername;
	
	@Value("${fincluez.mart.datasource.password}")
	public String dataSourcePassword;
	
	@Value("${fincluez.mart.datasource.driver}")
	public String dataSourceDriver;
	 
    @Bean(name="martDataSource")
    @Primary
    public DataSource martDataSource() {
        DriverManagerDataSource martDataSource = new DriverManagerDataSource();
        martDataSource.setDriverClassName(dataSourceDriver);
        martDataSource.setUrl(dataSourceUrl);
        martDataSource.setUsername(dataSourceUsername);
        martDataSource.setPassword(dataSourcePassword);
        return martDataSource;
    }

    @Bean(name = "martEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean martEntityManagerFactory
            (EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        //properties.put("hibernate.hbm2ddl.auto", "update");
        //properties.put("hibernate.implicit_naming_strategy","org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl");
        //properties.put("hibernate.physical_naming_strategy","org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        properties.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        properties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        return builder
                .dataSource(martDataSource())
                .properties(properties)
                .packages("com.profinch.fincluez.casaTransformation.mart")
                .build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            final @Qualifier("martEntityManagerFactory") LocalContainerEntityManagerFactoryBean martEntityManagerFactory) {
        return new JpaTransactionManager(martEntityManagerFactory.getObject());
    }
}