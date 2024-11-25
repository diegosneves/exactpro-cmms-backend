package org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;

import java.util.Objects;

@Entity
@Table(name = "client_contacts")
@NoArgsConstructor
public class ClientContactJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;

    private ClientContactJpaEntity(final String email, final String phone) {
        this.email = email;
        this.phone = phone;
    }

    public static ClientContactJpaEntity from(final ClientContact aContact) {
        return new ClientContactJpaEntity(aContact.getEmail(), aContact.getPhone());
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClientContactJpaEntity that = (ClientContactJpaEntity) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPhone());
    }

    public ClientContact toAggregate() {
        return new ClientContact(this.getEmail(), this.getPhone());
    }
}
