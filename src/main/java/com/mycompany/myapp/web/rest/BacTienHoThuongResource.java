package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BacTienHoThuong;
import com.mycompany.myapp.repository.BacTienHoThuongRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BacTienHoThuong}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BacTienHoThuongResource {

    private final Logger log = LoggerFactory.getLogger(BacTienHoThuongResource.class);

    private static final String ENTITY_NAME = "bacTienHoThuong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BacTienHoThuongRepository bacTienHoThuongRepository;

    public BacTienHoThuongResource(BacTienHoThuongRepository bacTienHoThuongRepository) {
        this.bacTienHoThuongRepository = bacTienHoThuongRepository;
    }

    /**
     * {@code POST  /bac-tien-ho-thuongs} : Create a new bacTienHoThuong.
     *
     * @param bacTienHoThuong the bacTienHoThuong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bacTienHoThuong, or with status {@code 400 (Bad Request)} if the bacTienHoThuong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bac-tien-ho-thuongs")
    public ResponseEntity<BacTienHoThuong> createBacTienHoThuong(@RequestBody BacTienHoThuong bacTienHoThuong) throws URISyntaxException {
        log.debug("REST request to save BacTienHoThuong : {}", bacTienHoThuong);
        if (bacTienHoThuong.getId() != null) {
            throw new BadRequestAlertException("A new bacTienHoThuong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BacTienHoThuong result = bacTienHoThuongRepository.save(bacTienHoThuong);
        return ResponseEntity
            .created(new URI("/api/bac-tien-ho-thuongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bac-tien-ho-thuongs/:id} : Updates an existing bacTienHoThuong.
     *
     * @param id the id of the bacTienHoThuong to save.
     * @param bacTienHoThuong the bacTienHoThuong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bacTienHoThuong,
     * or with status {@code 400 (Bad Request)} if the bacTienHoThuong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bacTienHoThuong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bac-tien-ho-thuongs/{id}")
    public ResponseEntity<BacTienHoThuong> updateBacTienHoThuong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BacTienHoThuong bacTienHoThuong
    ) throws URISyntaxException {
        log.debug("REST request to update BacTienHoThuong : {}, {}", id, bacTienHoThuong);
        if (bacTienHoThuong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bacTienHoThuong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bacTienHoThuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BacTienHoThuong result = bacTienHoThuongRepository.save(bacTienHoThuong);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bacTienHoThuong.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bac-tien-ho-thuongs/:id} : Partial updates given fields of an existing bacTienHoThuong, field will ignore if it is null
     *
     * @param id the id of the bacTienHoThuong to save.
     * @param bacTienHoThuong the bacTienHoThuong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bacTienHoThuong,
     * or with status {@code 400 (Bad Request)} if the bacTienHoThuong is not valid,
     * or with status {@code 404 (Not Found)} if the bacTienHoThuong is not found,
     * or with status {@code 500 (Internal Server Error)} if the bacTienHoThuong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bac-tien-ho-thuongs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BacTienHoThuong> partialUpdateBacTienHoThuong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BacTienHoThuong bacTienHoThuong
    ) throws URISyntaxException {
        log.debug("REST request to partial update BacTienHoThuong partially : {}, {}", id, bacTienHoThuong);
        if (bacTienHoThuong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bacTienHoThuong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bacTienHoThuongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BacTienHoThuong> result = bacTienHoThuongRepository
            .findById(bacTienHoThuong.getId())
            .map(
                existingBacTienHoThuong -> {
                    if (bacTienHoThuong.getTenBac() != null) {
                        existingBacTienHoThuong.setTenBac(bacTienHoThuong.getTenBac());
                    }
                    if (bacTienHoThuong.getGiaTriBac() != null) {
                        existingBacTienHoThuong.setGiaTriBac(bacTienHoThuong.getGiaTriBac());
                    }

                    return existingBacTienHoThuong;
                }
            )
            .map(bacTienHoThuongRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bacTienHoThuong.getId().toString())
        );
    }

    /**
     * {@code GET  /bac-tien-ho-thuongs} : get all the bacTienHoThuongs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bacTienHoThuongs in body.
     */
    @GetMapping("/bac-tien-ho-thuongs")
    public List<BacTienHoThuong> getAllBacTienHoThuongs() {
        log.debug("REST request to get all BacTienHoThuongs");
        return bacTienHoThuongRepository.findAll();
    }

    /**
     * {@code GET  /bac-tien-ho-thuongs/:id} : get the "id" bacTienHoThuong.
     *
     * @param id the id of the bacTienHoThuong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bacTienHoThuong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bac-tien-ho-thuongs/{id}")
    public ResponseEntity<BacTienHoThuong> getBacTienHoThuong(@PathVariable Long id) {
        log.debug("REST request to get BacTienHoThuong : {}", id);
        Optional<BacTienHoThuong> bacTienHoThuong = bacTienHoThuongRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bacTienHoThuong);
    }

    /**
     * {@code DELETE  /bac-tien-ho-thuongs/:id} : delete the "id" bacTienHoThuong.
     *
     * @param id the id of the bacTienHoThuong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bac-tien-ho-thuongs/{id}")
    public ResponseEntity<Void> deleteBacTienHoThuong(@PathVariable Long id) {
        log.debug("REST request to delete BacTienHoThuong : {}", id);
        bacTienHoThuongRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
