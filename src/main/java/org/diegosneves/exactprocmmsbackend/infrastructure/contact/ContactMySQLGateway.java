package org.diegosneves.exactprocmmsbackend.infrastructure.contact;

import org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactMySQLGateway {

    private final ContactRepository contactRepository;

    public ContactMySQLGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

}
