package org.diegosneves.application.client.retrieve.list;

import org.diegosneves.domain.client.Client;
import org.diegosneves.domain.client.ClientID;
import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;

public record ClientListOutput(
        ClientID id,
        String cnpj,
        Address address,
        Contact contact,
        String companyName,
        String companyBrach,
        String companySector
) {

    public static ClientListOutput from(final Client aClient) {
        return new ClientListOutput(
                aClient.getId(),
                aClient.getCnpj(),
                aClient.getAddress(),
                aClient.getContact(),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector());
    }

}
