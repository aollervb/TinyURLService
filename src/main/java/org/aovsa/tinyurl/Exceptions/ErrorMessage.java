package org.aovsa.tinyurl.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus requestStatus;
    private Date date;
    private String errorMessage;
}
