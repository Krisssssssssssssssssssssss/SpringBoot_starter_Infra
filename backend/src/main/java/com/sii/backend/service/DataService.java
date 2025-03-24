package com.sii.backend.service;

import com.sii.backend.model.DataModel;
import com.sii.backend.repository.DataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataService {
    private final DataRepository dataRepository;

    public String getById(String id) {
         return dataRepository.findById(id).orElseThrow().getField();
    }
    public List<DataModel> getAll() {
        return dataRepository.findAll();
    }

    public DataModel addData(DataModel dataModel) {
        return dataRepository.save(dataModel);
    }
    public DataModel editData(DataModel dataModel) throws Exception {
        DataModel model = dataRepository.findById(dataModel.getId()).orElseThrow();
        return dataRepository.save((model));
    }
    public void deleteById (String id) {
        dataRepository.deleteById(id);
    }
}
