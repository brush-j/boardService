package com.example.exam_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing //데이터 입력시간과 최종수정시간을 자동으로 입력하기위한 오디팅선언
public class ExamBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamBoardApplication.class, args);
	}

}
