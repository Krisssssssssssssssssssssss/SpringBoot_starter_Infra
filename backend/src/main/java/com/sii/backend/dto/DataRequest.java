package com.sii.backend.dto;

import com.sii.backend.model.DataModel;
import lombok.NonNull;

public record DataRequest(@NonNull String field, @NonNull String intentions) {
    public DataModel toModel (){
        return DataModel.builder()
                .field(field)
                .build();
    }
}
