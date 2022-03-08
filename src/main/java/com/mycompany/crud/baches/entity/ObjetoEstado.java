/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crud.baches.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author josem
 */
@Entity
@Table(name = "objeto_estado")
@NamedQueries({
    @NamedQuery(name = "ObjetoEstado.findAll", query = "SELECT o FROM ObjetoEstado o"),
    @NamedQuery(name = "ObjetoEstado.findByIdObjetoEstado", query = "SELECT o FROM ObjetoEstado o WHERE o.idObjetoEstado = :idObjetoEstado"),
    @NamedQuery(name = "ObjetoEstado.findByActual", query = "SELECT o FROM ObjetoEstado o WHERE o.actual = :actual"),
    @NamedQuery(name = "ObjetoEstado.findByFechaAlcanzado", query = "SELECT o FROM ObjetoEstado o WHERE o.fechaAlcanzado = :fechaAlcanzado"),
    @NamedQuery(name = "ObjetoEstado.findByObservaciones", query = "SELECT o FROM ObjetoEstado o WHERE o.observaciones = :observaciones")})
public class ObjetoEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_objeto_estado")
    private Long idObjetoEstado;
    @Column(name = "actual")
    private Boolean actual;
    @Column(name = "fecha_alcanzado")
    @Temporal(TemporalType.DATE)
    private Date fechaAlcanzado;
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "id_objeto", referencedColumnName = "id_objeto")
    @ManyToOne
    private Objeto idObjeto;

    public ObjetoEstado() {
    }

    public ObjetoEstado(Long idObjetoEstado) {
        this.idObjetoEstado = idObjetoEstado;
    }

    public Long getIdObjetoEstado() {
        return idObjetoEstado;
    }

    public void setIdObjetoEstado(Long idObjetoEstado) {
        this.idObjetoEstado = idObjetoEstado;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public Date getFechaAlcanzado() {
        return fechaAlcanzado;
    }

    public void setFechaAlcanzado(Date fechaAlcanzado) {
        this.fechaAlcanzado = fechaAlcanzado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Objeto getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Objeto idObjeto) {
        this.idObjeto = idObjeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObjetoEstado != null ? idObjetoEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetoEstado)) {
            return false;
        }
        ObjetoEstado other = (ObjetoEstado) object;
        if ((this.idObjetoEstado == null && other.idObjetoEstado != null) || (this.idObjetoEstado != null && !this.idObjetoEstado.equals(other.idObjetoEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.crud.baches.entity.ObjetoEstado[ idObjetoEstado=" + idObjetoEstado + " ]";
    }
    
}
