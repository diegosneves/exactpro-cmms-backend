package org.diegosneves.application.domain.client;

import org.diegosneves.domain.client.ClientID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientIDTest {

    @Test
    void shouldGenerateAndReturnEqualsClientID() {
        final var aClientID = ClientID.unique();
        final var bClientID = aClientID;

        assertNotNull(aClientID);
        assertEquals(aClientID, bClientID);
    }

    @Test
    void shouldGenerateAndReturnNotEqualsClientID() {
        final var aClientID = ClientID.unique();
        final var bClientID = ClientID.unique();

        assertNotNull(aClientID);
        assertNotNull(bClientID);
        assertNotEquals(aClientID, bClientID);
    }

    @Test
    void shouldGenerateClientIDAndEnsureNotEqualsNull() {
        final var aClientID = ClientID.unique();
        ClientID bClientID = null;
        boolean isEqual = aClientID.equals(bClientID);

        assertNotNull(aClientID);
        assertFalse(isEqual);
    }

    @Test
    void shouldNotEqualItsStringValue() {
        final var aClientID = ClientID.unique();
        final var nonClientID = aClientID.getValue();
        boolean isEqual = aClientID.equals(nonClientID);

        assertNotNull(aClientID);
        assertFalse(isEqual);
    }

    @Test
    void should() {
        final var aClientID = ClientID.unique();
        int hashCode = aClientID.hashCode();

        assertNotNull(aClientID);
        assertNotEquals(0, hashCode);
    }

}
