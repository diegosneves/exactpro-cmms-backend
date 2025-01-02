package org.diegosneves.application.client.create;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;

public record CreateClientOutput(ClientID clientID) {


    public static CreateClientOutput from(final Client aClient) {
        return new CreateClientOutput(aClient.getId());
    }

}
