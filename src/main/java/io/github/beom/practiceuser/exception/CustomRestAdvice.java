package io.github.beom.practiceuser.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {
    // Valid 과정에서 문제가 발생하면 처리할 수 있도록
    // ResControllerAdvice를 설계하자
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleBindException(BindException e){
        log.error("Validation 오류 발생: {}", e.getMessage());

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("error", "Validation Failed");
        errorMap.put("message", "입력 데이터 검증에 실패했습니다.");
        errorMap.put("status", 400);
        errorMap.put("timestamp", System.currentTimeMillis());

        Map<String, String> fieldErrors = new HashMap<>();
        if(e.hasErrors()){
            BindingResult bindingResult = e.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
        }
        errorMap.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(errorMap);
    }


    //서버의 문제가 아니라 데이터의 문제가 있다고 전송
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Map<String, Object>> handleFKException(Exception e) {

        log.error("데이터 무결성 위반: {}", e.getMessage());

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("error", "Data Integrity Violation");
        errorMap.put("message", "데이터 제약조건을 위반했습니다.");
        errorMap.put("status", 409);
        errorMap.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMap);
    }


    //데이터가 존재하지 않는 경우의 처리
    @ExceptionHandler({NoSuchElementException.class,
            EmptyResultDataAccessException.class})    //존재하지 않는 번호의 삭제 예외
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleNoSuchElement(Exception e){

        log.error("데이터를 찾을 수 없음: {}", e.getMessage());

        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("error", "Not Found");
        errorMap.put("message", "요청한 데이터를 찾을 수 없습니다.");
        errorMap.put("status", 404);
        errorMap.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMap);
    }




}
