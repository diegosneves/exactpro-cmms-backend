package org.diegosneves.exactprocmmsbackend.application.client.create;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateClientUseCase extends CreateClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultCreateClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public CreateClientOutput execute(final CreateClientCommand aCommand) {
        final var aClient = Client.newClient(
                aCommand.cnpj(),
                aCommand.address(),
                aCommand.contact(),
                aCommand.companyName(),
                aCommand.companyBrach(),
                aCommand.companySector());
        aClient.validate(new ThrowsValidationHandler());

        return CreateClientOutput.from(this.clientGateway.create(aClient));
    }

}
