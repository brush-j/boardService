package com.example.exam_board.repository;

import com.example.exam_board.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    UserAccount findByUserId(String userId);
}
