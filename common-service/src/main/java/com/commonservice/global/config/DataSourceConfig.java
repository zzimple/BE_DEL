package com.commonservice.global.config;


import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

  // application.yml 의 spring.datasource.* 를 읽어오는 빈
  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  public DataSourceProperties primaryDataSourceProperties() {
    return new DataSourceProperties();
  }

  // 위 프로퍼티로 DataSource 생성
  @Bean
  public DataSource dataSource(DataSourceProperties props) {
    return props
        .initializeDataSourceBuilder()
        .build();
  }
}