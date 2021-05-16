package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.HoGiaDinh;
import com.mycompany.myapp.repository.HoGiaDinhRepository;
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
 * Integration tests for the {@link HoGiaDinhResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HoGiaDinhResourceIT {

    private static final String DEFAULT_TEN_CHU_HO = "AAAAAAAAAA";
    private static final String UPDATED_TEN_CHU_HO = "BBBBBBBBBB";

    private static final String DEFAULT_MA_HO = "AAAAAAAAAA";
    private static final String UPDATED_MA_HO = "BBBBBBBBBB";

    private static final String DEFAULT_SO_CAN_CUOC = "AAAAAAAAAA";
    private static final String UPDATED_SO_CAN_CUOC = "BBBBBBBBBB";

    private static final String DEFAULT_LOAI_HO = "AAAAAAAAAA";
    private static final String UPDATED_LOAI_HO = "BBBBBBBBBB";

    private static final String DEFAULT_SO_HO_NGHEO = "AAAAAAAAAA";
    private static final String UPDATED_SO_HO_NGHEO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SDT = "AAAAAAAAAA";
    private static final String UPDATED_SDT = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ho-gia-dinhs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HoGiaDinhRepository hoGiaDinhRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHoGiaDinhMockMvc;

    private HoGiaDinh hoGiaDinh;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoGiaDinh createEntity(EntityManager em) {
        HoGiaDinh hoGiaDinh = new HoGiaDinh()
            .tenChuHo(DEFAULT_TEN_CHU_HO)
            .maHo(DEFAULT_MA_HO)
            .soCanCuoc(DEFAULT_SO_CAN_CUOC)
            .loaiHo(DEFAULT_LOAI_HO)
            .soHoNgheo(DEFAULT_SO_HO_NGHEO)
            .email(DEFAULT_EMAIL)
            .sdt(DEFAULT_SDT)
            .diaChi(DEFAULT_DIA_CHI);
        return hoGiaDinh;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HoGiaDinh createUpdatedEntity(EntityManager em) {
        HoGiaDinh hoGiaDinh = new HoGiaDinh()
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .maHo(UPDATED_MA_HO)
            .soCanCuoc(UPDATED_SO_CAN_CUOC)
            .loaiHo(UPDATED_LOAI_HO)
            .soHoNgheo(UPDATED_SO_HO_NGHEO)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .diaChi(UPDATED_DIA_CHI);
        return hoGiaDinh;
    }

    @BeforeEach
    public void initTest() {
        hoGiaDinh = createEntity(em);
    }

    @Test
    @Transactional
    void createHoGiaDinh() throws Exception {
        int databaseSizeBeforeCreate = hoGiaDinhRepository.findAll().size();
        // Create the HoGiaDinh
        restHoGiaDinhMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoGiaDinh)))
            .andExpect(status().isCreated());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeCreate + 1);
        HoGiaDinh testHoGiaDinh = hoGiaDinhList.get(hoGiaDinhList.size() - 1);
        assertThat(testHoGiaDinh.getTenChuHo()).isEqualTo(DEFAULT_TEN_CHU_HO);
        assertThat(testHoGiaDinh.getMaHo()).isEqualTo(DEFAULT_MA_HO);
        assertThat(testHoGiaDinh.getSoCanCuoc()).isEqualTo(DEFAULT_SO_CAN_CUOC);
        assertThat(testHoGiaDinh.getLoaiHo()).isEqualTo(DEFAULT_LOAI_HO);
        assertThat(testHoGiaDinh.getSoHoNgheo()).isEqualTo(DEFAULT_SO_HO_NGHEO);
        assertThat(testHoGiaDinh.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testHoGiaDinh.getSdt()).isEqualTo(DEFAULT_SDT);
        assertThat(testHoGiaDinh.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
    }

    @Test
    @Transactional
    void createHoGiaDinhWithExistingId() throws Exception {
        // Create the HoGiaDinh with an existing ID
        hoGiaDinh.setId(1L);

        int databaseSizeBeforeCreate = hoGiaDinhRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHoGiaDinhMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoGiaDinh)))
            .andExpect(status().isBadRequest());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHoGiaDinhs() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        // Get all the hoGiaDinhList
        restHoGiaDinhMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hoGiaDinh.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenChuHo").value(hasItem(DEFAULT_TEN_CHU_HO)))
            .andExpect(jsonPath("$.[*].maHo").value(hasItem(DEFAULT_MA_HO)))
            .andExpect(jsonPath("$.[*].soCanCuoc").value(hasItem(DEFAULT_SO_CAN_CUOC)))
            .andExpect(jsonPath("$.[*].loaiHo").value(hasItem(DEFAULT_LOAI_HO)))
            .andExpect(jsonPath("$.[*].soHoNgheo").value(hasItem(DEFAULT_SO_HO_NGHEO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sdt").value(hasItem(DEFAULT_SDT)))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)));
    }

    @Test
    @Transactional
    void getHoGiaDinh() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        // Get the hoGiaDinh
        restHoGiaDinhMockMvc
            .perform(get(ENTITY_API_URL_ID, hoGiaDinh.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hoGiaDinh.getId().intValue()))
            .andExpect(jsonPath("$.tenChuHo").value(DEFAULT_TEN_CHU_HO))
            .andExpect(jsonPath("$.maHo").value(DEFAULT_MA_HO))
            .andExpect(jsonPath("$.soCanCuoc").value(DEFAULT_SO_CAN_CUOC))
            .andExpect(jsonPath("$.loaiHo").value(DEFAULT_LOAI_HO))
            .andExpect(jsonPath("$.soHoNgheo").value(DEFAULT_SO_HO_NGHEO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sdt").value(DEFAULT_SDT))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI));
    }

    @Test
    @Transactional
    void getNonExistingHoGiaDinh() throws Exception {
        // Get the hoGiaDinh
        restHoGiaDinhMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHoGiaDinh() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();

        // Update the hoGiaDinh
        HoGiaDinh updatedHoGiaDinh = hoGiaDinhRepository.findById(hoGiaDinh.getId()).get();
        // Disconnect from session so that the updates on updatedHoGiaDinh are not directly saved in db
        em.detach(updatedHoGiaDinh);
        updatedHoGiaDinh
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .maHo(UPDATED_MA_HO)
            .soCanCuoc(UPDATED_SO_CAN_CUOC)
            .loaiHo(UPDATED_LOAI_HO)
            .soHoNgheo(UPDATED_SO_HO_NGHEO)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .diaChi(UPDATED_DIA_CHI);

        restHoGiaDinhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHoGiaDinh.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHoGiaDinh))
            )
            .andExpect(status().isOk());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
        HoGiaDinh testHoGiaDinh = hoGiaDinhList.get(hoGiaDinhList.size() - 1);
        assertThat(testHoGiaDinh.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoGiaDinh.getMaHo()).isEqualTo(UPDATED_MA_HO);
        assertThat(testHoGiaDinh.getSoCanCuoc()).isEqualTo(UPDATED_SO_CAN_CUOC);
        assertThat(testHoGiaDinh.getLoaiHo()).isEqualTo(UPDATED_LOAI_HO);
        assertThat(testHoGiaDinh.getSoHoNgheo()).isEqualTo(UPDATED_SO_HO_NGHEO);
        assertThat(testHoGiaDinh.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHoGiaDinh.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testHoGiaDinh.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void putNonExistingHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hoGiaDinh.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoGiaDinh))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hoGiaDinh))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hoGiaDinh)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHoGiaDinhWithPatch() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();

        // Update the hoGiaDinh using partial update
        HoGiaDinh partialUpdatedHoGiaDinh = new HoGiaDinh();
        partialUpdatedHoGiaDinh.setId(hoGiaDinh.getId());

        partialUpdatedHoGiaDinh.tenChuHo(UPDATED_TEN_CHU_HO);

        restHoGiaDinhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoGiaDinh.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoGiaDinh))
            )
            .andExpect(status().isOk());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
        HoGiaDinh testHoGiaDinh = hoGiaDinhList.get(hoGiaDinhList.size() - 1);
        assertThat(testHoGiaDinh.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoGiaDinh.getMaHo()).isEqualTo(DEFAULT_MA_HO);
        assertThat(testHoGiaDinh.getSoCanCuoc()).isEqualTo(DEFAULT_SO_CAN_CUOC);
        assertThat(testHoGiaDinh.getLoaiHo()).isEqualTo(DEFAULT_LOAI_HO);
        assertThat(testHoGiaDinh.getSoHoNgheo()).isEqualTo(DEFAULT_SO_HO_NGHEO);
        assertThat(testHoGiaDinh.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testHoGiaDinh.getSdt()).isEqualTo(DEFAULT_SDT);
        assertThat(testHoGiaDinh.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
    }

    @Test
    @Transactional
    void fullUpdateHoGiaDinhWithPatch() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();

        // Update the hoGiaDinh using partial update
        HoGiaDinh partialUpdatedHoGiaDinh = new HoGiaDinh();
        partialUpdatedHoGiaDinh.setId(hoGiaDinh.getId());

        partialUpdatedHoGiaDinh
            .tenChuHo(UPDATED_TEN_CHU_HO)
            .maHo(UPDATED_MA_HO)
            .soCanCuoc(UPDATED_SO_CAN_CUOC)
            .loaiHo(UPDATED_LOAI_HO)
            .soHoNgheo(UPDATED_SO_HO_NGHEO)
            .email(UPDATED_EMAIL)
            .sdt(UPDATED_SDT)
            .diaChi(UPDATED_DIA_CHI);

        restHoGiaDinhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHoGiaDinh.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHoGiaDinh))
            )
            .andExpect(status().isOk());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
        HoGiaDinh testHoGiaDinh = hoGiaDinhList.get(hoGiaDinhList.size() - 1);
        assertThat(testHoGiaDinh.getTenChuHo()).isEqualTo(UPDATED_TEN_CHU_HO);
        assertThat(testHoGiaDinh.getMaHo()).isEqualTo(UPDATED_MA_HO);
        assertThat(testHoGiaDinh.getSoCanCuoc()).isEqualTo(UPDATED_SO_CAN_CUOC);
        assertThat(testHoGiaDinh.getLoaiHo()).isEqualTo(UPDATED_LOAI_HO);
        assertThat(testHoGiaDinh.getSoHoNgheo()).isEqualTo(UPDATED_SO_HO_NGHEO);
        assertThat(testHoGiaDinh.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHoGiaDinh.getSdt()).isEqualTo(UPDATED_SDT);
        assertThat(testHoGiaDinh.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
    }

    @Test
    @Transactional
    void patchNonExistingHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hoGiaDinh.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoGiaDinh))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hoGiaDinh))
            )
            .andExpect(status().isBadRequest());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHoGiaDinh() throws Exception {
        int databaseSizeBeforeUpdate = hoGiaDinhRepository.findAll().size();
        hoGiaDinh.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHoGiaDinhMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hoGiaDinh))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HoGiaDinh in the database
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHoGiaDinh() throws Exception {
        // Initialize the database
        hoGiaDinhRepository.saveAndFlush(hoGiaDinh);

        int databaseSizeBeforeDelete = hoGiaDinhRepository.findAll().size();

        // Delete the hoGiaDinh
        restHoGiaDinhMockMvc
            .perform(delete(ENTITY_API_URL_ID, hoGiaDinh.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HoGiaDinh> hoGiaDinhList = hoGiaDinhRepository.findAll();
        assertThat(hoGiaDinhList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
