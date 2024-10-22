package org.diegosneves.exactprocmmsbackend.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    private static final String PREFIX = "templates/";
    private static final String SUFFIX = ".html";
    private static final String TEMPLATE_MODE = "HTML";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final int ORDER = 1;

    @Bean
    public ClassLoaderTemplateResolver contractTemplateResolver() {
        ClassLoaderTemplateResolver pdfTemplateResolver = new ClassLoaderTemplateResolver();
        pdfTemplateResolver.setPrefix(PREFIX);
        pdfTemplateResolver.setSuffix(SUFFIX);
        pdfTemplateResolver.setTemplateMode(TEMPLATE_MODE);
        pdfTemplateResolver.setCharacterEncoding(CHARACTER_ENCODING);
        pdfTemplateResolver.setOrder(ORDER);
        return pdfTemplateResolver;
    }

}
