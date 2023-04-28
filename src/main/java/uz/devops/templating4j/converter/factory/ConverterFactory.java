package uz.devops.templating4j.converter.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.impl.CommonConverter;

@Component
@RequiredArgsConstructor
public class ConverterFactory {

    private final ApplicationContext applicationContext;

    public Converter getConverter(String fileExtension) {
        return applicationContext.getBeansOfType(Converter.class)
                .values()
                .stream()
                .filter(converter -> converter.getFileExtensions().contains(fileExtension))
                .findFirst()
                .orElse((Converter) applicationContext.getBean(CommonConverter.BEAN_NAME));
    }
}
