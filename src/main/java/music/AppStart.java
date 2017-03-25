package music;

import music.dao.impl.Mp3Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/*@SpringBootApplication(scanBasePackages = "music")*/
@SpringBootApplication
public class AppStart {

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


        //System.out.println(sqlServerDao.getById(2));
    }
}
