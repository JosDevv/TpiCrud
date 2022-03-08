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

/**
 *
 * @author josem
 */
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
        Estado estado = new Estado();

        estado.setFechaCreacion(new Date());
        estado.setNombre("editado prueba" + System.currentTimeMillis());

        EstadoJpaController instance = new EstadoJpaController();
        instance.edit(estado,2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

//    /**
//     * Test of destroy method, of class EstadoJpaController.
////     */
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        Integer id = 1;//id a eliminar
//        EstadoJpaController instance = new EstadoJpaController();
//        instance.destroy(id);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//
//  
    @Test
    public void testFindEstado() {
        System.out.println("findEstado");
        Integer id = 2;
        EstadoJpaController instance = new EstadoJpaController();
        Estado expResult = new Estado();
        Estado result = instance.findEstado(id);
        assertEquals(result.getClass(), Estado.class);
        System.out.println(result.getNombre());

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
