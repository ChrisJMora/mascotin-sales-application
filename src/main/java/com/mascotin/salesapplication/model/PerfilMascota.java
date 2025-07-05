package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.catalogue.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class PerfilMascota {
    @Id @GeneratedValue
    private Long id;

    @Required
    private String nombre;

    @Required
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @Required
    @Enumerated(EnumType.STRING)
    private Raza raza;

    @Required
    @Enumerated(EnumType.STRING)
    private Edad edad;

    @File
    private byte[] foto;
}