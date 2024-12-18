package org.diegosneves.exactprocmmsbackend.application.client.retrieve.get;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.ClientID;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Contact;

public record ClientOutput(
        ClientID id,
        String cnpj,
        Address address,
        Contact contact,
        String companyName,
        String companyBrach,
        String companySector) {


    public static ClientOutput from(final Client aClient) {

        return new ClientOutput(
                aClient.getId(),
                aClient.getCnpj(),
                aClient.getAddress(),
                aClient.getContact(),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector());

    }

}
