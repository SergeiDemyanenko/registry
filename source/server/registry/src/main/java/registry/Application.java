package registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Application {
    private static final String CONFIG_FILE_NAME = "config.properties";

    public static void main(String[] args) throws Exception {
        initProperties();
        SpringApplication.run(Application.class, args);
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