package com.swd391.bachhoasi.service;

import com.swd391.bachhoasi.model.dto.request.ProductOrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkService {
    List<ProductOrderDTO> getBestSellingProduct();
}
