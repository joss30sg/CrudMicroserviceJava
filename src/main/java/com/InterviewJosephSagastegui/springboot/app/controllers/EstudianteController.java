package com.InterviewJosephSagastegui.springboot.app.controllers;
import com.InterviewJosephSagastegui.springboot.app.models.entity.Estudiantes;
import com.InterviewJosephSagastegui.springboot.app.models.service.IEstudiantesService;
import com.InterviewJosephSagastegui.springboot.app.util.paginator.PageRender;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"estudiante"})
public class EstudianteController {
    @Autowired
    private IEstudiantesService clienteService;

    public EstudianteController() {
    }

// Leer estudiante
    @GetMapping({"/ver/{id}"})
    public String ver(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Estudiantes cliente = clienteService.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en DDBB");
            return "redirect:/listar";
        } else {
            model.put("cliente", cliente);
            model.put("titulo", "Detalle Estudiante: " + cliente.getNombre());
            return "ver";
        }
    }

    @RequestMapping(
        value = {"/listar"},
        method = {RequestMethod.GET}
    )
    public String listar(@RequestParam(name = "page",defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Estudiantes> Estudiantes = clienteService.findAll(pageRequest);
        PageRender<Estudiantes> pageRender = new PageRender<Estudiantes>("/listar", Estudiantes);
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", Estudiantes);
        model.addAttribute("page", pageRender);
        return "listarEstudiantes";
    }

    @RequestMapping({"/form"})
    public String crear(Map<String, Object> model) {
        Estudiantes Estudiante = new Estudiantes();
        model.put("Estudiante", Estudiante);
        model.put("titulo", "Formulario de Estudiante");
        return "form";
    }

    @RequestMapping({"/form/{id}"})
    public String editar(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Estudiantes Estudiante = null;
        if (id > 0L) {
            Estudiante = clienteService.findOne(id);
            if (Estudiante == null) {
                flash.addFlashAttribute("error", "El ID del Estudiante no existe en la BBDD!");
                return "redirect:/listar";
            } else {
                model.put("Estudiante", Estudiante);
                model.put("titulo", "Editar Estudiante");
                return "form";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del Estudiante no puede ser cero!");
            return "redirect:/listarEstudiantes";
        }
    }


    // crear y actualizar estudiantes
    @RequestMapping(
        value = {"/form"},
        method = {RequestMethod.POST}
    )
    public String Actualizar(@Valid Estudiantes estudiante, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Estudiante");
            return "form";
        } else {
            String mensajeFlash;
            if (estudiante.getId() != null) {
                mensajeFlash = "Estudiante actualizado con éxito!";
            } else {
                mensajeFlash = "Estudiante creado con éxito!";
            }
            clienteService.save(estudiante);
            status.setComplete();
            flash.addFlashAttribute("success", mensajeFlash);
            return "redirect:/listarEstudiantes";
        }
    }

     //Eliminar estudiantes

    @RequestMapping({"/eliminar/{id}"})
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
        if (id > 0L) {
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Estudiante eliminado con éxito!");
        }

        return "redirect:/listarEstudiantes";
    }
}