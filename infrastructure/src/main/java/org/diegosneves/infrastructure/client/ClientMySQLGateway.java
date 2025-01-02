package org.diegosneves.infrastructure.client;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.ClientSearchQuery;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.domain.pagination.Pagination;
import org.diegosneves.domain.validation.ErrorData;
import org.diegosneves.domain.validation.handler.ThrowsValidationHandler;
import org.diegosneves.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.infrastructure.contact.ContactMySQLGateway;
import org.diegosneves.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static org.diegosneves.infrastructure.utils.SpecificationUtils.like;

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
    public Client create(final Client aClient) {
        aClient.validate(new ThrowsValidationHandler());
        Optional<ClientJpaEntity> areadyExists = this.clientRepository.findClientJpaEntityByCnpj(aClient.getCnpj());
        if (areadyExists.isPresent()) {
            throw DomainException.with(new ErrorData(String.format("O CNPJ %s já foi cadastrado!", aClient.getCnpj())));
        }
        final var newClientEntity = this.clientRepository.save(ClientJpaEntity.from(aClient));
        return newClientEntity.toAggregate();
    }

    @Override
    public void deleteById(final ClientID anID) {
        if (anID != null && this.clientRepository.existsById(anID.getValue())) {
            this.clientRepository.deleteById(anID.getValue());
        }
    }

    @Override
    public Optional<Client> findById(final ClientID anID) {
        Optional<Client> foundClient = Optional.empty();
        if (anID != null) {
            foundClient = this.clientRepository.findById(anID.getValue()).map(ClientJpaEntity::toAggregate);
        }
        return foundClient;
    }

    @Override
    public Client update(final Client aClient) {
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

    private void cleanDataBase(final Client aClient, final Client currentClient) {
        var addr = currentClient.getAddress();
        var contact = currentClient.getContact();
        boolean deletedAddress = Objects.equals(aClient.getAddress(), currentClient.getAddress());
        boolean deletedContact = Objects.equals(aClient.getContact(), currentClient.getContact());

        if (deletedAddress) {
            currentClient.setAddress(null);
        }
        if (deletedContact) {
            currentClient.setContact(null);
        }

        if (deletedAddress || deletedContact) {
            this.clientRepository.save(ClientJpaEntity.from(currentClient));
            if (deletedAddress) {
                this.addressMySQLGateway.deleteAddress(addr);
            }
            if (deletedContact) {
                this.contactMySQLGateway.deleteContact(contact);
            }
        }
    }


    private void updateValueObject(final Address oldAddress, final Address updateAddress) {
        if (oldAddress != null && updateAddress == null) {
            this.addressMySQLGateway.deleteAddress(oldAddress);
        }
        if (oldAddress != null && updateAddress != null) {
            this.addressMySQLGateway.deleteAddress(oldAddress);
        }
    }

    private void updateValueObject(final Contact oldContact, final Contact updateContact) {
        if (oldContact != null && updateContact == null) {
            this.contactMySQLGateway.deleteContact(oldContact);
        }
        if (oldContact != null && updateContact != null) {
            this.contactMySQLGateway.deleteContact(oldContact);
        }
    }


    @Override
    public Pagination<Client> findAll(final ClientSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var spec = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str ->
                        SpecificationUtils.<ClientJpaEntity>like("cnpj", str).or(like("companyName", str))
                )
                .orElse(null);

        final var pageResult = this.clientRepository.findAll(Specification.where(spec), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ClientJpaEntity::toAggregate).toList()
        );
    }

}
