package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Contact;
import org.diegosneves.exactprocmmsbackend.infrastructure.MySQLGatewayTest;
import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MySQLGatewayTest
class ClientMySQLGatewayTest {

    @Autowired
    private ClientMySQLGateway gateway;

    @Autowired
    private ClientRepository repository;


    @Test
    void testInjectedDependencies() {
        assertNotNull(gateway);
        assertNotNull(repository);
    }

    @Test
    void givenAValidClientWhenCallsCreateShouldReturnANewClient() {
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

}
