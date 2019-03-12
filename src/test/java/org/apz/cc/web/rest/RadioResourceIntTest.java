package org.apz.cc.web.rest;

import org.apz.cc.CcApp;

import org.apz.cc.domain.Radio;
import org.apz.cc.repository.RadioRepository;
import org.apz.cc.service.RadioService;
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
 * Test class for the RadioResource REST controller.
 *
 * @see RadioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CcApp.class)
public class RadioResourceIntTest {

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_CCG = "AAAAAAAAAA";
    private static final String UPDATED_CCG = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private RadioRepository radioRepository;

    @Autowired
    private RadioService radioService;

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

    private MockMvc restRadioMockMvc;

    private Radio radio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioResource radioResource = new RadioResource(radioService);
        this.restRadioMockMvc = MockMvcBuilders.standaloneSetup(radioResource)
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
    public static Radio createEntity(EntityManager em) {
        Radio radio = new Radio()
            .folder(DEFAULT_FOLDER)
            .alias(DEFAULT_ALIAS)
            .ccg(DEFAULT_CCG)
            .note(DEFAULT_NOTE);
        return radio;
    }

    @Before
    public void initTest() {
        radio = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadio() throws Exception {
        int databaseSizeBeforeCreate = radioRepository.findAll().size();

        // Create the Radio
        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radio)))
            .andExpect(status().isCreated());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeCreate + 1);
        Radio testRadio = radioList.get(radioList.size() - 1);
        assertThat(testRadio.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testRadio.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testRadio.getCcg()).isEqualTo(DEFAULT_CCG);
        assertThat(testRadio.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createRadioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioRepository.findAll().size();

        // Create the Radio with an existing ID
        radio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radio)))
            .andExpect(status().isBadRequest());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFolderIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioRepository.findAll().size();
        // set the field null
        radio.setFolder(null);

        // Create the Radio, which fails.

        restRadioMockMvc.perform(post("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radio)))
            .andExpect(status().isBadRequest());

        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRadios() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);

        // Get all the radioList
        restRadioMockMvc.perform(get("/api/radios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radio.getId().intValue())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER.toString())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())))
            .andExpect(jsonPath("$.[*].ccg").value(hasItem(DEFAULT_CCG.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getRadio() throws Exception {
        // Initialize the database
        radioRepository.saveAndFlush(radio);

        // Get the radio
        restRadioMockMvc.perform(get("/api/radios/{id}", radio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(radio.getId().intValue()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER.toString()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()))
            .andExpect(jsonPath("$.ccg").value(DEFAULT_CCG.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRadio() throws Exception {
        // Get the radio
        restRadioMockMvc.perform(get("/api/radios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadio() throws Exception {
        // Initialize the database
        radioService.save(radio);

        int databaseSizeBeforeUpdate = radioRepository.findAll().size();

        // Update the radio
        Radio updatedRadio = radioRepository.findById(radio.getId()).get();
        // Disconnect from session so that the updates on updatedRadio are not directly saved in db
        em.detach(updatedRadio);
        updatedRadio
            .folder(UPDATED_FOLDER)
            .alias(UPDATED_ALIAS)
            .ccg(UPDATED_CCG)
            .note(UPDATED_NOTE);

        restRadioMockMvc.perform(put("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRadio)))
            .andExpect(status().isOk());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeUpdate);
        Radio testRadio = radioList.get(radioList.size() - 1);
        assertThat(testRadio.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testRadio.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testRadio.getCcg()).isEqualTo(UPDATED_CCG);
        assertThat(testRadio.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingRadio() throws Exception {
        int databaseSizeBeforeUpdate = radioRepository.findAll().size();

        // Create the Radio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadioMockMvc.perform(put("/api/radios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radio)))
            .andExpect(status().isBadRequest());

        // Validate the Radio in the database
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRadio() throws Exception {
        // Initialize the database
        radioService.save(radio);

        int databaseSizeBeforeDelete = radioRepository.findAll().size();

        // Delete the radio
        restRadioMockMvc.perform(delete("/api/radios/{id}", radio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Radio> radioList = radioRepository.findAll();
        assertThat(radioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Radio.class);
        Radio radio1 = new Radio();
        radio1.setId(1L);
        Radio radio2 = new Radio();
        radio2.setId(radio1.getId());
        assertThat(radio1).isEqualTo(radio2);
        radio2.setId(2L);
        assertThat(radio1).isNotEqualTo(radio2);
        radio1.setId(null);
        assertThat(radio1).isNotEqualTo(radio2);
    }
}
