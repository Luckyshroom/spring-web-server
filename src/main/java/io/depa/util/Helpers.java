package io.depa.util;

import io.depa.exception.BadRequestException;
import io.depa.model.User;
import io.depa.model.audit.UserDateAudit;
import io.depa.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Helpers {
    static <T extends UserDateAudit> Map<Integer, User> getCreatorMap(List<T> list, UserRepository userRepository) {
        // Get Creator details of the given list
        List<Integer> creatorIds = list.stream()
                .map(T::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);

        return creators.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    static Date getExpiration() {
        return new Date(new Date().getTime() + Constants.JWT_EXPIRATION_MS);
    }

    static <T> BinaryOperator<T> toOnlyElement() {
        return toOnlyElementThrowing(IllegalArgumentException::new);
    }

    static <T, E extends RuntimeException> BinaryOperator<T> toOnlyElementThrowing(Supplier<E> exception) {
        return (element, otherElement) -> {
            throw exception.get();
        };
    }

    static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > Constants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + Constants.MAX_PAGE_SIZE);
        }
    }
}
