package com.edunavajas.coursemanagement.service.specification;

import com.edunavajas.coursemanagement.domain.StudentEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class StudentSpecification implements Specification<StudentEntity> {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;

    public StudentSpecification(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (firstName != null) {
            Expression<String> firstNameExpression = criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("firstName")));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(firstNameExpression, "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null) {
            Expression<String> lastNameExpression = criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("lastName")));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(lastNameExpression, "%" + lastName.toLowerCase() + "%"));
        }
        if (birthDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("birthDate"), birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return predicate;
    }
}
