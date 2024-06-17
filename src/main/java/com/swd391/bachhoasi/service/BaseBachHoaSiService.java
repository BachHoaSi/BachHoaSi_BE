package com.swd391.bachhoasi.service;

public interface BaseBachHoaSiService {
    <K> Boolean isExist(K id, boolean isThrowError, Exception exception);

    <E,D> D basicConvertEntityToDto(E entity);
    <E,D> E basicConvertDtoToEntity(D dto);
}
