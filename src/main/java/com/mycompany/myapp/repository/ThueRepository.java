package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Thue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThueRepository extends JpaRepository<Thue, Long> {}
