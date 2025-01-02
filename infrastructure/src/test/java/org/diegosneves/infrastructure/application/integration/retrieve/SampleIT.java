package org.diegosneves.infrastructure.application.integration.retrieve;


import org.diegosneves.infrastructure.IntegrationTest;
import org.diegosneves.application.client.create.CreateClientUseCase;
import org.diegosneves.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationTest
class SampleIT {

    @Autowired
    private CreateClientUseCase useCase;

    @Autowired
    private ClientRepository clientRepository;


    @Test
    void testInjectedComponents() {
        assertNotNull(this.useCase);
        assertNotNull(this.clientRepository);
    }

}
