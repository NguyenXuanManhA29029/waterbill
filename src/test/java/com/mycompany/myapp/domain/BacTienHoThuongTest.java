package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BacTienHoThuongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BacTienHoThuong.class);
        BacTienHoThuong bacTienHoThuong1 = new BacTienHoThuong();
        bacTienHoThuong1.setId(1L);
        BacTienHoThuong bacTienHoThuong2 = new BacTienHoThuong();
        bacTienHoThuong2.setId(bacTienHoThuong1.getId());
        assertThat(bacTienHoThuong1).isEqualTo(bacTienHoThuong2);
        bacTienHoThuong2.setId(2L);
        assertThat(bacTienHoThuong1).isNotEqualTo(bacTienHoThuong2);
        bacTienHoThuong1.setId(null);
        assertThat(bacTienHoThuong1).isNotEqualTo(bacTienHoThuong2);
    }
}
