package org.diegosneves.exactprocmmsbackend.domain.client.valueobject;

import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validator;

public class ClientContactValidator extends Validator {

    private static final String EMAIL_VALIDATION_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = ".*\\d.*";

    public static final String INVALID_PHONE_NUMBER = "Os dados do telefone precisam conter números";
    public static final String INVALID_EMAIL_FORMAT = "Formato de e-mail inválido!";

    private final ClientContact contact;

    protected ClientContactValidator(ClientContact clientContact, ValidationHandler validationHandler) {
        super(validationHandler);
        this.contact = clientContact;
    }

    @Override
    public void validate() {
        this.checkEmailConstraints();
        this.checkPhoneConstraints();
    }

    private void checkPhoneConstraints() {
        final String phone = this.contact.getPhone();
        if (phone != null && !phone.trim().matches(PHONE_NUMBER_REGEX)) {
            this.getValidationHandler().append(new ErrorData(INVALID_PHONE_NUMBER));
        }
    }

    private void checkEmailConstraints() {
        final String email = this.contact.getEmail();
        if (email != null && !email.trim().matches(EMAIL_VALIDATION_REGEX)) {
            this.getValidationHandler().append(new ErrorData(INVALID_EMAIL_FORMAT));
        }

    }


}
