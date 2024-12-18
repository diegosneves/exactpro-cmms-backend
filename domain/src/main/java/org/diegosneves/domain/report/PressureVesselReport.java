package org.diegosneves.domain.report;

import org.diegosneves.domain.AggregateRoot;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.validation.ValidationHandler;

import java.time.Instant;

public class PressureVesselReport extends AggregateRoot<ReportID> {

    private Client client;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    private PressureVesselReport(ReportID reportID, Client client, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(reportID);
        this.client = client;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static PressureVesselReport newReport(Client client) {
        var id = ReportID.unique();
        var now = Instant.now();
        return new PressureVesselReport(id, client, now, now, null);
    }

    public Client getClient() {
        return client;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new PressureVesselReportValidator(this, handler).validate();
    }
}
