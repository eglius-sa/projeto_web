package br.uel.projetoweb.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "livros")

public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 13, message = "O ISBN deve ter no minimo 6 e máximo 13 caracteres")
    @NotBlank(message = "ISBN é obrigatório")
    @Column(unique = true, nullable = false, length = 13)
    private String isbn;

    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    @NotBlank(message = "O título é obrigatório")
    @Column(nullable = false, length = 100)
    private String titulo;

    @Size(min = 5, max = 150, message = "O nome do autor deve ter no minimo 5 e no máximo 150 caracteres")
    @NotBlank(message = "O autor é obrigatório")
    @Column(nullable = false, length = 150)
    private String autor;

    @Column
    private String imagem;

    @Column
    @Min(value = 1, message = "O edicao não pode ser mwnor que 1.")
    @Max(value = 50, message = "O edicao não pode ser maior que 50.")
    private Integer edicao;

    @Column
    @Min(value = 1800, message = "O ano não pode ser anterior a 1800.")
    @Max(value = 2100, message = "O ano não pode ser maior que 2100.")
    private int ano;

    @Column
    @Min(value = 1, message = "O exemplar não pode ser menor que 1.")
    @Max(value = 100, message = "O exemplar não pode ser maior que 100.")
    private int exemplar;
// Getters e setters
}
