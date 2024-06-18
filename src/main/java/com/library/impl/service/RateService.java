package com.library.impl.service;

import com.library.impl.db.entity.RateEntity;

import java.util.List;

public interface RateService {
    void saveRating(RateEntity rateEntity);
    List<RateEntity> findRatesByBookId(Long id);
}
