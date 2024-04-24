package ee.taltech.inbankbackend.exceptions;

import ee.taltech.inbankbackend.endpoint.DecisionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({InvalidPersonalCodeException.class, InvalidLoanAmountException.class, InvalidLoanPeriodException.class})
    public ResponseEntity<DecisionResponse> handleBadRequestException(Exception e) {
        DecisionResponse response = new DecisionResponse();
        response.setLoanAmount(null);
        response.setLoanPeriod(null);
        response.setErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoValidLoanException.class)
    public ResponseEntity<DecisionResponse> handleNotFoundException(NoValidLoanException e) {
        DecisionResponse response = new DecisionResponse();
        response.setLoanAmount(null);
        response.setLoanPeriod(null);
        response.setErrorMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DecisionResponse> handleOtherException(Exception e) {
        DecisionResponse response = new DecisionResponse();
        response.setLoanAmount(null);
        response.setLoanPeriod(null);
        response.setErrorMessage("An unexpected error occurred");
        return ResponseEntity.internalServerError().body(response);
    }
}

