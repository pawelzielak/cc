package org.apz.cc.service.impl;

import org.apz.cc.service.CCGService;
import org.apz.cc.domain.CCG;
import org.apz.cc.repository.CCGRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CCG.
 */
@Service
@Transactional
public class CCGServiceImpl implements CCGService {

    private final Logger log = LoggerFactory.getLogger(CCGServiceImpl.class);

    private final CCGRepository cCGRepository;

    public CCGServiceImpl(CCGRepository cCGRepository) {
        this.cCGRepository = cCGRepository;
    }

    /**
     * Save a cCG.
     *
     * @param cCG the entity to save
     * @return the persisted entity
     */
    @Override
    public CCG save(CCG cCG) {
        log.debug("Request to save CCG : {}", cCG);
        return cCGRepository.save(cCG);
    }

    /**
     * Get all the cCGS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CCG> findAll() {
        log.debug("Request to get all CCGS");
        return cCGRepository.findAll();
    }


    /**
     * Get one cCG by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CCG> findOne(Long id) {
        log.debug("Request to get CCG : {}", id);
        return cCGRepository.findById(id);
    }

    /**
     * Delete the cCG by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCG : {}", id);
        cCGRepository.deleteById(id);
    }
}
