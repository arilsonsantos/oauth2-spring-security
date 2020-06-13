package br.com.example.auth.security.oauth2poc.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractService<E, I extends Serializable, R extends CrudRepository<E, I>> {

    private R repository;

    public E save(E entity) {
        return this.repository.save(entity);
    }

    public E findById(I id) {
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Resource not found"));
    }

    public List<E> findAll() {
        return (List<E>) this.repository.findAll();
    }

    public void delete(I id) {
        this.repository.deleteById(id);
    }

    public R getRepository() {
        return repository;
    }

}
