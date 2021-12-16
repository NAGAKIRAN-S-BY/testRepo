package com.blueyonder.exec.ecom.ud.daas.etl.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueyonder.exec.ecom.execud_daas_etl.api.FoosApi;
import com.blueyonder.exec.ecom.execud_daas_etl.api.model.FooModel;
import com.blueyonder.exec.ecom.execud_daas_etl.api.model.MetaModel;
import com.blueyonder.exec.ecom.execud_daas_etl.api.model.PageMetaModel;
import com.blueyonder.exec.ecom.execud_daas_etl.api.model.PagedFoosModel;
import com.blueyonder.exec.ecom.ud.daas.etl.model.Foo;
import com.blueyonder.exec.ecom.ud.daas.etl.rbac.LiamPermissions;
import com.blueyonder.exec.ecom.ud.daas.etl.repository.FooRepository;

/**
 * Implements endpoints for the Foo api.
 */
@RestController
@RequestMapping("/v1")
public class FooController implements FoosApi {

    private final FooRepository repo;

    /**
     * Constructs a new controller.
     *
     * @param repo The Spring Data Repository that manages Foo entities.
     */
    public FooController(FooRepository repo) {
        this.repo = repo;
    }

    /**
     * This endpoint is responsible for creating new Foo entities.
     * @param foo The api model that defines the {@link Foo} entity to create.
     * @return A new {@link Foo} entity wrapped inside a response object.
     */
    @PreAuthorize(LiamPermissions.MANAGE)
    @Override
    public ResponseEntity<FooModel> createFoo(@Valid FooModel foo) {
        var created = repo.save(Foo.builder().bar(foo.getBar()).build());
        return ResponseEntity.ok(toModel(created));
    }

    /**
     * This endpoint is responsible for listing all the Foo entities found
     * in the database.
     *
     * @param pageNumber The page of foos to fetch.
     * @param pageSize The maximum number of foos to include in the page.
     *
     * @return A list of {@link Foo} entities wrapped in a response object.
     * Note: the wrapped list of entities may be empty but should never be
     * {@code null}.
     */
    @PreAuthorize(LiamPermissions.READ)
    @Override
    public ResponseEntity<PagedFoosModel> listFoos(String fulfillmentCenterId, Integer pageNumber, Integer pageSize) {
        var pageRequest = PageRequest.of(Objects.requireNonNullElse(pageNumber, 0), Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE));
        var foos = repo.findAll(pageRequest).map(FooController::toModel);
        return ResponseEntity.ok(pagedFoosResponseBody(foos));
    }

    private static PagedFoosModel pagedFoosResponseBody(Page<FooModel> foos) {
        var pageable = foos.getPageable();

        var pageMeta = new PageMetaModel()
                .pageNumber(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalCount(foos.getTotalElements());

        return new PagedFoosModel().meta(new MetaModel().page(pageMeta))
                .count(foos.getNumberOfElements())
                .entities(foos.getContent());
    }

    private static FooModel toModel(Foo foo) {
        return new FooModel()
                .id(foo.getId().toString())
                .fulfillmentCenterId(foo.getFulfillmentCenterId())
                .bar(foo.getBar());
    }

    public static final int DEFAULT_PAGE_SIZE = 50;
}
