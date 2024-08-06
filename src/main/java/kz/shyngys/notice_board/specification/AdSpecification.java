package kz.shyngys.notice_board.specification;

import jakarta.persistence.criteria.Predicate;
import kz.shyngys.notice_board.dto.filter.AdFilter;
import kz.shyngys.notice_board.model.Advertisement;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static kz.shyngys.notice_board.util.StrUtil.isNotNullAndEmpty;

public final class AdSpecification {

    private AdSpecification() {
    }

    public static Specification<Advertisement> withFilter(@NonNull AdFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (isNotNullAndEmpty(filter.title())) {
                predicates.add(criteriaBuilder.like(root.get(Advertisement.Fields.title),
                        "%" + filter.title().toLowerCase() + "%"));
            }

            if (isNotNullAndEmpty(filter.description())) {
                predicates.add(criteriaBuilder.like(root.get(Advertisement.Fields.description),
                        "%" + filter.description().toLowerCase() + "%"));
            }

            if (filter.createdAtFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Advertisement.Fields.createdAt),
                        filter.createdAtFrom()));
            }

            if (filter.createdAtTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Advertisement.Fields.createdAt),
                        filter.createdAtTo()));
            }

            if (filter.priceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Advertisement.Fields.minPrice),
                        filter.priceFrom()));
            }

            if (filter.priceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Advertisement.Fields.minPrice),
                        filter.priceTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
