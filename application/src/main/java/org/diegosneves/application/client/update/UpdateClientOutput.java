package org.diegosneves.application.client.update;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;

public record UpdateClientOutput(ClientID clientID) {

    public static UpdateClientOutput from(Client aClient) {
        return new UpdateClientOutput(aClient.getId());
    }

}
