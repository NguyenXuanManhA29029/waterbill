package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TaiKhoan;
import com.mycompany.myapp.repository.TaiKhoanRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaiKhoan}.
 */
@Service
@Transactional
public class TaiKhoanService {

    private final Logger log = LoggerFactory.getLogger(TaiKhoanService.class);

    private final TaiKhoanRepository taiKhoanRepository;

    public TaiKhoanService(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

    /**
     * Save a taiKhoan.
     *
     * @param taiKhoan the entity to save.
     * @return the persisted entity.
     */
    public TaiKhoan save(TaiKhoan taiKhoan) {
        log.debug("Request to save TaiKhoan : {}", taiKhoan);
        return taiKhoanRepository.save(taiKhoan);
    }

    /**
     * Partially update a taiKhoan.
     *
     * @param taiKhoan the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaiKhoan> partialUpdate(TaiKhoan taiKhoan) {
        log.debug("Request to partially update TaiKhoan : {}", taiKhoan);

        return taiKhoanRepository
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
    }

    /**
     * Get all the taiKhoans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaiKhoan> findAll(Pageable pageable) {
        log.debug("Request to get all TaiKhoans");
        return taiKhoanRepository.findAll(pageable);
    }

    /**
     *  Get all the taiKhoans where HoGiaDinh is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaiKhoan> findAllWhereHoGiaDinhIsNull() {
        log.debug("Request to get all taiKhoans where HoGiaDinh is null");
        return StreamSupport
            .stream(taiKhoanRepository.findAll().spliterator(), false)
            .filter(taiKhoan -> taiKhoan.getHoGiaDinh() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one taiKhoan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaiKhoan> findOne(Long id) {
        log.debug("Request to get TaiKhoan : {}", id);
        return taiKhoanRepository.findById(id);
    }

    /**
     * Delete the taiKhoan by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaiKhoan : {}", id);
        taiKhoanRepository.deleteById(id);
    }
}
