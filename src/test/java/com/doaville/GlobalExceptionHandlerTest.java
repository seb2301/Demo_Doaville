package com.doaville;

import com.doaville.exception.BusinessException;
import com.doaville.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBusinessException() {
        BusinessException ex = new BusinessException("Erro de negócio");
        ResponseEntity<String> response = handler.handleBusinessException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro de negócio", response.getBody());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Erro runtime");
        ResponseEntity<String> response = handler.handleRuntimeException(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro runtime", response.getBody());
    }
}
