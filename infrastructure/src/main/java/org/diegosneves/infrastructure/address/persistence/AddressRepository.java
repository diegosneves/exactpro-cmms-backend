package org.diegosneves.infrastructure.address.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressJpaEntity, Long> {

    Optional<AddressJpaEntity> findAddressJpaEntitiesByNumberAndZip(String number, String zip);
}
