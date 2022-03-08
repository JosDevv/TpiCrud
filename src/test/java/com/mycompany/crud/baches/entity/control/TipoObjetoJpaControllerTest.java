/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.TipoObjeto;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TipoObjetoJpaControllerTest {

    public TipoObjetoJpaControllerTest() {
    }

    @Test
    public void testCreate() {
        System.out.println("create");
        TipoObjeto tipo_objeto = new TipoObjeto();
        tipo_objeto.setActivo(true);
        TipoObjetoJpaController instance = new TipoObjetoJpaController();

        boolean expResult = true;
        boolean result = instance.create(tipo_objeto);
        assertEquals(expResult, result);
    }

    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        TipoObjeto tipo_objeto;
        TipoObjetoJpaController instance = new TipoObjetoJpaController();
        int id = 1;
        tipo_objeto = instance.findTipoObjeto(id);

        tipo_objeto.setActivo(false);
        instance.edit(tipo_objeto);
    }

//    @Test
//    public void testDestroy() throws Exception {
//        System.out.println("destroy");
//        int id = 1;//id a eliminar
//        TipoObjetoJpaController instance = new TipoObjetoJpaController();
//        instance.destroy(id);
//    }


    @Test
    public void testFindTipoObjeto() {
        System.out.println("findEstado");
        int id = 2;
        TipoObjetoJpaController instance = new TipoObjetoJpaController();
        TipoObjeto expResult = new TipoObjeto();
        TipoObjeto result = instance.findTipoObjeto(id);
        assertEquals(result.getClass(), TipoObjeto.class);
        System.out.println(result.getIdTipoObjeto());
    }

}
