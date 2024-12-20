package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.RatingsDto;
import com.malikoyv.core.model.Summary;

public class SummaryMapper implements IMapper<RatingsDto.Summary, Summary>{
    @Override
    public Summary map(RatingsDto.Summary summary) {
        return map(summary, new Summary());
    }

    @Override
    public Summary map(RatingsDto.Summary summaryDto, Summary summary) {
        summary.setAverage(summaryDto.average());
        summary.setCount(summaryDto.count());
        summary.setSortableAverage(summaryDto.sortableAverage());
        return summary;
    }
}
