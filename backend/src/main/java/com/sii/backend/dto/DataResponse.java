package com.sii.backend.dto;

import com.sii.backend.model.DataModel;
import lombok.Builder;

@Builder
public record DataResponse(String id, String field, Boolean somethingElse) {
    public static DataResponse from (DataModel dataModel){
        return DataResponse.builder()
                .field(dataModel.getField())
                .id(dataModel.getId())
                .somethingElse(true)
                .build();
    }
}
