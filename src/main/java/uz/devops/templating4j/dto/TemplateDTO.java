package uz.devops.templating4j.dto;

import lombok.*;
import uz.devops.templating4j.domain.Template;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TemplateDTO {

    private Long id;
    private String templateFilePath;


    public static TemplateDTO toDTO(Template template) {
        return TemplateDTO.builder()
            .id(template.getId())
            .templateFilePath(template.getTemplateFilePath())
            .build();
    }
}
