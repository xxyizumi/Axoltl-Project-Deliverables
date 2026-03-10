package com.example.mybatisadvanced.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.example.mybatisadvanced.plugin.AuditInterceptor;
import com.example.mybatisadvanced.plugin.SqlLogPlugin;

@Configuration
public class MyBatisConfig {

    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        System.out.println(factoryBean);
        // インターセプターを登録
        factoryBean.setPlugins(
            new SqlLogPlugin(),      // SQLログ出力
            new AuditInterceptor()   // 監査情報設定
        );
        
        // 他の設定...
        
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return factoryBean.getObject();
    }
}
