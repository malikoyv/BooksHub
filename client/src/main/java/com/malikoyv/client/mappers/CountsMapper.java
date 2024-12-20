package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingsDto;
import com.malikoyv.core.model.Counts;

public class CountsMapper implements IMapper<RatingsDto.Counts, Counts>{
    @Override
    public Counts map(RatingsDto.Counts countsDto) {
        return map(countsDto, new Counts());
    }

    @Override
    public Counts map(RatingsDto.Counts countsDto, Counts counts) {
        counts.setOneStar(countsDto.oneStar());
        counts.setTwoStars(countsDto.twoStars());
        counts.setThreeStars(countsDto.threeStars());
        counts.setFourStars(countsDto.fourStars());
        counts.setFiveStars(countsDto.fiveStars());
        return counts;
    }
}
