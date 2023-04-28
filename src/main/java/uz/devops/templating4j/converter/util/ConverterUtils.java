package uz.devops.templating4j.converter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.templating4j.domain.PdfFile;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ConverterUtils {

    @Value("${app.pdf-file.root}")
    public String pdfFileRootPath;

    private final ObjectMapper objectMapper;

    public String getFileRootPath() {
        return pdfFileRootPath;
    }

    public File createFile(String fileName) {
        return new File(pdfFileRootPath + fileName + ".pdf");
    }

    public String getFileName(File file) {
        return file.getName()
                .substring(0, file.getName().lastIndexOf('.'));
    }

    public PdfFile create(File outputPdf, String templatePath) {
        return new PdfFile()
                .setPath(outputPdf.getAbsolutePath())
                .setTemplateRootPath(templatePath);
    }

    public String getAsPretty(String output) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
        JsonNode node = mapper.readTree(output);
        return mapper.writeValueAsString(node);
    }

    public Map<String, String> getAsStringMap(Map<String, Object> form) {

        Map<String, String> res = new LinkedHashMap<>();
        Map<String, JsonNode> objectsAsMap = new LinkedHashMap<>();

        form.forEach((key, value) -> {
            try {
                String jsonString = objectMapper.writeValueAsString(value);
                JsonNode jsonNode = objectMapper.readTree(jsonString);
                objectsAsMap.put(key, jsonNode);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        objectsAsMap.forEach((key, value) -> addAsAsMap(res, key, value));

        return res;
    }

    private void addAsAsMap(Map<String, String> res, String key, JsonNode node) {
        Iterator<String> names = node.fieldNames();
        if (!names.hasNext()) {
            res.put(key, node.asText());
        } else {
            while (names.hasNext()) {
                String field = names.next();
                addAsAsMap(res, key + "." + field, node.get(field));
            }
        }
    }
}
