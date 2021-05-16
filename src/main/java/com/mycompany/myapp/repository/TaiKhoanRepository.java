package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TaiKhoan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TaiKhoan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Long> {}
