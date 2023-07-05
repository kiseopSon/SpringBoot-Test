package com.jpa;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan(value = "com.jpa", sqlSessionFactoryRef = "SqlSessionFactory")
@Configuration
public class config {
	@Value("${mybatis.mapper-locations}")
	String path;
	
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public javax.sql.DataSource DataSource() {
        return DataSourceBuilder.create().build();
    } 
	
	@Bean(name = "SqlSessionFactory")
    public org.apache.ibatis.session.SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") javax.sql.DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(path));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "SessionTemplate")
    public org.mybatis.spring.SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") org.apache.ibatis.session.SqlSessionFactory firstSqlSessionFactory) {
        return new org.mybatis.spring.SqlSessionTemplate(firstSqlSessionFactory);
    }
}
