package org.diegosneves.infrastructure.api.controller;

import org.diegosneves.application.client.create.CreateClientCommand;
import org.diegosneves.application.client.create.CreateClientOutput;
import org.diegosneves.application.client.create.CreateClientUseCase;
import org.diegosneves.application.client.retrieve.get.GetClientByIdUseCase;
import org.diegosneves.domain.pagination.Pagination;
import org.diegosneves.domain.validation.handler.Notification;
import org.diegosneves.infrastructure.api.ClientAPI;
import org.diegosneves.infrastructure.client.model.ClientApiOutput;
import org.diegosneves.infrastructure.client.model.CreateClientApiInput;
import org.diegosneves.infrastructure.client.presenters.ClientApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class ClientController implements ClientAPI {

    private final CreateClientUseCase createClientUseCase;
    private final GetClientByIdUseCase getClientByIdUseCase;

    public ClientController(CreateClientUseCase createClientUseCase, GetClientByIdUseCase getClientByIdUseCase) {
        this.createClientUseCase = Objects.requireNonNull(createClientUseCase);
        this.getClientByIdUseCase = Objects.requireNonNull(getClientByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createClient(final CreateClientApiInput input) {
        final var aCommand = CreateClientCommand.with(
                input.cnpj(),
                input.address().toAddress(),
                input.contact().toContact(),
                input.companyName(),
                input.companyBranch(),
                input.companySector()
        );
        final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity().body(notification);
        final Function<CreateClientOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/clients/" + output.clientID().getValue())).body(output);

        return this.createClientUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listClients(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public ClientApiOutput getById(final String id) {
        return ClientApiPresenter.present(this.getClientByIdUseCase.execute(id));
    }
}
