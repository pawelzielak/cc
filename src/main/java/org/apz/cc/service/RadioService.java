package org.apz.cc.service;

import org.apz.cc.domain.Radio;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Radio.
 */
public interface RadioService {

    /**
     * Save a radio.
     *
     * @param radio the entity to save
     * @return the persisted entity
     */
    Radio save(Radio radio);

    /**
     * Get all the radios.
     *
     * @return the list of entities
     */
    List<Radio> findAll();


    /**
     * Get the "id" radio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Radio> findOne(Long id);

    /**
     * Delete the "id" radio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
