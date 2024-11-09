package org.diegosneves.exactprocmmsbackend.application.client.update;

import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;

public record UpdateClientCommand(
        String id,
        String cnpj,
        Address address,
        ClientContact contact,
        String companyName,
        String companyBrach,
        String companySector) {

    public static UpdateClientCommand with(
            final String id,
            final String cnpj,
            final Address address,
            final ClientContact contact,
            final String companyName,
            final String companyBrach,
            final String companySector) {

        return new UpdateClientCommand(id, cnpj, address, contact, companyName, companyBrach, companySector);
    }

}
