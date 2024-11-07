package org.diegosneves.exactprocmmsbackend.application.client.create;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientValidator;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;
import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
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
        final var contact = new ClientContact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);

        when(this.clientGateway.create(any(Client.class))).thenAnswer(returnsFirstArg());

        final var actualOutput = this.useCase.execute(command);

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
        final var contact = new ClientContact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var command = CreateClientCommand.with(cnpj, address, contact, companyName, companyBranch, companySector);


        final var actualOutput = assertThrows(DomainException.class, () -> this.useCase.execute(command));

        verify(this.clientGateway, never()).create(any());

        assertNotNull(actualOutput);
        assertEquals(1, actualOutput.getErrors().size());
        assertEquals("O segundo dígito verificador do CNPJ não é válido.", actualOutput.getErrors().get(0).message());
    }

    // TODO - Passando apenas parametros necessarios

    // TODO - Recebendo um erro generico do ClientGateway

}
