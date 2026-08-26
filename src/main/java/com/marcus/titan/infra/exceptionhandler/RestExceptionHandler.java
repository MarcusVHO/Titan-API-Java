package com.marcus.titan.infra.exceptionhandler;

import com.marcus.titan.exceptions.*;
import com.marcus.titan.infra.exceptionresponse.RestErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(SupplyNotFoundException.class)
    private ResponseEntity<RestErrorMessage> supplyNotFoundHandler(SupplyNotFoundException exception) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ErrorInPickingMaterial.class)
    private ResponseEntity<RestErrorMessage> errorInPickingMaterial(ErrorInPickingMaterial error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.BAD_REQUEST, error.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MaterialNotFound.class)
    private ResponseEntity<RestErrorMessage> materialNotFoundHandler(MaterialNotFound error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.NOT_FOUND, error.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MaterialCantRefund.class)
    private ResponseEntity<RestErrorMessage> MaterialCantRefundHandler(MaterialCantRefund error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, error.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MaterialAlreadyExists.class)
    private ResponseEntity<RestErrorMessage> MaterialAlreadyExistsHandler(MaterialAlreadyExists error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, error.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ModuleMisMatch.class)
    private ResponseEntity<RestErrorMessage> ModuleMisMatch(ModuleMisMatch error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, error.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(SuMisMatchException.class)
    private ResponseEntity<RestErrorMessage> ModuleMisMatch(SuMisMatchException error) {
        RestErrorMessage response = new RestErrorMessage(HttpStatus.CONFLICT, error.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
