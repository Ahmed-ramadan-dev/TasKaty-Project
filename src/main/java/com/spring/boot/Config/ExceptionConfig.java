package com.spring.boot.Config;

import com.spring.boot.BundleMessage.BundleMessage;
import com.spring.boot.BundleMessage.BundleTranslationService;
import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Vm.ExceptionResponseVm;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ExceptionResponseVm> handleSystemException(SystemException exception) {
        BundleMessage bundleMessage = BundleTranslationService.getBundleMessageInEnglishAndArabic(exception.getMessage());
        List<BundleMessage> messages = List.of(bundleMessage);
        return ResponseEntity
                .status(exception.getStatus())
                .body(new ExceptionResponseVm(messages, exception.getStatus()));    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseVm> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<BundleMessage> errors = fieldErrors.stream()
                .map(fieldError -> BundleTranslationService.getBundleMessageInEnglishAndArabic(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ExceptionResponseVm response = new ExceptionResponseVm(errors, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseVm> handleConstraintViolationException(ConstraintViolationException ex) {

        List<BundleMessage> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    String messageKey = violation.getMessage();
                    return BundleTranslationService.getBundleMessageInEnglishAndArabic(messageKey);
                })
                .collect(Collectors.toList());

        ExceptionResponseVm response = new ExceptionResponseVm(errors, HttpStatus.BAD_REQUEST);

        return ResponseEntity.badRequest().body(response);
    }


}
