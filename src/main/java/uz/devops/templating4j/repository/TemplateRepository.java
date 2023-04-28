package uz.devops.templating4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.devops.templating4j.domain.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
}
