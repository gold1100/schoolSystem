package com.example.schoolSystem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericPersonController<T> {
    Page<T> getAll(Pageable pageable);
    Page<T> searchByName(String firstName, String lastName, Pageable pageable);
    T create(T dto);
    T editDetails(Long id, T dto);
    void delete(Long id);
}
