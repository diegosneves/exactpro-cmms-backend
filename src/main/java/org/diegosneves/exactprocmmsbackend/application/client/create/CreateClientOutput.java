package org.diegosneves.exactprocmmsbackend.application.client.create;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;

public record CreateClientOutput(ClientID clientID) {


    public static CreateClientOutput from(final Client aClient) {
        return new CreateClientOutput(aClient.getId());
    }

}
