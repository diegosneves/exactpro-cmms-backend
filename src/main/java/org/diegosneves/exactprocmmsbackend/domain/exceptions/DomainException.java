package org.diegosneves.exactprocmmsbackend.domain.exceptions;

import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;

import java.util.List;

public class DomainException extends NoStackTraceException {

    private final List<ErrorData> errors;

    private DomainException(final String message, final List<ErrorData> errors) {
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
