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
package com.profinch.fincluez.casaTransformation.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(
		value = "file:${PROPERTY_PATH}", 
		factory = YamlPropertyLoaderFactory.class)
 public class DataSourceConfig { 
 
	
	@Value("${fincluez.datasource.url}")
	public String dataSourceUrl;
	
	@Value("${fincluez.datasource.username}")
	public String dataSourceUsername;
	
	@Value("${fincluez.datasource.password}")
	public String dataSourcePassword;
	
	@Value("${fincluez.datasource.driver}")
	public String dataSourceDriver;
	 
    @Bean
    @Primary
    public DataSource dataSource() { 
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        //dataSource.setDriverClassName("org.postgresql.Driver");
        //dataSource.setUrl("jdbc:postgresql://localhost:5432/FinFlowz?searchpath=fincluez_staging");
        //dataSource.setUsername("fincluez_staging");
        //dataSource.setPassword("fincluez_staging");

        dataSource.setDriverClassName(dataSourceDriver);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);


        return dataSource;
    }
}