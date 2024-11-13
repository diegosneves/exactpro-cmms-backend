package org.diegosneves.exactprocmmsbackend.application.client.retrieve.get;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;
import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetClientByIdUseCaseTest {

    @InjectMocks
    private DefaultGetClientByIdUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(this.clientGateway);
    }

    @Test
    void shouldBeRetrievedFromGatewayAValidClientWhenReceiveAValidClientId() {
        final var cnpj = "34494244000190";
        final var address = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var contact = new ClientContact("email@email.com", "12334567896");
        final var companyName = "Company Name";
        final var companyBranch = "Company Branch";
        final var companySector = "Company Sector";

        final var client = Client.newClient(cnpj, address, contact, companyName, companyBranch, companySector);
        final var expectedId = client.getId();

        when(this.clientGateway.findById(expectedId)).thenReturn(Optional.of(client));

        final var actualClient = this.useCase.execute(expectedId.getValue());

        assertNotNull(actualClient);
        assertEquals(cnpj, actualClient.cnpj());
        assertEquals(address, actualClient.address());
        assertEquals(contact, actualClient.contact());
        assertEquals(companyName, actualClient.companyName());
        assertEquals(companyBranch, actualClient.companyBrach());
        assertEquals(companySector, actualClient.companySector());
    }

    @Test
    void shouldNotBeRetrievedFromGatewayAValidClientWhenReceiveAnInvalidClientId() {
        final var expectedId = ClientID.from("123456789");

        when(this.clientGateway.findById(expectedId)).thenReturn(Optional.empty());

        final var actualException = assertThrows(DomainException.class, () -> this.useCase.execute(expectedId.getValue()));

        assertNotNull(actualException);
        assertEquals(1, actualException.getErrors().size());
        assertEquals(DefaultGetClientByIdUseCase.CLIENT_NOT_FOUND_MESSAGE.formatted(expectedId.getValue()), actualException.getMessage());
    }

    @Test
    void shouldBeReceiveFromGatewayAnException() {
        final String errorMessage = "Gateway error";
        final var expectedId = ClientID.from("123456789");

        when(this.clientGateway.findById(expectedId)).thenThrow(new IllegalStateException(errorMessage));

        final var actualException = assertThrows(IllegalStateException.class, () -> this.useCase.execute(expectedId.getValue()));

        assertNotNull(actualException);
        assertEquals(errorMessage, actualException.getMessage());
    }

}
