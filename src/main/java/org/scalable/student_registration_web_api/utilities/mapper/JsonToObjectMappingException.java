package org.scalable.student_registration_web_api.utilities.mapper;

public class JsonToObjectMappingException extends Exception {
    public static String EXCEPTION_MSG = "JsonToObjectMappingException: Failed to map json to object.";

    public JsonToObjectMappingException(Exception ex){
        super(EXCEPTION_MSG, ex);
    }
}
