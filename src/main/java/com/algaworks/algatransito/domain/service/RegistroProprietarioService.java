package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public RegistroProprietarioService(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    @Transactional // Esse metodo deve ser executado dentro de uma transação.
    public Proprietario salvar(Proprietario proprietario){
        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                .filter(p -> !p.equals(proprietario))
                .isPresent();
        if(emailEmUso){
            throw new NegocioException("Já existe um proprietario cadastrado com este email");
        }

        return proprietarioRepository.save(proprietario);

    }

    @Transactional
    public  void excluir(Long id){
        proprietarioRepository.deleteById(id);
    }
}
