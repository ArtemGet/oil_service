package com.artemget.oil_service.service;

public interface FinderService<T, F> {
    T find(F t);
}
