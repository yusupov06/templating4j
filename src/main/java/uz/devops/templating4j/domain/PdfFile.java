package uz.devops.templating4j.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class representing converted file and which template used for conversion
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PdfFile {

    private String filename;
    private String path;
    private String templateRootPath;

    public PdfFile setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public PdfFile setPath(String path) {
        this.path = path;
        return this;
    }

    public PdfFile setTemplateRootPath(String templateRootPath) {
        this.templateRootPath = templateRootPath;
        return this;
    }
}
