package br.uel.projetoweb.Controller;

import br.uel.projetoweb.Model.Livro;
import br.uel.projetoweb.Service.LivroService;
//import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
//import java.util.List;
//package br.uel.Livro.Controller;
//import br.uel.Livro.Service.LivroService;
//import br.uel.Livro.Model;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import java.util.List;

@Controller
@RequestMapping("/Livros")
public class LivroController {
    private final LivroService service;

    @Autowired
    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", service.listarLivros());
        return "Livros/lista";
    }

    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("livro", new Livro());
        return "Livros/form";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute Livro livro,
                            BindingResult erros, RedirectAttributes ra) {
        if (erros.hasErrors()) {
            return "Livros/form";
        }
        service.cadastrarLivro(livro);
        ra.addFlashAttribute("msg", "Cliente cadastrado!");
        return "redirect:/Livros";
    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("livro", service.buscarLivroPorId(id));
        return "Livros/form";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid
                            @ModelAttribute Livro livro, BindingResult erros,
                            RedirectAttributes ra) {
        if (erros.hasErrors()) {
            return "Livros/form";
        }
        service.atualizarLivro(id, livro);
        ra.addFlashAttribute("msg", "Cliente atualizado!");
        return "redirect:/Livros";
    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluirLivro(id);
        ra.addFlashAttribute("msg", "Cliente excluído!");
        return "redirect:/Livros";
    }
}
