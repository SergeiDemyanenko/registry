package registry.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;

@Component
public class AutowiredStaticFieldsForHelper {

    @Autowired
    private ApplicationContext context;

    private static final String FILE_EXT = ".class";

    @PostConstruct
    public void init() throws IOException, ClassNotFoundException, IllegalAccessException {
        String packageName = this.getClass().getPackage().getName();
        String packagePath = packageName.replace('.', '/');
        Enumeration<URL> resources = this.getClass().getClassLoader().getResources(packagePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            for (File file : new File(resource.getFile()).listFiles()) {
                if (file.getName().endsWith(FILE_EXT)) {
                    Class<?> clazz = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - FILE_EXT.length()));
                    for (Field field : clazz.getDeclaredFields()) {
                        if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) && field.getAnnotation(Autowired.class) != null) {
                            field.setAccessible(true);
                            field.set(null, context.getBean(field.getType()));
                        }
                    }
                }
            }
        }
    }
}
