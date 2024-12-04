package org.diegosneves.exactprocmmsbackend.infrastructure.contact.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactJpaEntity, Long> {

    Optional<ContactJpaEntity> findContactJpaEntitiesByEmailAndPhone(String email, String phoneNumber);

}
