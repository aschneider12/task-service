package br.dev.as.task_service;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    private TaskSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<Task> create(final String status) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();
            if(status != null && !status.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("status"), status.toUpperCase()));
            }
            return predicates;
        };
    }
}
