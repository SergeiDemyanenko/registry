package registry;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PropConfig {
    static private List<String> propertyFilesList;
    static private List<Map> listProperties;
    static private final String folder = "models/";

    private static void setPropertyFilesList() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        String path = url.getPath();
        propertyFilesList = Arrays.asList(new File(path).list());
    }

    public static List<String> getPropertyFilesList() {
        List<String> propFiles = new ArrayList<>();
        for(String str: propertyFilesList) {
            propFiles.add(str.split("\\.")[0]);
        }
        return propFiles;
    }

    static List<Map<String, String>> getListProperties() throws IOException {
        setPropertyFilesList();
        List<Map<String, String>> listProperties = new ArrayList<>();
        for(String file : propertyFilesList) {
            Properties properties = new Properties();
            properties.load(ApplicationTest.class.getClassLoader().getResourceAsStream(folder + file));
            Map<String, String> param = new HashMap<>();
            for (String key : properties.stringPropertyNames()) {
                param.put(key, properties.getProperty(key));
            }
            listProperties.add(param);
        }
        return listProperties;
    }
}
