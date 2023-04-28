package uz.devops.templating4j.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.templating4j.Dummy;
import uz.devops.templating4j.config.TestConfig;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.util.TestUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@SpringBootTest(classes = {TestConfig.class})
@Slf4j
@ActiveProfiles("testdev")
@Transactional
public class WordConverterTest {

    // region Beans
    @Autowired
    @Qualifier("WORD_CONVERTER")
    private Converter converter;

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private ConverterUtils converterUtils;

    // endregion

    @Value("${app.pdf-file.root}")
    private String pdfFileRootPath;

    @RepeatedTest(10)
    void test() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test1.docx");
        Dummy.Student student = new Dummy.Student();

        student.setName("Muhammadqodir");
        student.setSurname("Yusupov");

        Map<String, Object> map = Map.of(
            "student", student,
            "phone", "+998931668648"
        );

        PdfFile parsed = converter.convert(file, map);
    }

//    @AfterEach
    void tearDown() {

        File directory = new File(pdfFileRootPath);

        File[] files = directory.listFiles();

        if (files != null) {
            Arrays.stream(files)
                .filter(File::isFile)
                .forEach(File::delete);
        }
    }

    @Test
    void shouldWork() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test1.docx");

        Dummy.Student student = new Dummy.Student();
        student.setName("Muhammadqodir");
        student.setSurname("Yusupov");
        student.setSubject(new Dummy.Subject("Math", 100));

        Map<String, Object> map = Map.of(
            "student", student,
            "phone", "+998931668648",
            "mapper", new ObjectMapper()
        );


        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains("Muhammadqodir"));
        Assertions.assertTrue(content.contains("Yusupov"));
        Assertions.assertTrue(content.contains("Math"));
        Assertions.assertTrue(content.contains("+998931668648"));
        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "w_test1.pdf");
    }

    @Test
    void shouldNotChangeIfNotExistedInTheForm() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test2.docx");

        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "day", "10 April"
        );


        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        log.info("PdfFile content: " + content);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains((String) map.get("name")));
        Assertions.assertTrue(content.contains((String) map.get("day")));
        Assertions.assertTrue(content.contains("phone"));
        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "w_test2.pdf");

    }

    @Test
    void shouldWorkWithExpression() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test3.docx");

        Map<String, Object> map = Map.of(
            "name", "Muhammadqodir",
            "day", "10 April",
            "phone", "+998931668648"
        );


        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);
        log.info("PdfFile content: " + content);


        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains((String) map.get("name")));
        Assertions.assertTrue(content.contains((String) map.get("day")));
        Assertions.assertTrue(content.contains((String) map.get("phone")));

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "w_test3.pdf");

    }

    @Test
    void shouldWorkWithInvalidExpression() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test4.docx");

        Map<String, Object> map = Map.of(
            "phone", "+998931668648"
        );


        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);
        log.info("PdfFile content: " + content);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains("name"));
        Assertions.assertTrue(content.contains("day"));
        Assertions.assertTrue(content.contains((String) map.get("phone")));

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "w_test4.pdf");

    }

    @Test
    void shouldWorkWithNested() throws Exception {

        File file = new File("src/test/resources/sample/word/w_test5.docx");

        Dummy.Student student = new Dummy.Student();
        student.setName("Muhammadqodir");
        student.setSurname("Yusupov");
        student.setPhone("+998931668649");


        Map<String, Object> map = Map.of(
            "student", student,
            "phone", "+998931668648",
            "mapper", new ObjectMapper()
        );


        PdfFile parsed = converter.convert(file, map);

        String content = testUtils.getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        Assertions.assertTrue(content.contains("Muhammadqodir"));
        Assertions.assertTrue(content.contains("Yusupov"));
        Assertions.assertTrue(content.contains("+998931668649"));
        Assertions.assertFalse(content.contains("+998931668648"));
        Assertions.assertTrue(content.contains("$phone"));
        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + "w_test1.pdf");
    }

}
