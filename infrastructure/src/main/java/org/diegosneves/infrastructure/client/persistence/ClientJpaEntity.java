package org.diegosneves.infrastructure.client.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.diegosneves.infrastructure.address.persistence.AddressJpaEntity;
import org.diegosneves.infrastructure.contact.persistence.ContactJpaEntity;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
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
    private ContactJpaEntity contact;
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
            final ContactJpaEntity contact,
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
                ContactJpaEntity.from(aClient.getContact()),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector()
        );
    }

    public Client toAggregate() {
        return Client.with(
                ClientID.from(this.getId()),
                this.getCnpj(),
                this.getAddress() != null ? this.getAddress().toAggregate() : null,
                this.getContact() != null ? this.getContact().toAggregate() : null,
                this.getCompanyName(),
                this.getCompanyBranch(),
                this.getCompanySector()
        );
    }

}
