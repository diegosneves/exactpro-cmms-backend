package org.diegosneves.infrastructure.configuration.web;

import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.domain.validation.ErrorData;

import java.util.List;

public record ApiError(String message, List<ErrorData> errors) {

    static ApiError from(final DomainException exception) {
        return new ApiError(exception.getMessage(), exception.getErrors());
    }

}
