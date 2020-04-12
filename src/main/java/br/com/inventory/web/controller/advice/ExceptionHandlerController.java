package br.com.inventory.web.controller.advice;

import br.com.inventory.application.exception.IlegalOrderStateException;
import br.com.inventory.web.dto.response.errors.ErrorMessageResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessageResponseDto errorMessage = new ErrorMessageResponseDto();
        errorMessage.setCode(400);
        errorMessage.setDescription(ex.getLocalizedMessage());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setPath(request.getContextPath());
        errorMessage.setStackTrace(ex.getStackTrace().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(IlegalOrderStateException.class)
    public ResponseEntity<?> handleIlegalOrderStateException(IlegalOrderStateException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorMessageResponseDto errorMessage = new ErrorMessageResponseDto();
        errorMessage.setCode(422);
        errorMessage.setDescription(ex.getLocalizedMessage());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setPath(request.getContextPath());
        errorMessage.setStackTrace(ex.getStackTrace().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorMessageResponseDto errorMessage = new ErrorMessageResponseDto();
        errorMessage.setCode(500);
        errorMessage.setDescription(ex.getLocalizedMessage());
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setPath(request.getContextPath());
        errorMessage.setStackTrace(ex.getStackTrace().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
