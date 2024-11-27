package org.diegosneves.exactprocmmsbackend.domain.client.valueobject;

import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;

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
}
