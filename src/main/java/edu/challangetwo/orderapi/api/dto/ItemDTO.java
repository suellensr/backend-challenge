package edu.challangetwo.orderapi.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemDTO {

    @NotBlank
    private String descricao;

    @NotNull
    private BigDecimal precoUnitario;

    @NotNull
    private int qtd;

    public ItemDTO() {}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return  Objects.equals(descricao, itemDTO.getDescricao()) &&
                Objects.equals(precoUnitario, itemDTO.getPrecoUnitario()) &&
                Objects.equals(qtd, itemDTO.getQtd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, precoUnitario, qtd);
    }
}
