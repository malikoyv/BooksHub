package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.core.model.Counts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountsMapperTest {

    private CountsMapper countsMapper;

    @BeforeEach
    void setUp() {
        countsMapper = new CountsMapper();
    }

    @Test
    void testMap_CountsDtoToCounts() {
        RatingDto.CountsDto countsDto = new RatingDto.CountsDto(10, 20, 30, 40, 50);

        Counts counts = countsMapper.map(countsDto);

        assertThat(counts).isNotNull();
        assertThat(counts.getOneStar()).isEqualTo(10);
        assertThat(counts.getTwoStars()).isEqualTo(20);
        assertThat(counts.getThreeStars()).isEqualTo(30);
        assertThat(counts.getFourStars()).isEqualTo(40);
        assertThat(counts.getFiveStars()).isEqualTo(50);
    }

    @Test
    void testMap_CountsDtoToExistingCounts() {
        RatingDto.CountsDto countsDto = new RatingDto.CountsDto(5, 15, 25, 35, 45);
        Counts existingCounts = new Counts();
        existingCounts.setOneStar(1);
        existingCounts.setTwoStars(2);
        existingCounts.setThreeStars(3);
        existingCounts.setFourStars(4);
        existingCounts.setFiveStars(5);

        Counts counts = countsMapper.map(countsDto, existingCounts);

        assertThat(counts).isNotNull();
        assertThat(counts.getOneStar()).isEqualTo(5);
        assertThat(counts.getTwoStars()).isEqualTo(15);
        assertThat(counts.getThreeStars()).isEqualTo(25);
        assertThat(counts.getFourStars()).isEqualTo(35);
        assertThat(counts.getFiveStars()).isEqualTo(45);
    }

    @Test
    void testMap_CountsToCountsDto() {
        Counts counts = new Counts();
        counts.setOneStar(12);
        counts.setTwoStars(22);
        counts.setThreeStars(32);
        counts.setFourStars(42);
        counts.setFiveStars(52);

        RatingDto.CountsDto countsDto = countsMapper.map(counts);

        assertThat(countsDto).isNotNull();
        assertThat(countsDto.oneStar()).isEqualTo(12);
        assertThat(countsDto.twoStars()).isEqualTo(22);
        assertThat(countsDto.threeStars()).isEqualTo(32);
        assertThat(countsDto.fourStars()).isEqualTo(42);
        assertThat(countsDto.fiveStars()).isEqualTo(52);
    }
}
