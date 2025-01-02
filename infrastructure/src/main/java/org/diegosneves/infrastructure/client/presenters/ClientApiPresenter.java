package org.diegosneves.infrastructure.client.presenters;

import org.diegosneves.application.client.retrieve.get.ClientOutput;
import org.diegosneves.infrastructure.address.model.CreateAddressApiInput;
import org.diegosneves.infrastructure.client.model.ClientApiOutput;
import org.diegosneves.infrastructure.contact.model.CreateContactApiInput;

public interface ClientApiPresenter {

    static ClientApiOutput present(ClientOutput output) {
        return new ClientApiOutput(
                output.id().getValue(),
                output.cnpj(),
                CreateAddressApiInput.fromAddress(output.address()),
                CreateContactApiInput.fromContact(output.contact()),
                output.companyName(),
                output.companyBrach(),
                output.companySector()
        );
    }

}
