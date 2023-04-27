package uz.devops.templating.4.j.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "templating-4-j")
public class Templating4jProperties {

}
