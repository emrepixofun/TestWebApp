package com.emre.gelirgider.dto;

import com.emre.gelirgider.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "Kategori adı boş olamaz")
    private String name;
    
    private String icon;
    
    private String color;
    
    @NotNull(message = "İşlem tipi boş olamaz")
    private TransactionType type;
}
