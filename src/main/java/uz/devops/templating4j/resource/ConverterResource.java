package uz.devops.templating4j.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.devops.templating4j.dto.ConvertingRequest;
import uz.devops.templating4j.service.PdfConverterService;
import uz.devops.templating4j.util.ApplicationProperties;

@RestController
@RequestMapping(ConverterResource.BASE_URL)
@RequiredArgsConstructor
public class ConverterResource {

    public static final String BASE_URL = ApplicationProperties.BASE_URL + "converting";

    private final PdfConverterService pdfConverterService;

    @PostMapping
    public ResponseEntity<Resource> convert(@RequestBody ConvertingRequest request) {
        return pdfConverterService.convertWithTemplateAndForm(request);
    }

}
