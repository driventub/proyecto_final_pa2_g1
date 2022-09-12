package ec.edu.uce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import ec.edu.uce.modelo.Vehiculo;

@SpringBootTest
@Rollback(true)
@Transactional
public class VehiculoServicesImplTest {
    @Autowired
    private IVehiculoService vehiculoService;


    @BeforeEach
    void datos(){
        Vehiculo v = new Vehiculo();
        v.setAnioFabricacion("2004");
        v.setAvaluo(new BigDecimal("20000"));
        v.setCilindraje("1.5");
        v.setEstado("D");
        v.setMarca("Lexus");
        v.setModelo("Rx");
        v.setPais("Estados Unidos");
        v.setPlaca("ABC-523");
        v.setValorPorDia(new BigDecimal("62.3"));

        this.vehiculoService.insertar(v);

        Vehiculo v2 = new Vehiculo();
        v2.setAnioFabricacion("2004");
        v2.setAvaluo(new BigDecimal("20000"));
        v2.setCilindraje("1.5");
        v2.setEstado("D");
        v2.setMarca("Lexus");
        v2.setModelo("Rx");
        v2.setPais("Estados Unidos");
        v2.setPlaca("ABC-333");
        v2.setValorPorDia(new BigDecimal("62.3"));

        this.vehiculoService.insertar(v2);
    }


    @Test
    void testInsertar() {
        Vehiculo v = new Vehiculo();
        v.setAnioFabricacion("2004");
        v.setAvaluo(new BigDecimal("20000"));
        v.setCilindraje("1.5");
        v.setEstado("D");
        v.setMarca("Lexus");
        v.setModelo("Rx");
        v.setPais("Estados Unidos");
        v.setPlaca("ABC-332");
        v.setValorPorDia(new BigDecimal("62.3"));
        this.vehiculoService.insertar(v);

        Vehiculo vBuscado = this.vehiculoService.buscarPorPlaca("ABC-332");
        assertEquals(v.getEstado(), vBuscado.getEstado());

    }

    
    @Test
    void testBuscarTodos() {
        List<Vehiculo> lista = this.vehiculoService.buscarTodos();
        assertNotNull(lista);
    }

    
    @Test
    void testBuscarPorPlaca() {
        Vehiculo v = this.vehiculoService.buscarPorPlaca("ABC-523");
        assertNotNull(v);
    }
  
    @Test
    void testActualizar() {
        Vehiculo v = this.vehiculoService.buscarPorPlaca("ABC-523");
        v.setAnioFabricacion("2005");
        this.vehiculoService.actualizar(v);

        Vehiculo vBuscado = this.vehiculoService.buscarPorPlaca("ABC-523");
        assertEquals( v.getAnioFabricacion(),vBuscado.getAnioFabricacion());
    }

    @Test
    void testBorrar() {
        Vehiculo v = this.vehiculoService.buscarPorPlaca("ABC-333");
        this.vehiculoService.borrar(v.getId());
       
        boolean comprobar = false;
        try {
            
            Vehiculo vBuscado = this.vehiculoService.buscarPorPlaca("ABC-523");
            if (vBuscado != null) {
                comprobar = true;
            }
        } catch (Exception e) {
            
        }

        assertEquals(true, comprobar);

    }

  

    @Test
    void testBuscarMarca() {
        List<Vehiculo> v = this.vehiculoService.buscarMarca("Lexus");
        v.forEach(vehiculo -> assertEquals(vehiculo.getMarca(), "Lexus"));
        
    }

    @Test
    void testBuscarMarcaModelo() {
        List<Vehiculo> v = this.vehiculoService.buscarMarcaModelo("Lexus", "Rx");
        v.forEach(vehiculo -> {
            boolean comprobar;
            if (vehiculo.getMarca() == "Lexus" || vehiculo.getModelo() == "Rx") {
                comprobar = true;
            } else {
                comprobar = false;
            }

            assertEquals(true, comprobar);
        });
        
    }


   
   

    @Test
    void testVerificarVehiculo() {
        Vehiculo v = this.vehiculoService.buscarPorPlaca("ABC-523");
        Boolean comprobar = this.vehiculoService.verificarVehiculo(v.getId());
        assertEquals(true, comprobar);
    }

    @Test
    void testVerificarNoExistente(){
        assertNull(this.vehiculoService.buscarPorPlaca("ABC-323"));
    }


    @Test
    void testVerificarNoDisponible(){
        Vehiculo v = this.vehiculoService.buscarPorPlaca("ABC-523");
        v.setEstado("ND");
        this.vehiculoService.actualizar(v);

        Vehiculo vBuscado = this.vehiculoService.buscarPorPlaca("ABC-523");
        Boolean comprobar = this.vehiculoService.verificarVehiculo(vBuscado.getId());
        assertEquals(false, comprobar);
    }
}
