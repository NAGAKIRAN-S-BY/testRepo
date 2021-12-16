package com.blueyonder.exec.ecom.ud.daas.etl.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blueyonder.exec.ecom.ud.daas.etl.model.Foo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FooRepositoryTests {

    @Autowired
    private FooRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void givenServiceStartup_EmptyItemsAreReturned() {
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void createFoo() {
        Foo foo = Foo.builder().bar("test").build();
        repository.save(foo);
        assertEquals(1, repository.findAll().size());
        assertNotNull(repository.getOne(foo.getId()));
    }

    @Test
    public void deleteFoo() {
        Foo foo = Foo.builder().bar("test").build();
        repository.save(foo);
        assertEquals(1, repository.findAll().size());
        repository.deleteById(foo.getId());
        assertEquals(0, repository.findAll().size());
    }

    @Test
    public void updateFoo() {
        Foo item = Foo.builder().bar("test").build();
        repository.save(item);
        assertEquals(1, repository.findAll().size());

        item.setBar("newName");
        repository.save(item);
        assertEquals("newName", repository.findById(item.getId()).orElseThrow().getBar());
    }
}
