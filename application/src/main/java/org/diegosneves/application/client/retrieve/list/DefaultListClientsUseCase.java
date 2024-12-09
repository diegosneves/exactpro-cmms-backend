package org.diegosneves.application.client.retrieve.list;

import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientSearchQuery;
import org.diegosneves.domain.pagination.Pagination;

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
