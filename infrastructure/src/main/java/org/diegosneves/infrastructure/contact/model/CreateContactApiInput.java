package org.diegosneves.infrastructure.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.diegosneves.domain.client.valueobject.Contact;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateContactApiInput(
        @JsonProperty("email") String email,
        @JsonProperty("phone") String phone
) {
    public Contact toContact() {
        return new Contact(email, phone);
    }
}
