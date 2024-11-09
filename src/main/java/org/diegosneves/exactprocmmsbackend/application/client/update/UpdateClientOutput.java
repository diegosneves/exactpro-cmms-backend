package org.diegosneves.exactprocmmsbackend.application.client.update;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;

public record UpdateClientOutput(ClientID clientID) {

    public static UpdateClientOutput from(Client aClient) {
        return new UpdateClientOutput(aClient.getId());
    }

}
