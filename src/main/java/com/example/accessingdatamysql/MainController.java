package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/mascotas")
public class MainController {
    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping(path="/add")
    public @ResponseBody String addMascota (@RequestParam String nombre, @RequestParam String raza, @RequestParam String propietario) {

        Mascota m = new Mascota();
        m.setNombre(nombre);
        m.setRaza(raza);
        m.setPropietario(propietario);
        mascotaRepository.save(m);
        return "Mascota Guardada";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }
    @PutMapping(path="/edit")
    public @ResponseBody String editMascota(@RequestParam Integer id, @RequestParam String nombre, @RequestParam String raza, @RequestParam String propietario) {
        Mascota mascota = mascotaRepository.findById(id).orElse(null);
        if (mascota != null) {
            mascota.setNombre(nombre);
            mascota.setRaza(raza);
            mascota.setPropietario(propietario);
            mascotaRepository.save(mascota);
            return "Mascota Editada Correctamente";
        }
        return "Mascota no encontrada";
    }

    @GetMapping(path="/ver/{id}")
    public @ResponseBody Mascota getMascota(@PathVariable("id") Integer id) {
        return mascotaRepository.findById(id).orElse(null);
    }
    @DeleteMapping(path="/del")
    public @ResponseBody String deleteMascota(@RequestParam Integer id) {
        Mascota mascota = mascotaRepository.findById(id).orElse(null);
        if (mascota != null) {
            mascotaRepository.delete(mascota);
            return "Mascota eliminada";
        }
        return "Mascota no encontrada";
    }

    @GetMapping(path="/get/report")
    public @ResponseBody List getReport() {
        String sql = "SELECT CONCAT('Nombre de Mascota: ',nombre, ', Raza: ', raza, ', Propietario: ',propietario) as reporte FROM mascota";
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(sql);
        return queryResult;
    }

}