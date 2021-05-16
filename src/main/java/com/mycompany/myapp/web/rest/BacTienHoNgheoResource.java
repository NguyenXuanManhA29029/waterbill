package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BacTienHoNgheo;
import com.mycompany.myapp.repository.BacTienHoNgheoRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BacTienHoNgheo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BacTienHoNgheoResource {

    private final Logger log = LoggerFactory.getLogger(BacTienHoNgheoResource.class);

    private static final String ENTITY_NAME = "bacTienHoNgheo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BacTienHoNgheoRepository bacTienHoNgheoRepository;

    public BacTienHoNgheoResource(BacTienHoNgheoRepository bacTienHoNgheoRepository) {
        this.bacTienHoNgheoRepository = bacTienHoNgheoRepository;
    }

    /**
     * {@code POST  /bac-tien-ho-ngheos} : Create a new bacTienHoNgheo.
     *
     * @param bacTienHoNgheo the bacTienHoNgheo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bacTienHoNgheo, or with status {@code 400 (Bad Request)} if the bacTienHoNgheo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bac-tien-ho-ngheos")
    public ResponseEntity<BacTienHoNgheo> createBacTienHoNgheo(@RequestBody BacTienHoNgheo bacTienHoNgheo) throws URISyntaxException {
        log.debug("REST request to save BacTienHoNgheo : {}", bacTienHoNgheo);
        if (bacTienHoNgheo.getId() != null) {
            throw new BadRequestAlertException("A new bacTienHoNgheo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BacTienHoNgheo result = bacTienHoNgheoRepository.save(bacTienHoNgheo);
        return ResponseEntity
            .created(new URI("/api/bac-tien-ho-ngheos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bac-tien-ho-ngheos/:id} : Updates an existing bacTienHoNgheo.
     *
     * @param id the id of the bacTienHoNgheo to save.
     * @param bacTienHoNgheo the bacTienHoNgheo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bacTienHoNgheo,
     * or with status {@code 400 (Bad Request)} if the bacTienHoNgheo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bacTienHoNgheo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bac-tien-ho-ngheos/{id}")
    public ResponseEntity<BacTienHoNgheo> updateBacTienHoNgheo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BacTienHoNgheo bacTienHoNgheo
    ) throws URISyntaxException {
        log.debug("REST request to update BacTienHoNgheo : {}, {}", id, bacTienHoNgheo);
        if (bacTienHoNgheo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bacTienHoNgheo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bacTienHoNgheoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BacTienHoNgheo result = bacTienHoNgheoRepository.save(bacTienHoNgheo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bacTienHoNgheo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bac-tien-ho-ngheos/:id} : Partial updates given fields of an existing bacTienHoNgheo, field will ignore if it is null
     *
     * @param id the id of the bacTienHoNgheo to save.
     * @param bacTienHoNgheo the bacTienHoNgheo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bacTienHoNgheo,
     * or with status {@code 400 (Bad Request)} if the bacTienHoNgheo is not valid,
     * or with status {@code 404 (Not Found)} if the bacTienHoNgheo is not found,
     * or with status {@code 500 (Internal Server Error)} if the bacTienHoNgheo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bac-tien-ho-ngheos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BacTienHoNgheo> partialUpdateBacTienHoNgheo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BacTienHoNgheo bacTienHoNgheo
    ) throws URISyntaxException {
        log.debug("REST request to partial update BacTienHoNgheo partially : {}, {}", id, bacTienHoNgheo);
        if (bacTienHoNgheo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bacTienHoNgheo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bacTienHoNgheoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BacTienHoNgheo> result = bacTienHoNgheoRepository
            .findById(bacTienHoNgheo.getId())
            .map(
                existingBacTienHoNgheo -> {
                    if (bacTienHoNgheo.getTenBac() != null) {
                        existingBacTienHoNgheo.setTenBac(bacTienHoNgheo.getTenBac());
                    }
                    if (bacTienHoNgheo.getGiaTriBac() != null) {
                        existingBacTienHoNgheo.setGiaTriBac(bacTienHoNgheo.getGiaTriBac());
                    }

                    return existingBacTienHoNgheo;
                }
            )
            .map(bacTienHoNgheoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bacTienHoNgheo.getId().toString())
        );
    }

    /**
     * {@code GET  /bac-tien-ho-ngheos} : get all the bacTienHoNgheos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bacTienHoNgheos in body.
     */
    @GetMapping("/bac-tien-ho-ngheos")
    public List<BacTienHoNgheo> getAllBacTienHoNgheos() {
        log.debug("REST request to get all BacTienHoNgheos");
        return bacTienHoNgheoRepository.findAll();
    }

    /**
     * {@code GET  /bac-tien-ho-ngheos/:id} : get the "id" bacTienHoNgheo.
     *
     * @param id the id of the bacTienHoNgheo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bacTienHoNgheo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bac-tien-ho-ngheos/{id}")
    public ResponseEntity<BacTienHoNgheo> getBacTienHoNgheo(@PathVariable Long id) {
        log.debug("REST request to get BacTienHoNgheo : {}", id);
        Optional<BacTienHoNgheo> bacTienHoNgheo = bacTienHoNgheoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bacTienHoNgheo);
    }

    /**
     * {@code DELETE  /bac-tien-ho-ngheos/:id} : delete the "id" bacTienHoNgheo.
     *
     * @param id the id of the bacTienHoNgheo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bac-tien-ho-ngheos/{id}")
    public ResponseEntity<Void> deleteBacTienHoNgheo(@PathVariable Long id) {
        log.debug("REST request to delete BacTienHoNgheo : {}", id);
        bacTienHoNgheoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
