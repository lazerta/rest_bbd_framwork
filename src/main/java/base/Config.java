package base;

import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@Data
public class Config {
    private String baseURl = "";
    private String content_type = "application/json";
    private static Config config;
    public static final String resourcePath = System.getProperty("user.dir")
            + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    private String data_path = resourcePath + File.separator;
    private String base_path="";

    public static Config getInstance() {
        if (config == null) {
            load();
            if (config == null) {
                throw new IllegalStateException("failed to load application.yaml in test resources");
            }
        }
        return config;
    }

    private synchronized static void load() {
        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream(resourcePath + "application.yaml")) {
            config = yaml.loadAs(inputStream, Config.class);
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
