package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Thue;
import com.mycompany.myapp.repository.ThueRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Thue}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ThueResource {

    private final Logger log = LoggerFactory.getLogger(ThueResource.class);

    private static final String ENTITY_NAME = "thue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThueRepository thueRepository;

    public ThueResource(ThueRepository thueRepository) {
        this.thueRepository = thueRepository;
    }

    /**
     * {@code POST  /thues} : Create a new thue.
     *
     * @param thue the thue to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thue, or with status {@code 400 (Bad Request)} if the thue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thues")
    public ResponseEntity<Thue> createThue(@RequestBody Thue thue) throws URISyntaxException {
        log.debug("REST request to save Thue : {}", thue);
        if (thue.getId() != null) {
            throw new BadRequestAlertException("A new thue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thue result = thueRepository.save(thue);
        return ResponseEntity
            .created(new URI("/api/thues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thues/:id} : Updates an existing thue.
     *
     * @param id the id of the thue to save.
     * @param thue the thue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thue,
     * or with status {@code 400 (Bad Request)} if the thue is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thues/{id}")
    public ResponseEntity<Thue> updateThue(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thue thue)
        throws URISyntaxException {
        log.debug("REST request to update Thue : {}, {}", id, thue);
        if (thue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Thue result = thueRepository.save(thue);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thue.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thues/:id} : Partial updates given fields of an existing thue, field will ignore if it is null
     *
     * @param id the id of the thue to save.
     * @param thue the thue to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thue,
     * or with status {@code 400 (Bad Request)} if the thue is not valid,
     * or with status {@code 404 (Not Found)} if the thue is not found,
     * or with status {@code 500 (Internal Server Error)} if the thue couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thues/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Thue> partialUpdateThue(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thue thue)
        throws URISyntaxException {
        log.debug("REST request to partial update Thue partially : {}, {}", id, thue);
        if (thue.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thue.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thue> result = thueRepository
            .findById(thue.getId())
            .map(
                existingThue -> {
                    if (thue.getTenThue() != null) {
                        existingThue.setTenThue(thue.getTenThue());
                    }
                    if (thue.getGiaTriThue() != null) {
                        existingThue.setGiaTriThue(thue.getGiaTriThue());
                    }

                    return existingThue;
                }
            )
            .map(thueRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, thue.getId().toString())
        );
    }

    /**
     * {@code GET  /thues} : get all the thues.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thues in body.
     */
    @GetMapping("/thues")
    public List<Thue> getAllThues() {
        log.debug("REST request to get all Thues");
        return thueRepository.findAll();
    }

    /**
     * {@code GET  /thues/:id} : get the "id" thue.
     *
     * @param id the id of the thue to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thue, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thues/{id}")
    public ResponseEntity<Thue> getThue(@PathVariable Long id) {
        log.debug("REST request to get Thue : {}", id);
        Optional<Thue> thue = thueRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thue);
    }

    /**
     * {@code DELETE  /thues/:id} : delete the "id" thue.
     *
     * @param id the id of the thue to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thues/{id}")
    public ResponseEntity<Void> deleteThue(@PathVariable Long id) {
        log.debug("REST request to delete Thue : {}", id);
        thueRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
