package net.carlosfe.tests.sintad.global.services;

import java.util.List;

public interface IGenericService<T, ID> {
    T save(T object);
    T update(ID id, T object);
    void deleteById(ID id);
    T findById(ID id);
    T findByIdAndEstado(ID id, boolean estado);
    List<T> findAll();
}
