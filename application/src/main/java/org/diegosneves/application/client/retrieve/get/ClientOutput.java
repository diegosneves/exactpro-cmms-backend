package org.diegosneves.application.client.retrieve.get;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;

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
