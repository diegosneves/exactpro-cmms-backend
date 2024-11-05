package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.Entity;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

public class Client extends Entity<ClientID> {

    private final String cnpj;
    private final Address address;
    private final ClientContact contact;
    private final String companyName;
    private final String companyBranch;
    private final String companySector;

    private Client(final ClientID id, final String cnpj, final Address address, final ClientContact contact, final String companyName, final String companyBranch, final String companySector) {
        super(id);
        this.cnpj = cnpj;
        this.address = address;
        this.contact = contact;
        this.companyName = companyName;
        this.companyBranch = companyBranch;
        this.companySector = companySector;
    }

    public static Client newClient(final String cnpj, final Address address, final ClientContact contact, final String companyName, final String companyBranch, final String companySector) {
        final var id = ClientID.unique();
        return new Client(id, cnpj.trim(), address, contact, companyName.trim(), companyBranch.trim(), companySector.trim());
    }

    public static Client newClient(final String cnpj, final String companyName, final String companyBranch, final String companySector) {
        final var id = ClientID.unique();
        return new Client(id, cnpj, null, null, companyName, companyBranch, companySector);
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public String getCompanyBranch() {
        return this.companyBranch;
    }

    public String getCompanySector() {
        return this.companySector;
    }

    public ClientContact getContact() {
        return contact;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ClientValidator(this, handler).validate();
    }
}
