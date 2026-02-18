package com.bancoUnion.prueba.domain.models;

import com.bancoUnion.prueba.api.dto.request.ClientRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "tipo_documento")
    private String documentType;

    @Column(name = "numero_documento")
    private String documentNumber;


    @Column(name = "primer_nombre")
    private String firstName;

    @Column(name = "segundo_nombre")
    private String secondName;

    @Column(name = "primer_apellido")
    private String lastName;

    @Column(name = "segundo_apellido")
    private String secondLastName;

    @Column(name = "telefono")
    private String phoneNumber;

    @Column(name = "correo_electronico")
    private String email;

    public void updateFrom(ClientRequestDTO newData) {
        this.firstName = newData.getFirstName();
        this.secondName = newData.getSecondName();
        this.lastName = newData.getLastName();
        this.secondLastName = newData.getSecondLastName();
        this.phoneNumber = newData.getPhoneNumber();
        this.email = newData.getEmail();

    }
}
