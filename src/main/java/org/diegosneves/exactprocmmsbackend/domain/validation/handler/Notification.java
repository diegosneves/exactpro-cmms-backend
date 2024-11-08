package org.diegosneves.exactprocmmsbackend.domain.validation.handler;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validation;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<ErrorData> errors;

    private Notification(final List<ErrorData> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final ErrorData error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    public static Notification create(final Throwable throwable) {
        return create(new ErrorData(throwable.getMessage()));
    }

    @Override
    public Notification append(final ErrorData anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException e) {
            this.errors.addAll(e.getErrors());

        } catch (final Exception ex) {
            this.errors.add(new ErrorData(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<ErrorData> getErrors() {
        return this.errors;
    }

}
