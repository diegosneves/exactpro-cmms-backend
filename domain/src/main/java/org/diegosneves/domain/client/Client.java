package org.diegosneves.domain.client;

import org.diegosneves.domain.Entity;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.utils.Cleaner;
import org.diegosneves.domain.validation.ValidationHandler;

public class Client extends Entity<ClientID> {

    private String cnpj;
    private Address address;
    private Contact contact;
    private String companyName;
    private String companyBranch;
    private String companySector;

    private Client(final ClientID id, final String cnpj, final Address address, final Contact contact, final String companyName, final String companyBranch, final String companySector) {
        super(id);
        this.cnpj = cnpj;
        this.address = address;
        this.contact = contact;
        this.companyName = companyName;
        this.companyBranch = companyBranch;
        this.companySector = companySector;
    }

    public static Client newClient(final String cnpj, final Address address, final Contact contact, final String companyName, final String companyBranch, final String companySector) {
        final var id = ClientID.unique();
        return new Client(id, Cleaner.string(cnpj), address, contact, Cleaner.string(companyName), Cleaner.string(companyBranch), Cleaner.string(companySector));
    }

    public static Client with(final Client aClient) {
        return with(
                aClient.getId(),
                aClient.getCnpj(),
                aClient.getAddress(),
                aClient.getContact(),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector()
        );
    }

    public static Client with(
            final ClientID anId,
            final String cnpj,
            final Address anAddress,
            final Contact aContact,
            final String companyName,
            final String companyBranch,
            final String companySector)
    {
        return new Client(
                anId,
                cnpj,
                anAddress,
                aContact,
                companyName,
                companyBranch,
                companySector
        );
    }

    public Client update(final String cnpj, final Address address, final Contact contact, final String companyName, final String companyBranch, final String companySector) {
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

    public Contact getContact() {
        return contact;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ClientValidator(this, handler).validate();
    }
}
