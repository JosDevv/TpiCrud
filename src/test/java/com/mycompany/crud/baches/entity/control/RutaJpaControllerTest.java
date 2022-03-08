/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.Ruta;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RutaJpaControllerTest {

    public RutaJpaControllerTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        Ruta ruta = new Ruta();
        ruta.setNombre("avenida inventada " + System.currentTimeMillis());
        RutaJpaController instance = new RutaJpaController();

        boolean expResult = true;
        boolean result = instance.create(ruta);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Ruta ruta;
        RutaJpaController instance = new RutaJpaController();
        long id = 1;
        ruta = instance.findRuta(id);

        ruta.setNombre("editado test "+ System.currentTimeMillis());
        instance.edit(ruta);
    }


//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;//id a eliminar
//        RutaJpaController instance = new RutaJpaController();
//        instance.destroy(id);
//    }


    @Test
    public void testFindRuta() {
        System.out.println("findEstado");
        long id = 2;
        RutaJpaController instance = new RutaJpaController();
        Ruta expResult = new Ruta();
        Ruta result = instance.findRuta(id);
        assertEquals(result.getClass(), Ruta.class);
        System.out.println(result.getNombre());
    }

}
