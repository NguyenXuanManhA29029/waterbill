package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BacTienHoNgheoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BacTienHoNgheo.class);
        BacTienHoNgheo bacTienHoNgheo1 = new BacTienHoNgheo();
        bacTienHoNgheo1.setId(1L);
        BacTienHoNgheo bacTienHoNgheo2 = new BacTienHoNgheo();
        bacTienHoNgheo2.setId(bacTienHoNgheo1.getId());
        assertThat(bacTienHoNgheo1).isEqualTo(bacTienHoNgheo2);
        bacTienHoNgheo2.setId(2L);
        assertThat(bacTienHoNgheo1).isNotEqualTo(bacTienHoNgheo2);
        bacTienHoNgheo1.setId(null);
        assertThat(bacTienHoNgheo1).isNotEqualTo(bacTienHoNgheo2);
    }
}
