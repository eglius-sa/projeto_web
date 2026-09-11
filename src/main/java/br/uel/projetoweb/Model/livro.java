package br.uel.projetoweb.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")

public class livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 150)
    private String ISBN;
    @Column(nullable = false, length = 100)
    private String titulo;
    @Column(nullable = false, length = 150)
    private String autor;
    @Column
    private Integer edicao;
    @Column
    private int ano;
// Getters e setters
}
