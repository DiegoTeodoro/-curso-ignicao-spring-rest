package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.VeiculoAssembler;
import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final VeiculoAssembler veiculoAssembler;

    public VeiculoController(VeiculoRepository veiculoRepository, RegistroVeiculoService registroVeiculoService, ModelMapper modelMapper, VeiculoAssembler veiculoAssembler) {
        this.veiculoRepository = veiculoRepository;
        this.registroVeiculoService = registroVeiculoService;
        this.veiculoAssembler = veiculoAssembler;
    }

    @GetMapping
    public List<VeiculoModel> listar(){
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long id){
        return veiculoRepository.findById(id)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody Veiculo veiculo){
        return veiculoAssembler.toModel(registroVeiculoService.cadastrar(veiculo));
    }

    //Exception Handler e captura e trata as exceções
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
