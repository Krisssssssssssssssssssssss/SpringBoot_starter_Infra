package com.sii.backend.repository;

import com.sii.backend.model.DataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataRepository extends MongoRepository<DataModel, String> {
    Optional<DataModel> findByField (String fieldName);
}
