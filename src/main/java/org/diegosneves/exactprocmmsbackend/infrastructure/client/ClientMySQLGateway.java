package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressRepository;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence.ContactJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientMySQLGateway implements ClientGateway {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    public ClientMySQLGateway(ClientRepository clientRepository, AddressRepository addressRepository, ContactRepository contactRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Client create(Client aClient) {
        AddressJpaEntity persistedAddress = this.addressRepository.saveAndFlush(AddressJpaEntity.from(aClient.getAddress()));
        ContactJpaEntity persistedContact = this.contactRepository.saveAndFlush(ContactJpaEntity.from(aClient.getContact()));
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
