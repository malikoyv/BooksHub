package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.core.model.Summary;
import org.springframework.stereotype.Service;

@Service
public class SummaryMapper implements IMapper<RatingDto.SummaryDto, Summary>{
    @Override
    public Summary map(RatingDto.SummaryDto summaryDto) {
        return map(summaryDto, new Summary());
    }

    @Override
    public Summary map(RatingDto.SummaryDto summaryDto, Summary summary) {
        summary.setAverage(summaryDto.average());
        summary.setCount(summaryDto.count());
        summary.setSortableAverage(summaryDto.sortableAverage());
        return summary;
    }

    public RatingDto.SummaryDto map(Summary summary) {
        return new RatingDto.SummaryDto(
                summary.getAverage(),
                summary.getCount(),
                summary.getSortableAverage()
        );
    }
}
