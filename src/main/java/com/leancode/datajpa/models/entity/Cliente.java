package com.leancode.datajpa.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
// Siempre se pone en minuscula (nomenclatura muy usada):
@Table(name = "clientes")
public class Cliente implements Serializable {
    
    @Id
    // Este tipo de generation permite que se tome la estrategia de generar un valor incrementado en
    // cada registro que se haga:
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 8)
    @Pattern(regexp = "[0-9]{8}", message = "Ingrese un DNI valido.")
    private String dni;

    @NotBlank(message = "Rellene el campo.")
    @Size(max = 60, message = "No puede exceder los 60 caracteres")
    private String nombre;

    @NotBlank(message = "Rellene el campo.")
    @Size(max = 60, message = "No puede exceder los 60 caracteres")
    private String apellidos;

    @NotBlank(message = "Rellene el campo.")
    @Email(message = "Formato de email invalido.")
    private String email;

    // Este atributo mapea el atributo a la columna en la tabla de la BD, permitiendonos customizar:
    @Column(name = "fecha_registro")
    // Sirve para indicar que esta columna guardara las fechas ya sea con solo fecha o tambien con hora:
    /* @Temporal(TemporalType.DATE) */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fechaRegistro;

    @Column(name="url_foto")
    private String urlFoto;

    @Column(name="foto_id")
    private String fotoId;

    @Transient
    private MultipartFile file;

    private static final long serialVersionUID = 1L;

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFotoId() {
        return fotoId;
    }

    public void setFotoId(String fotoId) {
        this.fotoId = fotoId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

}
