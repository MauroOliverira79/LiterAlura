package br.com.alura.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IconverterDados {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> anyClass) {
        try {
            return objectMapper.readValue(json, anyClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error, can't process JSON.", e);
        }
    }
}
