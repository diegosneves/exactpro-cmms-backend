package org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactJpaEntity, Long> {

}
