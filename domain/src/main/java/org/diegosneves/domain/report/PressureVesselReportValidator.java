package org.diegosneves.domain.report;

import org.diegosneves.domain.validation.ErrorData;
import org.diegosneves.domain.validation.ValidationHandler;
import org.diegosneves.domain.validation.Validator;

public class PressureVesselReportValidator extends Validator {

    private final PressureVesselReport report;

    public PressureVesselReportValidator(final PressureVesselReport report, final ValidationHandler validationHandler) {
        super(validationHandler);
        this.report = report;
    }

    @Override
    public void validate() {
        if (this.report.getClient() == null) {
            this.getValidationHandler().append(new ErrorData("Client data cannot be null"));
        }
    }
}
