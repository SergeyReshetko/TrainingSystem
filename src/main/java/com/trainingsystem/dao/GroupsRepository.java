package com.trainingsystem.dao;

import com.trainingsystem.model.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupsRepository extends JpaRepository<GroupEntity, Integer> {
    
    @Query("SELECT g FROM GroupEntity g WHERE  g.groupNumber = :groupNumber")
    Optional<GroupEntity> findByGroupNumber(@Param("groupNumber") Integer groupNumber);
}