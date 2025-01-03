package org.diegosneves.application.client.create;

import io.vavr.control.Either;
import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.validation.handler.Notification;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateClientUseCase extends CreateClientUseCase {

    private final ClientGateway clientGateway;

    public DefaultCreateClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public Either<Notification, CreateClientOutput> execute(final CreateClientCommand aCommand) {
        final var aClient = Client.newClient(
                aCommand.cnpj(),
                aCommand.address(),
                aCommand.contact(),
                aCommand.companyName(),
                aCommand.companyBrach(),
                aCommand.companySector());
        final var notification = Notification.create();
        aClient.validate(notification);

        return notification.hasError() ? Left(notification) : this.create(aClient);
    }

    private Either<Notification, CreateClientOutput> create(final Client aClient) {
        return Try(() -> this.clientGateway.create(aClient))
                .toEither()
                .bimap(Notification::create, CreateClientOutput::from);
    }


}
