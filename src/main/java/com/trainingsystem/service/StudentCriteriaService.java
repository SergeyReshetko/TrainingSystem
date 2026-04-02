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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentCriteriaService {
    
    @PersistenceContext
    private final EntityManager entityManager;
    private final StudentMapper studentMapper;
    @Value("${app.pageSize:10}")
    private int defaultPageSize;
    @Value("${app.pageNumber:0}")
    private int defaultPageNumber;
    
    public Page<StudentDto> findAllStudentsByPredicates(
            String firstName,
            String lastName,
            Integer groupNumber,
            Pageable pageable
    ) {
        log.debug("Find all students by predicates {}", pageable);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = cb.createQuery(StudentEntity.class);
        Root<StudentEntity> root = query.from(StudentEntity.class);
        root.fetch("group");
        
        query.orderBy(
                cb.asc(root.get("firstName")),
                cb.asc(root.get("lastName"))
        );
        
        if (pageable == null) {
            pageable = PageRequest.of(defaultPageNumber, defaultPageSize);
        }
        
        List<Predicate> predicates = buildPredicates(cb, root, firstName, lastName, groupNumber);
        query.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<StudentEntity> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        
        List<StudentEntity> studentEntities = typedQuery.getResultList();
        
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<StudentEntity> countRoot = countQuery.from(StudentEntity.class);
        countQuery.select(cb.count(countRoot));
        
        Long total = entityManager.createQuery(countQuery).getSingleResult();
        
        return new PageImpl<>(
                studentEntities.stream()
                        .map(studentMapper::toStudentDto)
                        .toList(),
                pageable,
                total);
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
