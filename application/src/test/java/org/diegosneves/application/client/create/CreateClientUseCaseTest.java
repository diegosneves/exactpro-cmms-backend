package org.diegosneves.application.client.create;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {

    @InjectMocks
    private DefaultCreateClientUseCase useCase;

    @Mock
    private ClientGateway clientGateway;


    @Test
    void shouldCreateClientGivenValidInput() {
        final var cnpj = "34494244000190";
        final var address = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var contact = new Contact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);

        when(this.clientGateway.create(any(Client.class))).thenAnswer(returnsFirstArg());

        final var actualOutput = this.useCase.execute(command).get();

        verify(this.clientGateway, times(1)).create(argThat(aClient -> Objects.equals(cnpj, aClient.getCnpj()) &&
                Objects.equals(address, aClient.getAddress()) &&
                Objects.nonNull(aClient.getId()) &&
                Objects.equals(contact, aClient.getContact()) &&
                Objects.equals(companyName, aClient.getCompanyName()) &&
                Objects.equals(companyBranch, aClient.getCompanyBranch()) &&
                Objects.equals(companySector, aClient.getCompanySector())));

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.clientID());
    }

    @Test
    void givenAnInvalidParamWhenCreatingClientUseCaseShouldThrowException() {
        final var cnpj = "34494244000191";
        final var address = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var contact = new Contact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);


        final var actualOutput = this.useCase.execute(command).getLeft();

        verify(this.clientGateway, never()).create(any());

        assertNotNull(actualOutput);
        assertEquals(1, actualOutput.getErrors().size());
        assertEquals("O segundo dígito verificador do CNPJ não é válido.", actualOutput.findFirst().message());
    }

    @Test
    void shouldCreateClientWithValidInputButWithoutAddressAndContact() {
        final var cnpj = "34494244000190";
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, null, null, companyName, companyBranch, companySector);

        when(this.clientGateway.create(any(Client.class))).thenAnswer(returnsFirstArg());

        final var actualOutput = this.useCase.execute(command).get();

        verify(this.clientGateway, times(1)).create(argThat(aClient -> Objects.equals(cnpj, aClient.getCnpj()) &&
                Objects.isNull(aClient.getAddress()) &&
                Objects.nonNull(aClient.getId()) &&
                Objects.isNull(aClient.getContact()) &&
                Objects.equals(companyName, aClient.getCompanyName()) &&
                Objects.equals(companyBranch, aClient.getCompanyBranch()) &&
                Objects.equals(companySector, aClient.getCompanySector())));

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.clientID());
    }

    @Test
    void shouldCreateClientWithValidInputButWithoutAddressAnd() {
        final var cnpj = "34494244000190";
        final var contact = new Contact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, null, contact, companyName, companyBranch, companySector);

        when(this.clientGateway.create(any(Client.class))).thenAnswer(returnsFirstArg());

        final var actualOutput = this.useCase.execute(command).get();

        verify(this.clientGateway, times(1)).create(argThat(aClient -> Objects.equals(cnpj, aClient.getCnpj()) &&
                Objects.isNull(aClient.getAddress()) &&
                Objects.nonNull(aClient.getId()) &&
                Objects.nonNull(aClient.getContact()) &&
                Objects.equals(companyName, aClient.getCompanyName()) &&
                Objects.equals(companyBranch, aClient.getCompanyBranch()) &&
                Objects.equals(companySector, aClient.getCompanySector())));

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.clientID());
    }

    @Test
    void givenValidCreateClientCommand_whenGatewayThrowsException_thenExceptionIsPropagated() {
        final var cnpj = "34494244000190";
        final var address = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var contact = new Contact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";
        final var expectErrorMessage = "Falha gerada pelo Gateway";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);

        when(this.clientGateway.create(any())).thenThrow(new IllegalStateException(expectErrorMessage));

        final var notification = this.useCase.execute(command).getLeft();

        verify(this.clientGateway, times(1)).create(argThat(aClient -> Objects.equals(cnpj, aClient.getCnpj()) &&
                Objects.equals(address, aClient.getAddress()) &&
                Objects.nonNull(aClient.getId()) &&
                Objects.equals(contact, aClient.getContact()) &&
                Objects.equals(companyName, aClient.getCompanyName()) &&
                Objects.equals(companyBranch, aClient.getCompanyBranch()) &&
                Objects.equals(companySector, aClient.getCompanySector())));

        assertNotNull(notification);
        assertEquals(1, notification.getErrors().size());
        assertEquals(expectErrorMessage, notification.findFirst().message());
    }

}
