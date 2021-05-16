package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Thue;
import com.mycompany.myapp.repository.ThueRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thue}.
 */
@Service
@Transactional
public class ThueService {

    private final Logger log = LoggerFactory.getLogger(ThueService.class);

    private final ThueRepository thueRepository;

    public ThueService(ThueRepository thueRepository) {
        this.thueRepository = thueRepository;
    }

    /**
     * Save a thue.
     *
     * @param thue the entity to save.
     * @return the persisted entity.
     */
    public Thue save(Thue thue) {
        log.debug("Request to save Thue : {}", thue);
        return thueRepository.save(thue);
    }

    /**
     * Partially update a thue.
     *
     * @param thue the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Thue> partialUpdate(Thue thue) {
        log.debug("Request to partially update Thue : {}", thue);

        return thueRepository
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
    }

    /**
     * Get all the thues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Thue> findAll(Pageable pageable) {
        log.debug("Request to get all Thues");
        return thueRepository.findAll(pageable);
    }

    /**
     * Get one thue by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Thue> findOne(Long id) {
        log.debug("Request to get Thue : {}", id);
        return thueRepository.findById(id);
    }

    /**
     * Delete the thue by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Thue : {}", id);
        thueRepository.deleteById(id);
    }
}
