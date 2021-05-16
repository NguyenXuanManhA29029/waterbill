package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HoGiaDinhTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HoGiaDinh.class);
        HoGiaDinh hoGiaDinh1 = new HoGiaDinh();
        hoGiaDinh1.setId(1L);
        HoGiaDinh hoGiaDinh2 = new HoGiaDinh();
        hoGiaDinh2.setId(hoGiaDinh1.getId());
        assertThat(hoGiaDinh1).isEqualTo(hoGiaDinh2);
        hoGiaDinh2.setId(2L);
        assertThat(hoGiaDinh1).isNotEqualTo(hoGiaDinh2);
        hoGiaDinh1.setId(null);
        assertThat(hoGiaDinh1).isNotEqualTo(hoGiaDinh2);
    }
}
