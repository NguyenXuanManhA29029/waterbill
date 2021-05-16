package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BacTienHoNgheo;
import com.mycompany.myapp.repository.BacTienHoNgheoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BacTienHoNgheo}.
 */
@Service
@Transactional
public class BacTienHoNgheoService {

    private final Logger log = LoggerFactory.getLogger(BacTienHoNgheoService.class);

    private final BacTienHoNgheoRepository bacTienHoNgheoRepository;

    public BacTienHoNgheoService(BacTienHoNgheoRepository bacTienHoNgheoRepository) {
        this.bacTienHoNgheoRepository = bacTienHoNgheoRepository;
    }

    /**
     * Save a bacTienHoNgheo.
     *
     * @param bacTienHoNgheo the entity to save.
     * @return the persisted entity.
     */
    public BacTienHoNgheo save(BacTienHoNgheo bacTienHoNgheo) {
        log.debug("Request to save BacTienHoNgheo : {}", bacTienHoNgheo);
        return bacTienHoNgheoRepository.save(bacTienHoNgheo);
    }

    /**
     * Partially update a bacTienHoNgheo.
     *
     * @param bacTienHoNgheo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BacTienHoNgheo> partialUpdate(BacTienHoNgheo bacTienHoNgheo) {
        log.debug("Request to partially update BacTienHoNgheo : {}", bacTienHoNgheo);

        return bacTienHoNgheoRepository
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
    }

    /**
     * Get all the bacTienHoNgheos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BacTienHoNgheo> findAll(Pageable pageable) {
        log.debug("Request to get all BacTienHoNgheos");
        return bacTienHoNgheoRepository.findAll(pageable);
    }

    /**
     * Get one bacTienHoNgheo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BacTienHoNgheo> findOne(Long id) {
        log.debug("Request to get BacTienHoNgheo : {}", id);
        return bacTienHoNgheoRepository.findById(id);
    }

    /**
     * Delete the bacTienHoNgheo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BacTienHoNgheo : {}", id);
        bacTienHoNgheoRepository.deleteById(id);
    }
}
