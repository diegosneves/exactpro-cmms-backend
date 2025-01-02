package org.diegosneves.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.diegosneves.infrastructure.address.model.CreateAddressApiInput;
import org.diegosneves.infrastructure.contact.model.CreateContactApiInput;

public record ClientApiOutput(
        @JsonProperty("id") String id,
        @JsonProperty("cnpj") String cnpj,
        @JsonProperty("address") CreateAddressApiInput address,
        @JsonProperty("contact") CreateContactApiInput contact,
        @JsonProperty("company_name") String companyName,
        @JsonProperty("company_branch") String companyBranch,
        @JsonProperty("company_sector") String companySector
) {
}
