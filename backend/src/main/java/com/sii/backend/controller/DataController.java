package com.sii.backend.controller;

import com.sii.backend.dto.DataRequest;
import com.sii.backend.dto.DataResponse;
import com.sii.backend.model.DataModel;
import com.sii.backend.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class DataController {

    private final DataService dataService;

    @GetMapping
    public List<DataModel> getAll () {
        return dataService.getAll();
    }
    @GetMapping("find_by_id/{id}")
    public String getById (@PathVariable String id) {
        return dataService.getById(id);
    }
    @PostMapping
    public DataResponse getData(@RequestBody DataRequest request) throws Exception {
        DataModel model = request.toModel();
        return DataResponse.from(dataService.addData(model));
    }
    @PutMapping("/{id}")
    public DataResponse deleteUser(@PathVariable String id, @RequestBody DataRequest request) throws Exception {
        DataModel model = request.toModel();
        model.setId(id);
        return DataResponse.from(dataService.addData(model));
    }
    @DeleteMapping("/{id}")
    public void deleteData(@PathVariable String id) throws Exception {
        dataService.deleteById(id);
    }
}
