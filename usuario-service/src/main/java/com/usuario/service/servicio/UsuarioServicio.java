package com.usuario.service.servicio;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeingClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.ClientInfoStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioServicio {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private CarroFeignClient carroFeignClient;

  @Autowired
  private MotoFeingClient motoFeingClient;

  public List <Usuario> getAll(){
    return usuarioRepository.findAll();
  }

  public Usuario getUsuarioById(int id){
    return usuarioRepository.findById(id).orElse(null);
  }

  public Usuario save(Usuario usuario){
    Usuario nuevoUsuario = usuarioRepository.save(usuario);
    return nuevoUsuario;
  }

  public void setRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Carro> getCarros (int usuarioId){
    List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
    return carros;
  }

  public List<Moto> getMotos (int usuarioId){
    List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
    return motos;
  }

  public Carro saveCarro(int usuarioId, Carro carro){
    carro.setUsuarioId(usuarioId);
    return carroFeignClient.save(carro);
  }

  public Moto saveMoto(int usuarioId, Moto moto){
    moto.setUsuarioId(usuarioId);
    return motoFeingClient.save(moto);
  }

  public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
    Map<String, Object> resultado = new HashMap<>();
    Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

    if (usuario == null){
      resultado.put("mensaje", "el usuario no existe");
      return resultado;
    }

    resultado.put("Usuario", usuario);
    List<Carro> carros = carroFeignClient.getCarros(usuarioId);
    if (carros.isEmpty()){
      resultado.put("Carros", "El usuario no tiene carros");
    }else{
      resultado.put("carros", carros);
    }

    List<Moto> motos = motoFeingClient.getMotos(usuarioId);
    if (motos.isEmpty()){
      resultado.put("Motos", "El usuario no tiene motos");
    }else{
      resultado.put("Motos", motos);
    }
    return resultado;
  }
}
