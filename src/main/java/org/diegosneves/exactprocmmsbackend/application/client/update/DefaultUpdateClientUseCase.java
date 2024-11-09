package org.diegosneves.exactprocmmsbackend.application.client.update;

import io.vavr.control.Either;
import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientGateway;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.exceptions.DomainException;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.handler.Notification;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateClientUseCase extends UpdateClientUseCase {

    public static final String CLIENT_NOT_FOUND_MESSAGE = "Não foi encontrado o cliente com o ID %s";

    private final ClientGateway clientGateway;

    public DefaultUpdateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public Either<Notification, UpdateClientOutput> execute(final UpdateClientCommand aCommand) {
        final var anId = ClientID.from(aCommand.id());

        final var foundClient = this.clientGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();
        foundClient.update(
                aCommand.cnpj(),
                aCommand.address(),
                aCommand.contact(),
                aCommand.companyName(),
                aCommand.companyBrach(),
                aCommand.companySector()).validate(notification);
        return notification.hasError() ? Left(notification) : this.updated(foundClient);
    }

    private Either<Notification, UpdateClientOutput> updated(final Client aClient) {
        return Try(() -> this.clientGateway.update(aClient))
                .toEither()
                .bimap(Notification::create, UpdateClientOutput::from);
    }

    private static Supplier<DomainException> notFound(final ClientID anId) {
        return () -> DomainException.with(new ErrorData(CLIENT_NOT_FOUND_MESSAGE.formatted(anId.getValue())));
    }
}
