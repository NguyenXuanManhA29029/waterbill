package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BacTienHoThuong;
import com.mycompany.myapp.repository.BacTienHoThuongRepository;
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
 * Integration tests for the {@link BacTienHoThuongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BacTienHoThuongResourceIT {

    private static final String DEFAULT_TEN_BAC = "AAAAAAAAAA";
    private static final String UPDATED_TEN_BAC = "BBBBBBBBBB";

    private static final Long DEFAULT_GIA_TRI_BAC = 1L;
    private static final Long UPDATED_GIA_TRI_BAC = 2L;

    private static final String ENTITY_API_URL = "/api/bac-tien-ho-thuongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BacTienHoThuongRepository bacTienHoThuongRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBacTienHoThuongMockMvc;

    private BacTienHoThuong bacTienHoThuong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BacTienHoThuong createEntity(EntityManager em) {
        BacTienHoThuong bacTienHoThuong = new BacTienHoThuong().tenBac(DEFAULT_TEN_BAC).giaTriBac(DEFAULT_GIA_TRI_BAC);
        return bacTienHoThuong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BacTienHoThuong createUpdatedEntity(EntityManager em) {
        BacTienHoThuong bacTienHoThuong = new BacTienHoThuong().tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);
        return bacTienHoThuong;
    }

    @BeforeEach
    public void initTest() {
        bacTienHoThuong = createEntity(em);
    }

    @Test
    @Transactional
    void createBacTienHoThuong() throws Exception {
        int databaseSizeBeforeCreate = bacTienHoThuongRepository.findAll().size();
        // Create the BacTienHoThuong
        restBacTienHoThuongMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isCreated());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeCreate + 1);
        BacTienHoThuong testBacTienHoThuong = bacTienHoThuongList.get(bacTienHoThuongList.size() - 1);
        assertThat(testBacTienHoThuong.getTenBac()).isEqualTo(DEFAULT_TEN_BAC);
        assertThat(testBacTienHoThuong.getGiaTriBac()).isEqualTo(DEFAULT_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void createBacTienHoThuongWithExistingId() throws Exception {
        // Create the BacTienHoThuong with an existing ID
        bacTienHoThuong.setId(1L);

        int databaseSizeBeforeCreate = bacTienHoThuongRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBacTienHoThuongMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBacTienHoThuongs() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        // Get all the bacTienHoThuongList
        restBacTienHoThuongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bacTienHoThuong.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenBac").value(hasItem(DEFAULT_TEN_BAC)))
            .andExpect(jsonPath("$.[*].giaTriBac").value(hasItem(DEFAULT_GIA_TRI_BAC.intValue())));
    }

    @Test
    @Transactional
    void getBacTienHoThuong() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        // Get the bacTienHoThuong
        restBacTienHoThuongMockMvc
            .perform(get(ENTITY_API_URL_ID, bacTienHoThuong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bacTienHoThuong.getId().intValue()))
            .andExpect(jsonPath("$.tenBac").value(DEFAULT_TEN_BAC))
            .andExpect(jsonPath("$.giaTriBac").value(DEFAULT_GIA_TRI_BAC.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBacTienHoThuong() throws Exception {
        // Get the bacTienHoThuong
        restBacTienHoThuongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBacTienHoThuong() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();

        // Update the bacTienHoThuong
        BacTienHoThuong updatedBacTienHoThuong = bacTienHoThuongRepository.findById(bacTienHoThuong.getId()).get();
        // Disconnect from session so that the updates on updatedBacTienHoThuong are not directly saved in db
        em.detach(updatedBacTienHoThuong);
        updatedBacTienHoThuong.tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);

        restBacTienHoThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBacTienHoThuong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBacTienHoThuong))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoThuong testBacTienHoThuong = bacTienHoThuongList.get(bacTienHoThuongList.size() - 1);
        assertThat(testBacTienHoThuong.getTenBac()).isEqualTo(UPDATED_TEN_BAC);
        assertThat(testBacTienHoThuong.getGiaTriBac()).isEqualTo(UPDATED_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void putNonExistingBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bacTienHoThuong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBacTienHoThuongWithPatch() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();

        // Update the bacTienHoThuong using partial update
        BacTienHoThuong partialUpdatedBacTienHoThuong = new BacTienHoThuong();
        partialUpdatedBacTienHoThuong.setId(bacTienHoThuong.getId());

        restBacTienHoThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBacTienHoThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBacTienHoThuong))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoThuong testBacTienHoThuong = bacTienHoThuongList.get(bacTienHoThuongList.size() - 1);
        assertThat(testBacTienHoThuong.getTenBac()).isEqualTo(DEFAULT_TEN_BAC);
        assertThat(testBacTienHoThuong.getGiaTriBac()).isEqualTo(DEFAULT_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void fullUpdateBacTienHoThuongWithPatch() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();

        // Update the bacTienHoThuong using partial update
        BacTienHoThuong partialUpdatedBacTienHoThuong = new BacTienHoThuong();
        partialUpdatedBacTienHoThuong.setId(bacTienHoThuong.getId());

        partialUpdatedBacTienHoThuong.tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);

        restBacTienHoThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBacTienHoThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBacTienHoThuong))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoThuong testBacTienHoThuong = bacTienHoThuongList.get(bacTienHoThuongList.size() - 1);
        assertThat(testBacTienHoThuong.getTenBac()).isEqualTo(UPDATED_TEN_BAC);
        assertThat(testBacTienHoThuong.getGiaTriBac()).isEqualTo(UPDATED_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void patchNonExistingBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bacTienHoThuong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBacTienHoThuong() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoThuongRepository.findAll().size();
        bacTienHoThuong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoThuongMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoThuong))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BacTienHoThuong in the database
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBacTienHoThuong() throws Exception {
        // Initialize the database
        bacTienHoThuongRepository.saveAndFlush(bacTienHoThuong);

        int databaseSizeBeforeDelete = bacTienHoThuongRepository.findAll().size();

        // Delete the bacTienHoThuong
        restBacTienHoThuongMockMvc
            .perform(delete(ENTITY_API_URL_ID, bacTienHoThuong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BacTienHoThuong> bacTienHoThuongList = bacTienHoThuongRepository.findAll();
        assertThat(bacTienHoThuongList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
