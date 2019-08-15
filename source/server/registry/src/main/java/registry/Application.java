package registry;

import dataBase.DataBase;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

@SpringBootApplication
public class Application {
    private static final String CONFIG_FILE_NAME = "config.properties";

    public static void main(String[] args) throws Exception {
        initProperties();
//        SpringApplication.run(Application.class, args);
        DataBase dataBase = new DataBase();
        Connection connection = DataBase.getConnection();
        System.out.println(dataBase.getJsonFromSQL(connection, "SELECT  * from PERSON"));
    }

    private static void initProperties() throws Exception {
        Properties prop = new Properties();
        InputStream readFile = Application.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
        prop.load(readFile);
        for (String propName : prop.stringPropertyNames()) {
            System.setProperty(propName, prop.getProperty(propName));
        }
    }
}