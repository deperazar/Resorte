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
    private int G;//MPa
    private int limfluencia;//MPa
    private int tys;//MPa
    private int esfult;//MPa
    private int costo; //$/Kg
    private int densidad; // Kg/m^3
    
    
    
    public Material(String nombre, int G, int limfluencia, int esfult, int costo,int densidad){
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
    
    public int getG(){
        return this.G;
    } 
    
    public int getlimfluencia(){
        return this.limfluencia;
    }
    
    public int getesfult(){
        return this.esfult;
    }
    
    public int getcosto(){
        return this.costo;
    }
    
    public double getdensidad(){
        return this.densidad;
    }
}
