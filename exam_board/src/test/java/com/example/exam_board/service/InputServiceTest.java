package com.example.exam_board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class InputServiceTest {
    @Autowired
    InputService inputService;

    @Test
    void inputTest(){
        inputService.inputArticleTest();
    }

}