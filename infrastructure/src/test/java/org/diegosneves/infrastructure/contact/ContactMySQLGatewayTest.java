package org.diegosneves.infrastructure.contact;

import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.infrastructure.MySQLGatewayTest;
import org.diegosneves.infrastructure.contact.persistence.ContactJpaEntity;
import org.diegosneves.infrastructure.contact.persistence.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MySQLGatewayTest
class ContactMySQLGatewayTest {

    @Autowired
    private ContactMySQLGateway gateway;

    @Autowired
    private ContactRepository repository;



    @Test
    void givenAValidContactWhenCallsCreateShouldReturnANewContact() {
        final var expectedEmail = "email@gmail.com";
        final var expectedPhone = "11998789987";

        final var aContact = new Contact(expectedEmail, expectedPhone);

        assertEquals(0, this.repository.count());

        final var actualContact = this.gateway.create(aContact);

        assertEquals(1, this.repository.count());

        assertNotNull(actualContact);
        assertEquals(expectedEmail, actualContact.getEmail());
        assertEquals(expectedPhone, actualContact.getPhone());

        final ContactJpaEntity existingContact = this.repository.findContactJpaEntitiesByEmailAndPhone(expectedEmail, expectedPhone).orElse(null);

        assertNotNull(existingContact);
        assertNotNull(existingContact.getId());
        assertEquals(expectedEmail, existingContact.getEmail());
        assertEquals(expectedPhone, existingContact.getPhone());
    }

    @Test
    void givenAnExistingContactWhenCallsCreateShouldReturnAExistingContact() {
        final var expectedEmail = "email@gmail.com";
        final var expectedPhone = "11998789987";

        final var aContact = new Contact(expectedEmail, expectedPhone);

        this.repository.save(ContactJpaEntity.from(aContact));

        assertEquals(1, repository.count());

        final var actualContact = this.gateway.create(aContact);

        assertEquals(1, this.repository.count());

        assertNotNull(actualContact);
        assertEquals(expectedEmail, actualContact.getEmail());
        assertEquals(expectedPhone, actualContact.getPhone());

        final ContactJpaEntity existingContact = this.repository.findContactJpaEntitiesByEmailAndPhone(expectedEmail, expectedPhone).orElse(null);

        assertNotNull(existingContact);
        assertNotNull(existingContact.getId());
        assertEquals(expectedEmail, existingContact.getEmail());
        assertEquals(expectedPhone, existingContact.getPhone());
    }

    @Test
    void givenAContactWithWrongEmailWhenCallsCreateShouldReturnANotification() {
        final var expectedEmail = "email";
        final var expectedPhone = "11998789987";
        final var expectErrorCount = 1;
        final var expectErrorMessage = "Formato de e-mail inválido!";

        final var aContact = new Contact(expectedEmail, expectedPhone);

        assertEquals(0, repository.count());

        final var notification = assertThrows(DomainException.class, () -> this.gateway.create(aContact));

        assertEquals(0, this.repository.count());

        assertNotNull(notification);
        assertEquals(expectErrorCount, notification.getErrors().size());
        assertEquals(expectErrorMessage, notification.getErrors().get(0).message());

    }

    @Test
    void givenAContactWithWrongPhoneWhenCallsCreateShouldReturnANotification() {
        final var expectedEmail = "email@gmail.com";
        final var expectedPhone = "Phone";
        final var expectErrorCount = 1;
        final var expectErrorMessage = "Os dados do telefone precisam conter números";

        final var aContact = new Contact(expectedEmail, expectedPhone);

        assertEquals(0, repository.count());

        final var notification = assertThrows(DomainException.class, () -> this.gateway.create(aContact));

        assertEquals(0, this.repository.count());

        assertNotNull(notification);
        assertEquals(expectErrorCount, notification.getErrors().size());
        assertEquals(expectErrorMessage, notification.getErrors().get(0).message());

    }

    @Test
    void givenANullContactWhenCallsCreateShouldReturnANullContact() {

        assertEquals(0, repository.count());

        final var actualContact = this.gateway.create(null);

        assertEquals(0, repository.count());
        assertNull(actualContact);
    }

}
