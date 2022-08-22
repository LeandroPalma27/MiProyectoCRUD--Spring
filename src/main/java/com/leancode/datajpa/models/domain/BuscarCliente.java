package com.leancode.datajpa.models.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BuscarCliente {
    
    @NotBlank(message = "Rellene el campo.")
    @Size(max = 8)
    @Pattern(regexp = "[0-9]{8}", message = "Ingrese un DNI valido.")
    private String dni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
