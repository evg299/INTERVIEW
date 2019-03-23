package ru.bs.interview.demo.model.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.bs.interview.demo.model.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
