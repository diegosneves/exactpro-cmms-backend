package org.diegosneves.exactprocmmsbackend.domain.client.valueobject;

import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientContactTest {


    @Test
    void shouldReturnNonNullContactWhenEmailAndPhoneAreProvided() {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        ClientContact clientContact = new ClientContact(userEmail, userPhone);

        assertNotNull(clientContact);
        assertDoesNotThrow(() -> clientContact.validate(new ThrowsValidationHandler()));
        assertEquals(userEmail, clientContact.getEmail());
        assertEquals(userPhone, clientContact.getPhone());
    }

    @Test
    void shouldReturnNonNullContactWhenEmailAndPhoneAreNotProvided() {

        ClientContact clientContact = new ClientContact();

        assertNotNull(clientContact);
        assertDoesNotThrow(() -> clientContact.validate(new ThrowsValidationHandler()));
        assertNull(clientContact.getEmail());
        assertNull(clientContact.getPhone());
    }

    @ParameterizedTest
    @ValueSource(strings = {"email@teste.com.br", "teste@gmail.com", "email+teste234235dealgo@junit.com"})
    void shouldUpdateEmailAndRetainPhone(String email) {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        ClientContact clientContact = new ClientContact(userEmail, userPhone);
        clientContact.setEmail(email);

        assertNotNull(clientContact);
        assertDoesNotThrow(() -> clientContact.validate(new ThrowsValidationHandler()));
        assertEquals(email, clientContact.getEmail());
        assertEquals(userPhone, clientContact.getPhone());
    }

    @ParameterizedTest
    @ValueSource(strings = {"51 9 98282838", "51 3232-2736 Ramal: 3545"})
    void shouldUpdatePhoneAndRetainEmail(String phone) {
        String userEmail = "email@test.com";
        String userPhone = "51998282838";

        ClientContact clientContact = new ClientContact(userEmail, userPhone);
        clientContact.setPhone(phone);

        assertNotNull(clientContact);
        assertDoesNotThrow(() -> clientContact.validate(new ThrowsValidationHandler()));
        assertEquals(userEmail, clientContact.getEmail());
        assertEquals(phone, clientContact.getPhone());
    }

    @Test
    void shouldThrowExceptionWhenPhoneNumberIsInvalidWhileCreatingClientContact() {
        String userEmail = "email@test.com";
        String userPhone = "dfasdfasdf";
        final int expectErrorCount = 1;

        ClientContact clientContact = new ClientContact(userEmail, userPhone);

        final var actualException = assertThrows(DomainException.class, () -> clientContact.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(ClientContactValidator.INVALID_PHONE_NUMBER, actualException.getErrors().get(0).message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"@mail.com", "asdfiiasdfiifasf.com.br", "com.gmail@teste"})
    void shouldThrowExceptionWhenEmailIsInvalidWhileCreatingClientContact(String email) {
        String userPhone = "51998282838";
        final int expectErrorCount = 1;

        final var actual = new ClientContact(email, userPhone);
        final var actualException = assertThrows(DomainException.class, () -> actual.validate(new ThrowsValidationHandler()));

        assertNotNull(actualException);
        assertEquals(expectErrorCount, actualException.getErrors().size());
        assertEquals(ClientContactValidator.INVALID_EMAIL_FORMAT, actualException.getErrors().get(0).message());
    }


}
