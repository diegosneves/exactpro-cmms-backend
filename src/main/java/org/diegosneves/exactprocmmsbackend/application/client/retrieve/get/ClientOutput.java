package org.diegosneves.exactprocmmsbackend.application.client.retrieve.get;

import org.diegosneves.exactprocmmsbackend.domain.client.Client;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;

public record ClientOutput(String cnpj,
                           Address address,
                           ClientContact contact,
                           String companyName,
                           String companyBrach,
                           String companySector) {


    public static ClientOutput from(final Client aClient) {

        return new ClientOutput(
                aClient.getCnpj(),
                aClient.getAddress(),
                aClient.getContact(),
                aClient.getCompanyName(),
                aClient.getCompanyBranch(),
                aClient.getCompanySector());

    }

}
