package uz.devops.templating4j.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;
import uz.devops.templating4j.service.Templating4jService;

import static uz.devops.templating4j.service.Templating4jService.NAME;

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
