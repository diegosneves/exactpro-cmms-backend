package org.diegosneves.infrastructure.client;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.ClientSearchQuery;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.infrastructure.MySQLGatewayTest;
import org.diegosneves.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.infrastructure.contact.ContactMySQLGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MySQLGatewayTest
class ClientMySQLGatewayTest {

    @Autowired
    private ClientMySQLGateway gateway;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AddressMySQLGateway addressGateway;

    @Autowired
    private ContactMySQLGateway contactGateway;


    @Test
    void testInjectedDependencies() {
        assertNotNull(gateway);
        assertNotNull(repository);
    }

    @Test
    void givenAValidClientWhenCallsTheCreateShouldReturnANewClient() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());

        final var actualClient = this.gateway.create(aClient);

        assertEquals(1, this.repository.count());

        assertNotNull(actualClient);
        assertEquals(aClient.getId(), actualClient.getId());
        assertEquals(expectedCnpj, actualClient.getCnpj());
        assertEquals(expectedAddress, actualClient.getAddress());
        assertEquals(expectedContact, actualClient.getContact());
        assertEquals(expectedCompanyName, actualClient.getCompanyName());
        assertEquals(expectedCompanyBranch, actualClient.getCompanyBranch());
        assertEquals(expectedCompanySector, actualClient.getCompanySector());

        final var actualEntity = this.repository.findById(aClient.getId().getValue()).orElse(null);

        assertNotNull(actualEntity);
        assertEquals(aClient.getId().getValue(), actualEntity.getId());
        assertEquals(expectedCnpj, actualEntity.getCnpj());
        assertNotNull(actualEntity.getAddress());
        assertNotNull(actualEntity.getContact());
        assertEquals(expectedCompanyName, actualEntity.getCompanyName());
        assertEquals(expectedCompanyBranch, actualEntity.getCompanyBranch());
        assertEquals(expectedCompanySector, actualEntity.getCompanySector());
    }

    @Test
    void givenAValidClientWithNulableAddressWhenCallsTheCreateShouldReturnANewClientWithNullAddress() {
        final var expectedCnpj = "24888114000188";
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, null, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());

        final var actualClient = this.gateway.create(aClient);

        assertEquals(1, this.repository.count());

        assertNotNull(actualClient);
        assertEquals(aClient.getId(), actualClient.getId());
        assertEquals(expectedCnpj, actualClient.getCnpj());
        assertNull(actualClient.getAddress());
        assertEquals(expectedContact, actualClient.getContact());
        assertEquals(expectedCompanyName, actualClient.getCompanyName());
        assertEquals(expectedCompanyBranch, actualClient.getCompanyBranch());
        assertEquals(expectedCompanySector, actualClient.getCompanySector());

        final var actualEntity = this.repository.findById(aClient.getId().getValue()).orElse(null);

        assertNotNull(actualEntity);
        assertEquals(aClient.getId().getValue(), actualEntity.getId());
        assertEquals(expectedCnpj, actualEntity.getCnpj());
        assertNull(actualEntity.getAddress());
        assertNotNull(actualEntity.getContact());
        assertEquals(expectedCompanyName, actualEntity.getCompanyName());
        assertEquals(expectedCompanyBranch, actualEntity.getCompanyBranch());
        assertEquals(expectedCompanySector, actualEntity.getCompanySector());
    }

    @Test
    void givenAValidClientWithNulableContactWhenCallsTheCreateShouldReturnANewClientWithNullContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());

        final var actualClient = this.gateway.create(aClient);

        assertEquals(1, this.repository.count());

        assertNotNull(actualClient);
        assertEquals(aClient.getId(), actualClient.getId());
        assertEquals(expectedCnpj, actualClient.getCnpj());
        assertEquals(expectedAddress, actualClient.getAddress());
        assertNull(actualClient.getContact());
        assertEquals(expectedCompanyName, actualClient.getCompanyName());
        assertEquals(expectedCompanyBranch, actualClient.getCompanyBranch());
        assertEquals(expectedCompanySector, actualClient.getCompanySector());

        final var actualEntity = this.repository.findById(aClient.getId().getValue()).orElse(null);

        assertNotNull(actualEntity);
        assertEquals(aClient.getId().getValue(), actualEntity.getId());
        assertEquals(expectedCnpj, actualEntity.getCnpj());
        assertNotNull(actualEntity.getAddress());
        assertNull(actualEntity.getContact());
        assertEquals(expectedCompanyName, actualEntity.getCompanyName());
        assertEquals(expectedCompanyBranch, actualEntity.getCompanyBranch());
        assertEquals(expectedCompanySector, actualEntity.getCompanySector());
    }

    @Test
    void givenAClientIDValidWhenCallsTheDeleteByIdShouldDeleteClient() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        this.gateway.deleteById(aClient.getId());

        assertEquals(0, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());
    }

    @Test
    void givenAClientIDInvalidWhenCallsTheDeleteByIdShouldBeNothing() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var clientID = ClientID.from("c881725d450e432781725d450ed3275e");

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        this.gateway.deleteById(clientID);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());
    }

    @Test
    void givenANullClientIDWhenCallsTheDeleteByIdShouldBeNothing() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        this.gateway.deleteById(null);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());
    }

    @Test
    void givenAValidClientIDWhenCallsTheFindByIdShouldReturnClient() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        final var actualResult = this.gateway.findById(aClient.getId()).orElse(null);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenANullClientIDWhenCallsTheFindByIdShouldReturnNull() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        final var actualResult = this.gateway.findById(null).orElse(null);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNull(actualResult);
    }

    @Test
    void givenAnInvalidClientIDWhenCallsTheFindByIdShouldReturnNull() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var clientID = ClientID.from("c881725d450e432781725d450ed3275e");

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        final var actualResult = this.gateway.findById(clientID).orElse(null);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNull(actualResult);
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithoutAddressAndContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("expectedCnpj", expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        aClient.update(expectedCnpj, null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertNull(actualResult.getAddress());
        assertNull(actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenANullClientWhenCallsTheUpdateShouldReturnNull() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("expectedCnpj", expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        aClient.update(expectedCnpj, null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(null);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNull(actualResult);
    }

    @Test
    void givenAValidClientWithWrongIdWhenCallsTheUpdateShouldReturnNull() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        final var aWrongClient = Client.newClient("expectedCnpj", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aWrongClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNull(actualResult);
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithAddressAndContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("expectedCnpj", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        aClient.update(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithModifyAddressAndContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "444", "Bairro", "Cidade", "Estado", "93000000");
        final var anAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@outlook.com", "564555");
        final var aContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("expectedCnpj", anAddress, aContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        aClient.update(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithNullAddressAndContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("expectedCnpj", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        aClient.update(expectedCnpj, null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertNull(actualResult.getAddress());
        assertNull(actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithEqualsAddressAndContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "444", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@outlook.com", "564555");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("24888114000189", expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        aClient.update(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count(), "Address was duplicated or removed");
        assertEquals(1, this.contactGateway.count(), "Contact was duplicated or removed");

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithEqualsAddress() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "444", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@outlook.com", "564555");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("24888114000189", expectedAddress, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count());
        assertEquals(0, this.contactGateway.count());

        aClient.update(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count(), "Address was duplicated or removed");
        assertEquals(1, this.contactGateway.count(), "Contact was duplicated or removed");

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenAValidClientWhenCallsTheUpdateShouldReturnClientWithEqualsContact() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "444", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@outlook.com", "564555");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClient = Client.newClient("24888114000189", null, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        this.repository.save(ClientJpaEntity.from(aClient));

        assertEquals(1, this.repository.count());
        assertEquals(0, this.addressGateway.count());
        assertEquals(1, this.contactGateway.count());

        aClient.update(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var actualResult = this.gateway.update(aClient);

        assertEquals(1, this.repository.count());
        assertEquals(1, this.addressGateway.count(), "Address was duplicated or removed");
        assertEquals(1, this.contactGateway.count(), "Contact was duplicated or removed");

        assertNotNull(actualResult);
        assertEquals(aClient.getId(), actualResult.getId());
        assertEquals(expectedCnpj, actualResult.getCnpj());
        assertEquals(expectedAddress, actualResult.getAddress());
        assertEquals(expectedContact, actualResult.getContact());
        assertEquals(expectedCompanyName, actualResult.getCompanyName());
        assertEquals(expectedCompanyBranch, actualResult.getCompanyBranch());
        assertEquals(expectedCompanySector, actualResult.getCompanySector());
    }

    @Test
    void givenPrePersistedClientWhenCallsFindAllShouldReturnAllClientsPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClientOne = Client.newClient("24888114000188", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientTwo = Client.newClient("74584232000170", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientThree = Client.newClient("44837185000169", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());
        this.repository.saveAll(List.of(
                ClientJpaEntity.from(aClientOne),
                ClientJpaEntity.from(aClientTwo),
                ClientJpaEntity.from(aClientThree)
        ));
        assertEquals(3, this.repository.count());

        final var query = new ClientSearchQuery(expectedPage, expectedPerPage, "", "cnpj", "asc");

        final var actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientOne.getId(), actualResult.items().get(0).getId());
    }

    @Test
    void givenEmptyClientTableWhenCallsFindAllShouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        assertEquals(0, this.repository.count());

        final var query = new ClientSearchQuery(expectedPage, expectedPerPage, "", "cnpj", "asc");

        final var actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(0, actualResult.items().size());

    }

    @Test
    void givenFollowPaginationWhenCallsFindAllWithPage1ShouldReturnPaginated() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClientOne = Client.newClient("24888114000188", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientTwo = Client.newClient("74584232000170", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientThree = Client.newClient("44837185000169", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());
        this.repository.saveAll(List.of(
                ClientJpaEntity.from(aClientOne),
                ClientJpaEntity.from(aClientTwo),
                ClientJpaEntity.from(aClientThree)
        ));
        assertEquals(3, this.repository.count());

        // Page 0
        var query = new ClientSearchQuery(expectedPage, expectedPerPage, "", "cnpj", "asc");

        var actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientOne.getId(), actualResult.items().get(0).getId());

        // Page 1
        expectedPage = 1;
        query = new ClientSearchQuery(expectedPage, expectedPerPage, "", "cnpj", "asc");

        actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientThree.getId(), actualResult.items().get(0).getId());

        // Page 2
        expectedPage = 2;
        query = new ClientSearchQuery(expectedPage, expectedPerPage, "", "cnpj", "asc");

        actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientTwo.getId(), actualResult.items().get(0).getId());
    }

    @Test
    void givenPrePersistedClientAnd2000TermsWhenCallsFindAllShouldReturnAllClientsPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClientOne = Client.newClient("24888114000188", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientTwo = Client.newClient("74584232000170", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientThree = Client.newClient("44837185000169", null, null, "Actions Labs", "Canoas/RS", expectedCompanySector);

        assertEquals(0, this.repository.count());
        this.repository.saveAll(List.of(
                ClientJpaEntity.from(aClientOne),
                ClientJpaEntity.from(aClientTwo),
                ClientJpaEntity.from(aClientThree)
        ));
        assertEquals(3, this.repository.count());

        final var query = new ClientSearchQuery(expectedPage, expectedPerPage, "2000", "cnpj", "asc");

        final var actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientTwo.getId(), actualResult.items().get(0).getId());
    }

    @Test
    void givenPrePersistedClientAnd2000TermsWhenCallsFindAllAndTermsMatchsShouldReturnAllClientsPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";

        final var aClientOne = Client.newClient("24888114000188", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientTwo = Client.newClient("74584232000170", null, null, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var aClientThree = Client.newClient("44837185000169", null, null, "Actions Labs", "Canoas/RS", expectedCompanySector);

        assertEquals(0, this.repository.count());
        this.repository.saveAll(List.of(
                ClientJpaEntity.from(aClientOne),
                ClientJpaEntity.from(aClientTwo),
                ClientJpaEntity.from(aClientThree)
        ));
        assertEquals(3, this.repository.count());

        final var query = new ClientSearchQuery(expectedPage, expectedPerPage, "actions", "companyName", "asc");

        final var actualResult = this.gateway.findAll(query);

        assertNotNull(actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(aClientThree.getId(), actualResult.items().get(0).getId());
    }

    @Test
    void givenACnpjThatAlreadyExistsWhenCallsTheSaveShouldThrowException() {
        final var expectedCnpj = "24888114000188";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
        final var expectedContact = new Contact("email@gmail.com", "5645");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Esteio/RS";
        final var expectedCompanySector = "Caldeiraria / Funelaria";
        final var expectedErroMessage = String.format("O CNPJ %s já foi cadastrado!", expectedCnpj);

        final var aClient = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());

        this.gateway.create(aClient);

        assertEquals(1, this.repository.count());

        final var actualException = assertThrows(DomainException.class, () -> this.gateway.create(aClient));

        assertNotNull(actualException);
        assertEquals(DomainException.class, actualException.getClass());
        assertEquals(1, actualException.getErrors().size());
        assertEquals(expectedErroMessage, actualException.getErrors().get(0).message());

    }

}
