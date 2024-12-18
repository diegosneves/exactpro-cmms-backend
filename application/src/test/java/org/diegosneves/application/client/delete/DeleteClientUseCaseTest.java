package org.diegosneves.application.client.delete;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseTest {

    @InjectMocks
    private DefaultDeleteClientUseCase useCase;
    @Mock
    private ClientGateway clientGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(clientGateway);
    }

    @Test
    void shouldDeleteClientWhenReceiveAValidID() {
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var aClient = Client.newClient("90.265.697/0001-15", expectedAddress, expectedContact, "expectedCompanyName", "expectedCompanyBranch", "expectedCompanySector");
        final var expectedId = aClient.getId();

        doNothing().when(this.clientGateway).deleteById(expectedId);

        assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        verify(this.clientGateway, times(1)).deleteById(expectedId);

    }

    @Test
    void shouldNotDeleteClientWhenReceiveAnInvalidID() {
        final var expectedId = ClientID.from("123456789");

        doNothing().when(this.clientGateway).deleteById(expectedId);

        assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        verify(this.clientGateway, times(1)).deleteById(expectedId);
    }

    @Test
    void shouldReturnAnExceptionWhenClientGatewayThrowsAnException() {
        final var expectedAddress = new Address("Rua", "333", "Bairro", "Cidade", "RS", "82456789");
        final var expectedContact = new Contact("email@email.com", "12334567896");
        final var aClient = Client.newClient("90.265.697/0001-15", expectedAddress, expectedContact, "expectedCompanyName", "expectedCompanyBranch", "expectedCompanySector");
        final var expectedId = aClient.getId();
        final var clientId = expectedId.getValue();

        doThrow(new IllegalStateException("Gateway Error")).when(this.clientGateway).deleteById(expectedId);

        final var actualException = assertThrows(IllegalStateException.class, () -> this.useCase.execute(clientId));

        verify(this.clientGateway, times(1)).deleteById(expectedId);

        assertNotNull(actualException);
        assertEquals(IllegalStateException.class, actualException.getClass());
    }

}
