package org.diegosneves.application.client.delete;

import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;

import java.util.Objects;

public class DefaultDeleteClientUseCase extends DeleteClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultDeleteClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public void execute(final String id) {
        this.clientGateway.deleteById(ClientID.from(id));
    }
}
