package br.com.gabrielflorentino.api_boleto.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoletoRequestDTO {

    @NotNull(message = "Não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String codigoBarras;
}
