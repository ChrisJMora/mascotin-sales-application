package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.model.catalogue.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class PetProfile {

    @Id @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petProfileId;

    @Required
    private String name;

    @Required
    @Enumerated(EnumType.STRING)
    private PetSpecie petSpecie;

    @Required
    @Enumerated(EnumType.STRING)
    private PetBreed petBreed;

    @Required
    @Enumerated(EnumType.STRING)
    private PetAge petAge;

    @Files
    @Column(length=32)
    private String image;
}