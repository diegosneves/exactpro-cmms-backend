package org.diegosneves.exactprocmmsbackend.domain.validation;

public abstract class Validator {

    private final ValidationHandler validationHandler;

    protected Validator(ValidationHandler validationHandler) {
        this.validationHandler = validationHandler;
    }

    public abstract void validate();

    protected ValidationHandler getValidationHandler() {
        return this.validationHandler;
    }

}
