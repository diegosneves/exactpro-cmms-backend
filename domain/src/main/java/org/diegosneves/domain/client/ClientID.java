package org.diegosneves.domain.client;

import org.diegosneves.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ClientID extends Identifier {

    private final String value;

    private ClientID(final String value) {
        this.value = value;
    }

    public static ClientID unique() {
        return ClientID.from(UUID.randomUUID());
    }

    public static ClientID from(UUID value) {
        return new ClientID(value.toString().toLowerCase());
    }

    public static ClientID from(final String value) {
        return new ClientID(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ClientID clientID = (ClientID) object;
        return Objects.equals(getValue(), clientID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
