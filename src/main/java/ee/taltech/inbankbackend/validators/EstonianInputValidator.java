package ee.taltech.inbankbackend.validators;

import com.github.vladislavgoltjajev.personalcode.exception.PersonalCodeException;
import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeParser;
import ee.taltech.inbankbackend.config.DecisionEngineConstants;
import ee.taltech.inbankbackend.exceptions.InvalidAgeException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanAmountException;
import ee.taltech.inbankbackend.exceptions.InvalidLoanPeriodException;
import ee.taltech.inbankbackend.exceptions.InvalidPersonalCodeException;
import ee.taltech.inbankbackend.utils.InputValidator;

import java.time.Period;

public class EstonianInputValidator implements InputValidator {

    //Used to check user's age & checks for the validity of the presented ID code.
    private final EstonianPersonalCodeParser personalCodeParser = new EstonianPersonalCodeParser();

    /**
     * Verify that all inputs are valid according to business rules.
     * If inputs are invalid, then throws corresponding exceptions.
     *
     * @param personalCode Provided personal ID code
     * @param loanAmount   Requested loan amount
     * @param loanPeriod   Requested loan period
     * @throws InvalidLoanAmountException   If the requested loan amount is invalid
     * @throws InvalidLoanPeriodException   If the requested loan period is invalid
     */
    @Override
    public void validate(String personalCode, Long loanAmount, int loanPeriod)
            throws InvalidLoanAmountException, InvalidLoanPeriodException {
        if (DecisionEngineConstants.MINIMUM_LOAN_AMOUNT > loanAmount
                || loanAmount > DecisionEngineConstants.MAXIMUM_LOAN_AMOUNT) {
            throw new InvalidLoanAmountException("Invalid loan amount!");
        }
        if (DecisionEngineConstants.MINIMUM_LOAN_PERIOD > loanPeriod
                || loanPeriod > DecisionEngineConstants.MAXIMUM_LOAN_PERIOD) {
            throw new InvalidLoanPeriodException("Invalid loan period!");
        }
    }

    /**
     * Verify the age of user by its personalCode
     *
     * @param personalCode Provided personal ID code
     * @throws InvalidPersonalCodeException If the provided personal ID code is invalid
     * @throws InvalidAgeException   If the requested age is invalid
     */
    @Override
    public void validateAge(String personalCode) throws InvalidPersonalCodeException, InvalidAgeException {
        Period userAge;
        try {
            userAge = personalCodeParser.getAge(personalCode);
        } catch (PersonalCodeException e) {
            throw new InvalidPersonalCodeException("Invalid personal ID code!");
        }

        if (userAge.getYears() < DecisionEngineConstants.MINIMUM_CUSTOMER_AGE) {
            throw new InvalidAgeException("Age is below allowed for loan application!");
        }
        int highestAllowedAge = DecisionEngineConstants.MAXIMUM_CUSTOMER_AGE -
                (int) Math.ceil((double) DecisionEngineConstants.MAXIMUM_LOAN_PERIOD / 12);
        if (userAge.getYears() > highestAllowedAge) {
            throw new InvalidAgeException("Age is above allowed for loan application!");
        }
    }
}
