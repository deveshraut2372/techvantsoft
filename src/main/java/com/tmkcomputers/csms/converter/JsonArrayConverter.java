package com.tmkcomputers.csms.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.json.JSONArray;
import org.json.JSONException;

@Converter
public class JsonArrayConverter implements AttributeConverter<JSONArray, String> {

    @Override
    public String convertToDatabaseColumn(JSONArray attribute) {
        // Convert JSONArray to a String
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        // Convert String to JSONArray
        try {
            return dbData != null ? new JSONArray(dbData) : null;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Error converting JSON String to JSONArray", e);
        }
    }
}
