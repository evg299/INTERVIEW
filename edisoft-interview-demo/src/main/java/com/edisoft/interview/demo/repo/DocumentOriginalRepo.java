package com.edisoft.interview.demo.repo;

import com.edisoft.interview.demo.model.DocumentOriginal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentOriginalRepo extends CrudRepository<DocumentOriginal, Long> {
}
