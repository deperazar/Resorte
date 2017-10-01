/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resorte;

/**
 *
 * @author Usuario
 */
public class Material {
    static final double densidad1=7850;
    static final double densidad2=2810;
    
    private String nombre;
    private double G;//MPa
    private double limfluencia;//MPa
    private double tys;//MPa
    private double esfult;//MPa
    private double costo; //$/Kg
    private double densidad; // Kg/m^3
    
    
    
    public Material(String nombre, double G, double limfluencia, double esfult, double costo,double densidad){
        this.nombre=nombre;
        this.G=G;
        this.limfluencia=limfluencia;
        this.esfult=esfult;
        this.costo=costo;
        this.densidad=densidad;
        this.tys=this.limfluencia/2;
    }
    
    public double gettys(){
        return this.tys;
    } 
    
    public String getnombre(){
        return this.nombre;
    } 
    
    public double getG(){
        return this.G;
    } 
    
    public double getlimfluencia(){
        return this.limfluencia;
    }
    
    public double getesfult(){
        return this.esfult;
    }
    
    public double getcosto(){
        return this.costo;
    }
    
    public double getdensidad(){
        return this.densidad;
    }
}
