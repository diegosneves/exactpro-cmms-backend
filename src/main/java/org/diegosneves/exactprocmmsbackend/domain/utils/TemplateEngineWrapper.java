package org.diegosneves.exactprocmmsbackend.domain.utils;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TemplateEngineWrapper {

    private static final String TH_BUILDER = "request"; // TODO - Melhorar esse nome

    private final ITemplateEngine templateEngine;

    @Autowired
    public TemplateEngineWrapper(ITemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void createdPdf(FileOutputStream fileOutputStream, String template, Object context) throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(this.templateEngine.process(template, createdContext(context)));
        renderer.layout();
        renderer.createPDF(fileOutputStream, false);
        renderer.finishPDF();
    }

    private static Context createdContext(Object builder) {
        Map<String, Object> data = new HashMap<>();
        data.put(TH_BUILDER, builder);

        Context context = new Context();
        context.setVariables(data);
        return context;
    }

}
