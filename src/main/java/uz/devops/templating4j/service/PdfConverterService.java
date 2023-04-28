package uz.devops.templating4j.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.dto.ConvertingRequest;

import java.util.List;
import java.util.Map;

public interface PdfConverterService {

    PdfFile convertWithTemplateAndForm(String filePath, Map<String, Object> form);

    List<PdfFile> convertWithTemplateAndForms(String filePath, List<Map<String, Object>> forms);

    ResponseEntity<Resource> convertWithTemplateAndForm(ConvertingRequest request);
}
