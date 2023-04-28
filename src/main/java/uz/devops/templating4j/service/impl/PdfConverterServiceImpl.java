package uz.devops.templating4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.factory.ConverterFactory;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.domain.Template;
import uz.devops.templating4j.dto.ConvertingRequest;
import uz.devops.templating4j.repository.TemplateRepository;
import uz.devops.templating4j.service.PdfConverterService;
import uz.devops.templating4j.util.ServiceUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converter service implementation
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PdfConverterServiceImpl implements PdfConverterService {

    private final ConverterFactory converterFactory;
    private final ServiceUtil serviceUtil;
    private final TemplateRepository templateRepository;

    /**
     * Converts the given template with fulling form
     *
     * @param templatePath template file path
     * @param form         key-value paired form
     * @return {@link PdfFile}
     */
    @Override
    public PdfFile convertWithTemplateAndForm(String templatePath, Map<String, Object> form) {
        File template = new File(templatePath);
        String fileExtension = serviceUtil.getFileExtension(templatePath);
        Converter converter = converterFactory.getConverter(fileExtension);
        try {
            return converter.convert(template, form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the given template into fulling forms
     *
     * @param templatePath template file path
     * @param forms        different forms for fulling template
     * @return List of {@link PdfFile}
     */
    @Override
    public List<PdfFile> convertWithTemplateAndForms(String templatePath, List<Map<String, Object>> forms) {

        File template = new File(templatePath);
        String fileExtension = serviceUtil.getFileExtension(templatePath);
        Converter converter = converterFactory.getConverter(fileExtension);

        return forms.stream()
                .map(formDTO -> {
                    try {
                        return converter.convert(template, formDTO);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Resource> convertWithTemplateAndForm(ConvertingRequest request) {

        Template template = templateRepository.findById(request.getTemplateId())
                .orElseThrow();

        String fileExtension = serviceUtil.getFileExtension(template.getTemplateFilePath());
        Converter converter = converterFactory.getConverter(fileExtension);

        Map<String, Object> formBody = request.getFormBody();
        File file = new File(template.getTemplateFilePath());
        try {
            PdfFile converted = converter.convert(file, formBody);

            Path path = Paths.get(converted.getPath());
            Resource resource = new UrlResource(path.toUri());
            return ResponseEntity.ok(resource);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
