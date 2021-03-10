package net.carlosfe.tests.sintad.global.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Component
public class MessageUtil {

    private final MessageSource messageSource;
    final Locale locale;

    public MessageUtil(final MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = LocaleContextHolder.getLocale();
    }

    public String getMessage(String code) {
        return this.getMessage(code, false);
    }

    public String getMessage(String code, boolean nulleable) {
        String message = null;
        try {
            message = this.messageSource.getMessage(code, null, this.locale);
        } catch (Exception ignored) {
            if (!nulleable) {
                message = code;
            }
        }

        return message;
    }

    public String getMessageError(FieldError code) {
        String message;
        try {
            String messageByCode = this.getMessage(code.getDefaultMessage(), true);
            if (messageByCode == null) {
                message = this.messageSource.getMessage(code, this.locale);
            } else {
                message = messageByCode;
            }
        } catch (Exception ignored) {
            message = code.getDefaultMessage();
        }
        return message;
    }
}
