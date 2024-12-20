package com.malikoyv.client.mappers;

public interface IMapper<TDto, TEntity> {
    TEntity map(TDto dto);
    TEntity map(TDto dto, TEntity entity);
}
