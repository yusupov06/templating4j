package uz.devops.templating4j.converter;

import uz.devops.templating4j.domain.PdfFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Parser for Parsing desired file to PDF
 */
public interface Converter {

    List<String> getFileExtensions();

    PdfFile convert(File file, Map<String, Object> form) throws Exception;

}
