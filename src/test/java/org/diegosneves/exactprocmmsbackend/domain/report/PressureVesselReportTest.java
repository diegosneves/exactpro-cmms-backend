package org.diegosneves.exactprocmmsbackend.domain.report;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PressureVesselReportTest {


    @Test
    void givenValidsParamWhenCallNewReportAndValidateThenShouldReturnAPressureVesselReport() {

        Client client = Client.newClient("40294568000187","Exatidão", "Sapucaia do Sul - RS", "RH/Dpto Comercial/Engenharia");

        PressureVesselReport newReport = PressureVesselReport.newReport(client);

        assertNotNull(newReport);
        assertDoesNotThrow(() -> newReport.validate(new ThrowsValidationHandler()));
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
