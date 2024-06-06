package com.swd391.bachhoasi.model.dto.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.swd391.bachhoasi.util.TextUtils;

import io.swagger.v3.oas.annotations.media.Schema;

public class ParameterRequest {
    @Schema(description = "A map of string to string parameters")
    private Map<String, String> parameters;
    public String getValueFromParam(String parameter) {
        if(parameter == null) return null;
        return parameters.get(parameter);
    }

    public List<String> getParameters() {
        if(parameters == null) return Collections.emptyList();
        return new ArrayList<>(parameters.keySet());
    }

    public Map<String,String> getAllParameter() {
        return TextUtils.convertKeysToCamel(parameters);
    }
}
