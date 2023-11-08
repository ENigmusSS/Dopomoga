package ua.holovchenko.dopomoga.repository.criteria;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpecification<T> {
    public Specification<T> columnEqual(List<Filter> filterList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            filterList.forEach( filter -> {
                Predicate predicate = criteriaBuilder.equal(root.get(filter.getColumnName()), filter.getColumnValue());
                predicates.add(predicate);
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    public Specification<T> dateLessThan(List<DateFilter> lessThanList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            lessThanList.forEach(filter -> {
                Predicate predicate = criteriaBuilder.lessThan(root.get(filter.getColumnName()), filter.getColumnValue());
                predicates.add(predicate);
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    public Specification<T> dateGreaterThan(List<DateFilter> lessThanList) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            lessThanList.forEach(filter -> {
                Predicate predicate = criteriaBuilder.greaterThan(root.get(filter.getColumnName()), filter.getColumnValue());
                predicates.add(predicate);
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
