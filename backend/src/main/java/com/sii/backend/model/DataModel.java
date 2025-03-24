package com.sii.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Data
@Document("Data")
public class DataModel {
    @Id
    private String id;
    private String field;
}
