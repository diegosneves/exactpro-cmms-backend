package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Contact;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.exactprocmmsbackend.infrastructure.contact.ContactMySQLGateway;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
        aClient.validate(new ThrowsValidationHandler());
        final var newClientEntity = this.clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));
        return newClientEntity.toAggregate();
    }

    @Override
    public void deleteById(ClientID anID) {
        if (anID != null) {
            this.clientRepository.deleteById(anID.getValue());
        }
    }

    @Override
    public Optional<Client> findById(ClientID anID) {
        Optional<Client> foundClient = Optional.empty();
        if (anID != null) {
            foundClient = this.clientRepository.findById(anID.getValue()).map(ClientJpaEntity::toAggregate);
        }
        return foundClient;
    }

    @Override
    public Client update(Client aClient) {
        Client updatedClient = null;
        if (aClient != null) {
            Optional<Client> existingClient = this.findById(aClient.getId());
            if (existingClient.isPresent()) {
                Client currentClient = existingClient.get();
                this.cleanDataBase(aClient, currentClient);
                updatedClient = this.clientRepository.save(ClientJpaEntity.from(aClient)).toAggregate();
                this.updateValueObject(currentClient.getAddress(), updatedClient.getAddress());
                this.updateValueObject(currentClient.getContact(), updatedClient.getContact());
            }
        }
        return updatedClient;
    }

    private void cleanDataBase(Client aClient, Client currentClient) {
        var addr = currentClient.getAddress();
        var contact = currentClient.getContact();
        boolean deletedAddress = false;
        boolean deletedContact = false;

        if (Objects.equals(aClient.getAddress(), currentClient.getAddress())) {
            currentClient.setAddress(null);
            deletedAddress = true;
        }
        if (Objects.equals(aClient.getContact(), currentClient.getContact())) {
            currentClient.setContact(null);
            deletedContact = true;
        }

        if (deletedAddress && deletedContact) {
            this.clientRepository.save(ClientJpaEntity.from(currentClient));
            this.addressMySQLGateway.deleteAddress(addr);
            this.contactMySQLGateway.deleteContact(contact);
        } else if (deletedAddress) {
            this.clientRepository.save(ClientJpaEntity.from(currentClient));
            this.addressMySQLGateway.deleteAddress(addr);
        } else if (deletedContact) {
            this.clientRepository.save(ClientJpaEntity.from(currentClient));
            this.contactMySQLGateway.deleteContact(contact);
        }
    }


    private void updateValueObject(Address oldAddress, Address updateAddress) {
        if (oldAddress != null && updateAddress == null) {
            this.addressMySQLGateway.deleteAddress(oldAddress);
        }
        if (oldAddress != null && updateAddress != null) {
            this.addressMySQLGateway.deleteAddress(oldAddress);
        }
    }

    private void updateValueObject(Contact oldContact, Contact updateContact) {
        if (oldContact != null && updateContact == null) {
            this.contactMySQLGateway.deleteContact(oldContact);
        }
        if (oldContact != null && updateContact != null) {
            this.contactMySQLGateway.deleteContact(oldContact);
        }
    }


    @Override
    public Pagination<Client> findAll(ClientSearchQuery aQuery) {
        return null;
    }

}
