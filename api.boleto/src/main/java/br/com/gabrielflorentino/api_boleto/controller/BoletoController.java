package br.com.gabrielflorentino.api_boleto.controller;

import br.com.gabrielflorentino.api_boleto.dto.BoletoDTO;
import br.com.gabrielflorentino.api_boleto.dto.BoletoRequestDTO;
import br.com.gabrielflorentino.api_boleto.services.BoletoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boleto")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<BoletoDTO> salvar(@Valid @RequestBody BoletoRequestDTO boletoRequestDTO) {
        var boleto = boletoService.salvar(boletoRequestDTO.getCodigoBarras());
        return new ResponseEntity<>(boleto, HttpStatus.CREATED);
    }

}
