package org.scalable.amigoscodetests.middlewares;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto{
    public String statusCode;
    public String exceptionMessage;
}
