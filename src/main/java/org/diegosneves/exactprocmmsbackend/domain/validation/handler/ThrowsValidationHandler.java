package org.diegosneves.exactprocmmsbackend.domain.validation.handler;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validation;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final ErrorData anError) {
        throw DomainException.with(List.of(anError));
    }

    @Override
    public ValidationHandler append(final ValidationHandler aHandler) {
        throw DomainException.with(aHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (Exception e) {
            throw DomainException.with(List.of(new ErrorData(e.getMessage())));
        }
        return this;
    }

    @Override
    public List<ErrorData> getErrors() {
        return List.of();
    }

}
