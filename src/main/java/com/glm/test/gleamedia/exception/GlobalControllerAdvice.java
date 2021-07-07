package com.glm.test.gleamedia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(GlemRuntimeException.class)
    public ResponseEntity<?> glemRuntimeException(GlemRuntimeException gre) {
      log.info("[{}:{}] {}",gre.getCode(), gre.getMessage());
      gre.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gre);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException re) {
        log.info("[{}:{}] {}",ExceptionCode.SERVER_ERRER.getCode(), re.getMessage());
        re.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
