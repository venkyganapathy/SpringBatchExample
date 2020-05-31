package com.profinch.fincluez.casaTransformation.batch.step1;

import com.profinch.fincluez.casaTransformation.staging.StagingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CasaQueuePopulatorPagingReaderConfig {
    private Logger log = LoggerFactory.getLogger(CasaQueuePopulatorPagingReaderConfig.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    @StepScope
    public ItemReader<? extends StagingAccount> casaQueuePopulatorPagingReader
            (@Value("#{jobParameters['entityCode']}")String entityCode,
             @Value("#{jobParameters['branchCode']}")String branchCode,
             @Value("#{jobParameters['elRunDate']}") Date elRunDate) {
        log.debug("*************** STEP-1P ************** Configuration");
        log.debug("inside....casaQueuePopulatorPagingReader -- Instantiation with EntityCode {}",entityCode);
        log.debug("inside....casaQueuePopulatorPagingReader -- Instantiation with branchCode {}",branchCode);
        log.debug("inside....casaQueuePopulatorPagingReader -- Instantiation with elRunDate {}",elRunDate);

        JdbcPagingItemReader<StagingAccount> reader = new JdbcPagingItemReader<StagingAccount>();

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
        sqlPagingQueryProviderFactoryBean.setFromClause("from staging_account");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where entity_code = :entityCode and branch_code = :branchCode and el_run_date = :elRunDate");
        sqlPagingQueryProviderFactoryBean.setSortKey("customer_account_number");

        Map<String,Object> parameterValues = new HashMap<>();
        parameterValues.put("entityCode",entityCode);
        parameterValues.put("branchCode",branchCode);
        parameterValues.put("elRunDate",elRunDate);

        try {
            reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            reader.setParameterValues(parameterValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
        reader.setDataSource(dataSource);
        reader.setPageSize(5);
        reader.setRowMapper(new BeanPropertyRowMapper<StagingAccount>(StagingAccount.class));
        return reader;
    }
}
