package com.ngg.servernewgenie.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ngg.servernewgenie.config.BaseResponseStatus.*;


@RestController
@ControllerAdvice
//extends ResponseEntityExceptionHandler
public class BaseExceptionHandler {
    // pltName이 "No object detected"인 경우에 대한 예외 처리 코드
    @ExceptionHandler(BaseException.class)
    public final ResponseEntity<Object> handleBaseExceptions(BaseException exception) {
        if (NO_OBJECT_DETECTES_ERROR.equals(exception.getStatus())) {
            ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                    .isSuccess(exception.getStatus().isSuccess())
                    .code(exception.getStatus().getCode())
                    .message(NO_OBJECT_DETECTES_ERROR.getMessage())
                    .build();
            return new ResponseEntity<>(exceptionResponse, exception.getHttpStatus());
        } else if (PRINCIPAL_TYPE_ERROR.equals(exception.getStatus())) {
            ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                    .isSuccess(exception.getStatus().isSuccess())
                    .code(exception.getStatus().getCode())
                    .message(PRINCIPAL_TYPE_ERROR.getMessage())
                    .build();
            return new ResponseEntity<>(exceptionResponse, exception.getHttpStatus());
        } else {
            ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                    .isSuccess(exception.getStatus().isSuccess())
                    .code(exception.getStatus().getCode())
                    .message(exception.getStatus().getMessage())
                    .build();
            return new ResponseEntity<>(exceptionResponse, exception.getHttpStatus());
        }
    }


//    @ExceptionHandler(BaseException.class)
//    public final ResponseEntity<Object> handleBaseExceptions(BaseException exception){
//        ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
//                .isSuccess(exception.getStatus().isSuccess())
//                .code(exception.getStatus().getCode())
//                .message(exception.getStatus().getMessage())
//                .build();
//        return new ResponseEntity(exceptionResponse, exception.getHttpStatus());
//    }

    // 유효한 값이 아닐 때 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception){
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors()
//                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                .isSuccess(false)
                .code(VALIDATION_ERROR.getCode())
                .message(VALIDATION_ERROR.getMessage())
                .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // 값의 타입이 올바르지 않을 때 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Map<String, String>> handleValidationExceptions(HttpMessageNotReadableException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse().builder()
                .isSuccess(false)
                .code(VALIDATION_TYPE_ERROR.getCode())
                .message(VALIDATION_TYPE_ERROR.getMessage())
                .build();
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
