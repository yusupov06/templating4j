package uz.devops.templating.4.j.config;



import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(Templating4jProperties.class)
public class Templating4jConfiguration {

//    @Bean("templating4JRestTemplate")
//    public RestTemplate templating4JRestTemplate(RestTemplateBuilder restTemplateBuilder, Templating4jProperties templating4JProperties) {
//        return restTemplateBuilder
//                .setReadTimeout(templating4JProperties.getReadTimeout())
//                .setConnectTimeout(templating4JProperties.getConnectTimeout())
//                .basicAuthorization(templating4JProperties.getLogin(), templating4JProperties.getPassword())
//                .build();
//    }



}
