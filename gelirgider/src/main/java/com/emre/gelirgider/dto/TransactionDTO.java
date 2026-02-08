package com.emre.gelirgider.dto;

import com.emre.gelirgider.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    
    private Long id;
    
    @NotBlank(message = "Başlık boş olamaz")
    private String title;
    
    private String description;
    
    @NotNull(message = "Miktar boş olamaz")
    @Positive(message = "Miktar pozitif olmalı")
    private BigDecimal amount;
    
    @NotNull(message = "İşlem tipi boş olamaz")
    private TransactionType type;
    
    private Long categoryId;
    
    private String categoryName;
    
    @NotNull(message = "İşlem tarihi boş olamaz")
    private LocalDate transactionDate;
}
