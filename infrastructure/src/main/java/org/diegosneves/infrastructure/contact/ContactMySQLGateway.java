package org.diegosneves.infrastructure.contact;

import org.diegosneves.domain.client.valueobject.Contact;
import org.diegosneves.domain.validation.handler.ThrowsValidationHandler;
import org.diegosneves.infrastructure.contact.persistence.ContactJpaEntity;
import org.diegosneves.infrastructure.contact.persistence.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactMySQLGateway {

    private final ContactRepository contactRepository;

    public ContactMySQLGateway(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ContactJpaEntity create(Contact aContact) {
        ContactJpaEntity newContact = null;
        if (aContact != null) {
            newContact = createOrUpdateContact(aContact);
        }
        return newContact;
    }

    private ContactJpaEntity createOrUpdateContact(Contact aContact) {
        aContact.validate(new ThrowsValidationHandler());
        Optional<ContactJpaEntity> existingContact = this.contactRepository
                .findContactJpaEntitiesByEmailAndPhone(aContact.getEmail(), aContact.getPhone());
        return existingContact.orElseGet(() -> this.contactRepository.saveAndFlush(ContactJpaEntity.from(aContact)));
    }

    public Long count() {
        return this.contactRepository.count();
    }

    public void deleteContact(Contact oldContact) {
        if (oldContact == null) return;
        Optional<ContactJpaEntity> foundContactEntity = this.contactRepository
                .findContactJpaEntitiesByEmailAndPhone(oldContact.getEmail(), oldContact.getPhone());
        foundContactEntity.ifPresent(contactJpaEntity -> this.contactRepository.deleteById(contactJpaEntity.getId()));
    }
}
