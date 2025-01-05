package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.core.model.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SummaryMapperTest {

    private SummaryMapper summaryMapper;

    @BeforeEach
    void setUp() {
        summaryMapper = new SummaryMapper();
    }

    @Test
    void testMap_SummaryDtoToSummary() {
        RatingDto.SummaryDto summaryDto = new RatingDto.SummaryDto(4.5, 100, 4.7);

        Summary summary = summaryMapper.map(summaryDto);

        assertThat(summary).isNotNull();
        assertThat(summary.getAverage()).isEqualTo(4.5);
        assertThat(summary.getCount()).isEqualTo(100);
        assertThat(summary.getSortableAverage()).isEqualTo(4.7);
    }

    @Test
    void testMap_SummaryDtoToExistingSummary() {
        RatingDto.SummaryDto summaryDto = new RatingDto.SummaryDto(3.8, 50, 3.9);
        Summary existingSummary = new Summary();
        existingSummary.setAverage(2.0);
        existingSummary.setCount(20);
        existingSummary.setSortableAverage(2.1);

        Summary summary = summaryMapper.map(summaryDto, existingSummary);

        assertThat(summary).isNotNull();
        assertThat(summary.getAverage()).isEqualTo(3.8);
        assertThat(summary.getCount()).isEqualTo(50);
        assertThat(summary.getSortableAverage()).isEqualTo(3.9);
    }

    @Test
    void testMap_SummaryToSummaryDto() {
        Summary summary = new Summary();
        summary.setAverage(4.2);
        summary.setCount(200);
        summary.setSortableAverage(4.3);

        RatingDto.SummaryDto summaryDto = summaryMapper.map(summary);

        assertThat(summaryDto).isNotNull();
        assertThat(summaryDto.average()).isEqualTo(4.2);
        assertThat(summaryDto.count()).isEqualTo(200);
        assertThat(summaryDto.sortableAverage()).isEqualTo(4.3);
    }
}
