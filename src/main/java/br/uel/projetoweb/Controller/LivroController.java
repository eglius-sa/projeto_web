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
//import java.util.List;
//package br.uel.Livro.Controller;
//import br.uel.Livro.Service.LivroService;
//import br.uel.Livro.Model;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
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
    public String listar(@RequestParam(defaultValue = "titulo") String atributo,
                         @RequestParam(defaultValue = "asc") String ordem,
                         @RequestParam(defaultValue = "titulo") String buscarPor,                          @RequestParam(defaultValue = "") String termo,
                         Model model) {

        model.addAttribute("livros", service.listarLivros(atributo, ordem, buscarPor, termo));
        model.addAttribute("ordem", ordem);
        model.addAttribute("atributo", atributo);
        model.addAttribute("buscarPor", buscarPor);
        model.addAttribute("termo", termo);

        return "Livros/lista";
    }

    @GetMapping("/novo")
    public String abrirCadastro(Model model) {
        model.addAttribute("livro", new Livro());
        return "Livros/form";
    }

    @PostMapping
    public String cadastrar(@Valid @ModelAttribute Livro livro,
                            BindingResult erros, RedirectAttributes ra,
                            @RequestParam(value = "arquivo", required = false) MultipartFile imagem) {
        if (erros.hasErrors()) {
            return "Livros/form";
        }
        try{
            service.cadastrarLivro(livro, imagem);
            ra.addFlashAttribute("msgSucesso", "Livro cadastrado!");
            return "redirect:/Livros";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("msgErro", e.getMessage());
            return "redirect:/Livros/novo";
        }

    }

    @GetMapping("/editar/{id}")
    public String abrirEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("livro", service.buscarLivroPorId(id));
        return "Livros/form";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid
                            @ModelAttribute Livro livro, BindingResult erros,
                            RedirectAttributes ra,
                            @RequestParam(value = "arquivoAtualizado", required = false) MultipartFile imagem) {
        if (erros.hasErrors()) {
            return "Livros/form";
        }
        try{
            service.atualizarLivro(id, livro, imagem);
            ra.addFlashAttribute("msgSucesso", "Livro atualizado!");
            return "redirect:/Livros";
        }catch (IllegalArgumentException e) {
            ra.addFlashAttribute("msgErro", e.getMessage());
            return "redirect:/Livros/editar/" + id;
        }

    }

    @DeleteMapping("/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.excluirLivro(id);
        ra.addFlashAttribute("msgExcluido", "Livro excluído!");
        return "redirect:/Livros";
    }
}
