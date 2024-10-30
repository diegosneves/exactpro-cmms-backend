package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.Identifier;

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

}
