package org.diegosneves.infrastructure.application.integration.delete;

import org.diegosneves.application.client.delete.DeleteClientUseCase;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.infrastructure.IntegrationTest;
import org.diegosneves.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
class DeleteClientUseCaseIT {

    @Autowired
    private DeleteClientUseCase useCase;

    @Autowired
    private ClientRepository repository;


    @BeforeEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    private void save(final Client... clients) {
        this.repository.saveAllAndFlush(Arrays.stream(clients).map(ClientJpaEntity::from).toList());
    }

    @Test
    void shouldDeleteClientWhenReceiveAValidID() {
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var aClient = Client.newClient("90.265.697/0001-15", expectedAddress, expectedContact, "expectedCompanyName", "expectedCompanyBranch", "expectedCompanySector");
        final var expectedId = aClient.getId();

        this.save(aClient);

        assertEquals(1, this.repository.count());

        assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        assertEquals(0, this.repository.count());

    }

    @Test
    void shouldNotDeleteClientWhenReceiveAnInvalidID() {
        final var expectedId = ClientID.from("123456789");

        assertEquals(0, this.repository.count());

        assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        assertEquals(0, this.repository.count());
    }


}
