package org.diegosneves.exactprocmmsbackend.domain.client.valueobject;

import org.diegosneves.exactprocmmsbackend.domain.utils.RegexPatternCompileUtil;
import org.diegosneves.exactprocmmsbackend.domain.validation.ErrorData;
import org.diegosneves.exactprocmmsbackend.domain.validation.ValidationHandler;
import org.diegosneves.exactprocmmsbackend.domain.validation.Validator;

public class ContactValidator extends Validator {

    private static final String EMAIL_VALIDATION_REGEX = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_NUMBER_REGEX = ".*\\d.*";

    public static final String INVALID_PHONE_NUMBER = "Os dados do telefone precisam conter números";
    public static final String INVALID_EMAIL_FORMAT = "Formato de e-mail inválido!";

    private final Contact contact;

    protected ContactValidator(Contact contact, ValidationHandler validationHandler) {
        super(validationHandler);
        this.contact = contact;
    }

    @Override
    public void validate() {
        this.checkEmailConstraints();
        this.checkPhoneConstraints();
    }

    private void checkPhoneConstraints() {
        final String phone = this.contact.getPhone();
        if (phone != null && !RegexPatternCompileUtil.matches(PHONE_NUMBER_REGEX, phone.trim())) {
            this.getValidationHandler().append(new ErrorData(INVALID_PHONE_NUMBER));
        }
    }

    private void checkEmailConstraints() {
        final String email = this.contact.getEmail();
        if (email != null && !RegexPatternCompileUtil.matches(EMAIL_VALIDATION_REGEX, email.trim())) {
            this.getValidationHandler().append(new ErrorData(INVALID_EMAIL_FORMAT));
        }
    }


}
