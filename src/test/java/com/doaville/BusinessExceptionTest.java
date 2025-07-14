package com.doaville;

import com.doaville.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void deveCriarBusinessExceptionComMensagem() {
        String mensagem = "Erro de negócio";
        BusinessException ex = new BusinessException(mensagem);
        assertEquals(mensagem, ex.getMessage());
    }
}
