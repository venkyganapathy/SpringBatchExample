package com.profinch.fincluez.casaTransformation.batch.step2;

import com.profinch.fincluez.casaTransformation.mart.CasaTransformationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CasaTransformationPagingReaderConfig {
    private Logger log = LoggerFactory.getLogger(CasaTransformationPagingReaderConfig.class);

    @Autowired
    @Qualifier("martDataSource")
    private DataSource martDataSource;

    @Bean
    @StepScope
    public ItemReader<? extends CasaTransformationQueue> casaTransformationPagingReader
            (@Value("#{jobParameters['entityCode']}")String entityCode,
            @Value("#{jobParameters['branchCode']}")String branchCode,
            @Value("#{jobParameters['elRunDate']}") Date elRunDate) {
        log.debug("*************** STEP-2P ************** Configuration");
        log.debug("inside....casaTransformationCursorReaderConfig -- Instantiation");

        JdbcPagingItemReader<CasaTransformationQueue> reader = new JdbcPagingItemReader<CasaTransformationQueue>();

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(martDataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
        sqlPagingQueryProviderFactoryBean.setFromClause("from casa_transformation_queue");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where entity_code = :entityCode" +
                " and branch_code = :branchCode" +
                " and el_run_date = :elRunDate" +
                " and transformation_process_status = \'UNPROCESSED\'");
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
        reader.setDataSource(martDataSource);
        reader.setPageSize(5);
        reader.setRowMapper(new BeanPropertyRowMapper<CasaTransformationQueue>(CasaTransformationQueue.class));
        return reader;
    }
}
