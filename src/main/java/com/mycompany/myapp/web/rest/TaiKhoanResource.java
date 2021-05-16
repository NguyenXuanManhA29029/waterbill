package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TaiKhoan;
import com.mycompany.myapp.repository.TaiKhoanRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.TaiKhoan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaiKhoanResource {

    private final Logger log = LoggerFactory.getLogger(TaiKhoanResource.class);

    private static final String ENTITY_NAME = "taiKhoan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaiKhoanRepository taiKhoanRepository;

    public TaiKhoanResource(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    /**
     * {@code POST  /tai-khoans} : Create a new taiKhoan.
     *
     * @param taiKhoan the taiKhoan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taiKhoan, or with status {@code 400 (Bad Request)} if the taiKhoan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tai-khoans")
    public ResponseEntity<TaiKhoan> createTaiKhoan(@RequestBody TaiKhoan taiKhoan) throws URISyntaxException {
        log.debug("REST request to save TaiKhoan : {}", taiKhoan);
        if (taiKhoan.getId() != null) {
            throw new BadRequestAlertException("A new taiKhoan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaiKhoan result = taiKhoanRepository.save(taiKhoan);
        return ResponseEntity
            .created(new URI("/api/tai-khoans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tai-khoans/:id} : Updates an existing taiKhoan.
     *
     * @param id the id of the taiKhoan to save.
     * @param taiKhoan the taiKhoan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taiKhoan,
     * or with status {@code 400 (Bad Request)} if the taiKhoan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taiKhoan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tai-khoans/{id}")
    public ResponseEntity<TaiKhoan> updateTaiKhoan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaiKhoan taiKhoan
    ) throws URISyntaxException {
        log.debug("REST request to update TaiKhoan : {}, {}", id, taiKhoan);
        if (taiKhoan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taiKhoan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taiKhoanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaiKhoan result = taiKhoanRepository.save(taiKhoan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taiKhoan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tai-khoans/:id} : Partial updates given fields of an existing taiKhoan, field will ignore if it is null
     *
     * @param id the id of the taiKhoan to save.
     * @param taiKhoan the taiKhoan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taiKhoan,
     * or with status {@code 400 (Bad Request)} if the taiKhoan is not valid,
     * or with status {@code 404 (Not Found)} if the taiKhoan is not found,
     * or with status {@code 500 (Internal Server Error)} if the taiKhoan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tai-khoans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaiKhoan> partialUpdateTaiKhoan(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaiKhoan taiKhoan
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaiKhoan partially : {}, {}", id, taiKhoan);
        if (taiKhoan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taiKhoan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taiKhoanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaiKhoan> result = taiKhoanRepository
            .findById(taiKhoan.getId())
            .map(
                existingTaiKhoan -> {
                    if (taiKhoan.getUserName() != null) {
                        existingTaiKhoan.setUserName(taiKhoan.getUserName());
                    }
                    if (taiKhoan.getPassWord() != null) {
                        existingTaiKhoan.setPassWord(taiKhoan.getPassWord());
                    }

                    return existingTaiKhoan;
                }
            )
            .map(taiKhoanRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taiKhoan.getId().toString())
        );
    }

    /**
     * {@code GET  /tai-khoans} : get all the taiKhoans.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taiKhoans in body.
     */
    @GetMapping("/tai-khoans")
    public List<TaiKhoan> getAllTaiKhoans(@RequestParam(required = false) String filter) {
        if ("hogiadinh-is-null".equals(filter)) {
            log.debug("REST request to get all TaiKhoans where hoGiaDinh is null");
            return StreamSupport
                .stream(taiKhoanRepository.findAll().spliterator(), false)
                .filter(taiKhoan -> taiKhoan.getHoGiaDinh() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TaiKhoans");
        return taiKhoanRepository.findAll();
    }

    /**
     * {@code GET  /tai-khoans/:id} : get the "id" taiKhoan.
     *
     * @param id the id of the taiKhoan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taiKhoan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tai-khoans/{id}")
    public ResponseEntity<TaiKhoan> getTaiKhoan(@PathVariable Long id) {
        log.debug("REST request to get TaiKhoan : {}", id);
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taiKhoan);
    }

    /**
     * {@code DELETE  /tai-khoans/:id} : delete the "id" taiKhoan.
     *
     * @param id the id of the taiKhoan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tai-khoans/{id}")
    public ResponseEntity<Void> deleteTaiKhoan(@PathVariable Long id) {
        log.debug("REST request to delete TaiKhoan : {}", id);
        taiKhoanRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
