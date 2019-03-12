package org.apz.cc.web.rest;

import org.apz.cc.CcApp;

import org.apz.cc.domain.Alias;
import org.apz.cc.repository.AliasRepository;
import org.apz.cc.service.AliasService;
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
 * Test class for the AliasResource REST controller.
 *
 * @see AliasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CcApp.class)
public class AliasResourceIntTest {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final String DEFAULT_FOLDER = "AAAAAAAAAA";
    private static final String UPDATED_FOLDER = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private AliasRepository aliasRepository;

    @Autowired
    private AliasService aliasService;

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

    private MockMvc restAliasMockMvc;

    private Alias alias;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AliasResource aliasResource = new AliasResource(aliasService);
        this.restAliasMockMvc = MockMvcBuilders.standaloneSetup(aliasResource)
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
    public static Alias createEntity(EntityManager em) {
        Alias alias = new Alias()
            .alias(DEFAULT_ALIAS)
            .folder(DEFAULT_FOLDER)
            .note(DEFAULT_NOTE);
        return alias;
    }

    @Before
    public void initTest() {
        alias = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlias() throws Exception {
        int databaseSizeBeforeCreate = aliasRepository.findAll().size();

        // Create the Alias
        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isCreated());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeCreate + 1);
        Alias testAlias = aliasList.get(aliasList.size() - 1);
        assertThat(testAlias.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testAlias.getFolder()).isEqualTo(DEFAULT_FOLDER);
        assertThat(testAlias.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createAliasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aliasRepository.findAll().size();

        // Create the Alias with an existing ID
        alias.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAliasIsRequired() throws Exception {
        int databaseSizeBeforeTest = aliasRepository.findAll().size();
        // set the field null
        alias.setAlias(null);

        // Create the Alias, which fails.

        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolderIsRequired() throws Exception {
        int databaseSizeBeforeTest = aliasRepository.findAll().size();
        // set the field null
        alias.setFolder(null);

        // Create the Alias, which fails.

        restAliasMockMvc.perform(post("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAliases() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        // Get all the aliasList
        restAliasMockMvc.perform(get("/api/aliases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alias.getId().intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS.toString())))
            .andExpect(jsonPath("$.[*].folder").value(hasItem(DEFAULT_FOLDER.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getAlias() throws Exception {
        // Initialize the database
        aliasRepository.saveAndFlush(alias);

        // Get the alias
        restAliasMockMvc.perform(get("/api/aliases/{id}", alias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alias.getId().intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS.toString()))
            .andExpect(jsonPath("$.folder").value(DEFAULT_FOLDER.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlias() throws Exception {
        // Get the alias
        restAliasMockMvc.perform(get("/api/aliases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlias() throws Exception {
        // Initialize the database
        aliasService.save(alias);

        int databaseSizeBeforeUpdate = aliasRepository.findAll().size();

        // Update the alias
        Alias updatedAlias = aliasRepository.findById(alias.getId()).get();
        // Disconnect from session so that the updates on updatedAlias are not directly saved in db
        em.detach(updatedAlias);
        updatedAlias
            .alias(UPDATED_ALIAS)
            .folder(UPDATED_FOLDER)
            .note(UPDATED_NOTE);

        restAliasMockMvc.perform(put("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlias)))
            .andExpect(status().isOk());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeUpdate);
        Alias testAlias = aliasList.get(aliasList.size() - 1);
        assertThat(testAlias.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testAlias.getFolder()).isEqualTo(UPDATED_FOLDER);
        assertThat(testAlias.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlias() throws Exception {
        int databaseSizeBeforeUpdate = aliasRepository.findAll().size();

        // Create the Alias

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAliasMockMvc.perform(put("/api/aliases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alias)))
            .andExpect(status().isBadRequest());

        // Validate the Alias in the database
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlias() throws Exception {
        // Initialize the database
        aliasService.save(alias);

        int databaseSizeBeforeDelete = aliasRepository.findAll().size();

        // Delete the alias
        restAliasMockMvc.perform(delete("/api/aliases/{id}", alias.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alias> aliasList = aliasRepository.findAll();
        assertThat(aliasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alias.class);
        Alias alias1 = new Alias();
        alias1.setId(1L);
        Alias alias2 = new Alias();
        alias2.setId(alias1.getId());
        assertThat(alias1).isEqualTo(alias2);
        alias2.setId(2L);
        assertThat(alias1).isNotEqualTo(alias2);
        alias1.setId(null);
        assertThat(alias1).isNotEqualTo(alias2);
    }
}
