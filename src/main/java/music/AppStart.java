package music;

import music.dao.impl.Mp3Sql;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/*@SpringBootApplication(scanBasePackages = "music")*/
@Configuration
@SpringBootApplication
public class AppStart {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean
                = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource( this.getDataSource() );
        factoryBean.setPackagesToScan( new String[ ] { "music" } );

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
            {
                setGenerateDdl(false);
                setShowSql(true);
            }
        };

        factoryBean.setJpaVendorAdapter( vendorAdapter );
        factoryBean.setJpaProperties( this.additionlProperties() );

        return factoryBean;
    }

    private Properties additionlProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(this.entityManagerFactoryBean().getObject() );

        return transactionManager;
    }

    @Bean
    public static DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=Music;");
        dataSource.setUsername("sa");
        dataSource.setPassword("123456");

        return dataSource;
    }


    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        //Mp3Sql sqlServerDao = (Mp3Sql) context.getBean("sqlServerDao");
        DataSource ds = getDataSource();

        Mp3Sql sqlServerDao = new Mp3Sql();
        sqlServerDao.setDataSource(ds);

        SpringApplication.run(AppStart.class, args);


        //System.out.println(sqlServerDao.getMp3ById(2));
    }
}
