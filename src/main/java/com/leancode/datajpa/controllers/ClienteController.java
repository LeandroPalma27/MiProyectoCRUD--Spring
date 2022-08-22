package com.leancode.datajpa.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.leancode.datajpa.models.domain.BuscarCliente;
import com.leancode.datajpa.models.entity.Cliente;
import com.leancode.datajpa.models.service.IClienteService;
import com.leancode.datajpa.paginator.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"cliente"})
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    
    // Esta url puede recibir un parametro (el numero de pagina):
    @GetMapping({"", "/listado"})
    public String Listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model, SessionStatus status) {
        Pageable pageRequest = PageRequest.of(page, 6);
        // Esta lista iterable tiene 6 elementos por cada pagina (PERO CONTIENE INFORMACION DE
        // CUANTAS PAGINAS SE OBTUVIERON CONTANDO A TODOS LOS ELEMENTOS):
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/cliente/listado", clientes);
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        model.addAttribute("clienteDni", new BuscarCliente());
        status.setComplete();
        return "cliente/listar";
    }

    @GetMapping({"/lista"})
    public String Listar2(Model model, SessionStatus status) {
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clienteDni", new BuscarCliente());
        return "cliente/listarPorDni";
    }

    @PostMapping("/listaPorDni")
    public String listarPorDni(@Valid @ModelAttribute("clienteDni") BuscarCliente cliente, BindingResult result, Model model, SessionStatus status) {
        if (result.hasErrors()) {
            return "redirect:/cliente/lista";
        }
        model.addAttribute("titulo", "Listado de clientes");
        model.addAttribute("clientes", clienteService.findByDni(cliente.getDni()));
        return "cliente/listarPorDni";
    }

    // Este endpoint carga la vista del formulario para insertar un cliente:
    @GetMapping("/insertar")
    public String FormularioInsertar(/* @Valid @ModelAttribute("cliente")  */Cliente cliente, Model model) {
        model.addAttribute("titulo", "Crear cliente");
        model.addAttribute("cliente", cliente);
        return "cliente/insertar";
    }

    @PostMapping("/process-form")
    public String formularioProcess(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, RedirectAttributes flash, Model model, SessionStatus status) throws IOException {
        // El atributo de session cambia cada vez que pasamos al modelo un atributo con el mismo nombre
        // que esta persitido en la sesion, por lo tanto el resultado de esto varia en funcion de si
        // estamos editando o eliminando:
        if (result.hasErrors()) {
            return "cliente/insertar";
        }
        var res = clienteService.save(cliente);
        // Los flash attributtes son para retornar objetos al modelo luego de procesar un formulario
        // pero generalmente solo se usan para pasar mensajes de exito o fallo.
        flash.addFlashAttribute("success", "Se realizo la operacion con exito.");
        if (res != null) {
            if ((boolean) res.get("isUpload")) {
                flash.addFlashAttribute("upload_message", res.get("message"));
            } else {
                if (res.get("urlFoto") == null) {
                    flash.addFlashAttribute("error_upload", res.get("message"));
                }
            }
        }
        status.setComplete();
        return "redirect:/cliente/listado";
    }

    @GetMapping("/editar/{id}")
    public String FormularioEditar(@PathVariable("id") Long id, RedirectAttributes flash, Model model) {
        Cliente cliente = null;
        // Por si un pendejo intenta poner como id un "0":
        if (id > 0) {
            cliente = clienteService.findById(id);
            if (cliente != null) {
                // El atributo cliente en la sesion cambia (AHORA TIENE ID)
                model.addAttribute("cliente", cliente);
                model.addAttribute("titulo", "Editar cliente");
                return "cliente/editar";
            }
            flash.addFlashAttribute("error", "No existe un registro con el id ".concat(id.toString()) + ".");
        }
        return "redirect:/cliente/listado";
    }

    // Validamos que el id sea mayor que 0, no importa si existe o no, ya que se redireccionara igualmente
    // Es get por defecto, ya que no se usa la etiqueta form:
    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable("id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            clienteService.delete(id);
            flash.addFlashAttribute("success", "Se elimino el cliente con exito.");
        } else {
            flash.addFlashAttribute("error", "El id no puede ser 0.");
        }
        return "redirect:/cliente/listado";
    }

    @GetMapping("/detalle/{id}")
    public String Detalle(@PathVariable("id") Long id, Model model) {
        if (id > 0) {
            var clienteDetalles = clienteService.obtenerPorId(id);
            model.addAttribute("clienteDetalles", clienteDetalles);
            model.addAttribute("titulo", "Detalle de cliente");
            return "cliente/detalle";
        }
        return "redirect:/cliente/listado";
    }

}
