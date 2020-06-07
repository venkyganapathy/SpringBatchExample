package com.profinch.fincluez.casaTransformation.batch.step1;

import com.profinch.fincluez.casaTransformation.staging.StagingAccount;
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
public class CasaQueuePopulatorCursorReaderConfig {
    private Logger log = LoggerFactory.getLogger(CasaQueuePopulatorCursorReaderConfig.class);

    @Autowired
    private DataSource stagingDataSource;

    @Bean
    public ItemReader<? extends StagingAccount> casaQueuePopulatorCursorReader() {
        log.debug("*************** STEP-1C ************** Configuration");
        log.debug("inside....casaQueuePopulatorCursorReader -- Instantiation");
        JdbcCursorItemReader<StagingAccount> reader = new JdbcCursorItemReader<StagingAccount>();
        reader.setDataSource(stagingDataSource);
        reader.setName("CasaTransformationQueuePopulatorReader");
        reader.setSql("select * from staging_account");
        //reader.setSql("select * from staging_account where branch_code=:branch and entity_code=:entityCode and elRunDate=:elRunDate");
        reader.setRowMapper(new BeanPropertyRowMapper<StagingAccount>(StagingAccount.class));
        reader.setFetchSize(5);
        reader.setVerifyCursorPosition(false);
        return reader;
    }
}
