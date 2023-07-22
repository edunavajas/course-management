package com.edunavajas.coursemanagement.service.specification;

import com.edunavajas.coursemanagement.domain.CourseEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.OffsetDateTime;

public class CourseSpecification implements Specification<CourseEntity> {

    private final String name;
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;

    public CourseSpecification(String name, OffsetDateTime startDate, OffsetDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Predicate toPredicate(Root<CourseEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (name != null) {
            Expression<String> nameExpression = criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, root.get("name")));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(nameExpression, "%" + name.toLowerCase() + "%"));
        }

        if (startDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate.toInstant()));
        }
        if (endDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate.toInstant()));
        }

        return predicate;
    }
}