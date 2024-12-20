package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.EditionDto;
import com.malikoyv.core.model.Edition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditionMapper implements IMapper<EditionDto, Edition> {
    @Override
    public Edition map(EditionDto editionDto) {
        return map(editionDto, new Edition());
    }

    @Override
    public Edition map(EditionDto editionDto, Edition edition) {
        edition.setKey(editionDto.key());
        edition.setTitle(editionDto.title());
        edition.setLanguage(editionDto.language());

        if (editionDto.firstPublishDate() != null) {
            edition.setFirstPublishDate(LocalDate.parse(editionDto.firstPublishDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        edition.setCovers(editionDto.covers());
        edition.setSubjectPlaces(editionDto.subjectPlaces());
        edition.setSubjectPeople(editionDto.subjectPeople());
        edition.setSubjects(editionDto.subjects());
        edition.setSubjectTimes(editionDto.subjectTimes());
        return edition;
    }
}
