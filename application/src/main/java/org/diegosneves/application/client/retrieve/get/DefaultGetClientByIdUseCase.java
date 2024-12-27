package org.diegosneves.application.client.retrieve.get;

import org.diegosneves.domain.client.ClientGateway;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.exceptions.DomainException;
import org.diegosneves.domain.validation.ErrorData;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetClientByIdUseCase extends GetClientByIdUseCase {

    public static final String CLIENT_NOT_FOUND_MESSAGE = "Não foi encontrado o cliente com o ID %s";

    private final ClientGateway clientGateway;

    public DefaultGetClientByIdUseCase(final ClientGateway clientGateway) {
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public ClientOutput execute(final String anId) {
        final var targetID = ClientID.from(anId);

        return this.clientGateway
                .findById(targetID)
                .map(ClientOutput::from)
                .orElseThrow(notFound(targetID));
    }

    private static Supplier<DomainException> notFound(final ClientID anId) {
        return () -> DomainException.with(new ErrorData(CLIENT_NOT_FOUND_MESSAGE.formatted(anId.getValue())));
    }
}
