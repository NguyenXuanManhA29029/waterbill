package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Thue;
import com.mycompany.myapp.repository.ThueRepository;
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
 * Integration tests for the {@link ThueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThueResourceIT {

    private static final String DEFAULT_TEN_THUE = "AAAAAAAAAA";
    private static final String UPDATED_TEN_THUE = "BBBBBBBBBB";

    private static final Long DEFAULT_GIA_TRI_THUE = 1L;
    private static final Long UPDATED_GIA_TRI_THUE = 2L;

    private static final String ENTITY_API_URL = "/api/thues";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThueRepository thueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThueMockMvc;

    private Thue thue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thue createEntity(EntityManager em) {
        Thue thue = new Thue().tenThue(DEFAULT_TEN_THUE).giaTriThue(DEFAULT_GIA_TRI_THUE);
        return thue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thue createUpdatedEntity(EntityManager em) {
        Thue thue = new Thue().tenThue(UPDATED_TEN_THUE).giaTriThue(UPDATED_GIA_TRI_THUE);
        return thue;
    }

    @BeforeEach
    public void initTest() {
        thue = createEntity(em);
    }

    @Test
    @Transactional
    void createThue() throws Exception {
        int databaseSizeBeforeCreate = thueRepository.findAll().size();
        // Create the Thue
        restThueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thue)))
            .andExpect(status().isCreated());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeCreate + 1);
        Thue testThue = thueList.get(thueList.size() - 1);
        assertThat(testThue.getTenThue()).isEqualTo(DEFAULT_TEN_THUE);
        assertThat(testThue.getGiaTriThue()).isEqualTo(DEFAULT_GIA_TRI_THUE);
    }

    @Test
    @Transactional
    void createThueWithExistingId() throws Exception {
        // Create the Thue with an existing ID
        thue.setId(1L);

        int databaseSizeBeforeCreate = thueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thue)))
            .andExpect(status().isBadRequest());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThues() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        // Get all the thueList
        restThueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thue.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenThue").value(hasItem(DEFAULT_TEN_THUE)))
            .andExpect(jsonPath("$.[*].giaTriThue").value(hasItem(DEFAULT_GIA_TRI_THUE.intValue())));
    }

    @Test
    @Transactional
    void getThue() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        // Get the thue
        restThueMockMvc
            .perform(get(ENTITY_API_URL_ID, thue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thue.getId().intValue()))
            .andExpect(jsonPath("$.tenThue").value(DEFAULT_TEN_THUE))
            .andExpect(jsonPath("$.giaTriThue").value(DEFAULT_GIA_TRI_THUE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingThue() throws Exception {
        // Get the thue
        restThueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThue() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        int databaseSizeBeforeUpdate = thueRepository.findAll().size();

        // Update the thue
        Thue updatedThue = thueRepository.findById(thue.getId()).get();
        // Disconnect from session so that the updates on updatedThue are not directly saved in db
        em.detach(updatedThue);
        updatedThue.tenThue(UPDATED_TEN_THUE).giaTriThue(UPDATED_GIA_TRI_THUE);

        restThueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThue))
            )
            .andExpect(status().isOk());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
        Thue testThue = thueList.get(thueList.size() - 1);
        assertThat(testThue.getTenThue()).isEqualTo(UPDATED_TEN_THUE);
        assertThat(testThue.getGiaTriThue()).isEqualTo(UPDATED_GIA_TRI_THUE);
    }

    @Test
    @Transactional
    void putNonExistingThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thue.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThueWithPatch() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        int databaseSizeBeforeUpdate = thueRepository.findAll().size();

        // Update the thue using partial update
        Thue partialUpdatedThue = new Thue();
        partialUpdatedThue.setId(thue.getId());

        partialUpdatedThue.tenThue(UPDATED_TEN_THUE);

        restThueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThue))
            )
            .andExpect(status().isOk());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
        Thue testThue = thueList.get(thueList.size() - 1);
        assertThat(testThue.getTenThue()).isEqualTo(UPDATED_TEN_THUE);
        assertThat(testThue.getGiaTriThue()).isEqualTo(DEFAULT_GIA_TRI_THUE);
    }

    @Test
    @Transactional
    void fullUpdateThueWithPatch() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        int databaseSizeBeforeUpdate = thueRepository.findAll().size();

        // Update the thue using partial update
        Thue partialUpdatedThue = new Thue();
        partialUpdatedThue.setId(thue.getId());

        partialUpdatedThue.tenThue(UPDATED_TEN_THUE).giaTriThue(UPDATED_GIA_TRI_THUE);

        restThueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThue))
            )
            .andExpect(status().isOk());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
        Thue testThue = thueList.get(thueList.size() - 1);
        assertThat(testThue.getTenThue()).isEqualTo(UPDATED_TEN_THUE);
        assertThat(testThue.getGiaTriThue()).isEqualTo(UPDATED_GIA_TRI_THUE);
    }

    @Test
    @Transactional
    void patchNonExistingThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thue))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThue() throws Exception {
        int databaseSizeBeforeUpdate = thueRepository.findAll().size();
        thue.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(thue)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thue in the database
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThue() throws Exception {
        // Initialize the database
        thueRepository.saveAndFlush(thue);

        int databaseSizeBeforeDelete = thueRepository.findAll().size();

        // Delete the thue
        restThueMockMvc
            .perform(delete(ENTITY_API_URL_ID, thue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thue> thueList = thueRepository.findAll();
        assertThat(thueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
