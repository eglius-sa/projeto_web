package br.uel.projetoweb.Service;

import br.uel.projetoweb.Model.Livro;
import br.uel.projetoweb.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarLivros(){
        return livroRepository.findAll();
    }

    public Livro cadastrarLivro(Livro livro){
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
            throw new RuntimeException("Cliente não encontrado com id: " + id);
        }
        livroRepository.deleteById(id);
    }
}
