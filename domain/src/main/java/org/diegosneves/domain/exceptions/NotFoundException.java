package org.diegosneves.domain.exceptions;

import org.diegosneves.domain.Entity;
import org.diegosneves.domain.Identifier;
import org.diegosneves.domain.validation.ErrorData;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    public static final String ENTITY_NOT_FOUND_MESSAGE = "Não foi encontrado o %s com o ID %s";

    protected NotFoundException(final String message, final List<ErrorData> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
            final Class<? extends Entity<?>> entityClass,
            final Identifier id
    ) {
        final var anError = ENTITY_NOT_FOUND_MESSAGE.formatted(entityClass.getSimpleName(), id.getValue());
        return new NotFoundException(anError, Collections.emptyList());
    }
}
