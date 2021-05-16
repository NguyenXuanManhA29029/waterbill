package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HoaDon;
import com.mycompany.myapp.repository.HoaDonRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HoaDonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoaDonResourceIT {

    private static final String DEFAULT_TEN_CHU_HO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHU_HO = "BBBBBBBBBB";

    private static final Long DEFAULT_THANG_SU_DUNG = 1L;
    private static final Long UPDATED_THANG_SU_DUNG = 2L;

    private static final Long DEFAULT_CHI_SO_MOI = 1L;
    private static final Long UPDATED_CHI_SO_MOI = 2L;

    private static final Long DEFAULT_CHI_SO_CU = 1L;
    private static final Long UPDATED_CHI_SO_CU = 2L;

    private static final Long DEFAULT_SO_NUOC = 1L;
    private static final Long UPDATED_SO_NUOC = 2L;

    private static final Long DEFAULT_THANH_TIEN = 1L;
    private static final Long UPDATED_THANH_TIEN = 2L;

    private static final Long DEFAULT_TIEN_THUE = 1L;
    private static final Long UPDATED_TIEN_THUE = 2L;

    private static final Long DEFAULT_TONG_TIEN = 1L;
    private static final Long UPDATED_TONG_TIEN = 2L;

    private static final Instant DEFAULT_NGAY_THANH_TOAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_THANH_TOAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TRANG_THAI_THANH_TOAN = "AAAAAAAAAA";
    private static final String UPDATED_TRANG_THAI_THANH_TOAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hoa-dons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoaDonMockMvc;

    private HoaDon hoaDon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoaDon createEntity(EntityManager em) {
        HoaDon hoaDon = new HoaDon()
            .tenChuHo(DEFAULT_TEN_CHU_HO)
            .thangSuDung(DEFAULT_THANG_SU_DUNG)
            .chiSoMoi(DEFAULT_CHI_SO_MOI)
            .chiSoCu(DEFAULT_CHI_SO_CU)
            .soNuoc(DEFAULT_SO_NUOC)
            .thanhTien(DEFAULT_THANH_TIEN)
            .tienThue(DEFAULT_TIEN_THUE)
            .tongTien(DEFAULT_TONG_TIEN)
            .ngayThanhToan(DEFAULT_NGAY_THANH_TOAN)
            .trangThaiThanhToan(DEFAULT_TRANG_THAI_THANH_TOAN);
        return hoaDon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoaDon createUpdatedEntity(EntityManager em) {
        HoaDon hoaDon = new HoaDon()
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .thangSuDung(UPDATED_THANG_SU_DUNG)
            .chiSoMoi(UPDATED_CHI_SO_MOI)
            .chiSoCu(UPDATED_CHI_SO_CU)
            .soNuoc(UPDATED_SO_NUOC)
            .thanhTien(UPDATED_THANH_TIEN)
            .tienThue(UPDATED_TIEN_THUE)
            .tongTien(UPDATED_TONG_TIEN)
            .ngayThanhToan(UPDATED_NGAY_THANH_TOAN)
            .trangThaiThanhToan(UPDATED_TRANG_THAI_THANH_TOAN);
        return hoaDon;
    }

    @BeforeEach
    public void initTest() {
        hoaDon = createEntity(em);
    }

    @Test
    @Transactional
    void createHoaDon() throws Exception {
        int databaseSizeBeforeCreate = hoaDonRepository.findAll().size();
        // Create the HoaDon
        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDon)))
            .andExpect(status().isCreated());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeCreate + 1);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTenChuHo()).isEqualTo(DEFAULT_TEN_CHU_HO);
        assertThat(testHoaDon.getThangSuDung()).isEqualTo(DEFAULT_THANG_SU_DUNG);
        assertThat(testHoaDon.getChiSoMoi()).isEqualTo(DEFAULT_CHI_SO_MOI);
        assertThat(testHoaDon.getChiSoCu()).isEqualTo(DEFAULT_CHI_SO_CU);
        assertThat(testHoaDon.getSoNuoc()).isEqualTo(DEFAULT_SO_NUOC);
        assertThat(testHoaDon.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
        assertThat(testHoaDon.getTienThue()).isEqualTo(DEFAULT_TIEN_THUE);
        assertThat(testHoaDon.getTongTien()).isEqualTo(DEFAULT_TONG_TIEN);
        assertThat(testHoaDon.getNgayThanhToan()).isEqualTo(DEFAULT_NGAY_THANH_TOAN);
        assertThat(testHoaDon.getTrangThaiThanhToan()).isEqualTo(DEFAULT_TRANG_THAI_THANH_TOAN);
    }

    @Test
    @Transactional
    void createHoaDonWithExistingId() throws Exception {
        // Create the HoaDon with an existing ID
        hoaDon.setId(1L);

        int databaseSizeBeforeCreate = hoaDonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoaDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDon)))
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoaDons() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        // Get all the hoaDonList
        restHoaDonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoaDon.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenChuHo").value(hasItem(DEFAULT_TEN_CHU_HO)))
            .andExpect(jsonPath("$.[*].thangSuDung").value(hasItem(DEFAULT_THANG_SU_DUNG.intValue())))
            .andExpect(jsonPath("$.[*].chiSoMoi").value(hasItem(DEFAULT_CHI_SO_MOI.intValue())))
            .andExpect(jsonPath("$.[*].chiSoCu").value(hasItem(DEFAULT_CHI_SO_CU.intValue())))
            .andExpect(jsonPath("$.[*].soNuoc").value(hasItem(DEFAULT_SO_NUOC.intValue())))
            .andExpect(jsonPath("$.[*].thanhTien").value(hasItem(DEFAULT_THANH_TIEN.intValue())))
            .andExpect(jsonPath("$.[*].tienThue").value(hasItem(DEFAULT_TIEN_THUE.intValue())))
            .andExpect(jsonPath("$.[*].tongTien").value(hasItem(DEFAULT_TONG_TIEN.intValue())))
            .andExpect(jsonPath("$.[*].ngayThanhToan").value(hasItem(DEFAULT_NGAY_THANH_TOAN.toString())))
            .andExpect(jsonPath("$.[*].trangThaiThanhToan").value(hasItem(DEFAULT_TRANG_THAI_THANH_TOAN)));
    }

    @Test
    @Transactional
    void getHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        // Get the hoaDon
        restHoaDonMockMvc
            .perform(get(ENTITY_API_URL_ID, hoaDon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoaDon.getId().intValue()))
            .andExpect(jsonPath("$.tenChuHo").value(DEFAULT_TEN_CHU_HO))
            .andExpect(jsonPath("$.thangSuDung").value(DEFAULT_THANG_SU_DUNG.intValue()))
            .andExpect(jsonPath("$.chiSoMoi").value(DEFAULT_CHI_SO_MOI.intValue()))
            .andExpect(jsonPath("$.chiSoCu").value(DEFAULT_CHI_SO_CU.intValue()))
            .andExpect(jsonPath("$.soNuoc").value(DEFAULT_SO_NUOC.intValue()))
            .andExpect(jsonPath("$.thanhTien").value(DEFAULT_THANH_TIEN.intValue()))
            .andExpect(jsonPath("$.tienThue").value(DEFAULT_TIEN_THUE.intValue()))
            .andExpect(jsonPath("$.tongTien").value(DEFAULT_TONG_TIEN.intValue()))
            .andExpect(jsonPath("$.ngayThanhToan").value(DEFAULT_NGAY_THANH_TOAN.toString()))
            .andExpect(jsonPath("$.trangThaiThanhToan").value(DEFAULT_TRANG_THAI_THANH_TOAN));
    }

    @Test
    @Transactional
    void getNonExistingHoaDon() throws Exception {
        // Get the hoaDon
        restHoaDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon
        HoaDon updatedHoaDon = hoaDonRepository.findById(hoaDon.getId()).get();
        // Disconnect from session so that the updates on updatedHoaDon are not directly saved in db
        em.detach(updatedHoaDon);
        updatedHoaDon
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .thangSuDung(UPDATED_THANG_SU_DUNG)
            .chiSoMoi(UPDATED_CHI_SO_MOI)
            .chiSoCu(UPDATED_CHI_SO_CU)
            .soNuoc(UPDATED_SO_NUOC)
            .thanhTien(UPDATED_THANH_TIEN)
            .tienThue(UPDATED_TIEN_THUE)
            .tongTien(UPDATED_TONG_TIEN)
            .ngayThanhToan(UPDATED_NGAY_THANH_TOAN)
            .trangThaiThanhToan(UPDATED_TRANG_THAI_THANH_TOAN);

        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoaDon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoaDon.getThangSuDung()).isEqualTo(UPDATED_THANG_SU_DUNG);
        assertThat(testHoaDon.getChiSoMoi()).isEqualTo(UPDATED_CHI_SO_MOI);
        assertThat(testHoaDon.getChiSoCu()).isEqualTo(UPDATED_CHI_SO_CU);
        assertThat(testHoaDon.getSoNuoc()).isEqualTo(UPDATED_SO_NUOC);
        assertThat(testHoaDon.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
        assertThat(testHoaDon.getTienThue()).isEqualTo(UPDATED_TIEN_THUE);
        assertThat(testHoaDon.getTongTien()).isEqualTo(UPDATED_TONG_TIEN);
        assertThat(testHoaDon.getNgayThanhToan()).isEqualTo(UPDATED_NGAY_THANH_TOAN);
        assertThat(testHoaDon.getTrangThaiThanhToan()).isEqualTo(UPDATED_TRANG_THAI_THANH_TOAN);
    }

    @Test
    @Transactional
    void putNonExistingHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoaDon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoaDon))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoaDon))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoaDon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoaDonWithPatch() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon using partial update
        HoaDon partialUpdatedHoaDon = new HoaDon();
        partialUpdatedHoaDon.setId(hoaDon.getId());

        partialUpdatedHoaDon
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .thangSuDung(UPDATED_THANG_SU_DUNG)
            .chiSoCu(UPDATED_CHI_SO_CU)
            .tienThue(UPDATED_TIEN_THUE);

        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoaDon.getThangSuDung()).isEqualTo(UPDATED_THANG_SU_DUNG);
        assertThat(testHoaDon.getChiSoMoi()).isEqualTo(DEFAULT_CHI_SO_MOI);
        assertThat(testHoaDon.getChiSoCu()).isEqualTo(UPDATED_CHI_SO_CU);
        assertThat(testHoaDon.getSoNuoc()).isEqualTo(DEFAULT_SO_NUOC);
        assertThat(testHoaDon.getThanhTien()).isEqualTo(DEFAULT_THANH_TIEN);
        assertThat(testHoaDon.getTienThue()).isEqualTo(UPDATED_TIEN_THUE);
        assertThat(testHoaDon.getTongTien()).isEqualTo(DEFAULT_TONG_TIEN);
        assertThat(testHoaDon.getNgayThanhToan()).isEqualTo(DEFAULT_NGAY_THANH_TOAN);
        assertThat(testHoaDon.getTrangThaiThanhToan()).isEqualTo(DEFAULT_TRANG_THAI_THANH_TOAN);
    }

    @Test
    @Transactional
    void fullUpdateHoaDonWithPatch() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();

        // Update the hoaDon using partial update
        HoaDon partialUpdatedHoaDon = new HoaDon();
        partialUpdatedHoaDon.setId(hoaDon.getId());

        partialUpdatedHoaDon
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .thangSuDung(UPDATED_THANG_SU_DUNG)
            .chiSoMoi(UPDATED_CHI_SO_MOI)
            .chiSoCu(UPDATED_CHI_SO_CU)
            .soNuoc(UPDATED_SO_NUOC)
            .thanhTien(UPDATED_THANH_TIEN)
            .tienThue(UPDATED_TIEN_THUE)
            .tongTien(UPDATED_TONG_TIEN)
            .ngayThanhToan(UPDATED_NGAY_THANH_TOAN)
            .trangThaiThanhToan(UPDATED_TRANG_THAI_THANH_TOAN);

        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoaDon))
            )
            .andExpect(status().isOk());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
        HoaDon testHoaDon = hoaDonList.get(hoaDonList.size() - 1);
        assertThat(testHoaDon.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoaDon.getThangSuDung()).isEqualTo(UPDATED_THANG_SU_DUNG);
        assertThat(testHoaDon.getChiSoMoi()).isEqualTo(UPDATED_CHI_SO_MOI);
        assertThat(testHoaDon.getChiSoCu()).isEqualTo(UPDATED_CHI_SO_CU);
        assertThat(testHoaDon.getSoNuoc()).isEqualTo(UPDATED_SO_NUOC);
        assertThat(testHoaDon.getThanhTien()).isEqualTo(UPDATED_THANH_TIEN);
        assertThat(testHoaDon.getTienThue()).isEqualTo(UPDATED_TIEN_THUE);
        assertThat(testHoaDon.getTongTien()).isEqualTo(UPDATED_TONG_TIEN);
        assertThat(testHoaDon.getNgayThanhToan()).isEqualTo(UPDATED_NGAY_THANH_TOAN);
        assertThat(testHoaDon.getTrangThaiThanhToan()).isEqualTo(UPDATED_TRANG_THAI_THANH_TOAN);
    }

    @Test
    @Transactional
    void patchNonExistingHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoaDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoaDon))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoaDon))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoaDon() throws Exception {
        int databaseSizeBeforeUpdate = hoaDonRepository.findAll().size();
        hoaDon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoaDonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hoaDon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoaDon in the database
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoaDon() throws Exception {
        // Initialize the database
        hoaDonRepository.saveAndFlush(hoaDon);

        int databaseSizeBeforeDelete = hoaDonRepository.findAll().size();

        // Delete the hoaDon
        restHoaDonMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoaDon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        assertThat(hoaDonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
