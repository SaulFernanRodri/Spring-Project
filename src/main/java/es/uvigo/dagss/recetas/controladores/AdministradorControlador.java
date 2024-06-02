package es.uvigo.dagss.recetas.controladores;

import es.uvigo.dagss.recetas.excepciones.ResourceNotFoundException;
import es.uvigo.dagss.recetas.servicios.AdministradorServicioImpl;
import es.uvigo.dagss.recetas.entidades.Administrador;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins")

public class AdministradorControlador {

    private final AdministradorServicioImpl servicioAdministrador;

    @Autowired
    public AdministradorControlador(AdministradorServicioImpl servicioAdministrador) {
        this.servicioAdministrador = servicioAdministrador;
    }

    @GetMapping
    public List<Administrador> obtenerAdministradores(){
        return servicioAdministrador.obtenerAdmins();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable("id") Long id) {
        Optional<Administrador> admin = servicioAdministrador.buscarPorId(id);
        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Administrador no encontrado");
        } else {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrador> crear(@Valid @RequestBody Administrador admin) {
        Administrador nuevoAdmin = servicioAdministrador.crear(admin);
        URI uri = crearURIAdministrador(nuevoAdmin);

        return ResponseEntity.created(uri).body(nuevoAdmin);
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<HttpStatus> eliminar(@PathVariable("id") Long id) {
        Optional<Administrador> admin = servicioAdministrador.buscarPorId(id);

        if (admin.isEmpty()) {
            throw new ResourceNotFoundException("Almacen no encontrado");
        } else {
            servicioAdministrador.eliminar(admin.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrador> modificar(@PathVariable("id") Long id, @Valid @RequestBody Administrador admin) {
        Optional<Administrador> administradorOptional = servicioAdministrador.buscarPorId(id);

        if (administradorOptional.isEmpty()) {
            throw new ResourceNotFoundException("Almacen no encontrado");
        } else {
            Administrador nuevoAlmacen = servicioAdministrador.modificar(admin);
            return new ResponseEntity<>(nuevoAlmacen, HttpStatus.OK);
        }
    }



    // Construye la URI del nuevo recurso creado con POST
    private URI crearURIAdministrador(Administrador admin) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(admin.getId())
                .toUri();
    }



}
