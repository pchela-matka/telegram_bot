package core.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by astegnienko on 03.04.2017.
 */
public class GetProperties {
    public static String properies (String prop)
    {
        String ourprop = null;
        try {
            Properties properties = new Properties();
            File file = new File("properties.properties");
            properties.load(new FileInputStream(file));
            ourprop = properties.getProperty(prop);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ourprop;
    }
}
