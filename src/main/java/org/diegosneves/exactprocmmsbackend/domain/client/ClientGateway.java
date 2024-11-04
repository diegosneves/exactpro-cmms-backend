package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;

import java.util.Optional;

public interface ClientGateway {

    Client create(Client aClient);

    void deleteById(ClientID anID);

    Optional<Client> findById(ClientID anID);

    Client update(Client aClient);

    Pagination<Client> findAll(ClientSearchQuery aQuery);

}
