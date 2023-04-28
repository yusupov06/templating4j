package uz.devops.templating4j.converter.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import uz.devops.templating4j.converter.Converter;
import uz.devops.templating4j.converter.templatingEngine.TemplatingEngine;
import uz.devops.templating4j.converter.util.ConverterUtils;
import uz.devops.templating4j.domain.PdfFile;
import uz.devops.templating4j.util.ApplicationProperties;

import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * HTML files parser.
 */
@Service(value = HtmlConverter.BEAN_NAME)
@Transactional
@RequiredArgsConstructor
public class HtmlConverter implements Converter {

    public static final String BEAN_NAME = "HTML" + ApplicationProperties.CONVERTER_PREFIX;
    private final ConverterUtils converterUtils;
    private final TemplatingEngine templatingEngine;

    @Override
    public List<String> getFileExtensions() {
        return List.of("html");
    }

    /**
     * Main method for parsing
     * <br>
     * 1. It reads HTML and modify with form
     * <br>
     * 2. write modified html to pdf
     *
     * @param file template
     * @param form key-value paired data and
     *             fill template with the values
     * @return {@link PdfFile}
     * @throws IOException error occurred
     */
    @Override
    public PdfFile convert(File file, Map<String, Object> form) throws IOException, DocumentException {

        Document pdfDocument = new Document();

        String fileName = converterUtils.getFileName(file);

        File outputPdf = converterUtils.createFile(fileName);

        PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputPdf));

        // Open the PDF document
        pdfDocument.open();

        byte[] bytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

        String htmlString = new String(bytes);

        String modifiedHtml = templatingEngine
                .fillTemplate(htmlString, form);

        try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(modifiedHtml);
            renderer.layout();
            renderer.createPDF(outputStream);
        }

        return converterUtils.create(outputPdf, file.getAbsolutePath());
    }

}
