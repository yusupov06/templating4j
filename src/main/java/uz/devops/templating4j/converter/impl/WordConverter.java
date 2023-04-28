package uz.devops.templating4j.converter.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Service;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.util.ApplicationProperties;

import jakarta.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.Map;

@Service(WordConverter.BEAN_NAME)
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WordConverter implements Converter {

    public static final String BEAN_NAME = "WORD" + ApplicationProperties.CONVERTER_PREFIX;

    private final ConverterUtils converterUtils;

    @Override
    public List<String> getFileExtensions() {
        return List.of("doc", "docx");
    }

    @Override
    public PdfFile convert(File file, Map<String, Object> form) throws Exception {

        InputStream templateInputStream = new FileInputStream(file);

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);

        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        VariablePrepare.prepare(wordMLPackage);

        Map<String, String> formMap = converterUtils.getAsStringMap(form);

        documentPart.variableReplace(formMap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        wordMLPackage.save(outputStream);

        File outputPdf = converterUtils.createFile(converterUtils.getFileName(file));

        FileOutputStream fileOutputStream = new FileOutputStream(outputPdf);

        Docx4J.toPDF(wordMLPackage, fileOutputStream);

        outputStream.close();
        fileOutputStream.close();
        templateInputStream.close();

        return converterUtils.create(outputPdf, file.getAbsolutePath());

    }

}
