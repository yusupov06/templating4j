package uz.devops.templating4j.converter.templatingEngine;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

@Service
public class TemplatingEngineImpl implements TemplatingEngine {

    @Override
    public String fillTemplate(String context, Map<String, Object> form) {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        StringWriter writer = new StringWriter();
        VelocityContext velocityContext = new VelocityContext();
        form.forEach(velocityContext::put);
        engine.evaluate(velocityContext, writer, "Template", context);
        return writer.toString();
    }
}
