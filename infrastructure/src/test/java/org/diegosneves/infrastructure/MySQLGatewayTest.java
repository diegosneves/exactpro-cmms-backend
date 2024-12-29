package org.diegosneves.infrastructure;


import org.diegosneves.infrastructure.address.AddressMySQLGateway;
import org.diegosneves.infrastructure.client.ClientMySQLGateway;
import org.diegosneves.infrastructure.contact.ContactMySQLGateway;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@DataJpaTest
@ComponentScan(
        basePackages = {"org.diegosneves.infrastructure"},
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        ClientMySQLGateway.class,
                        ContactMySQLGateway.class,
                        AddressMySQLGateway.class
                })
        })
@ExtendWith(CleanUpExtension.class)
@Tag("integrationTest")
public @interface MySQLGatewayTest {

}
