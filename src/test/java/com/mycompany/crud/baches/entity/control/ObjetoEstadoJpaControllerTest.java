/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.ObjetoEstado;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ObjetoEstadoJpaControllerTest {
    
    public ObjetoEstadoJpaControllerTest() {
    }

 

    @Test
    public void testCreate() {
        System.out.println("create");
        ObjetoEstado objeto_estado = new ObjetoEstado();
        objeto_estado.setObservaciones("creado desde test "+System.currentTimeMillis());
        ObjetoEstadoJpaController instance = new ObjetoEstadoJpaController();

        boolean expResult = true;
        boolean result = instance.create(objeto_estado);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        ObjetoEstado objeto_estado;
        ObjetoEstadoJpaController instance = new ObjetoEstadoJpaController();
        long id = 1;
        objeto_estado = instance.findObjetoEstado(id);

        objeto_estado.setObservaciones("editado desde test"+System.currentTimeMillis());
        instance.edit(objeto_estado);
    }


//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        long id = 1;//id a eliminar
//        ObjetoEstadoJpaController instance = new ObjetoEstadoJpaController();
//        instance.destroy(id);
//    }


    @Test
    public void testFindObjetoEstado() {
        System.out.println("findEstado");
        long id = 2;
       ObjetoEstadoJpaController instance = new ObjetoEstadoJpaController();
        ObjetoEstado expResult = new ObjetoEstado();
        ObjetoEstado result = instance.findObjetoEstado(id);
        assertEquals(result.getClass(), ObjetoEstado.class);
        System.out.println(result.getObservaciones());
    }


}
