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
    @Named("apiLogMessageSource")
    public MessageSource apiLogMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("apiLog", "en");
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
    @Named("competencyMessageSource")
    public MessageSource competencyMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("competency", "en");
        return messageSource;
    }

    @Produces
    @Named("evaluationMessageSource")
    public MessageSource evaluationMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("evaluation", "en");
        return messageSource;
    }

    @Produces
    @Named("competencyEvaluationMessageSource")
    public MessageSource competencyEvaluationMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("competencyEvaluation", "en");
        return messageSource;
    }

    @Produces
    @Named("competencyGroupMessageSource")
    public MessageSource competencyGroupMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("competencyGroup", "en");
        return messageSource;
    }

    @Produces
    @Named("competencyGroupCommentMessageSource")
    public MessageSource competencyGroupCommentMessageSource() {
        MessageSource messageSource = new MessageSource();
        messageSource.loadControllerMessages("competencyGroupComment", "en");
        return messageSource;
    }

}
