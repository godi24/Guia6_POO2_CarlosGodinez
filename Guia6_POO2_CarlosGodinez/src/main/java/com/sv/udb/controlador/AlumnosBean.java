/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controlador;

import com.sv.udb.ejb.AlumnosFacadeLocal;
import com.sv.udb.modelo.Alumnos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Carlos
 */
@Named(value = "alumnosBean")
@ViewScoped
@ManagedBean
public class AlumnosBean implements Serializable{

    @EJB
    private AlumnosFacadeLocal FCDEAlum;
    
    private Alumnos objeAlum;
    private boolean guardar;
    private List<Alumnos> alumList = null;

    public Alumnos getObjeAlum() {
        return objeAlum;
    }

    public void setObjeAlum(Alumnos objeAlum) {
        this.objeAlum = objeAlum;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public List<Alumnos> getAlumList() {
        return alumList;
    }

    public void setAlumList(List<Alumnos> alumList) {
        this.alumList = alumList;
    }
    
    /**
     * Creates a new instance of AlumnosBean
     */
    
    public AlumnosBean() {
    }
    
    @PostConstruct
    public void init()
    {
        this.objeAlum = new Alumnos();
        this.guardar = true;
        this.ConsTodo();
    }
    
    public void ConsTodo() {
        
        try 
        {
            this.alumList = FCDEAlum.findAll();
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturar el contexto
        try
        {
            FCDEAlum.create(this.objeAlum);
            this.objeAlum = new Alumnos();
            this.ConsTodo();
            ctx.execute("setMessage('MESS_SUCC', 'Alerta', 'Datos guardados con éxito.');");
        }
        catch(Exception ex)
        {
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error.');");
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturar el contexto
        
        try
        {
            FCDEAlum.edit(this.objeAlum);
            this.objeAlum = new Alumnos();
            this.ConsTodo();
            ctx.execute("setMessage('MESS_SUCC', 'Alerta', 'Registro modificado con éxito.');");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void elim(int codi)
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturar el contexto
        try
        {
            FCDEAlum.remove(this.objeAlum);
            this.objeAlum = new Alumnos();
            this.ConsTodo();
            ctx.execute("setMessage('MESS_SUCC', 'Alerta', 'Registro eliminado con éxito.');");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void cons(int codi)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PARCIALPU");
        EntityManager em = emf.createEntityManager();
        try
        {
            this.objeAlum = em.find(Alumnos.class, codi);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            em.close();
            emf.close();            
        }
    }
}
