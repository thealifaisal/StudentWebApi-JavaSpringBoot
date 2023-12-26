package org.scalable.student_registration_web_api.interceptors;

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
