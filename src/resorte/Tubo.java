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
public class Tubo {
    private int Id; //numero del tubo
    private int largo;//mm
    private int grosor;//mm
    private int diametro;//mm
    private double volumen;//m^3
    private Material material;
    private double ecmax;//MPa
    private double precio;//$
    private double kt;//pendiente para cada tubo
    private double giro;//rad
    
    
    public Tubo(int Id, int largo, int grosor,int diametro){
        
        this.Id=Id;
        this.precio=0;
        switch (this.Id) {
            case 1:
                
                this.giro=19*(Math.PI)/180;
                this.kt=45/(4*((Math.PI)/180));
                break;
            case 2:
                this.giro=15*(Math.PI)/180;
                this.kt=(255/(8*(Math.PI)/180))-(45/(4*((Math.PI)/180)));
                break;
            default:
                this.giro=7*(Math.PI)/180;
                this.kt=(400/((7*Math.PI)/180))-(255/(8*(Math.PI)/180));
                break;
        }
        
        this.diametro=diametro;
        this.grosor=grosor;
        this.largo=largo;
        this.volumen=Math.PI*this.largo*(this.grosor*this.diametro-(this.grosor)*(this.grosor))*Math.pow(10, -9);
    }
    
    public boolean checkkt(){
        double a=0.001*(Math.PI/32)*this.getmaterial().getG()*(Math.pow(this.diametro, 4)-Math.pow(this.diametro-2*this.grosor, 4))/(this.largo);//Nm
        
        //System.out.println("G: "+this.getmaterial().getG());

    //redondear o aproximar a, en un rango de +-1
        
        if(a<=this.kt+1 && this.kt-1<=a){
            return true;
        }else{
            return false;
        }
        
    }
    
    
    public double getkt(){
        return this.kt;
    }
    
    public double getprecio(){
        return this.precio;
    }
    public void setgiro(double giro){//parametro giro en grados
        this.giro=giro*(Math.PI)/180;
    }
    
    public double getgiro(){
        return this.giro;
    }
    
    public void setecmax(){
        this.ecmax= this.material.getG()*this.diametro*this.giro/(this.largo*2);
    }
    
    public double getecmax(){
        return this.ecmax;
    }
    
    public void setmaterial(Material material){
        this.material=material;
    }
    
    public void setlargo(int largo){
        this.largo=largo;
    }
    public int getlargo(){
        return this.largo;
    }
    
    public void setgrosor(int grosor){
        this.grosor=grosor;
    }
    
    public int getgrosor(){
        return this.grosor;
    }
    
    public void setdiametro(int diametro){
        this.diametro=diametro;
    }
    
    public int getdiametro(){
        return this.diametro;
    }
    
    public void setvolumen(){
        this.volumen=Math.PI*this.largo*(this.grosor*this.diametro-(this.grosor)*(this.grosor))*Math.pow(10, -9);
    }
    
    public double getvolumen(){
        return this.volumen;
    }
    
    public void setprecio(){
        this.precio=this.volumen*this.material.getdensidad()*this.material.getcosto();
    }
    
    public boolean checkt(){//revisar que no falle
        //LOS OTROS MATERIALES NO SATISFACEN SIMULTÁNEAMENTE EL COMPORTAMIENTO T VS ANG, Y FS
        
        double a=(1.1*this.getmaterial().getG()*this.giro/2)*this.diametro/(this.largo);
        if(a<=this.getmaterial().gettys()){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public String getmaterialnm(){
        return this.material.getnombre();
    }
    
    public Material getmaterial(){
        return this.material;
    }
    
    public double gettreq(){//esfuerzo requisito
        return (1.1*this.material.getG()*this.diametro*this.giro/(this.largo*2));
    }
}
