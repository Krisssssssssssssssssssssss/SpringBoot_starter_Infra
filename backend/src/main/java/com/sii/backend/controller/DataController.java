package com.sii.backend.controller;

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
    @GetMapping("/test")
    public String getTest () {
        return "Hello World";
    }
    @GetMapping("find_by_id/{id}")
    public String getById (@PathVariable String id) {
        return dataService.getById(id);
    }
    @PostMapping
    public DataModel getData(@RequestBody DataModel dataModel) throws Exception {
        return dataService.addData(dataModel);
    }
}
