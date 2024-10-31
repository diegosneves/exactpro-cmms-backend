package org.diegosneves.exactprocmmsbackend.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(ErrorData anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    List<ErrorData> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

}
