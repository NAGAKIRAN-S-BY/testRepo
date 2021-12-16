package com.blueyonder.exec.ecom.execud-daas-etl.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueyonder.exec.ecom.execud-daas-etl.model.Foo;

/**
 * Spring Data DAO (repository) for {@link Foo}.
 */
@Repository
public interface FooRepository extends JpaRepository<Foo, UUID> {
}
