package com.mobcomms.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericEnumConverter<E extends Enum<E> & EnumType> implements AttributeConverter<E, String> {

    private final Map<String, E> codeToEnumMap = new HashMap<>();

    protected GenericEnumConverter(Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            codeToEnumMap.put(e.getCode(), e);
        }
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return dbData != null ? codeToEnumMap.get(dbData) : null;
    }
}
