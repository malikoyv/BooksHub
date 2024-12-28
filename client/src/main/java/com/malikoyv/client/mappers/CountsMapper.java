package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.core.model.Counts;
import org.springframework.stereotype.Service;

@Service
public class CountsMapper implements IMapper<RatingDto.CountsDto, Counts>{
    @Override
    public Counts map(RatingDto.CountsDto countsDto) {
        return map(countsDto, new Counts());
    }

    @Override
    public Counts map(RatingDto.CountsDto countsDto, Counts counts) {
        counts.setOneStar(countsDto.oneStar());
        counts.setTwoStars(countsDto.twoStars());
        counts.setThreeStars(countsDto.threeStars());
        counts.setFourStars(countsDto.fourStars());
        counts.setFiveStars(countsDto.fiveStars());
        return counts;
    }

    public RatingDto.CountsDto map(Counts counts) {
        return new RatingDto.CountsDto(
                counts.getOneStar(),
                counts.getTwoStars(),
                counts.getThreeStars(),
                counts.getFourStars(),
                counts.getFiveStars()
        );
    }
}
