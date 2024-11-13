package org.diegosneves.exactprocmmsbackend.application.client.retrieve.list;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientSearchQuery;
import org.diegosneves.exactprocmmsbackend.domain.pagination.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListClientsUseCaseTest {

    @InjectMocks
    private DefaultListClientsUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(this.clientGateway);
    }

    @Test
    void shouldReturnAllClientsWhenReceiveAValidQuery() {
        final var clients = List.of(
                Client.newClient("90.265.697/0001-15", null, null, "ACompanyName", "expectedCompanyBranch", "expectedCompanySector"),
                Client.newClient("90508117000173", null, null, "BCompanyName", "expectedCompanyBranch2", "expectedCompanySector2"));
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "companyName";
        final var expectedDirection = "asc";
        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, (long) clients.size(), clients);
        final var expectedItensCount = 2;
        final var expectedResult = expectedPagination.map(ClientListOutput::from);

        when(this.clientGateway.findAll(aQuery)).thenReturn(expectedPagination);

        final var actualResult = this.useCase.execute(aQuery);

        assertNotNull(actualResult);
        assertEquals(expectedItensCount, actualResult.items().size());
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(clients.size(), actualResult.total());
    }

    @Test
    void shouldReturnEmptyListWhenReceiveAValidQueryAndNoHasClients() {
        final var clients = List.<Client>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "companyName";
        final var expectedDirection = "asc";

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);
        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, (long) clients.size(), clients);
        final var expectedItensCount = 0;
        final var expectedResult = expectedPagination.map(ClientListOutput::from);

        when(this.clientGateway.findAll(aQuery)).thenReturn(expectedPagination);

        final var actualResult = this.useCase.execute(aQuery);

        assertNotNull(actualResult);
        assertEquals(expectedItensCount, actualResult.items().size());
        assertEquals(expectedResult, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(clients.size(), actualResult.total());
    }

    @Test
    void shouldThrowExceptionWhenValidQueryCausesGatewayError() {
        final var errorMessage = "Gateway error";
        final var clients = List.<Client>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "companyName";
        final var expectedDirection = "asc";

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);

        when(this.clientGateway.findAll(any())).thenThrow(new IllegalStateException(errorMessage));

        final var actualResult = assertThrows(IllegalStateException.class, () -> this.useCase.execute(aQuery));

        assertNotNull(actualResult);
        assertEquals(errorMessage, actualResult.getMessage());
    }

}
