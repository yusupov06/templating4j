package uz.devops.templating4j.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.devops.templating4j.dto.TemplateDTO;

import java.util.List;

public interface TemplateService {

    ResponseEntity<List<TemplateDTO>> getAll();

    ResponseEntity<TemplateDTO> getById(Long id);

    ResponseEntity<Void> deleteById(Long id);

    String storeFile(MultipartFile file);
}
