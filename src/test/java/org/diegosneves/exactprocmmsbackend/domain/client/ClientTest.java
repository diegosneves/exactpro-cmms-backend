package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientTest {

    private static final String CNPJ_NULL_OR_EMPTY_ERROR = "O CNPJ não deve ser nulo ou vazio";
    private static final String INVALID_CNPJ_LENGTH = "CNPJ deve ter 14 dígitos";
    private static final String INVALID_FIRST_DIGIT = "O primeiro dígito verificador do CNPJ não é válido.";
    private static final String INVALID_SECOND_DIGIT = "O segundo dígito verificador do CNPJ não é válido.";


    @Test
    void givenAValidParamWhenCallNewClientAndValidateThenShouldReturnAClient() {
        final String cnpj = "24888114000188";
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);

        assertNotNull(actual);
        assertDoesNotThrow(() -> actual.validate(new ThrowsValidationHandler()));
        assertEquals(cnpj, actual.getCnpj());
        assertEquals(companyName, actual.getCompanyName());
        assertEquals(companyBranch, actual.getCompanyBranch());
        assertEquals(companySector, actual.getCompanySector());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenAEmptyCnpjWhenCallNewClientAndValidateThenShouldThrowAnException(String cnpj) {
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(CNPJ_NULL_OR_EMPTY_ERROR, actualException.getErrors().get(0).message());
    }

    @Test
    void givenANullCnpjWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = null;
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(CNPJ_NULL_OR_EMPTY_ERROR, actualException.getErrors().get(0).message());
    }

    @Test
    void givenACnpjWithFirstDigitInvalidWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-76";
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(INVALID_FIRST_DIGIT, actualException.getErrors().get(0).message());
    }

    @Test
    void givenACnpjWithSecondDigitInvalidWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-97";
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(INVALID_SECOND_DIGIT, actualException.getErrors().get(0).message());
    }

    @Test
    void givenACnpjWithInvalidSizeWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-7";
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(INVALID_CNPJ_LENGTH, actualException.getErrors().get(0).message());
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenAEmptyCompanyNameWhenCallNewClientAndValidateThenShouldThrowAnException(String companyName) {
        final String cnpj = "22.675.463/0001-96";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company name is required", actualException.getErrors().get(0).message());
    }

    @Test
    void givenANullCompanyNameWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-96";
        final String companyBranch = "Esteio/RS";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, null, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company name is required", actualException.getErrors().get(0).message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenAEmptyCompanyBranchWhenCallNewClientAndValidateThenShouldThrowAnException(String companyBranch) {
        final String cnpj = "22.675.463/0001-96";
        final String companyName = "Company Name";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company branch is required", actualException.getErrors().get(0).message());
    }

    @Test
    void givenANullCompanyBranchWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-96";
        final String companyName = "Company Name";
        final String companySector = "Caldeiraria / Funelaria";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, null, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company branch is required", actualException.getErrors().get(0).message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void givenAEmptyCompanySectorWhenCallNewClientAndValidateThenShouldThrowAnException(String companySector) {
        final String cnpj = "22.675.463/0001-96";
        final String companyName = "Company Name";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyName, companySector);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company sector is required", actualException.getErrors().get(0).message());
    }

    @Test
    void givenANullCompanySectorWhenCallNewClientAndValidateThenShouldThrowAnException() {
        final String cnpj = "22.675.463/0001-96";
        final String companyName = "Company Name";
        final String companyBranch = "Esteio/RS";
        final int expectErrorCount = 1;

        final var actual = Client.newClient(cnpj, companyName, companyBranch, null);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals("Company sector is required", actualException.getErrors().get(0).message());
    }



}
