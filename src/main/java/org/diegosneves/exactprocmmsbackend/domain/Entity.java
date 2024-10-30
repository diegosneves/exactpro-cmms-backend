package org.diegosneves.exactprocmmsbackend.domain;

import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

import java.util.Objects;

public abstract class Entity <ID extends Identifier> {

    protected final ID id;

    protected Entity(final ID id) {
        Objects.requireNonNull(id, "id must not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Entity<?> entity = (Entity<?>) object;
        return Objects.equals(this.getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

}
