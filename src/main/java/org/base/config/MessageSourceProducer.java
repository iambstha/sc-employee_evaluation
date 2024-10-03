package org.base.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MessageSourceProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageSourceProducer.class);

    @Produces
    @Named("defaultMessageSource")
    public MessageSource defaultMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("default", "en");
        return messageSource;
    }

    @Produces
    @Named("clientMessageSource")
    public MessageSource clientMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("client", "en");
        return messageSource;
    }

    @Produces
    @Named("configurationMessageSource")
    public MessageSource configurationMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("configuration", "en");
        return messageSource;
    }

    @Produces
    @Named("competencyMessageSource")
    public MessageSource competencyMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("competency", "en");
        return messageSource;
    }

}
