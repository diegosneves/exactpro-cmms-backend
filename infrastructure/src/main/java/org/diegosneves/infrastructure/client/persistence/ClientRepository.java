package org.diegosneves.infrastructure.client.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientJpaEntity, String> {

    Page<ClientJpaEntity> findAll(Specification<ClientJpaEntity> spec, Pageable pageable);

    Optional<ClientJpaEntity> findClientJpaEntityByCnpj(String cnpj);

}
