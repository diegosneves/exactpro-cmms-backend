package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.exactprocmmsbackend.infrastructure.contact.ContactMySQLGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientMySQLGateway implements ClientGateway {

    private final ClientRepository clientRepository;
    private final AddressMySQLGateway addressMySQLGateway;
    private final ContactMySQLGateway contactMySQLGateway;

    public ClientMySQLGateway(ClientRepository clientRepository, AddressMySQLGateway addressMySQLGateway, ContactMySQLGateway contactMySQLGateway) {
        this.clientRepository = clientRepository;
        this.addressMySQLGateway = addressMySQLGateway;
        this.contactMySQLGateway = contactMySQLGateway;
    }

    @Override
    public Client create(Client aClient) {
        final var persistedAddress = this.addressMySQLGateway.create(aClient.getAddress());
        final var persistedContact = this.contactMySQLGateway.create(aClient.getContact());
        ClientJpaEntity clientEntity = ClientJpaEntity.from(aClient);
        clientEntity.setAddress(persistedAddress);
        clientEntity.setContact(persistedContact);
        ClientJpaEntity newClientEntity = this.clientRepository.saveAndFlush(clientEntity);
        return newClientEntity.toAggregate();
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
