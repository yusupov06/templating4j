package uz.devops.templating4j.util;


import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class TestUtils {

    @Autowired
    private ConverterUtils converterUtils;

    public String getPdfFileContent(PdfFile pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile.getPath());
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            sb.append(PdfTextExtractor.getTextFromPage(reader, i));
        }
        return sb.toString();
    }

    public void checkForConverterWithForm(File file,
                                          Map<String, Object> map,
                                          Converter converter,
                                          String fileName) throws Exception {

        PdfFile parsed = converter.convert(file, map);

        String content = getPdfFileContent(parsed);

        Assertions.assertNotNull(content);
        checkForFormCorrectlySet(content, map);

        Assertions.assertNotNull(parsed);
//        Assertions.assertEquals(parsed.getPath(), converterUtils.getFileRootPath() + fileName + ".pdf");
    }

    private void checkForFormCorrectlySet(String content, Map<String, Object> map) {
        map.values().forEach(val -> Assertions.assertTrue(content.contains((String)val)));
    }
}
