package org.apz.cc.web.rest;

import org.apz.cc.CcApp;

import org.apz.cc.domain.CCG;
import org.apz.cc.repository.CCGRepository;
import org.apz.cc.service.CCGService;
import org.apz.cc.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static org.apz.cc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CCGResource REST controller.
 *
 * @see CCGResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CcApp.class)
public class CCGResourceIntTest {

    private static final String DEFAULT_CCG = "AAAAAAAAAA";
    private static final String UPDATED_CCG = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private CCGRepository cCGRepository;

    @Autowired
    private CCGService cCGService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCCGMockMvc;

    private CCG cCG;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CCGResource cCGResource = new CCGResource(cCGService);
        this.restCCGMockMvc = MockMvcBuilders.standaloneSetup(cCGResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCG createEntity(EntityManager em) {
        CCG cCG = new CCG()
            .ccg(DEFAULT_CCG)
            .note(DEFAULT_NOTE);
        return cCG;
    }

    @Before
    public void initTest() {
        cCG = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCG() throws Exception {
        int databaseSizeBeforeCreate = cCGRepository.findAll().size();

        // Create the CCG
        restCCGMockMvc.perform(post("/api/ccgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCG)))
            .andExpect(status().isCreated());

        // Validate the CCG in the database
        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeCreate + 1);
        CCG testCCG = cCGList.get(cCGList.size() - 1);
        assertThat(testCCG.getCcg()).isEqualTo(DEFAULT_CCG);
        assertThat(testCCG.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createCCGWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCGRepository.findAll().size();

        // Create the CCG with an existing ID
        cCG.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCGMockMvc.perform(post("/api/ccgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCG)))
            .andExpect(status().isBadRequest());

        // Validate the CCG in the database
        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCcgIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCGRepository.findAll().size();
        // set the field null
        cCG.setCcg(null);

        // Create the CCG, which fails.

        restCCGMockMvc.perform(post("/api/ccgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCG)))
            .andExpect(status().isBadRequest());

        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCGS() throws Exception {
        // Initialize the database
        cCGRepository.saveAndFlush(cCG);

        // Get all the cCGList
        restCCGMockMvc.perform(get("/api/ccgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCG.getId().intValue())))
            .andExpect(jsonPath("$.[*].ccg").value(hasItem(DEFAULT_CCG.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getCCG() throws Exception {
        // Initialize the database
        cCGRepository.saveAndFlush(cCG);

        // Get the cCG
        restCCGMockMvc.perform(get("/api/ccgs/{id}", cCG.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cCG.getId().intValue()))
            .andExpect(jsonPath("$.ccg").value(DEFAULT_CCG.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCCG() throws Exception {
        // Get the cCG
        restCCGMockMvc.perform(get("/api/ccgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCG() throws Exception {
        // Initialize the database
        cCGService.save(cCG);

        int databaseSizeBeforeUpdate = cCGRepository.findAll().size();

        // Update the cCG
        CCG updatedCCG = cCGRepository.findById(cCG.getId()).get();
        // Disconnect from session so that the updates on updatedCCG are not directly saved in db
        em.detach(updatedCCG);
        updatedCCG
            .ccg(UPDATED_CCG)
            .note(UPDATED_NOTE);

        restCCGMockMvc.perform(put("/api/ccgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCCG)))
            .andExpect(status().isOk());

        // Validate the CCG in the database
        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeUpdate);
        CCG testCCG = cCGList.get(cCGList.size() - 1);
        assertThat(testCCG.getCcg()).isEqualTo(UPDATED_CCG);
        assertThat(testCCG.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCG() throws Exception {
        int databaseSizeBeforeUpdate = cCGRepository.findAll().size();

        // Create the CCG

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCGMockMvc.perform(put("/api/ccgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCG)))
            .andExpect(status().isBadRequest());

        // Validate the CCG in the database
        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCG() throws Exception {
        // Initialize the database
        cCGService.save(cCG);

        int databaseSizeBeforeDelete = cCGRepository.findAll().size();

        // Delete the cCG
        restCCGMockMvc.perform(delete("/api/ccgs/{id}", cCG.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CCG> cCGList = cCGRepository.findAll();
        assertThat(cCGList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCG.class);
        CCG cCG1 = new CCG();
        cCG1.setId(1L);
        CCG cCG2 = new CCG();
        cCG2.setId(cCG1.getId());
        assertThat(cCG1).isEqualTo(cCG2);
        cCG2.setId(2L);
        assertThat(cCG1).isNotEqualTo(cCG2);
        cCG1.setId(null);
        assertThat(cCG1).isNotEqualTo(cCG2);
    }
}
