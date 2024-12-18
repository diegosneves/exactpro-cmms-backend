package org.diegosneves.infrastructure.application.integration.retrieve.get;

import org.diegosneves.application.client.retrieve.get.DefaultGetClientByIdUseCase;
import org.diegosneves.application.client.retrieve.get.GetClientByIdUseCase;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.infrastructure.IntegrationTest;
import org.diegosneves.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class GetClientByIdUseCaseIT {

    @Autowired
    private GetClientByIdUseCase useCase;

    @Autowired
    private ClientRepository repository;


    @BeforeEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    @Test
    void shouldBeRetrievedFromGatewayAValidClientWhenReceiveAValidClientId() {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var client = Client.newClient(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);
        final var expectedId = client.getId();

        this.save(client);

        assertEquals(1, this.repository.count());

        final var actualClient = this.useCase.execute(expectedId.getValue());

        assertEquals(1, this.repository.count());

        assertNotNull(actualClient);
        assertEquals(expectedCnpj, actualClient.cnpj());
        assertEquals(expectedAddress, actualClient.address());
        assertEquals(expectedContact, actualClient.contact());
        assertEquals(expectedCompanyName, actualClient.companyName());
        assertEquals(expectedCompanyBranch, actualClient.companyBrach());
        assertEquals(expectedCompanySector, actualClient.companySector());
    }

    private void save(final Client... clients) {
        this.repository.saveAllAndFlush(Arrays.stream(clients).map(ClientJpaEntity::from).toList());
    }

    @Test
    void shouldNotBeRetrievedFromGatewayAValidClientWhenReceiveAnInvalidClientId() {
        final var expectedId = ClientID.from("123456789").getValue();


        final var actualException = assertThrows(DomainException.class, () -> this.useCase.execute(expectedId));

        assertNotNull(actualException);
        assertEquals(1, actualException.getErrors().size());
        assertEquals(DefaultGetClientByIdUseCase.CLIENT_NOT_FOUND_MESSAGE.formatted(expectedId), actualException.getMessage());
    }


}
