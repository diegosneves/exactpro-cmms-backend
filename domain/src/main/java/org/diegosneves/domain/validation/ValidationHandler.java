package org.diegosneves.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(ErrorData anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    List<ErrorData> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default ErrorData findFirst() {
        return hasError() ? getErrors().get(0) : null;
    }

}
