package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoaDon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoaDon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {}
