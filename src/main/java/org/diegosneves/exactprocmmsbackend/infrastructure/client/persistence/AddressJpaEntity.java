package org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;

import java.util.Objects;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
public class AddressJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "zip", nullable = false)
    private String zip;

    private AddressJpaEntity(
            final String street,
            final String number,
            final String neighborhood,
            final String city,
            final String state,
            final String zip) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public static AddressJpaEntity from(final Address anAddress) {
        return new AddressJpaEntity(
                anAddress.getStreet(),
                anAddress.getNumber(),
                anAddress.getNeighborhood(),
                anAddress.getCity(),
                anAddress.getState(),
                anAddress.getZip()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddressJpaEntity that = (AddressJpaEntity) o;
        return Objects.equals(getNumber(), that.getNumber()) && Objects.equals(getZip(), that.getZip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getZip());
    }

    public Address toAggregate() {
        return new Address(
                this.getStreet(),
                this.getNumber(),
                this.getNeighborhood(),
                this.getCity(),
                this.getState(),
                this.getZip()
        );
    }
}
