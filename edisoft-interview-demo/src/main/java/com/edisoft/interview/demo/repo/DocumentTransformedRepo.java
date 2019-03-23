package com.edisoft.interview.demo.repo;

import com.edisoft.interview.demo.model.DocumentTransformed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTransformedRepo extends CrudRepository<DocumentTransformed, Long> {
}
