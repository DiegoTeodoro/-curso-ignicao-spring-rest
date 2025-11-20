package com.algaworks.algatransito;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listar() {
        return proprietarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> bucarPorId(@PathVariable Long id) {
        Optional<Proprietario> proprietario = proprietarioRepository.findById(id);
        return proprietario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // 201
    public ResponseEntity<Proprietario> adicionar(@RequestBody Proprietario proprietario) {
        Proprietario salvo = proprietarioRepository.save(proprietario);
        return new ResponseEntity<>(salvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long id,
                                                  @RequestBody Proprietario proprietario) {
        if (!proprietarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(id);
        Proprietario proprietarioAtualizado = proprietarioRepository.save(proprietario);

        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        if(!proprietarioRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        proprietarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

