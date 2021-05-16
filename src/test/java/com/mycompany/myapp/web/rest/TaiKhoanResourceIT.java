package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TaiKhoan;
import com.mycompany.myapp.repository.TaiKhoanRepository;
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
 * Integration tests for the {@link TaiKhoanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaiKhoanResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASS_WORD = "AAAAAAAAAA";
    private static final String UPDATED_PASS_WORD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tai-khoans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaiKhoanMockMvc;

    private TaiKhoan taiKhoan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaiKhoan createEntity(EntityManager em) {
        TaiKhoan taiKhoan = new TaiKhoan().userName(DEFAULT_USER_NAME).passWord(DEFAULT_PASS_WORD);
        return taiKhoan;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaiKhoan createUpdatedEntity(EntityManager em) {
        TaiKhoan taiKhoan = new TaiKhoan().userName(UPDATED_USER_NAME).passWord(UPDATED_PASS_WORD);
        return taiKhoan;
    }

    @BeforeEach
    public void initTest() {
        taiKhoan = createEntity(em);
    }

    @Test
    @Transactional
    void createTaiKhoan() throws Exception {
        int databaseSizeBeforeCreate = taiKhoanRepository.findAll().size();
        // Create the TaiKhoan
        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoan)))
            .andExpect(status().isCreated());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeCreate + 1);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testTaiKhoan.getPassWord()).isEqualTo(DEFAULT_PASS_WORD);
    }

    @Test
    @Transactional
    void createTaiKhoanWithExistingId() throws Exception {
        // Create the TaiKhoan with an existing ID
        taiKhoan.setId(1L);

        int databaseSizeBeforeCreate = taiKhoanRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaiKhoanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoan)))
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaiKhoans() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        // Get all the taiKhoanList
        restTaiKhoanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taiKhoan.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].passWord").value(hasItem(DEFAULT_PASS_WORD)));
    }

    @Test
    @Transactional
    void getTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        // Get the taiKhoan
        restTaiKhoanMockMvc
            .perform(get(ENTITY_API_URL_ID, taiKhoan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taiKhoan.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.passWord").value(DEFAULT_PASS_WORD));
    }

    @Test
    @Transactional
    void getNonExistingTaiKhoan() throws Exception {
        // Get the taiKhoan
        restTaiKhoanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan
        TaiKhoan updatedTaiKhoan = taiKhoanRepository.findById(taiKhoan.getId()).get();
        // Disconnect from session so that the updates on updatedTaiKhoan are not directly saved in db
        em.detach(updatedTaiKhoan);
        updatedTaiKhoan.userName(UPDATED_USER_NAME).passWord(UPDATED_PASS_WORD);

        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaiKhoan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaiKhoan))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTaiKhoan.getPassWord()).isEqualTo(UPDATED_PASS_WORD);
    }

    @Test
    @Transactional
    void putNonExistingTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taiKhoan.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoan))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoan))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taiKhoan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaiKhoanWithPatch() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan using partial update
        TaiKhoan partialUpdatedTaiKhoan = new TaiKhoan();
        partialUpdatedTaiKhoan.setId(taiKhoan.getId());

        partialUpdatedTaiKhoan.userName(UPDATED_USER_NAME);

        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaiKhoan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaiKhoan))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTaiKhoan.getPassWord()).isEqualTo(DEFAULT_PASS_WORD);
    }

    @Test
    @Transactional
    void fullUpdateTaiKhoanWithPatch() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();

        // Update the taiKhoan using partial update
        TaiKhoan partialUpdatedTaiKhoan = new TaiKhoan();
        partialUpdatedTaiKhoan.setId(taiKhoan.getId());

        partialUpdatedTaiKhoan.userName(UPDATED_USER_NAME).passWord(UPDATED_PASS_WORD);

        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaiKhoan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaiKhoan))
            )
            .andExpect(status().isOk());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
        TaiKhoan testTaiKhoan = taiKhoanList.get(taiKhoanList.size() - 1);
        assertThat(testTaiKhoan.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testTaiKhoan.getPassWord()).isEqualTo(UPDATED_PASS_WORD);
    }

    @Test
    @Transactional
    void patchNonExistingTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taiKhoan.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoan))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taiKhoan))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaiKhoan() throws Exception {
        int databaseSizeBeforeUpdate = taiKhoanRepository.findAll().size();
        taiKhoan.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaiKhoanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taiKhoan)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaiKhoan in the database
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaiKhoan() throws Exception {
        // Initialize the database
        taiKhoanRepository.saveAndFlush(taiKhoan);

        int databaseSizeBeforeDelete = taiKhoanRepository.findAll().size();

        // Delete the taiKhoan
        restTaiKhoanMockMvc
            .perform(delete(ENTITY_API_URL_ID, taiKhoan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaiKhoan> taiKhoanList = taiKhoanRepository.findAll();
        assertThat(taiKhoanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
