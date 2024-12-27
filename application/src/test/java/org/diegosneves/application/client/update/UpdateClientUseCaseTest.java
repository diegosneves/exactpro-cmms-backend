package org.diegosneves.application.client.update;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateClientUseCaseTest {

    @InjectMocks
    private DefaultUpdateClientUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @Test
    void shouldCreateClientGivenValidInput() {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var oldAddress = new Address("Rua2", "3332", "Bairro2", "Cidade2", "RS", "824567892");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var oldContact = new Contact("email2@email.com", "11111111");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var aClient = Client.newClient("90.265.697/0001-15", oldAddress, oldContact, "expectedCompanyName", "expectedCompanyBranch", "expectedCompanySector");
        final var expectedId = aClient.getId();

        when(this.clientGateway.findById(expectedId)).thenReturn(Optional.of(aClient));
        when(this.clientGateway.update(any(Client.class))).thenAnswer(returnsFirstArg());

        final var aCommand = UpdateClientCommand.with(
                expectedId.getValue(),
                expectedCnpj,
                expectedAddress,
                expectedContact,
                expectedCompanyName,
                expectedCompanyBranch,
                expectedCompanySector);

        final var actualOutput = this.useCase.execute(aCommand).get();

        verify(this.clientGateway, times(1)).findById(expectedId);
        verify(this.clientGateway, times(1)).update(argThat(updateClient -> Objects.equals(expectedId.getValue(), updateClient.getId().getValue()) &&
                Objects.equals(expectedCnpj, updateClient.getCnpj()) &&
                Objects.equals(expectedAddress, updateClient.getAddress()) &&
                Objects.equals(expectedContact, updateClient.getContact()) &&
                Objects.equals(expectedCompanyName, updateClient.getCompanyName()) &&
                Objects.equals(expectedCompanyBranch, updateClient.getCompanyBranch()) &&
                Objects.equals(expectedCompanySector, updateClient.getCompanySector())));

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.clientID());
    }

    @Test
    void givenAnInvalidParamWhenUpdateClientUseCaseShouldThrowException() {
        final var expectedCnpj = "34494244000191";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var oldAddress = new Address("Rua2", "3332", "Bairro2", "Cidade2", "RS", "824567892");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var oldContact = new Contact("email2@email.com", "11111111");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var aClient = Client.newClient("90.265.697/0001-15", oldAddress, oldContact, "expectedCompanyName", "expectedCompanyBranch", "expectedCompanySector");
        final var expectedId = aClient.getId();

        when(this.clientGateway.findById(expectedId)).thenReturn(Optional.of(aClient));

        final var aCommand = UpdateClientCommand.with(
                expectedId.getValue(),
                expectedCnpj,
                expectedAddress,
                expectedContact,
                expectedCompanyName,
                expectedCompanyBranch,
                expectedCompanySector);

        final var actualOutput = this.useCase.execute(aCommand).getLeft();

        verify(this.clientGateway, times(1)).findById(expectedId);
        verify(this.clientGateway, never()).update(any());

        assertNotNull(actualOutput);
        assertEquals(1, actualOutput.getErrors().size());
        assertEquals("O segundo dígito verificador do CNPJ não é válido.", actualOutput.findFirst().message());
    }

    @Test
    void givenAnInvalidIDWhenUpdateClientUseCaseShouldThrowException() {
        final var expectedCnpj = "34494244000190";
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var expectedCompanyName = "Company Name";
        final var expectedCompanyBranch = "Company Branch";
        final var expectedCompanySector = "Company Sector";

        final var expectedId = ClientID.from("f89f392c-8429-4760-9f39-2c8429876052");

        when(this.clientGateway.findById(expectedId)).thenReturn(Optional.empty());

        final var aCommand = UpdateClientCommand.with(
                expectedId.getValue(),
                expectedCnpj,
                expectedAddress,
                expectedContact,
                expectedCompanyName,
                expectedCompanyBranch,
                expectedCompanySector);

        final var actualOutput = assertThrows(DomainException.class, () -> this.useCase.execute(aCommand));

        verify(this.clientGateway, times(1)).findById(expectedId);
        verify(this.clientGateway, never()).update(any());

        assertNotNull(actualOutput);
        assertEquals(1, actualOutput.getErrors().size());
        assertEquals(DefaultUpdateClientUseCase.CLIENT_NOT_FOUND_MESSAGE.formatted(expectedId.getValue()), actualOutput.getErrors().get(0).message());
    }

}
