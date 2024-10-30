package org.diegosneves.exactprocmmsbackend.domain.report.valueobject;

import org.diegosneves.exactprocmmsbackend.domain.ValueObject;

import java.util.Objects;

public class Client extends ValueObject {

    private final String companyName;
    private final String companyBranch;
    private final String companySector;

    public Client(final String companyName, final String companyBranch, final String companySector) {
        this.companyName = companyName;
        this.companyBranch = companyBranch;
        this.companySector = companySector;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyBranch() {
        return companyBranch;
    }

    public String getCompanySector() {
        return companySector;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Client client = (Client) object;
        return Objects.equals(getCompanyName(),
                client.getCompanyName()) && Objects.equals(getCompanyBranch(),
                client.getCompanyBranch()) && Objects.equals(getCompanySector(),
                client.getCompanySector());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompanyName(), getCompanyBranch(), getCompanySector());
    }
}
