package org.diegosneves.infrastructure.application.integration.retrieve.list;

import org.diegosneves.application.client.retrieve.list.ListClientsUseCase;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientSearchQuery;
import org.diegosneves.infrastructure.IntegrationTest;
import org.diegosneves.infrastructure.client.persistence.ClientJpaEntity;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
class ListClientsUseCaseTestIT {

    @Autowired
    private ListClientsUseCase useCase;

    @Autowired
    private ClientRepository repository;


    @BeforeEach
    void mockUp() {
        this.repository.deleteAll();
        final var clients = Stream.of(
                Client.newClient("42643256000167", null, null, "Hotel", "Esteio", "Banheiro"),
                Client.newClient("28793666000163", null, null, "Zulu", "Canoas", "Garagem"),
                Client.newClient("59112564000169", null, null, "Romeo", "Gramado", "Sala"),
                Client.newClient("38022337000172", null, null, "Alfa", "Ivorá", "Cozinha")
        ).map(ClientJpaEntity::from).toList();
        this.repository.saveAllAndFlush(clients);
    }


    @Test
    void givenAValidTermWhenTermDoesntMatchsPrePersistThenShouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerm = "jkjkjk";
        final var expectedSort = "cnpj";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0L;
        final var expectedTotal = 0L;

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerm, expectedSort, expectedDirection);

        final var actualResult = this.useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
    }

    @ParameterizedTest
    @CsvSource({
            "ZU,0,10,1,1,Zulu",
            "al,0,10,1,1,Alfa",
            "rome,0,10,1,1,Romeo",
            "hOT,0,10,1,1,Hotel"
    })
    void givenAValidTermWhenCallsListClientsUseCaseThenShouldReturnClientsFiltered(
            final String expectedTerm,
            final Integer expectedPage,
            final Integer expectedPerPage,
            final Integer expectedItemsCount,
            final Long expectedTotal,
            final String expectedCompanyName
    ) {

        final var expectedSort = "companyName";
        final var expectedDirection = "asc";

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerm, expectedSort, expectedDirection);

        final var actualResult = this.useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedCompanyName, actualResult.items().get(0).companyName());
    }

    @ParameterizedTest
    @CsvSource({
            "companyName,asc,0,10,4,4,Alfa",
            "companyName,desc,0,10,4,4,Zulu",
            "companySector,asc,0,10,4,4,Hotel",
            "companySector,desc,0,10,4,4,Romeo"
    })
    void givenAValidSortAndDirectionWhenCallsListClientsUseCaseThenShouldReturnClientsSorted(
            final String expectedSort,
            final String expectedDirection,
            final Integer expectedPage,
            final Integer expectedPerPage,
            final Integer expectedItemsCount,
            final Long expectedTotal,
            final String expectedCompanyName
    ) {
        final var expectedTerm = "";

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerm, expectedSort, expectedDirection);

        final var actualResult = this.useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedCompanyName, actualResult.items().get(0).companyName());

    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,4,Alfa;Hotel",
            "1,2,2,4,Romeo;Zulu",
    })
    void givenAValidPageWhenCallsListClientsUseCaseThenShouldReturnClientsPaginated(
            final Integer expectedPage,
            final Integer expectedPerPage,
            final Integer expectedItemsCount,
            final Long expectedTotal,
            final String expectedCompanyName
    ) {

        final var expectedSort = "companyName";
        final var expectedDirection = "asc";
        final var expectedTerm = "";

        final var aQuery = new ClientSearchQuery(expectedPage, expectedPerPage, expectedTerm, expectedSort, expectedDirection);

        final var actualResult = this.useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String expectedName : expectedCompanyName.split(";")) {
            final var actualName = actualResult.items().get(index++).companyName();
            assertEquals(expectedName, actualName);
        }

    }


}
