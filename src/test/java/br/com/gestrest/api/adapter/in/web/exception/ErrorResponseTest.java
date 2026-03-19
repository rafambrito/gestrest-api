package br.com.gestrest.api.adapter.in.web.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void gettersAndSetters() {
        var now = OffsetDateTime.now();
        var resp = new ErrorResponse(now, 400, "Err", "Msg", "/path");
        resp.setStatus(401);
        resp.setError("E2");
        resp.setMessage("M2");
        resp.setPath("/p");
        resp.setTimestamp(now);

        var fe = new ErrorResponse.FieldError("field", "msg");
        resp.setErrors(List.of(fe));

        assertEquals(401, resp.getStatus());
        assertEquals("E2", resp.getError());
        assertNotNull(resp.getErrors());
        assertEquals("field", resp.getErrors().get(0).getField());
    }
}
