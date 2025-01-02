package org.diegosneves.domain.exceptions;

import org.diegosneves.domain.validation.ErrorData;

import java.util.List;

public class DomainException extends NoStackTraceException {

    protected final List<ErrorData> errors;

    protected DomainException(final String message, final List<ErrorData> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final ErrorData anErrors) {
        return new DomainException(anErrors.message(), List.of(anErrors));
    }

    public static DomainException with(final List<ErrorData> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<ErrorData> getErrors() {
        return this.errors;
    }

}
