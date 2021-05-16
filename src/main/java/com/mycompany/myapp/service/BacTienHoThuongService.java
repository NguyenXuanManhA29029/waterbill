package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BacTienHoThuong;
import com.mycompany.myapp.repository.BacTienHoThuongRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BacTienHoThuong}.
 */
@Service
@Transactional
public class BacTienHoThuongService {

    private final Logger log = LoggerFactory.getLogger(BacTienHoThuongService.class);

    private final BacTienHoThuongRepository bacTienHoThuongRepository;

    public BacTienHoThuongService(BacTienHoThuongRepository bacTienHoThuongRepository) {
        this.bacTienHoThuongRepository = bacTienHoThuongRepository;
    }

    /**
     * Save a bacTienHoThuong.
     *
     * @param bacTienHoThuong the entity to save.
     * @return the persisted entity.
     */
    public BacTienHoThuong save(BacTienHoThuong bacTienHoThuong) {
        log.debug("Request to save BacTienHoThuong : {}", bacTienHoThuong);
        return bacTienHoThuongRepository.save(bacTienHoThuong);
    }

    /**
     * Partially update a bacTienHoThuong.
     *
     * @param bacTienHoThuong the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BacTienHoThuong> partialUpdate(BacTienHoThuong bacTienHoThuong) {
        log.debug("Request to partially update BacTienHoThuong : {}", bacTienHoThuong);

        return bacTienHoThuongRepository
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
    }

    /**
     * Get all the bacTienHoThuongs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BacTienHoThuong> findAll(Pageable pageable) {
        log.debug("Request to get all BacTienHoThuongs");
        return bacTienHoThuongRepository.findAll(pageable);
    }

    /**
     * Get one bacTienHoThuong by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BacTienHoThuong> findOne(Long id) {
        log.debug("Request to get BacTienHoThuong : {}", id);
        return bacTienHoThuongRepository.findById(id);
    }

    /**
     * Delete the bacTienHoThuong by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BacTienHoThuong : {}", id);
        bacTienHoThuongRepository.deleteById(id);
    }
}
