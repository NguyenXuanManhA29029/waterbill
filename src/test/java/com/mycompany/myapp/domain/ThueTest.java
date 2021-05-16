package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thue.class);
        Thue thue1 = new Thue();
        thue1.setId(1L);
        Thue thue2 = new Thue();
        thue2.setId(thue1.getId());
        assertThat(thue1).isEqualTo(thue2);
        thue2.setId(2L);
        assertThat(thue1).isNotEqualTo(thue2);
        thue1.setId(null);
        assertThat(thue1).isNotEqualTo(thue2);
    }
}
