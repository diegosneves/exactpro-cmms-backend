package org.diegosneves.domain.report;

import org.diegosneves.domain.Identifier;

import java.util.UUID;

public class ReportID extends Identifier {

    private final String value;

    private ReportID(final String value) {
        this.value = value;
    }

    public static ReportID unique() {
        return ReportID.from(UUID.randomUUID());
    }

    public static ReportID from(final String value) {
        return new ReportID(value);
    }

    public static ReportID from(UUID value) {
        return new ReportID(value.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
