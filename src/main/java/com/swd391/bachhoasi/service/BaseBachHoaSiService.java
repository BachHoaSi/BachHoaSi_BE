package com.swd391.bachhoasi.service;

public interface BaseBachHoaSiService {
    <E,D> D convertEntityToDto(E entity);
    <E,D> E convertDtoToEntity(D dto);
}
