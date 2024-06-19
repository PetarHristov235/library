package com.library.impl.service;

import com.library.impl.db.entity.RateEntity;
import com.library.impl.db.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;

    @Override
    public void saveRating(RateEntity rateEntity) {
        rateRepository.save(rateEntity);
    }

    @Override
    public List<RateEntity> findRatesByBookId(Long bookId) {
        return rateRepository.findRatesByBookId(bookId);
    }
}
