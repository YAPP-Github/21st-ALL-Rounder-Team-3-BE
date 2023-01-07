package yapp.allround3.common.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yapp.allround3.common.dto.CustomResponse;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public CustomResponse customExceptionHandler(CustomException e){
     return CustomResponse.customError(e);
    }
}
