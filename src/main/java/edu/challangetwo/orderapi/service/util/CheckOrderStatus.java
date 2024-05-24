package edu.challangetwo.orderapi.service.util;

import edu.challangetwo.orderapi.api.dto.ItemDTO;
import edu.challangetwo.orderapi.api.dto.OrderDTO;
import edu.challangetwo.orderapi.api.dto.StatusRequestDTO;
import edu.challangetwo.orderapi.model.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckOrderStatus {

    public List<String> updateStatus(OrderDTO orderDTO, StatusRequestDTO statusRequestDTO) {

        List<String> statusList = new ArrayList<>();

        BigDecimal totalOrderValue = orderDTO.getItens().stream()
                .map(itemDTO -> itemDTO.getPrecoUnitario().multiply(BigDecimal.valueOf(itemDTO.getQtd())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalOrderQuantity = orderDTO.getItens().stream()
                .mapToInt(ItemDTO::getQtd)
                .sum();

        if (statusRequestDTO.getStatus().equalsIgnoreCase("REPROVADO")) {
            statusList.add(OrderStatus.REPROVADO.toString());
        } else if (statusRequestDTO.getStatus().equalsIgnoreCase("APROVADO")) {

            if (statusRequestDTO.getValorAprovado().compareTo(totalOrderValue) > 0) {
                statusList.add(OrderStatus.APROVADO_VALOR_A_MAIOR.toString());
            } else if (statusRequestDTO.getValorAprovado().compareTo(totalOrderValue) < 0) {
                statusList.add(OrderStatus.APROVADO_VALOR_A_MENOR.toString());
            }

            if (statusRequestDTO.getItensAprovados() > totalOrderQuantity) {
                statusList.add(OrderStatus.APROVADO_QTD_A_MAIOR.toString());
            } else if (statusRequestDTO.getItensAprovados() < totalOrderQuantity) {
                statusList.add(OrderStatus.APROVADO_QTD_A_MENOR.toString());
            }

            if (statusList.isEmpty()
                    && statusRequestDTO.getValorAprovado().compareTo(totalOrderValue) == 0
                    && statusRequestDTO.getItensAprovados() == totalOrderQuantity) {

                statusList.add(OrderStatus.APROVADO.toString());
            }
        }

        return statusList;
    }
}