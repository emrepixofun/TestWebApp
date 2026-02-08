package com.emre.konumnot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationNoteRequest {

    @NotBlank(message = "Başlık boş olamaz")
    private String title;

    private String content;

    @NotNull(message = "Enlem bilgisi zorunludur")
    private Double latitude;

    @NotNull(message = "Boylam bilgisi zorunludur")
    private Double longitude;
}
