package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.Entity;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;
import org.diegosneves.exactprocmmsbackend.domain.utils.Cleaner;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

public class Client extends Entity<ClientID> {

    private String cnpj;
    private Address address;
    private ClientContact contact;
    private String companyName;
    private String companyBranch;
    private String companySector;

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
        return new Client(id, Cleaner.string(cnpj), address, contact, Cleaner.string(companyName), Cleaner.string(companyBranch), Cleaner.string(companySector));
    }

    public Client update(final String cnpj, final Address address, final ClientContact contact, final String companyName, final String companyBranch, final String companySector) {
        this.cnpj = cnpj;
        this.address = address;
        this.contact = contact;
        this.companyName = companyName;
        this.companyBranch = companyBranch;
        this.companySector = companySector;

        return this;
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
