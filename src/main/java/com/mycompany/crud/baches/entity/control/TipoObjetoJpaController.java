/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.crud.baches.entity.Objeto;
import com.mycompany.crud.baches.entity.TipoObjeto;
import com.mycompany.crud.baches.entity.control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class TipoObjetoJpaController implements Serializable {

    public TipoObjetoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public TipoObjetoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("bachesPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(TipoObjeto tipoObjeto) {
        if (tipoObjeto.getObjetoCollection() == null) {
            tipoObjeto.setObjetoCollection(new ArrayList<Objeto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Objeto> attachedObjetoCollection = new ArrayList<Objeto>();
            for (Objeto objetoCollectionObjetoToAttach : tipoObjeto.getObjetoCollection()) {
                objetoCollectionObjetoToAttach = em.getReference(objetoCollectionObjetoToAttach.getClass(), objetoCollectionObjetoToAttach.getIdObjeto());
                attachedObjetoCollection.add(objetoCollectionObjetoToAttach);
            }
            tipoObjeto.setObjetoCollection(attachedObjetoCollection);
            em.persist(tipoObjeto);
            for (Objeto objetoCollectionObjeto : tipoObjeto.getObjetoCollection()) {
                TipoObjeto oldIdTipoObjetoOfObjetoCollectionObjeto = objetoCollectionObjeto.getIdTipoObjeto();
                objetoCollectionObjeto.setIdTipoObjeto(tipoObjeto);
                objetoCollectionObjeto = em.merge(objetoCollectionObjeto);
                if (oldIdTipoObjetoOfObjetoCollectionObjeto != null) {
                    oldIdTipoObjetoOfObjetoCollectionObjeto.getObjetoCollection().remove(objetoCollectionObjeto);
                    oldIdTipoObjetoOfObjetoCollectionObjeto = em.merge(oldIdTipoObjetoOfObjetoCollectionObjeto);
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

    public void edit(TipoObjeto tipoObjeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoObjeto persistentTipoObjeto = em.find(TipoObjeto.class, tipoObjeto.getIdTipoObjeto());
            Collection<Objeto> objetoCollectionOld = persistentTipoObjeto.getObjetoCollection();
            Collection<Objeto> objetoCollectionNew = tipoObjeto.getObjetoCollection();
            Collection<Objeto> attachedObjetoCollectionNew = new ArrayList<Objeto>();
            for (Objeto objetoCollectionNewObjetoToAttach : objetoCollectionNew) {
                objetoCollectionNewObjetoToAttach = em.getReference(objetoCollectionNewObjetoToAttach.getClass(), objetoCollectionNewObjetoToAttach.getIdObjeto());
                attachedObjetoCollectionNew.add(objetoCollectionNewObjetoToAttach);
            }
            objetoCollectionNew = attachedObjetoCollectionNew;
            tipoObjeto.setObjetoCollection(objetoCollectionNew);
            tipoObjeto = em.merge(tipoObjeto);
            for (Objeto objetoCollectionOldObjeto : objetoCollectionOld) {
                if (!objetoCollectionNew.contains(objetoCollectionOldObjeto)) {
                    objetoCollectionOldObjeto.setIdTipoObjeto(null);
                    objetoCollectionOldObjeto = em.merge(objetoCollectionOldObjeto);
                }
            }
            for (Objeto objetoCollectionNewObjeto : objetoCollectionNew) {
                if (!objetoCollectionOld.contains(objetoCollectionNewObjeto)) {
                    TipoObjeto oldIdTipoObjetoOfObjetoCollectionNewObjeto = objetoCollectionNewObjeto.getIdTipoObjeto();
                    objetoCollectionNewObjeto.setIdTipoObjeto(tipoObjeto);
                    objetoCollectionNewObjeto = em.merge(objetoCollectionNewObjeto);
                    if (oldIdTipoObjetoOfObjetoCollectionNewObjeto != null && !oldIdTipoObjetoOfObjetoCollectionNewObjeto.equals(tipoObjeto)) {
                        oldIdTipoObjetoOfObjetoCollectionNewObjeto.getObjetoCollection().remove(objetoCollectionNewObjeto);
                        oldIdTipoObjetoOfObjetoCollectionNewObjeto = em.merge(oldIdTipoObjetoOfObjetoCollectionNewObjeto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoObjeto.getIdTipoObjeto();
                if (findTipoObjeto(id) == null) {
                    throw new NonexistentEntityException("The tipoObjeto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoObjeto tipoObjeto;
            try {
                tipoObjeto = em.getReference(TipoObjeto.class, id);
                tipoObjeto.getIdTipoObjeto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoObjeto with id " + id + " no longer exists.", enfe);
            }
            Collection<Objeto> objetoCollection = tipoObjeto.getObjetoCollection();
            for (Objeto objetoCollectionObjeto : objetoCollection) {
                objetoCollectionObjeto.setIdTipoObjeto(null);
                objetoCollectionObjeto = em.merge(objetoCollectionObjeto);
            }
            em.remove(tipoObjeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoObjeto> findTipoObjetoEntities() {
        return findTipoObjetoEntities(true, -1, -1);
    }

    public List<TipoObjeto> findTipoObjetoEntities(int maxResults, int firstResult) {
        return findTipoObjetoEntities(false, maxResults, firstResult);
    }

    private List<TipoObjeto> findTipoObjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoObjeto.class));
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

    public TipoObjeto findTipoObjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoObjeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoObjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoObjeto> rt = cq.from(TipoObjeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
