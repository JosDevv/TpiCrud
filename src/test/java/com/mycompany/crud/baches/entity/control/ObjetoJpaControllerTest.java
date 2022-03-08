/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;


import com.mycompany.crud.baches.entity.Objeto;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ObjetoJpaControllerTest {
    
    public ObjetoJpaControllerTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        Objeto objeto = new Objeto();
        objeto.setNombre("Creando prueba" + System.currentTimeMillis());
        ObjetoJpaController instance = new ObjetoJpaController();
        
        boolean expResult = true;
        boolean result = instance.create(objeto);
        assertEquals(expResult, result);
    }

    public void testEdit() throws Exception {
        System.out.println("edit");
        Objeto objeto;
        ObjetoJpaController instance = new ObjetoJpaController();
        long id=2;
        objeto=instance.findObjeto(id);
       
        objeto.setNombre("editado prueba" + System.currentTimeMillis());
        instance.edit(objeto);

    }
//
//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;//id a eliminar
//        ObjetoJpaController instance = new ObjetoJpaController();
//        instance.destroy(id);
//
//    }
//
//
//
    @Test
    public void testFindObjeto() {
        System.out.println("findEstado");
        long id = 2;
        ObjetoJpaController instance = new ObjetoJpaController();
        Objeto expResult = new Objeto();
        Objeto result = instance.findObjeto(id);
        assertEquals(result.getClass(), Objeto.class);
        System.out.println(result.getNombre());
    }
//
//
//    
}
