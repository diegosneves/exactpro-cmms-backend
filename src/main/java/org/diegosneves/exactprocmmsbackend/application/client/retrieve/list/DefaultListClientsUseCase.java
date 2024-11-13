package org.diegosneves.exactprocmmsbackend.application.client.retrieve.list;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListClientsUseCase extends ListClientsUseCase{

    private final ClientGateway clientGateway;

    public DefaultListClientsUseCase(ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public Pagination<ClientListOutput> execute(final ClientSearchQuery clientSearchQuery) {

        return this.clientGateway.findAll(clientSearchQuery).map(ClientListOutput::from);

    }
}
