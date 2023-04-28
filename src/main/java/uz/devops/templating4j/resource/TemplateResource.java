package uz.devops.templating4j.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.devops.templating4j.dto.TemplateDTO;
import uz.devops.templating4j.service.TemplateService;
import uz.devops.templating4j.util.ApplicationProperties;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(TemplateResource.BASE_URL)
@RequiredArgsConstructor
public class TemplateResource {

    public static final String BASE_URL = ApplicationProperties.BASE_URL + "templates";

    private final TemplateService templateService;

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAll() {
        return templateService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getById(@PathVariable Long id) {
        return templateService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return templateService.deleteById(id);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@NotNull @RequestParam("file") MultipartFile file) {
        String fileName = templateService.storeFile(file);
        return ResponseEntity.ok(fileName);
    }

}
