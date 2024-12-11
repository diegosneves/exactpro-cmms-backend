package org.diegosneves.infrastructure.application.integration.create;

import org.diegosneves.infrastructure.IntegrationTest;
import org.diegosneves.application.client.create.CreateClientCommand;
import org.diegosneves.application.client.create.CreateClientUseCase;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationTest
class CreateClientUseCaseIT {

    @Autowired
    private CreateClientUseCase useCase;

    @Autowired
    private ClientRepository repository;


    @BeforeEach
    void cleanUp() {
        this.repository.deleteAll();
    }

    @Test
    void shouldCreateClientGivenValidInput() {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var command = CreateClientCommand.with(expectedCnpj, expectedAddress, expectedContact, expectedCompanyName, expectedCompanyBranch, expectedCompanySector);

        assertEquals(0, this.repository.count());

        final var actualOutput = this.useCase.execute(command).get();

        assertEquals(1, this.repository.count());

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.clientID());

        final var entity = this.repository.findById(actualOutput.clientID().getValue()).orElse(null);
        assertNotNull(entity);
        assertEquals(expectedCnpj, entity.getCnpj());
        assertNotNull(entity.getAddress());
        assertNotNull(entity.getContact());
        assertEquals(expectedCompanyName, entity.getCompanyName());
        assertEquals(expectedCompanyBranch, entity.getCompanyBranch());
        assertEquals(expectedCompanySector, entity.getCompanySector());

    }

    @Test
    void givenValidCreateClientCommand_whenGatewayThrowsException_thenExceptionIsPropagated() {
        final var cnpj = "";
        final var address = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var contact = new Contact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";
        final var expectErrorMessage = "O CNPJ não deve ser nulo ou vazio";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);

        assertEquals(0, this.repository.count());

        final var notification = this.useCase.execute(command).getLeft();

        assertEquals(0, this.repository.count());

        assertNotNull(notification);
        assertEquals(1, notification.getErrors().size());
        assertEquals(expectErrorMessage, notification.findFirst().message());

    }


}
