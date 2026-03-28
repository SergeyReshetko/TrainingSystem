package com.trainingsystem.service;

import com.trainingsystem.model.dto.StudentDto;
import com.trainingsystem.model.entity.StudentEntity;
import com.trainingsystem.model.mapper.StudentMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentCriteriaService {
    
    @PersistenceContext
    private final EntityManager em;
    private final StudentMapper studentMapper;
    @Value("${app.pageSize:10}")
    private int pageSize;
    @Value("${app.pageNumber:0}")
    private int pageNumber;
    
    public List<StudentDto> findAllStudentsByPredicates(
            String firstName,
            String lastName,
            Integer groupNumber,
            Integer pageSize,
            Integer pageNumber
    ) {
        log.debug("Find all students by predicates");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> root = query.from(StudentEntity.class);
        
        root.fetch("group");
        
        this.pageSize = (pageSize != null) ? pageSize : this.pageSize;
        this.pageNumber = (pageNumber != null) ? pageNumber : this.pageNumber;
        var pageable = Pageable.ofSize(this.pageSize).withPage(this.pageNumber);
        
        List<Predicate> predicates = buildPredicates(cb, root, firstName, lastName, groupNumber);
        query.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<StudentEntity> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        
        List<StudentEntity> studentEntities = typedQuery.getResultList();
        
        return studentEntities.stream()
                       .map(studentMapper::toStudentDto)
                       .toList();
    }
    
    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<StudentEntity> root,
            String firstName,
            String lastName,
            Integer groupNumber
    ) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (firstName != null && !firstName.isEmpty()) {
            log.info("Searching for first name: {}", firstName);
            
            predicates.add(cb.like(root.get("firstName").as(String.class), "%" + firstName + "%"));
        }
        
        if (lastName != null && !lastName.isEmpty()) {
            log.info("Searching for last name: {}", lastName);
            
            predicates.add(cb.like(root.get("lastName").as(String.class), "%" + lastName + "%"));
        }
        
        if (groupNumber != null && groupNumber != 0) {
            log.info("Searching for group number: {}", groupNumber);
            
            predicates.add(cb.equal(root.get("group").get("groupNumber"), groupNumber));
        }
        
        return predicates;
    }
}
