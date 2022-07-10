package com.education.School.repository;


import com.education.School.model.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface classRoomRepository extends JpaRepository<ClassRoom, Integer > {

}
