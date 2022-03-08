/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.Objeto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.crud.baches.entity.TipoObjeto;
import com.mycompany.crud.baches.entity.ObjetoEstado;
import com.mycompany.crud.baches.entity.control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ObjetoJpaController implements Serializable {

    public ObjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ObjetoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("bachesPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Objeto objeto) {
        if (objeto.getObjetoEstadoCollection() == null) {
            objeto.setObjetoEstadoCollection(new ArrayList<ObjetoEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoObjeto idTipoObjeto = objeto.getIdTipoObjeto();
            if (idTipoObjeto != null) {
                idTipoObjeto = em.getReference(idTipoObjeto.getClass(), idTipoObjeto.getIdTipoObjeto());
                objeto.setIdTipoObjeto(idTipoObjeto);
            }
            Collection<ObjetoEstado> attachedObjetoEstadoCollection = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoCollectionObjetoEstadoToAttach : objeto.getObjetoEstadoCollection()) {
                objetoEstadoCollectionObjetoEstadoToAttach = em.getReference(objetoEstadoCollectionObjetoEstadoToAttach.getClass(), objetoEstadoCollectionObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoCollection.add(objetoEstadoCollectionObjetoEstadoToAttach);
            }
            objeto.setObjetoEstadoCollection(attachedObjetoEstadoCollection);
            em.persist(objeto);
            if (idTipoObjeto != null) {
                idTipoObjeto.getObjetoCollection().add(objeto);
                idTipoObjeto = em.merge(idTipoObjeto);
            }
            for (ObjetoEstado objetoEstadoCollectionObjetoEstado : objeto.getObjetoEstadoCollection()) {
                Objeto oldIdObjetoOfObjetoEstadoCollectionObjetoEstado = objetoEstadoCollectionObjetoEstado.getIdObjeto();
                objetoEstadoCollectionObjetoEstado.setIdObjeto(objeto);
                objetoEstadoCollectionObjetoEstado = em.merge(objetoEstadoCollectionObjetoEstado);
                if (oldIdObjetoOfObjetoEstadoCollectionObjetoEstado != null) {
                    oldIdObjetoOfObjetoEstadoCollectionObjetoEstado.getObjetoEstadoCollection().remove(objetoEstadoCollectionObjetoEstado);
                    oldIdObjetoOfObjetoEstadoCollectionObjetoEstado = em.merge(oldIdObjetoOfObjetoEstadoCollectionObjetoEstado);
                }
            }
            em.getTransaction().commit();
            return true;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
    }

    public void edit(Objeto objeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objeto persistentObjeto = em.find(Objeto.class, objeto.getIdObjeto());
            TipoObjeto idTipoObjetoOld = persistentObjeto.getIdTipoObjeto();
            TipoObjeto idTipoObjetoNew = objeto.getIdTipoObjeto();
            Collection<ObjetoEstado> objetoEstadoCollectionOld = persistentObjeto.getObjetoEstadoCollection();
            Collection<ObjetoEstado> objetoEstadoCollectionNew = objeto.getObjetoEstadoCollection();
            if (idTipoObjetoNew != null) {
                idTipoObjetoNew = em.getReference(idTipoObjetoNew.getClass(), idTipoObjetoNew.getIdTipoObjeto());
                objeto.setIdTipoObjeto(idTipoObjetoNew);
            }
            Collection<ObjetoEstado> attachedObjetoEstadoCollectionNew = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoCollectionNewObjetoEstadoToAttach : objetoEstadoCollectionNew) {
                objetoEstadoCollectionNewObjetoEstadoToAttach = em.getReference(objetoEstadoCollectionNewObjetoEstadoToAttach.getClass(), objetoEstadoCollectionNewObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoCollectionNew.add(objetoEstadoCollectionNewObjetoEstadoToAttach);
            }
            objetoEstadoCollectionNew = attachedObjetoEstadoCollectionNew;
            objeto.setObjetoEstadoCollection(objetoEstadoCollectionNew);
            objeto = em.merge(objeto);
            if (idTipoObjetoOld != null && !idTipoObjetoOld.equals(idTipoObjetoNew)) {
                idTipoObjetoOld.getObjetoCollection().remove(objeto);
                idTipoObjetoOld = em.merge(idTipoObjetoOld);
            }
            if (idTipoObjetoNew != null && !idTipoObjetoNew.equals(idTipoObjetoOld)) {
                idTipoObjetoNew.getObjetoCollection().add(objeto);
                idTipoObjetoNew = em.merge(idTipoObjetoNew);
            }
            for (ObjetoEstado objetoEstadoCollectionOldObjetoEstado : objetoEstadoCollectionOld) {
                if (!objetoEstadoCollectionNew.contains(objetoEstadoCollectionOldObjetoEstado)) {
                    objetoEstadoCollectionOldObjetoEstado.setIdObjeto(null);
                    objetoEstadoCollectionOldObjetoEstado = em.merge(objetoEstadoCollectionOldObjetoEstado);
                }
            }
            for (ObjetoEstado objetoEstadoCollectionNewObjetoEstado : objetoEstadoCollectionNew) {
                if (!objetoEstadoCollectionOld.contains(objetoEstadoCollectionNewObjetoEstado)) {
                    Objeto oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado = objetoEstadoCollectionNewObjetoEstado.getIdObjeto();
                    objetoEstadoCollectionNewObjetoEstado.setIdObjeto(objeto);
                    objetoEstadoCollectionNewObjetoEstado = em.merge(objetoEstadoCollectionNewObjetoEstado);
                    if (oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado != null && !oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado.equals(objeto)) {
                        oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado.getObjetoEstadoCollection().remove(objetoEstadoCollectionNewObjetoEstado);
                        oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado = em.merge(oldIdObjetoOfObjetoEstadoCollectionNewObjetoEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = objeto.getIdObjeto();
                if (findObjeto(id) == null) {
                    throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objeto objeto;
            try {
                objeto = em.getReference(Objeto.class, id);
                objeto.getIdObjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objeto with id " + id + " no longer exists.", enfe);
            }
            TipoObjeto idTipoObjeto = objeto.getIdTipoObjeto();
            if (idTipoObjeto != null) {
                idTipoObjeto.getObjetoCollection().remove(objeto);
                idTipoObjeto = em.merge(idTipoObjeto);
            }
            Collection<ObjetoEstado> objetoEstadoCollection = objeto.getObjetoEstadoCollection();
            for (ObjetoEstado objetoEstadoCollectionObjetoEstado : objetoEstadoCollection) {
                objetoEstadoCollectionObjetoEstado.setIdObjeto(null);
                objetoEstadoCollectionObjetoEstado = em.merge(objetoEstadoCollectionObjetoEstado);
            }
            em.remove(objeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objeto> findObjetoEntities() {
        return findObjetoEntities(true, -1, -1);
    }

    public List<Objeto> findObjetoEntities(int maxResults, int firstResult) {
        return findObjetoEntities(false, maxResults, firstResult);
    }

    private List<Objeto> findObjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objeto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Objeto findObjeto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objeto> rt = cq.from(Objeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
