package uz.devops.templating4j.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.devops.templating4j.IntegrationTest;
import uz.devops.templating4j.domain.Template;
import uz.devops.templating4j.dto.TemplateDTO;
import uz.devops.templating4j.repository.TemplateRepository;
import uz.devops.templating4j.service.TemplateService;

import java.util.ArrayList;
import java.util.List;

@IntegrationTest
public class TemplateServiceImplTest {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private TemplateRepository templateRepository;

    @Test
    void shouldGetAllTemplates() {

        List<Template> templates = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            templates.add(new Template("/src/templates/test" + (i + 1)));
        }

        templateRepository.saveAll(templates);

        ResponseEntity<List<TemplateDTO>> all = templateService.getAll();

        Assertions.assertNotNull(all);
        Assertions.assertEquals(all.getStatusCode(), HttpStatus.OK);

        List<TemplateDTO> list = all.getBody();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(), templates.size());

    }

    @Test
    void shouldGetById() {

        Template template = new Template("/src/templates/test");
        templateRepository.save(template);

        long countBefore = templateRepository.count();

        ResponseEntity<TemplateDTO> response = templateService.getById(template.getId());

        long count = templateRepository.count();
        Assertions.assertEquals(count, countBefore);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        TemplateDTO body = response.getBody();
        Assertions.assertNotNull(body);
        Assertions.assertEquals(body.getTemplateFilePath(), template.getTemplateFilePath());

    }

    @Test
    void deleteById() {
        Template template = new Template("/src/templates/test");
        templateRepository.save(template);

        long countBefore = templateRepository.count();

        ResponseEntity<Void> response = templateService.deleteById(template.getId());

        long count = templateRepository.count();
        Assertions.assertEquals(count + 1, countBefore);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}
