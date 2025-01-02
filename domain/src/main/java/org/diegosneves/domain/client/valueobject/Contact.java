package org.diegosneves.domain.client.valueobject;

import org.diegosneves.domain.validation.ValidationHandler;

import java.util.Objects;

public class Contact {

    private String email;
    private String phone;

    public Contact() {}

    public Contact(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public void validate(ValidationHandler validator) {
        new ContactValidator(this, validator).validate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getEmail(), contact.getEmail()) && Objects.equals(getPhone(), contact.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPhone());
    }

}
