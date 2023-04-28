package uz.devops.templating4j.converter.templatingEngine;

import java.util.Map;

public interface TemplatingEngine {

    String fillTemplate(String context, Map<String, Object> form);
}
