package uz.devops.templating.4.j.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
import uz.devops.templating.4.j.config.Templating4jProperties;
import uz.devops.templating.4.j.service.Templating4jService;
import lombok.extern.slf4j.Slf4j;

import static uz.devops.templating.4.j.service.Templating4jService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(
        prefix = "templating-4-j",
        name = "simulate",
        havingValue = "false"
)
public class Templating4jServiceImpl implements Templating4jService {

    private final Templating4jProperties templating4JProperties;
//    private final RestTemplate templating4JRestTemplate;

    @Autowired
    public Templating4jServiceImpl(Templating4jProperties templating4JProperties/*, @Qualifier("templating4JRestTemplate") RestTemplate templating4JRestTemplate*/) {
        this.templating4JProperties = templating4JProperties;
//        this.templating4JRestTemplate = templating4JRestTemplate;
    }

//    @Override
//    public String send(String request) {
//        log.debug("Request : {}, name : {}", request, templating4JProperties.getName());
//        return "Response for : " + request;
//    }

}
