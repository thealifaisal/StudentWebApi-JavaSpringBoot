package org.scalable.student_registration_web_api.utilities.mapper;

public class ObjectToJsonMappingException extends Exception {
    public static String EXCEPTION_MSG = "ObjectToJsonMappingException: Failed to map object to json.";

    public ObjectToJsonMappingException(Exception ex){
        super(EXCEPTION_MSG, ex);
    }
}
