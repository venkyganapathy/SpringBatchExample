package com.profinch.fincluez.casaTransformation.batch;

import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class CasaTransformationReaderConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<? extends CasaTransformationQueue> casaTransformationCustomReader() {
        JdbcPagingItemReader<CasaTransformationQueue> reader = new JdbcPagingItemReader<CasaTransformationQueue>();

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("select *");
        sqlPagingQueryProviderFactoryBean.setFromClause("from casa_transformation_queue");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where transformation_process_status = \'UNPROCESSED\'");
        sqlPagingQueryProviderFactoryBean.setSortKey("account_number");

        try {
            reader.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reader.setDataSource(dataSource);
        reader.setPageSize(4);
        reader.setRowMapper(new BeanPropertyRowMapper<CasaTransformationQueue>(CasaTransformationQueue.class));
        return reader;
    }
}
