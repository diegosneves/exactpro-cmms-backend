package org.diegosneves.infrastructure.api.controller;

import org.diegosneves.domain.pagination.Pagination;
import org.diegosneves.infrastructure.api.ClientAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController implements ClientAPI {

    @Override
    public ResponseEntity<?> createClient() {
        return null;
    }

    @Override
    public Pagination<?> listClients(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
