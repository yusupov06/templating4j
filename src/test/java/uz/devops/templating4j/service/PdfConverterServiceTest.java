package uz.devops.templating4j.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

@Transactional
public class PdfConverterServiceTest {

    @Autowired
    private PdfConverterService pdfConverterService;

    @Test
    void testPdfConverter() throws Exception {
//        pdfConverterService.convertWithTemplateAndForm();
    }

}
