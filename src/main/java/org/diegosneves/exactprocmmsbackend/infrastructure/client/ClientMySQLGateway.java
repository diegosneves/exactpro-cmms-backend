package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientMySQLGateway implements ClientGateway {

    private final ClientRepository clientRepository;

    public ClientMySQLGateway(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client aClient) {
        return null;
    }

    @Override
    public void deleteById(ClientID anID) {

    }

    @Override
    public Optional<Client> findById(ClientID anID) {
        return Optional.empty();
    }

    @Override
    public Client update(Client aClient) {
        return null;
    }

    @Override
    public Pagination<Client> findAll(ClientSearchQuery aQuery) {
        return null;
    }

}
