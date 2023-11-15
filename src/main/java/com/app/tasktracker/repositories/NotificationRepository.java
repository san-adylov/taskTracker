package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.app.tasktracker.models.Card;
import com.app.tasktracker.models.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByCard(Card card);


    @Query("select n from Notification n join n.members m where m.id = :id")
    List<Notification> getAllNotification(Long id);
    @Query("select n from Notification n join n.members m where m.id = :id and n.id = :userId")
    Optional<Notification> getNotificationByIdUser(Long id, Long userId);

    List<Notification> findByCardIn(List<Card> cards);
}