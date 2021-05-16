package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BacTienHoNgheo;
import com.mycompany.myapp.repository.BacTienHoNgheoRepository;
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
 * Integration tests for the {@link BacTienHoNgheoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BacTienHoNgheoResourceIT {

    private static final String DEFAULT_TEN_BAC = "AAAAAAAAAA";
    private static final String UPDATED_TEN_BAC = "BBBBBBBBBB";

    private static final Long DEFAULT_GIA_TRI_BAC = 1L;
    private static final Long UPDATED_GIA_TRI_BAC = 2L;

    private static final String ENTITY_API_URL = "/api/bac-tien-ho-ngheos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BacTienHoNgheoRepository bacTienHoNgheoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBacTienHoNgheoMockMvc;

    private BacTienHoNgheo bacTienHoNgheo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BacTienHoNgheo createEntity(EntityManager em) {
        BacTienHoNgheo bacTienHoNgheo = new BacTienHoNgheo().tenBac(DEFAULT_TEN_BAC).giaTriBac(DEFAULT_GIA_TRI_BAC);
        return bacTienHoNgheo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BacTienHoNgheo createUpdatedEntity(EntityManager em) {
        BacTienHoNgheo bacTienHoNgheo = new BacTienHoNgheo().tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);
        return bacTienHoNgheo;
    }

    @BeforeEach
    public void initTest() {
        bacTienHoNgheo = createEntity(em);
    }

    @Test
    @Transactional
    void createBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeCreate = bacTienHoNgheoRepository.findAll().size();
        // Create the BacTienHoNgheo
        restBacTienHoNgheoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isCreated());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeCreate + 1);
        BacTienHoNgheo testBacTienHoNgheo = bacTienHoNgheoList.get(bacTienHoNgheoList.size() - 1);
        assertThat(testBacTienHoNgheo.getTenBac()).isEqualTo(DEFAULT_TEN_BAC);
        assertThat(testBacTienHoNgheo.getGiaTriBac()).isEqualTo(DEFAULT_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void createBacTienHoNgheoWithExistingId() throws Exception {
        // Create the BacTienHoNgheo with an existing ID
        bacTienHoNgheo.setId(1L);

        int databaseSizeBeforeCreate = bacTienHoNgheoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBacTienHoNgheoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBacTienHoNgheos() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        // Get all the bacTienHoNgheoList
        restBacTienHoNgheoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bacTienHoNgheo.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenBac").value(hasItem(DEFAULT_TEN_BAC)))
            .andExpect(jsonPath("$.[*].giaTriBac").value(hasItem(DEFAULT_GIA_TRI_BAC.intValue())));
    }

    @Test
    @Transactional
    void getBacTienHoNgheo() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        // Get the bacTienHoNgheo
        restBacTienHoNgheoMockMvc
            .perform(get(ENTITY_API_URL_ID, bacTienHoNgheo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bacTienHoNgheo.getId().intValue()))
            .andExpect(jsonPath("$.tenBac").value(DEFAULT_TEN_BAC))
            .andExpect(jsonPath("$.giaTriBac").value(DEFAULT_GIA_TRI_BAC.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBacTienHoNgheo() throws Exception {
        // Get the bacTienHoNgheo
        restBacTienHoNgheoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBacTienHoNgheo() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();

        // Update the bacTienHoNgheo
        BacTienHoNgheo updatedBacTienHoNgheo = bacTienHoNgheoRepository.findById(bacTienHoNgheo.getId()).get();
        // Disconnect from session so that the updates on updatedBacTienHoNgheo are not directly saved in db
        em.detach(updatedBacTienHoNgheo);
        updatedBacTienHoNgheo.tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);

        restBacTienHoNgheoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBacTienHoNgheo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBacTienHoNgheo))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoNgheo testBacTienHoNgheo = bacTienHoNgheoList.get(bacTienHoNgheoList.size() - 1);
        assertThat(testBacTienHoNgheo.getTenBac()).isEqualTo(UPDATED_TEN_BAC);
        assertThat(testBacTienHoNgheo.getGiaTriBac()).isEqualTo(UPDATED_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void putNonExistingBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bacTienHoNgheo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBacTienHoNgheoWithPatch() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();

        // Update the bacTienHoNgheo using partial update
        BacTienHoNgheo partialUpdatedBacTienHoNgheo = new BacTienHoNgheo();
        partialUpdatedBacTienHoNgheo.setId(bacTienHoNgheo.getId());

        partialUpdatedBacTienHoNgheo.giaTriBac(UPDATED_GIA_TRI_BAC);

        restBacTienHoNgheoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBacTienHoNgheo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBacTienHoNgheo))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoNgheo testBacTienHoNgheo = bacTienHoNgheoList.get(bacTienHoNgheoList.size() - 1);
        assertThat(testBacTienHoNgheo.getTenBac()).isEqualTo(DEFAULT_TEN_BAC);
        assertThat(testBacTienHoNgheo.getGiaTriBac()).isEqualTo(UPDATED_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void fullUpdateBacTienHoNgheoWithPatch() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();

        // Update the bacTienHoNgheo using partial update
        BacTienHoNgheo partialUpdatedBacTienHoNgheo = new BacTienHoNgheo();
        partialUpdatedBacTienHoNgheo.setId(bacTienHoNgheo.getId());

        partialUpdatedBacTienHoNgheo.tenBac(UPDATED_TEN_BAC).giaTriBac(UPDATED_GIA_TRI_BAC);

        restBacTienHoNgheoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBacTienHoNgheo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBacTienHoNgheo))
            )
            .andExpect(status().isOk());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
        BacTienHoNgheo testBacTienHoNgheo = bacTienHoNgheoList.get(bacTienHoNgheoList.size() - 1);
        assertThat(testBacTienHoNgheo.getTenBac()).isEqualTo(UPDATED_TEN_BAC);
        assertThat(testBacTienHoNgheo.getGiaTriBac()).isEqualTo(UPDATED_GIA_TRI_BAC);
    }

    @Test
    @Transactional
    void patchNonExistingBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bacTienHoNgheo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isBadRequest());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBacTienHoNgheo() throws Exception {
        int databaseSizeBeforeUpdate = bacTienHoNgheoRepository.findAll().size();
        bacTienHoNgheo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBacTienHoNgheoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bacTienHoNgheo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BacTienHoNgheo in the database
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBacTienHoNgheo() throws Exception {
        // Initialize the database
        bacTienHoNgheoRepository.saveAndFlush(bacTienHoNgheo);

        int databaseSizeBeforeDelete = bacTienHoNgheoRepository.findAll().size();

        // Delete the bacTienHoNgheo
        restBacTienHoNgheoMockMvc
            .perform(delete(ENTITY_API_URL_ID, bacTienHoNgheo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BacTienHoNgheo> bacTienHoNgheoList = bacTienHoNgheoRepository.findAll();
        assertThat(bacTienHoNgheoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
