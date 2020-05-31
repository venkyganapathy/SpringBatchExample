package com.profinch.fincluez.casaTransformation.batch.step2;

import com.profinch.fincluez.casaTransformation.staging.CasaTransformationQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
public class CasaTransformationCursorReaderConfig {

    private Logger log = LoggerFactory.getLogger(CasaTransformationCursorReaderConfig.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    public ItemReader<? extends CasaTransformationQueue> casaTransformationCursorReader() {
        log.debug("*************** STEP-2C ************** Configuration");
        log.debug("inside....casaTransformationCursorReaderConfig -- Instantiation");

        JdbcCursorItemReader<CasaTransformationQueue> reader = new JdbcCursorItemReader<CasaTransformationQueue>();
        reader.setDataSource(dataSource);
        reader.setName("CasaTransformationQueueReader");
        reader.setSql("select * from casa_transformation_queue where transformation_process_status = \'UNPROCESSED\' order by customer_account_number");
        reader.setRowMapper(new BeanPropertyRowMapper<CasaTransformationQueue>(CasaTransformationQueue.class));
        reader.setFetchSize(3);
        reader.setVerifyCursorPosition(false);
        return reader;
    }
}
