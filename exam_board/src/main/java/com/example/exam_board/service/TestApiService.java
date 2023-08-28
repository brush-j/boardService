package com.example.exam_board.service;

import com.example.exam_board.dto.TestForm;
import com.example.exam_board.entity.TestEntity;
import com.example.exam_board.repository.TestEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TestApiService {
    @Autowired
    TestEntityRepository repository;


    public List<TestEntity> viewList(){
        return repository.findAll();
    }

    public ResponseEntity<TestEntity> getOneList(Long id) {
        TestEntity entity = repository.findById(id).orElse(null);
        if(entity == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(entity);
    }

    @Transactional
    public TestEntity insertList(TestForm form){
        TestEntity entity = new TestEntity();
        entity.setMemo(form.getMemo());
        return repository.save(entity);
    }

    @Transactional
    public ResponseEntity<TestEntity> deleteList(Long id){
        TestEntity entity = repository.findById(id).orElse(null);
        if(entity == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @Transactional
    public ResponseEntity<TestEntity> updateList(Long id, TestForm form) {
        TestEntity entity = repository.findById(id).orElse(null);
        if(entity == null || id != entity.getId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        entity.setMemo(form.getMemo());
        TestEntity update = repository.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body(update);

    }
}
