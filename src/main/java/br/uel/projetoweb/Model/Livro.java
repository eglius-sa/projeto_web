package br.uel.projetoweb.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Livros")

public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 13, message = "O ISBN deve ter no máximo 13 caracteres")
    @NotBlank(message = "ISBN é obrigatório")
    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false, length = 100)
    private String titulo;

    @Size(max = 150, message = "O autor deve ter no máximo 150 caracteres")
    @NotBlank(message = "O autor é obrigatório")
    @Column(nullable = false, length = 150)
    private String autor;

    @Column
    private Integer edicao;

    @Column
    private int ano;

    @Column
    private int exemplar;
// Getters e setters
}
