package com.swd391.bachhoasi.service.impl;

import com.swd391.bachhoasi.model.dto.request.ProductOrderDTO;
import com.swd391.bachhoasi.repository.OrderProductMenuRepository;
import com.swd391.bachhoasi.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {
    private final OrderProductMenuRepository orderProductMenuRepository;
    @Override
    public List<ProductOrderDTO> getBestSellingProduct() {
        List<Object[]> results = orderProductMenuRepository.findProductIdNameTotalQuantityOrderByTotalQuantityDesc();
        List<ProductOrderDTO> response = new ArrayList<>();

        for (Object[] result : results) {
            ProductOrderDTO item = ProductOrderDTO.builder()
                    .id((BigDecimal) result[0])
                    .productName((String) result[1])
                    .quantity(((Number) result[3]).intValue())
                    .urlImage((String) result[2])
                    .build();
            response.add(item);
        }
        return response;
    }
}
