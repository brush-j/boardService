package com.example.exam_board.controller;

import com.example.exam_board.dto.TestForm;
import com.example.exam_board.entity.TestEntity;
import com.example.exam_board.service.TestApiService;
import org.codehaus.groovy.transform.tailrec.TernaryToIfStatementConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestApiController {

    @Autowired
    TestApiService apiService;

    @GetMapping("/api/lists")
    public List<TestEntity> getLists(){

        return apiService.viewList();

    }
    @GetMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> getOneList(@PathVariable Long id){

        return apiService.getOneList(id);

    }

    @PostMapping("/api/list")
    public TestEntity insert(@RequestBody TestForm form){

        return apiService.insertList(form);

    }
    @DeleteMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> delete(@PathVariable Long id){

    return apiService.deleteList(id);

    }

    @PatchMapping("/api/lists/{id}")
    public ResponseEntity<TestEntity> update(@PathVariable Long id,
                                             @RequestBody TestForm form){
        return apiService.updateList(id, form);
    }
}
