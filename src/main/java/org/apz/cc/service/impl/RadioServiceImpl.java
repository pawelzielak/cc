package org.apz.cc.service.impl;

import org.apz.cc.service.RadioService;
import org.apz.cc.domain.Radio;
import org.apz.cc.repository.RadioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Radio.
 */
@Service
@Transactional
public class RadioServiceImpl implements RadioService {

    private final Logger log = LoggerFactory.getLogger(RadioServiceImpl.class);

    private final RadioRepository radioRepository;

    public RadioServiceImpl(RadioRepository radioRepository) {
        this.radioRepository = radioRepository;
    }

    /**
     * Save a radio.
     *
     * @param radio the entity to save
     * @return the persisted entity
     */
    @Override
    public Radio save(Radio radio) {
        log.debug("Request to save Radio : {}", radio);
        return radioRepository.save(radio);
    }

    /**
     * Get all the radios.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Radio> findAll() {
        log.debug("Request to get all Radios");
        return radioRepository.findAll();
    }


    /**
     * Get one radio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Radio> findOne(Long id) {
        log.debug("Request to get Radio : {}", id);
        return radioRepository.findById(id);
    }

    /**
     * Delete the radio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Radio : {}", id);
        radioRepository.deleteById(id);
    }
}
