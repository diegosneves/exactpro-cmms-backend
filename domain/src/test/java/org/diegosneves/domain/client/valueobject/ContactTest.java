package org.diegosneves.domain.client.valueobject;

import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContactTest {


    @Test
    void shouldReturnNonNullContactWhenEmailAndPhoneAreProvided() {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        Contact contact = new Contact(userEmail, userPhone);

        assertNotNull(contact);
        assertDoesNotThrow(() -> contact.validate(new ThrowsValidationHandler()));
        assertEquals(userEmail, contact.getEmail());
        assertEquals(userPhone, contact.getPhone());
    }

    @Test
    void shouldReturnNonNullContactWhenEmailAndPhoneAreNotProvided() {

        Contact contact = new Contact();

        assertNotNull(contact);
        assertDoesNotThrow(() -> contact.validate(new ThrowsValidationHandler()));
        assertNull(contact.getEmail());
        assertNull(contact.getPhone());
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@teste.com.br", "teste@gmail.com", "email+teste234235dealgo@junit.com"})
    void shouldUpdateEmailAndRetainPhone(String email) {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        Contact contact = new Contact(userEmail, userPhone);
        contact.setEmail(email);

        assertNotNull(contact);
        assertDoesNotThrow(() -> contact.validate(new ThrowsValidationHandler()));
        assertEquals(email, contact.getEmail());
        assertEquals(userPhone, contact.getPhone());
    }

    @ParameterizedTest
    @ValueSource(strings = {"51 9 98282838", "51 3232-2736 Ramal: 3545"})
    void shouldUpdatePhoneAndRetainEmail(String phone) {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        Contact contact = new Contact(userEmail, userPhone);
        contact.setPhone(phone);

        assertNotNull(contact);
        assertDoesNotThrow(() -> contact.validate(new ThrowsValidationHandler()));
        assertEquals(userEmail, contact.getEmail());
        assertEquals(phone, contact.getPhone());
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsInvalidWhileCreatingClientContact() {
        String userEmail = "email@test.com";
        String userPhone = "dfasdfasdf";
        final int expectErrorCount = 1;

        Contact contact = new Contact(userEmail, userPhone);

        final var actualException = assertThrows(DomainException.class, () -> contact.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(ContactValidator.INVALID_PHONE_NUMBER, actualException.getErrors().get(0).message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"@mail.com", "asdfiiasdfiifasf.com.br", "com.gmail@teste"})
    void shouldThrowExceptionWhenEmailIsInvalidWhileCreatingClientContact(String email) {
        String userPhone = "51998282838";
        final int expectErrorCount = 1;

        final var actual = new Contact(email, userPhone);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(ContactValidator.INVALID_EMAIL_FORMAT, actualException.getErrors().get(0).message());
    }


}
