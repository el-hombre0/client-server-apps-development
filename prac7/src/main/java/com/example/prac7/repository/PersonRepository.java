package com.example.prac7.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.example.prac7.entity.PersonEntity;

import reactor.core.publisher.Mono;

public interface PersonRepository extends R2dbcRepository<PersonEntity, Long> {
    Mono<PersonEntity> findByUsername(String username);
}
