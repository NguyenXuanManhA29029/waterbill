package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.HoGiaDinh;
import com.mycompany.myapp.repository.HoGiaDinhRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.HoGiaDinh}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HoGiaDinhResource {

    private final Logger log = LoggerFactory.getLogger(HoGiaDinhResource.class);

    private static final String ENTITY_NAME = "hoGiaDinh";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HoGiaDinhRepository hoGiaDinhRepository;

    public HoGiaDinhResource(HoGiaDinhRepository hoGiaDinhRepository) {
        this.hoGiaDinhRepository = hoGiaDinhRepository;
    }

    /**
     * {@code POST  /ho-gia-dinhs} : Create a new hoGiaDinh.
     *
     * @param hoGiaDinh the hoGiaDinh to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hoGiaDinh, or with status {@code 400 (Bad Request)} if the hoGiaDinh has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ho-gia-dinhs")
    public ResponseEntity<HoGiaDinh> createHoGiaDinh(@RequestBody HoGiaDinh hoGiaDinh) throws URISyntaxException {
        log.debug("REST request to save HoGiaDinh : {}", hoGiaDinh);
        if (hoGiaDinh.getId() != null) {
            throw new BadRequestAlertException("A new hoGiaDinh cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HoGiaDinh result = hoGiaDinhRepository.save(hoGiaDinh);
        return ResponseEntity
            .created(new URI("/api/ho-gia-dinhs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ho-gia-dinhs/:id} : Updates an existing hoGiaDinh.
     *
     * @param id the id of the hoGiaDinh to save.
     * @param hoGiaDinh the hoGiaDinh to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoGiaDinh,
     * or with status {@code 400 (Bad Request)} if the hoGiaDinh is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hoGiaDinh couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ho-gia-dinhs/{id}")
    public ResponseEntity<HoGiaDinh> updateHoGiaDinh(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HoGiaDinh hoGiaDinh
    ) throws URISyntaxException {
        log.debug("REST request to update HoGiaDinh : {}, {}", id, hoGiaDinh);
        if (hoGiaDinh.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoGiaDinh.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoGiaDinhRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HoGiaDinh result = hoGiaDinhRepository.save(hoGiaDinh);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoGiaDinh.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ho-gia-dinhs/:id} : Partial updates given fields of an existing hoGiaDinh, field will ignore if it is null
     *
     * @param id the id of the hoGiaDinh to save.
     * @param hoGiaDinh the hoGiaDinh to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hoGiaDinh,
     * or with status {@code 400 (Bad Request)} if the hoGiaDinh is not valid,
     * or with status {@code 404 (Not Found)} if the hoGiaDinh is not found,
     * or with status {@code 500 (Internal Server Error)} if the hoGiaDinh couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ho-gia-dinhs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HoGiaDinh> partialUpdateHoGiaDinh(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HoGiaDinh hoGiaDinh
    ) throws URISyntaxException {
        log.debug("REST request to partial update HoGiaDinh partially : {}, {}", id, hoGiaDinh);
        if (hoGiaDinh.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hoGiaDinh.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hoGiaDinhRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HoGiaDinh> result = hoGiaDinhRepository
            .findById(hoGiaDinh.getId())
            .map(
                existingHoGiaDinh -> {
                    if (hoGiaDinh.getTenChuHo() != null) {
                        existingHoGiaDinh.setTenChuHo(hoGiaDinh.getTenChuHo());
                    }
                    if (hoGiaDinh.getMaHo() != null) {
                        existingHoGiaDinh.setMaHo(hoGiaDinh.getMaHo());
                    }
                    if (hoGiaDinh.getSoCanCuoc() != null) {
                        existingHoGiaDinh.setSoCanCuoc(hoGiaDinh.getSoCanCuoc());
                    }
                    if (hoGiaDinh.getLoaiHo() != null) {
                        existingHoGiaDinh.setLoaiHo(hoGiaDinh.getLoaiHo());
                    }
                    if (hoGiaDinh.getSoHoNgheo() != null) {
                        existingHoGiaDinh.setSoHoNgheo(hoGiaDinh.getSoHoNgheo());
                    }
                    if (hoGiaDinh.getEmail() != null) {
                        existingHoGiaDinh.setEmail(hoGiaDinh.getEmail());
                    }
                    if (hoGiaDinh.getSdt() != null) {
                        existingHoGiaDinh.setSdt(hoGiaDinh.getSdt());
                    }
                    if (hoGiaDinh.getDiaChi() != null) {
                        existingHoGiaDinh.setDiaChi(hoGiaDinh.getDiaChi());
                    }

                    return existingHoGiaDinh;
                }
            )
            .map(hoGiaDinhRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hoGiaDinh.getId().toString())
        );
    }

    /**
     * {@code GET  /ho-gia-dinhs} : get all the hoGiaDinhs.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hoGiaDinhs in body.
     */
    @GetMapping("/ho-gia-dinhs")
    public List<HoGiaDinh> getAllHoGiaDinhs(@RequestParam(required = false) String filter) {
        if ("hoadongd-is-null".equals(filter)) {
            log.debug("REST request to get all HoGiaDinhs where hoadongd is null");
            return StreamSupport
                .stream(hoGiaDinhRepository.findAll().spliterator(), false)
                .filter(hoGiaDinh -> hoGiaDinh.getHoadongd() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all HoGiaDinhs");
        return hoGiaDinhRepository.findAll();
    }

    /**
     * {@code GET  /ho-gia-dinhs/:id} : get the "id" hoGiaDinh.
     *
     * @param id the id of the hoGiaDinh to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hoGiaDinh, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ho-gia-dinhs/{id}")
    public ResponseEntity<HoGiaDinh> getHoGiaDinh(@PathVariable Long id) {
        log.debug("REST request to get HoGiaDinh : {}", id);
        Optional<HoGiaDinh> hoGiaDinh = hoGiaDinhRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hoGiaDinh);
    }

    /**
     * {@code DELETE  /ho-gia-dinhs/:id} : delete the "id" hoGiaDinh.
     *
     * @param id the id of the hoGiaDinh to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ho-gia-dinhs/{id}")
    public ResponseEntity<Void> deleteHoGiaDinh(@PathVariable Long id) {
        log.debug("REST request to delete HoGiaDinh : {}", id);
        hoGiaDinhRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
