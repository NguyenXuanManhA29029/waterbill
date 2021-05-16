package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.HoaDon;
import com.mycompany.myapp.repository.HoaDonRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HoaDon}.
 */
@Service
@Transactional
public class HoaDonService {

    private final Logger log = LoggerFactory.getLogger(HoaDonService.class);

    private final HoaDonRepository hoaDonRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository) {
        this.hoaDonRepository = hoaDonRepository;
    }

    /**
     * Save a hoaDon.
     *
     * @param hoaDon the entity to save.
     * @return the persisted entity.
     */
    public HoaDon save(HoaDon hoaDon) {
        log.debug("Request to save HoaDon : {}", hoaDon);
        return hoaDonRepository.save(hoaDon);
    }

    /**
     * Partially update a hoaDon.
     *
     * @param hoaDon the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HoaDon> partialUpdate(HoaDon hoaDon) {
        log.debug("Request to partially update HoaDon : {}", hoaDon);

        return hoaDonRepository
            .findById(hoaDon.getId())
            .map(
                existingHoaDon -> {
                    if (hoaDon.getTenChuHo() != null) {
                        existingHoaDon.setTenChuHo(hoaDon.getTenChuHo());
                    }
                    if (hoaDon.getThangSuDung() != null) {
                        existingHoaDon.setThangSuDung(hoaDon.getThangSuDung());
                    }
                    if (hoaDon.getChiSoMoi() != null) {
                        existingHoaDon.setChiSoMoi(hoaDon.getChiSoMoi());
                    }
                    if (hoaDon.getChiSoCu() != null) {
                        existingHoaDon.setChiSoCu(hoaDon.getChiSoCu());
                    }
                    if (hoaDon.getSoNuoc() != null) {
                        existingHoaDon.setSoNuoc(hoaDon.getSoNuoc());
                    }
                    if (hoaDon.getThanhTien() != null) {
                        existingHoaDon.setThanhTien(hoaDon.getThanhTien());
                    }
                    if (hoaDon.getTienThue() != null) {
                        existingHoaDon.setTienThue(hoaDon.getTienThue());
                    }
                    if (hoaDon.getTongTien() != null) {
                        existingHoaDon.setTongTien(hoaDon.getTongTien());
                    }
                    if (hoaDon.getNgayThanhToan() != null) {
                        existingHoaDon.setNgayThanhToan(hoaDon.getNgayThanhToan());
                    }
                    if (hoaDon.getTrangThaiThanhToan() != null) {
                        existingHoaDon.setTrangThaiThanhToan(hoaDon.getTrangThaiThanhToan());
                    }

                    return existingHoaDon;
                }
            )
            .map(hoaDonRepository::save);
    }

    /**
     * Get all the hoaDons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HoaDon> findAll(Pageable pageable) {
        log.debug("Request to get all HoaDons");
        return hoaDonRepository.findAll(pageable);
    }

    /**
     * Get one hoaDon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HoaDon> findOne(Long id) {
        log.debug("Request to get HoaDon : {}", id);
        return hoaDonRepository.findById(id);
    }

    /**
     * Delete the hoaDon by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HoaDon : {}", id);
        hoaDonRepository.deleteById(id);
    }
}
