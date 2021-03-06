package com.reviews.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.reviews.web.domain.Category;
import com.reviews.web.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Category.
 */
@RestController
@RequestMapping("/app")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Inject
    private CategoryRepository categoryRepository;

    /**
     * POST  /rest/categories -> Create a new category.
     */
    @RequestMapping(value = "/rest/categories",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Category category) {
        log.debug("REST request to save Category : {}", category);
        categoryRepository.save(category);
    }

    /**
     * GET  /rest/categories -> get all the categories.
     */
    @RequestMapping(value = "/rest/categories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Category> getAll() {
        log.debug("REST request to get all Categories");
        return categoryRepository.findAll();
    }

    /**
     * GET  /rest/categories/:id -> get the "id" category.
     */
    @RequestMapping(value = "/rest/categories/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Category> get(@PathVariable String id) {
        log.debug("REST request to get Category : {}", id);
        return Optional.ofNullable(categoryRepository.findOne(id))
            .map(category -> new ResponseEntity<>(
                category,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rest/categories/:id -> delete the "id" category.
     */
    @RequestMapping(value = "/rest/categories/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
