package br.uel.projetoweb.Service;

import br.uel.projetoweb.Model.Livro;
import br.uel.projetoweb.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarLivros(String atributo, String ordem, String buscarPor, String termo) {
        Sort sort = ordem.equalsIgnoreCase("desc") ? Sort.by(atributo).descending() : Sort.by(atributo).ascending();
        if (termo == null || termo.trim().isEmpty()) {
            return livroRepository.findAll(sort);
        }
        Livro livroFiltro = new Livro();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();
        matcher = matcher.withIgnorePaths("id", "ano", "edicao", "exemplar");

        switch (buscarPor) {
            case "titulo" -> livroFiltro.setTitulo(termo.trim());
            case "autor" -> livroFiltro.setAutor(termo.trim());
            default -> livroFiltro.setTitulo(termo.trim()); // Fallback caso venha algo inesperado
        }

        return livroRepository.findAll(Example.of(livroFiltro, matcher), sort);
    }

    public Livro cadastrarLivro(Livro livro, MultipartFile imagem){
        if(!imagem.isEmpty()){

            String nomeArquivo = imagem.getOriginalFilename();
            Path pasta = Paths.get("uploads/images");

            try {
                Files.createDirectories(pasta);
                Path caminho = pasta.resolve(nomeArquivo);

                Files.copy(
                        imagem.getInputStream(),
                        caminho,
                        StandardCopyOption.REPLACE_EXISTING
                );

                livro.setImagem(nomeArquivo);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao adicionar imagem",e);
            }
        }
        return livroRepository.save(livro);
    }

    public Livro buscarLivroPorId(Long id){
        return livroRepository.findById(id).orElse(null);
    }

    public Livro atualizarLivro(Long id, Livro livroAtualizado){
        Livro livro = buscarLivroPorId(id);

        if (livro !=  null){
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setIsbn(livroAtualizado.getIsbn());
            livro.setEdicao(livroAtualizado.getEdicao());
            livro.setAno(livroAtualizado.getAno());
            livro.setExemplar(livroAtualizado.getExemplar());
            return livroRepository.save(livro);
        } else{
            throw new RuntimeException("Livro não encontrado por id: " + id);
        }
    }

    public void excluirLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com id: " + id);
        }
        livroRepository.deleteById(id);
    }
}
