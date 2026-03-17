package br.com.gestrest.api.adapter.in.web.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import br.com.gestrest.api.domain.exception.EntityNotFoundException;
import br.com.gestrest.api.domain.exception.DuplicateResourceException;
import br.com.gestrest.api.domain.exception.BusinessException;
import br.com.gestrest.api.domain.exception.UnauthorizedOperationException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound() {
        WebRequest req = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<ErrorResponse> r = handler.handleNotFound(new EntityNotFoundException("X"), req);
        assertEquals(404, r.getBody().getStatus());
    }

    @Test
    void handleConflict() {
        WebRequest req = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<ErrorResponse> r = handler.handleConflict(new DuplicateResourceException("D"), req);
        assertEquals(409, r.getBody().getStatus());
    }

    @Test
    void handleBusiness() {
        WebRequest req = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<ErrorResponse> r = handler.handleBusiness(new BusinessException("B"), req);
        assertEquals(400, r.getBody().getStatus());
    }

    @Test
    void handleUnauthorized() {
        WebRequest req = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<ErrorResponse> r = handler.handleUnauthorized(new UnauthorizedOperationException("U"), req);
        assertEquals(403, r.getBody().getStatus());
    }

    @Test
    void handleRuntime() {
        WebRequest req = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<ErrorResponse> r = handler.handleRuntime(new RuntimeException("R"), req);
        assertEquals(500, r.getBody().getStatus());
    }

    @Test
    void handleValidation() {
        MockHttpServletRequest servlet = new MockHttpServletRequest();
        WebRequest req = new ServletWebRequest(servlet);

        var target = new Object();
        var binding = new BeanPropertyBindingResult(target, "obj");
        binding.addError(new FieldError("obj", "field", "msg"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, binding);
        ResponseEntity<ErrorResponse> r = handler.handleValidation(ex, req);
        assertEquals(400, r.getBody().getStatus());
        assertEquals(1, r.getBody().getErrors().size());
    }
}
