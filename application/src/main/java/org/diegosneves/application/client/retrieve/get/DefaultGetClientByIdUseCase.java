package org.diegosneves.application.client.retrieve.get;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetClientByIdUseCase extends GetClientByIdUseCase {

    private final ClientGateway clientGateway;

    public DefaultGetClientByIdUseCase(final ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public ClientOutput execute(final String anId) {
        final var targetID = ClientID.from(anId);

        return this.clientGateway
                .findById(targetID)
                .map(ClientOutput::from)
                .orElseThrow(notFound(targetID));
    }

    private static Supplier<NotFoundException> notFound(final ClientID anId) {
        return () -> NotFoundException.with(Client.class, anId);
    }
}
