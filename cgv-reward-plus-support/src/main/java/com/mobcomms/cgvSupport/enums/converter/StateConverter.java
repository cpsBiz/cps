package com.mobcomms.cgvSupport.enums.converter;

import com.mobcomms.cgvSupport.enums.StateEnum;
import com.mobcomms.common.enums.GenericEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter extends GenericEnumConverter<StateEnum> {
    public StateConverter() {
        super(StateEnum.class);
    }
}
