package com.moto.service.controlador;

import com.moto.service.entidades.Moto;
import com.moto.service.servicios.MotoServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoServicios motoServicios;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMoto(){
        List<Moto> motos = motoServicios.getAll();
        if (motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(@PathVariable("id") int id){
        Moto motos = motoServicios.getMotoById(id);
        if(motos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motos);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarCarro(@RequestBody Moto moto){
        return ResponseEntity.ok(motoServicios.save(moto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosPorUsuarioId(@PathVariable ("usuarioId") int id){
        List<Moto> motos  =motoServicios.byUsuarioId(id);
        if(motos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(motos);
    }
}
