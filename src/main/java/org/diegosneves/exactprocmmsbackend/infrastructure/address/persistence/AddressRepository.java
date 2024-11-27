package org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressJpaEntity, Long> {

}
