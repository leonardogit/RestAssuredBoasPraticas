package utils;

import java.io.InputStream;
import java.util.Properties;

public class TestBase {

    public Properties LoadProperties() {
        try {
            InputStream inStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            Properties prop = new Properties();
            prop.load(inStream);
            return prop;
        }catch (Exception e){
            System.out.println("Arquivo não encontrado.....");
            return null;
        }
    }

}
