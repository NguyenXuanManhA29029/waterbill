package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BacTienHoNgheo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BacTienHoNgheo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BacTienHoNgheoRepository extends JpaRepository<BacTienHoNgheo, Long> {}
