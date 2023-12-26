package org.scalable.student_registration_web_api.utilities.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonObjectMapper {
    public static <T> T jsonToObject(String json, TypeReference<T> type) throws JsonToObjectMappingException {
        try{
            return new ObjectMapper().readValue(json, type);
        }
        catch (JsonProcessingException ex){
            throw new JsonToObjectMappingException(ex);
        }
    }

    public static String objectToJson(Object object) throws ObjectToJsonMappingException {
        try{
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (Exception ex){
            throw new ObjectToJsonMappingException(ex);
        }
    }
}
