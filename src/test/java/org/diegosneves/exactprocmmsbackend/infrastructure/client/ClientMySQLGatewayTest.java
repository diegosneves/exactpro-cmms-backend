package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Contact;
import org.diegosneves.exactprocmmsbackend.infrastructure.MySQLGatewayTest;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.diegosneves.exactprocmmsbackend.infrastructure.contact.ContactMySQLGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

}
