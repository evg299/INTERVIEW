package com.ifree.iterview.core.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Type1ServiceRequest.class, name = "type1"),
        @JsonSubTypes.Type(value = Type2ServiceRequest.class, name = "type2")
})

@Data
public abstract class AbstractServiceRequest {
    protected long id;
}
