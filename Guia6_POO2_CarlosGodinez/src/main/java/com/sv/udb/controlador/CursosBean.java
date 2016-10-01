package com.sv.udb.controlador;

import com.sv.udb.ejb.CursosFacadeLocal;
import com.sv.udb.modelo.Cursos;
import com.sv.udb.utils.LOG4J;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laboratorio
 */
@Named(value = "cursosBean")
@ViewScoped
public class CursosBean implements Serializable{

    @EJB
    private CursosFacadeLocal FCDECurs;    
    private Cursos objeCurs;
    private List<Cursos> listCurs;
    private boolean guardar;
    private LOG4J log;
    
    /**
     * Creates a new instance of CursosBean
     */
    public CursosBean() {
        
    }
    
    @PostConstruct
    public void init(){
        limpForm();
        consTodo();
        log = new LOG4J();
        log.debug("Se ha inicializado el modelo de Cursos");
    }
    
    public void limpForm(){
        this.objeCurs = new Cursos();
        this.guardar = true;
    }

    public Cursos getObjeCurs() {
        return objeCurs;
    }

    public void setObjeCurs(Cursos objeCurs) {
        this.objeCurs = objeCurs;
    }

    public List<Cursos> getListCurs() {
        return listCurs;
    }

    public void setListCurs(List<Cursos> listCurs) {
        this.listCurs = listCurs;
    }

    public boolean isGuardar() {
        return guardar;
    }

    public void setGuardar(boolean guardar) {
        this.guardar = guardar;
    }
    
    public void consTodo()
    {
        try
        {
            this.listCurs = FCDECurs.findAll();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    public void guar()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDECurs.create(this.objeCurs);
            this.listCurs.add(this.objeCurs);
            log.info("Curso agregado: " +objeCurs.getNombCurs());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos guardados')");
            this.limpForm();
        }
        catch(Exception ex)
        {
            log.error("Error al agregar Curso: " +  String.valueOf(ex.fillInStackTrace()));
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al guardar ')");
        }
        finally
        {
            
        }
    }
    
    public void modi()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            this.listCurs.remove(this.objeCurs); //Limpia el objeto viejo
            FCDECurs.edit(this.objeCurs);
            this.listCurs.add(this.objeCurs); //Agrega el objeto modificado
            log.info("Curso modificado: " +objeCurs.getNombCurs());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Modificados')");
        }
        catch(Exception ex)
        {
            log.error("Error al modificar Curso: " +  String.valueOf(ex.fillInStackTrace()));
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al modificar ')");
        }
        finally
        {
            
        }
    }
    
    public void elim()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        try
        {
            FCDECurs.remove(this.objeCurs);
            this.listCurs.remove(this.objeCurs);
            log.info("Curso eliminado: " +objeCurs.getNombCurs());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Datos Eliminados')");
        }
        catch(Exception ex)
        {
            log.error("Error al eliminado Curso: " +  String.valueOf(ex.fillInStackTrace()));
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al eliminar')");
        }
        finally
        {
            
        }
    }
    
    public void cons()
    {
        RequestContext ctx = RequestContext.getCurrentInstance(); //Capturo el contexto de la página
        int codi = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codiCursPara"));
        try
        {
            this.objeCurs = FCDECurs.find(codi);
            this.guardar = false;
            log.info("Curso consultado: " +objeCurs.getNombCurs());
            ctx.execute("setMessage('MESS_SUCC', 'Atención', 'Consultado a " + 
                    String.format("%s", this.objeCurs.getNombCurs()) + "')");
        }
        catch(Exception ex)
        {
            log.error("Error al consultar Curso: " +  String.valueOf(ex.fillInStackTrace()));
            ctx.execute("setMessage('MESS_ERRO', 'Atención', 'Error al consultar')");
        }
        finally
        {
            
        }
    }
}
