package uz.devops.templating4j.util;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

    public String getFileExtension(String templatePath) {
        return templatePath.substring(templatePath.lastIndexOf(".")+1);
    }

}
