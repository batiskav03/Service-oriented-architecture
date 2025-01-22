package com.soa.workerservice.ejb.models;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

/*
public class WorkerSpecification implements Specification<Worker> {
    private final SearchCriteria criteria;

    public WorkerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    @Nullable
    public Predicate toPredicate(Root<Worker> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        return null;
    }

    @Override
    public Specification<Worker> and(Specification<Worker> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Worker> or(Specification<Worker> other) {
        return Specification.super.or(other);
    }

   
}
*/
