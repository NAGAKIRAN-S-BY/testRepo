package com.blueyonder.exec.ecom.execud-daas-etl.controller;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import com.blueyonder.exec.ecom.execud-daas-etl.api.model.FooModel;
import com.blueyonder.exec.ecom.execud-daas-etl.model.Foo;
import com.blueyonder.exec.ecom.execud-daas-etl.repository.FooRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class FooControllerTests {
    public static final String FC1 = "FC1";

    @Autowired
    private FooRepository repo;

    private FooController controller;

    @BeforeEach
    void init() {
        controller = new FooController(repo);
    }

    @Test
    void noFoosAvailable() {
        var response = controller.listFoos(FC1, 0, 50);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var body = response.getBody();
        assertNotNull(body);
        assertEquals(List.of(), body.getEntities());
    }

    @Test
    void aFooIsFound() {
        var foo = repo.save(Foo.builder().bar("test").build());
        var response = controller.listFoos(FC1, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var body = response.getBody();
        assertNotNull(body);

        assertEquals(
                List.of(new FooModel().id(foo.getId().toString()).bar("test")),
                body.getEntities()
        );

        assertEquals(0, body.getMeta().getPage().getPageNumber());
        assertEquals(50, body.getMeta().getPage().getPageSize());
        assertEquals(1, body.getMeta().getPage().getTotalCount());
        assertEquals(1, body.getCount());
    }

    @Test
    void createFoo() {
        assertEquals(List.of(), repo.findAll());
        var response = controller.createFoo(new FooModel().bar("new"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        var foo = response.getBody();
        assertNotNull(foo, "A foo should have been created");

        var dbFoo = repo.findOne(Example.of(Foo.builder().bar("new").build())).orElseThrow();
        assertEquals(dbFoo.getId().toString(), foo.getId());
        assertEquals(dbFoo.getBar(), foo.getBar());
    }
}
