package org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
public class ClientJpaEntity {

    @Id
    private String id;
    @Column(name = "cnpj", nullable = false)
    private String cnpj;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressJpaEntity address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private ClientContactJpaEntity contact;
    @Column(name = "companyName", nullable = false)
    private String companyName;
    @Column(name = "companyBranch", nullable = false)
    private String companyBranch;
    @Column(name = "companySector", nullable = false)
    private String companySector;

    private ClientJpaEntity(
            final String id,
            final String cnpj,
            final AddressJpaEntity address,
            final ClientContactJpaEntity contact,
            final String companyName,
            final String companyBranch,
            final String companySector) {
        this.id = id;
        this.cnpj = cnpj;
        this.address = address;
        this.contact = contact;
        this.companyName = companyName;
        this.companyBranch = companyBranch;
        this.companySector = companySector;
    }

    public static ClientJpaEntity from(final Client aClient) {
        return new ClientJpaEntity(
                aClient.getId().getValue(),
                aClient.getCnpj(),
                AddressJpaEntity.from(aClient.getAddress()),
                ClientContactJpaEntity.from(aClient.getContact()),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector()
        );
    }

    public Client toAggregate() {
        return Client.with(
                ClientID.from(this.getId()),
                this.getCnpj(),
                this.getAddress().toAggregate(),
                this.getContact().toAggregate(),
                this.getCompanyName(),
                this.getCompanyBranch(),
                this.getCompanySector()
        );
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public AddressJpaEntity getAddress() {
        return address;
    }

    public void setAddress(AddressJpaEntity address) {
        this.address = address;
    }

    public ClientContactJpaEntity getContact() {
        return contact;
    }

    public void setContact(ClientContactJpaEntity contact) {
        this.contact = contact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBranch() {
        return companyBranch;
    }

    public void setCompanyBranch(String companyBranch) {
        this.companyBranch = companyBranch;
    }

    public String getCompanySector() {
        return companySector;
    }

    public void setCompanySector(String companySector) {
        this.companySector = companySector;
    }
}
