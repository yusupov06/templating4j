package uz.devops.templating4j.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uz.devops.templating4j.config.TestConfig;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.util.TestUtils;

import java.io.File;
import java.util.Map;

@SpringBootTest(classes = {TestConfig.class})
@ActiveProfiles("testdev")
public class HtmlConverterTest {

    // region Beans
    @Autowired
    @Qualifier("HTML_CONVERTER")
    private Converter converter;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private ConverterUtils converterUtils;
    // endregion

    @Test
    void shouldWork() throws Exception {
        File file = new File("src/test/resources/sample/html/html_test1.html");

        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "surname", "Yusupov",
            "age", "30",
            "work", "Devops"
        );

        testUtils.checkForConverterWithForm(file, map, converter, "html_test1");
    }

    @Test
    void shouldNotChangeIfNotExistedInTheForm() throws Exception {

        File file = new File("src/test/resources/sample/html/html_test2.html");

        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "surname", "Yusupov",
            "age", "30"
        );

        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains((String) map.get("name")));
        Assertions.assertTrue(content.contains((String) map.get("surname")));
        Assertions.assertTrue(content.contains((String) map.get("age")));
        Assertions.assertTrue(content.contains("work"));

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "html_test2.pdf");

    }

    @Test
    void shouldWorkWithExpression() throws Exception {

        File file = new File("src/test/resources/sample/html/html_test3.html");

        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "surname", "Yusupov",
            "age", "25"
        );

        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains((String) map.get("name")));
        Assertions.assertTrue(content.contains((String) map.get("surname")));
        Assertions.assertTrue(content.contains((String) map.get("age")));

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "html_test3.pdf");

    }

    @Test
    void shouldWorkWithInvalidExpression() throws Exception {

        File file = new File("src/test/resources/sample/html/html_test4.html");
        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "surname", "Yusupov",
            "age", "30",
            "work", "Devops"
        );

        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        Assertions.assertFalse(content.contains((String) map.get("name")));
        Assertions.assertTrue(content.contains("name}"));
        Assertions.assertFalse(content.contains((String) map.get("surname")));
        Assertions.assertTrue(content.contains("{surname"));
        Assertions.assertTrue(content.contains((String) map.get("age")));
        Assertions.assertTrue(content.contains("now( )"));

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "html_test4.pdf");

    }


}
