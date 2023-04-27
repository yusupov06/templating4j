package uz.devops.templating.4.j.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uz.devops.templating.4.j.config.TestConfig;
import uz.devops.templating.4.j.service.Templating4jService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
public class Templating4jServiceImplTest {

    @Autowired
    private Templating4jService templating4JService;

    @Before
    public void setUp() {
        //Do something on starting
    }

    @After
    public void tearDown() {
        //Do something on finishing
    }

    @Test
    public void testSend() {
//        String response = templating4JService.send("Hi, Javlon");
//        assertEquals("Response for : Hi, Javlon", response);
    }
}
