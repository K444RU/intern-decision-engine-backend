package ee.taltech.inbankbackend.utils;

import ee.taltech.inbankbackend.exceptions.InvalidAgeException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanAmountException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanPeriodException;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;

public interface InputValidator {

    void validate(String personalCode, Long loanAmount, int loanPeriod)
            throws InvalidLoanAmountException, InvalidLoanPeriodException;

    void validateAge(String personalCode) throws InvalidPersonalCodeException, InvalidAgeException;
}
