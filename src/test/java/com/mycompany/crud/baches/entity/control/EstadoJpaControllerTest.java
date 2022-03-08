/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.Estado;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class EstadoJpaControllerTest {

    public EstadoJpaControllerTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        Estado nuevo = new Estado();
        nuevo.setFechaCreacion(new Date());
        nuevo.setNombre("Creando prueba" + System.currentTimeMillis());
        EstadoJpaController instance = new EstadoJpaController();
        boolean expResult = true;
        boolean result = instance.create(nuevo);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Estado estado;
        EstadoJpaController instance = new EstadoJpaController();
        estado=instance.findEstado(2);
        estado.setFechaCreacion(new Date());
        estado.setNombre("editado prueba" + System.currentTimeMillis());
        instance.edit(estado);

    }


//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        Integer id = null;//id a eliminar
//        EstadoJpaController instance = new EstadoJpaController();
//        instance.destroy(id);
//         
//        //fail("esto falla por que el id es null para testear prueve con un id existente");
//    }

  
    @Test
    public void testFindEstado() {
        System.out.println("findEstado");
        Integer id = 2;
        EstadoJpaController instance = new EstadoJpaController();
        Estado expResult = new Estado();
        Estado result = instance.findEstado(id);
        assertEquals(result.getClass(), Estado.class);
        System.out.println(result.getNombre());

    }

}
