package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HoGiaDinh;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HoGiaDinh entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HoGiaDinhRepository extends JpaRepository<HoGiaDinh, Long> {}
