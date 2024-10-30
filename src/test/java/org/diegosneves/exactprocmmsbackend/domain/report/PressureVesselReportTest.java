package org.diegosneves.exactprocmmsbackend.domain.report;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.report.valueobject.Client;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PressureVesselReportTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenValidsParamWhenCallNewReportAndValidateThenShouldReturnAPressureVesselReport() {

        Client client = new Client("Exatidão", "Sapucaia do Sul - RS", "RH/Dpto Comercial/Engenharia");

        PressureVesselReport newReport = PressureVesselReport.newReport(client);
        newReport.validate(new ThrowsValidationHandler());

        assertNotNull(newReport);
        assertNotNull(newReport.getClient());
        assertEquals(client, newReport.getClient());
        assertNotNull(newReport.getCreatedAt());
        assertNotNull(newReport.getUpdatedAt());
        assertNotNull(newReport.getId());
        assertNull(newReport.getDeletedAt());
    }


    @Test
    void givenANullParamWhenCallNewReportAndValidateThenShouldThrowsException() {
        final String expectErrorMessage = "Client data cannot be null";
        final int expectErrorCount = 1;

        final var actualReport = PressureVesselReport.newReport(null);
        final var actualException = assertThrows(DomainException.class, () -> actualReport.validate(new ThrowsValidationHandler()));

        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(expectErrorMessage, actualException.getErrors().get(0).message());

    }

}
