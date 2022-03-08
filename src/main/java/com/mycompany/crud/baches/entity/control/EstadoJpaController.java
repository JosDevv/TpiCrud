/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity.control;

import com.mycompany.crud.baches.entity.Estado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.crud.baches.entity.ObjetoEstado;
import com.mycompany.crud.baches.entity.control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EstadoJpaController implements Serializable {

    public EstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EstadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("bachesPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Estado estado) {
        if (estado.getObjetoEstadoCollection() == null) {
            estado.setObjetoEstadoCollection(new ArrayList<ObjetoEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ObjetoEstado> attachedObjetoEstadoCollection = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoCollectionObjetoEstadoToAttach : estado.getObjetoEstadoCollection()) {
                objetoEstadoCollectionObjetoEstadoToAttach = em.getReference(objetoEstadoCollectionObjetoEstadoToAttach.getClass(), objetoEstadoCollectionObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoCollection.add(objetoEstadoCollectionObjetoEstadoToAttach);
            }
            estado.setObjetoEstadoCollection(attachedObjetoEstadoCollection);
            em.persist(estado);
            for (ObjetoEstado objetoEstadoCollectionObjetoEstado : estado.getObjetoEstadoCollection()) {
                Estado oldIdEstadoOfObjetoEstadoCollectionObjetoEstado = objetoEstadoCollectionObjetoEstado.getIdEstado();
                objetoEstadoCollectionObjetoEstado.setIdEstado(estado);
                objetoEstadoCollectionObjetoEstado = em.merge(objetoEstadoCollectionObjetoEstado);
                if (oldIdEstadoOfObjetoEstadoCollectionObjetoEstado != null) {
                    oldIdEstadoOfObjetoEstadoCollectionObjetoEstado.getObjetoEstadoCollection().remove(objetoEstadoCollectionObjetoEstado);
                    oldIdEstadoOfObjetoEstadoCollectionObjetoEstado = em.merge(oldIdEstadoOfObjetoEstadoCollectionObjetoEstado);
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

    public void edit(Estado estado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado persistentEstado = em.find(Estado.class, estado.getIdEstado());
            Collection<ObjetoEstado> objetoEstadoCollectionOld = persistentEstado.getObjetoEstadoCollection();
            Collection<ObjetoEstado> objetoEstadoCollectionNew = estado.getObjetoEstadoCollection();
            Collection<ObjetoEstado> attachedObjetoEstadoCollectionNew = new ArrayList<ObjetoEstado>();
            for (ObjetoEstado objetoEstadoCollectionNewObjetoEstadoToAttach : objetoEstadoCollectionNew) {
                objetoEstadoCollectionNewObjetoEstadoToAttach = em.getReference(objetoEstadoCollectionNewObjetoEstadoToAttach.getClass(), objetoEstadoCollectionNewObjetoEstadoToAttach.getIdObjetoEstado());
                attachedObjetoEstadoCollectionNew.add(objetoEstadoCollectionNewObjetoEstadoToAttach);
            }
            objetoEstadoCollectionNew = attachedObjetoEstadoCollectionNew;
            estado.setObjetoEstadoCollection(objetoEstadoCollectionNew);
            estado = em.merge(estado);
            for (ObjetoEstado objetoEstadoCollectionOldObjetoEstado : objetoEstadoCollectionOld) {
                if (!objetoEstadoCollectionNew.contains(objetoEstadoCollectionOldObjetoEstado)) {
                    objetoEstadoCollectionOldObjetoEstado.setIdEstado(null);
                    objetoEstadoCollectionOldObjetoEstado = em.merge(objetoEstadoCollectionOldObjetoEstado);
                }
            }
            for (ObjetoEstado objetoEstadoCollectionNewObjetoEstado : objetoEstadoCollectionNew) {
                if (!objetoEstadoCollectionOld.contains(objetoEstadoCollectionNewObjetoEstado)) {
                    Estado oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado = objetoEstadoCollectionNewObjetoEstado.getIdEstado();
                    objetoEstadoCollectionNewObjetoEstado.setIdEstado(estado);
                    objetoEstadoCollectionNewObjetoEstado = em.merge(objetoEstadoCollectionNewObjetoEstado);
                    if (oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado != null && !oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado.equals(estado)) {
                        oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado.getObjetoEstadoCollection().remove(objetoEstadoCollectionNewObjetoEstado);
                        oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado = em.merge(oldIdEstadoOfObjetoEstadoCollectionNewObjetoEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estado.getIdEstado();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        
//                EntityManager em = null ;
//        
//        nuevo=em.find(Estado.class, id);
//        if(nuevo!=null){
//          
//            em.getTransaction().begin();
//            em.merge(nuevo);
//            em.getTransaction().commit();
//        }else{
//            System.out.println("no se encontro el estado ");
//        }
//        try {
//           
//            
//        } catch (Exception ex) {
//            
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
        
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            Collection<ObjetoEstado> objetoEstadoCollection = estado.getObjetoEstadoCollection();
            for (ObjetoEstado objetoEstadoCollectionObjetoEstado : objetoEstadoCollection) {
                objetoEstadoCollectionObjetoEstado.setIdEstado(null);
                objetoEstadoCollectionObjetoEstado = em.merge(objetoEstadoCollectionObjetoEstado);
            }
            em.remove(estado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
