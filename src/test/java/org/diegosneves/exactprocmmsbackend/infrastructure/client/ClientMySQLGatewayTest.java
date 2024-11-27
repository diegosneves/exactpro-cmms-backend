package org.diegosneves.exactprocmmsbackend.infrastructure.client;

import org.diegosneves.exactprocmmsbackend.infrastructure.client.persistence.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MySQLGatewayTest
class ClientMySQLGatewayTest {

    @Autowired
    private ClientMySQLGateway gateway;

    @Autowired
    private ClientRepository repository;


    @Test
    void testInjectedDependencies() {
        assertNotNull(gateway);
        assertNotNull(repository);
    }

}
