package com.algaworks.algatransito.api.model.input;

import jakarta.validation.constraints.NotNull;

public class ProprietarioIdInput {


    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
