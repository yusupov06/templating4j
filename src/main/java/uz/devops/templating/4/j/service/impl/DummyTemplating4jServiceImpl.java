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
        havingValue = "true",
        matchIfMissing = true
)
public class DummyTemplating4jServiceImpl implements Templating4jService {

    public DummyTemplating4jServiceImpl() {
        log.debug("############### Templating4j simulation is ON ###############");
    }

//    @Override
//    public String send(String request) {
//        log.debug("Dummy request : {}", request);
//        return "Response for : " + request;
//    }

}
