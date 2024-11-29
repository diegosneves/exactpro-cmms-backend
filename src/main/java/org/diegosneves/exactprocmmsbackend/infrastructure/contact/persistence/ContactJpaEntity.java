package org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Contact;

import java.util.Objects;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@Getter
@Setter
public class ContactJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;

    private ContactJpaEntity(final String email, final String phone) {
        this.email = email;
        this.phone = phone;
    }

    public static ContactJpaEntity from(final Contact aContact) {
        return aContact == null ? null : new ContactJpaEntity(aContact.getEmail(), aContact.getPhone());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactJpaEntity that = (ContactJpaEntity) o;
        return Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPhone());
    }

    public Contact toAggregate() {
        return new Contact(this.getEmail(), this.getPhone());
    }
}
