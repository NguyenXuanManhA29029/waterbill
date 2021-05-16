package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.HoGiaDinh;
import com.mycompany.myapp.repository.HoGiaDinhRepository;
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
 * Service Implementation for managing {@link HoGiaDinh}.
 */
@Service
@Transactional
public class HoGiaDinhService {

    private final Logger log = LoggerFactory.getLogger(HoGiaDinhService.class);

    private final HoGiaDinhRepository hoGiaDinhRepository;

    public HoGiaDinhService(HoGiaDinhRepository hoGiaDinhRepository) {
        this.hoGiaDinhRepository = hoGiaDinhRepository;
    }

    /**
     * Save a hoGiaDinh.
     *
     * @param hoGiaDinh the entity to save.
     * @return the persisted entity.
     */
    public HoGiaDinh save(HoGiaDinh hoGiaDinh) {
        log.debug("Request to save HoGiaDinh : {}", hoGiaDinh);
        return hoGiaDinhRepository.save(hoGiaDinh);
    }

    /**
     * Partially update a hoGiaDinh.
     *
     * @param hoGiaDinh the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HoGiaDinh> partialUpdate(HoGiaDinh hoGiaDinh) {
        log.debug("Request to partially update HoGiaDinh : {}", hoGiaDinh);

        return hoGiaDinhRepository
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
    }

    /**
     * Get all the hoGiaDinhs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HoGiaDinh> findAll(Pageable pageable) {
        log.debug("Request to get all HoGiaDinhs");
        return hoGiaDinhRepository.findAll(pageable);
    }

    /**
     *  Get all the hoGiaDinhs where Hoadongd is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HoGiaDinh> findAllWhereHoadongdIsNull() {
        log.debug("Request to get all hoGiaDinhs where Hoadongd is null");
        return StreamSupport
            .stream(hoGiaDinhRepository.findAll().spliterator(), false)
            .filter(hoGiaDinh -> hoGiaDinh.getHoadongd() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one hoGiaDinh by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HoGiaDinh> findOne(Long id) {
        log.debug("Request to get HoGiaDinh : {}", id);
        return hoGiaDinhRepository.findById(id);
    }

    /**
     * Delete the hoGiaDinh by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HoGiaDinh : {}", id);
        hoGiaDinhRepository.deleteById(id);
    }
}
