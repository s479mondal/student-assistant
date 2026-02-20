package com.studentassistant.repository;

import com.studentassistant.entity.Message;
import com.studentassistant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiverOrderBySentAtDesc(User receiver);

    List<Message> findBySenderOrderBySentAtDesc(User sender);
}