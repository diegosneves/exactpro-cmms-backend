package org.diegosneves.exactprocmmsbackend.domain.client;

import org.diegosneves.exactprocmmsbackend.domain.utils.FiscalValidatorUtil;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validator;

public class ClientValidator extends Validator {

    private final Client client;

    public ClientValidator(Client client, final ValidationHandler validationHandler) {
        super(validationHandler);
        this.client = client;
    }

    @Override
    public void validate() {
        FiscalValidatorUtil.isCnpjValid(this.client.getCnpj());

        if (this.client.getCompanyBranch() == null || this.client.getCompanyBranch().isEmpty() || this.client.getCompanyBranch().isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company branch is required"));
        }
        if (this.client.getCompanyName() == null || this.client.getCompanyName().isEmpty() || this.client.getCompanyName().isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company name is required"));
        }
        if (this.client.getCompanySector() == null || this.client.getCompanySector().isEmpty() || this.client.getCompanySector().isBlank()) {
            this.getValidationHandler().append(new ErrorData("Company sector is required"));
        }
    }
}
