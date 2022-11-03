package com.carro.service.controladores;

import com.carro.service.entidades.Carro;
import com.carro.service.servicios.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarro(){
        List<Carro> carro = carroService.getAll();
        if (carro.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") int id){
        Carro carros = carroService.getCarroById(id);
        if(carros == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
        return ResponseEntity.ok(carroService.save(carro));
    }

    //La anotacion PathVariable se usa para que se tome la variable en cuenta en el mapping
    //tal cual se ponga en este caso @GetMapping("/usuario/{usuarioId}") la variable {usuarioId}
    //se debe poner en el Path y el valor que tenga ahi es el valor que tomara en este caso para
    //el id del usuario del cual creo que se tomara en cuenta para hacer la busqueda de dicha lista
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosPorUsuarioId(@PathVariable ("usuarioId") int id){
        List<Carro> carros  =carroService.byUsuarioId(id);
        if(carros == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carros);
    }
}
