package org.diegosneves.exactprocmmsbackend.domain.exceptions;

import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;

import java.util.List;

public class DomainException extends RuntimeException {

    private final List<ErrorData> errors;

    private DomainException(List<ErrorData> errors) {
        super("", null, true, false);
        this.errors = errors;
    }

    public static DomainException with(final List<ErrorData> anErrors) {
        return new DomainException(anErrors);
    }

    public List<ErrorData> getErrors() {
        return this.errors;
    }

}
