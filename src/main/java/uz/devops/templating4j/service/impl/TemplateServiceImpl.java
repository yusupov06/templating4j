package uz.devops.templating4j.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.devops.templating4j.domain.Template;
import uz.devops.templating4j.dto.TemplateDTO;
import uz.devops.templating4j.error.FileStorageException;
import uz.devops.templating4j.repository.TemplateRepository;
import uz.devops.templating4j.service.TemplateService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    @Value("${app.template.root}")
    private String templatesRootPath;

    private final TemplateRepository templateRepository;

    @Override
    public ResponseEntity<List<TemplateDTO>> getAll() {
        return ResponseEntity.ok(toDTO(templateRepository.findAll()));
    }

    private List<TemplateDTO> toDTO(List<Template> list) {
        return list.stream()
            .map(TemplateDTO::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<TemplateDTO> getById(Long id) {
        return ResponseEntity
            .ok(toDTO(templateRepository
                .findById(id)
                .orElseThrow()));
    }

    private TemplateDTO toDTO(Template template) {
        return TemplateDTO.toDTO(template);
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        templateRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public String storeFile(MultipartFile file) {

        if (file == null || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException();
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = Path.of(templatesRootPath + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

}
