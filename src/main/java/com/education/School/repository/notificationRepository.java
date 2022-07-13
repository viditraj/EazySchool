package com.education.School.repository;


import com.education.School.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface notificationRepository extends JpaRepository<Notification, Integer> {

}
