package uz.devops.templating4j.converter.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.templatingEngine.TemplatingEngine;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.util.ApplicationProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Common parser for file extensions that are not supported by one of implementations
 */
@Service(CommonConverter.BEAN_NAME)
@RequiredArgsConstructor
@Transactional
public class CommonConverter implements Converter {

    // Bean name
    public static final String BEAN_NAME = "COMMON" + ApplicationProperties.CONVERTER_PREFIX;
    // beans
    private final ConverterUtils converterUtils;
    private final TemplatingEngine templatingEngine;


    @Override
    public List<String> getFileExtensions() {
        return List.of();
    }

    /**
     * Main parser method
     * @param file template file
     * @param form form for filling template
     * @return {@link PdfFile}
     * @throws Exception error occurs
     */
    @Override
    public PdfFile convert(File file, Map<String, Object> form) throws Exception {

        String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));

        String modified = templatingEngine.fillTemplate(content, form);

        String fileName = converterUtils.getFileName(file);

        File outputPdf = converterUtils.createFile(fileName);

        // Create output PDF file
        Document pdfDocument = new Document();
        PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputPdf));
        pdfDocument.open();

        Scanner scanner = new Scanner(modified);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            pdfDocument.add(new Paragraph(line));
        }

        scanner.close();
        pdfDocument.close();

        return converterUtils.create(outputPdf, file.getAbsolutePath());
    }
}
