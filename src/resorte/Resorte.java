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
public class Resorte {

    public static double pre(double costo,int D, int g, int L,int den){
        
        return (costo*den*Math.PI*L*(g*D-g*g)*Math.pow(10, -9));
    }
    
    public static double esf(double G,int D, double FS, int L,double gir){
        
        return (FS*G*D*Math.PI*gir/180)/(2*L);
    }
    
    public static double ktf(double G,int D, int g, int L){
        return (0.001*(Math.PI/32)*G*(Math.pow(D, 4)-Math.pow(D-2*g, 4))/(L));
    }
    
    
    public static void main(String[] args) {
        Material [] m= new Material [3];
        m[0]= new Material ("ASTM A913", 77000, 460, 550, 8000, 7850);//nombre,G,lim fluenc,esf ult,precio/kg, densidad kg/m^3
        m[1]= new Material ("AISI 302", 75000, 530, 860, 15000, 7850);
        m[2]= new Material ("7075 T6", 28000, 510, 570, 24000,2810);
        
        Tubo t1=new Tubo(1,500,3,20);//material/largo en mm/grosor/diámetro
        Tubo t2=new Tubo(2,500,3,20);//material/largo en mm/grosor/diámetro
        Tubo t3=new Tubo(3,500,3,20);//material/largo en mm/grosor/diámetro
        
        t3.setmaterial(m[0]);
        t3.setecmax();
        
        t3.setprecio();
        
        double price =999999;
        int mat=0;
        int Lar=0;
        int Dia=0;
        int gro=0;
        double vol=0;
        
        /*RESTRICCIONES DE LOS DIAMETROS Y LOS GROSORES
            PARA T3:
                D2+2*g3<=D3<=60mm
            PARA T2:
                D1+2*g2<=D2<=D3-2*g3
            PARA T1:
                2*g1<=D1<=D2-2*g2
        
                g1>=2mm
        
        */
        
        
        
        for (int i = 0; i < 3; i++) {
            t1.setmaterial(m[i]);
            
            for (int L = 1; L < 756; L++) {
                t1.setlargo(L);
                for (int D = 4; D < 53; D++) {
                    t1.setdiametro(D); 
                    for (int g = 2; g <= D/2; g++) {
                        t1.setgrosor(g);
                        t1.setvolumen();
                        if(t1.checkkt()){//Cumple con el KT__________BIEN
                            if (t1.checkt()) {//Cumple factor de seguridad?_____PROBLEMA
                                t1.setprecio();   
                                if (t1.getprecio()<price) {//Se selecciona el precio menor
                                    
                                    t1.setvolumen(); 
                                    price=t1.getprecio();
                                    mat=i;
                                    Lar=L; 
                                    Dia=D;
                                    gro=g;
                                    vol=t1.getvolumen();
                                   
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        
        t1.setdiametro(Dia);
        t1.setgrosor(gro);
        t1.setlargo(Lar);
        t1.setmaterial(m[mat]);
        
        System.out.println("precio min t1: "+price);
        System.out.println("kt: "+t1.getkt());
        System.out.println("Material t1: "+m[mat].getnombre());
        System.out.println("G t1: "+m[mat].getG());
        System.out.println("L t1: "+Lar);
        System.out.println("D t1: "+Dia);
        System.out.println("gr t1: "+gro);
        System.out.println("Vol t1: "+vol);
        System.out.println("giro: "+t1.getgiro()*180/Math.PI);
        System.out.println("tys: "+t1.getmaterial().gettys());
        System.out.println("cortante: "+t1.gettreq()/1.1);
        System.out.println("cortante*fs: "+t1.gettreq());
        System.out.println("       ");
        
        
        
        //esf TESTEO double G,int D, double FS, int L,double gir
        /*
            (FS*G*D*(Math.PI*gir/180)/(2*L);
           
        
        */
        
        
        
        
        //tomando el primer material
        /*
        System.out.println(" ");
        System.out.println("PRECIO COMP= "+pre(8000,4,2,29,2810));
        

        
        System.out.println("ESF COMP= "+2*esf(28000,4,1.1,29,7));
        
        System.out.println("ESF= 510");
        
      
        System.out.println("KT3: "+t3.getkt());
        */
        
        
        price=99999999;
        
        for (int i = 0; i < 3; i++) {
            t2.setmaterial(m[i]);
            for (int L = 1; L < 756; L++) {
                t2.setlargo(L);
                for (int D = t1.getdiametro()+4 ; D < 56; D++) {
                    t2.setdiametro(D); 
                    for (int g = 2; g <= (int)(D-t1.getdiametro())/2; g++) {
                        t2.setgrosor(g);
                        t2.setvolumen();
                        if(t2.checkkt()){//Cumple con el KT
                            if (t2.checkt()) {//Cumple factor de seguridad
                                t2.setprecio();
                                if (t2.getprecio()<price) {//Se selecciona el precio menor
                                    t2.setvolumen(); 
                                    price=t2.getprecio();
                                    mat=i;
                                    Lar=L;
                                    Dia=D;
                                    gro=g;
                                    vol=t2.getvolumen();
                                    //System.out.println("hello");
                                    //System.out.println("price:"+price);
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        
        t2.setdiametro(Dia);
        t2.setgrosor(gro);
        t2.setlargo(Lar);
        t2.setmaterial(m[mat]);
        
        
        System.out.println("precio min t2: "+price);
        System.out.println("Material t2: "+m[mat].getnombre());
        System.out.println("G t2: "+m[mat].getG());
        System.out.println("L t2: "+Lar);
        System.out.println("D t2: "+Dia);
        System.out.println("gr t2: "+gro);
        System.out.println("Vol t2: "+vol);
        System.out.println("giro: "+t2.getgiro()*180/Math.PI);
        System.out.println("tys: "+t2.getmaterial().gettys());
        System.out.println("cortante: "+t2.gettreq()/1.1);
        System.out.println("cortante*fs: "+t2.gettreq());
        
        price=9999999;
        
        
        //TUBO 3
        
        for (int i = 0; i < 3; i++) {
            t3.setmaterial(m[i]);
            for (int L = 1; L < 756; L++) {
                t3.setlargo(L);
                for (int D = t2.getdiametro()+4; D < 61; D++) {
                    t3.setdiametro(D); 
                    for (int g = 2; g <=(int) (D-t2.getdiametro())/2; g++) {
                        t3.setgrosor(g);
                        
                        t3.setvolumen();
                        if(t3.checkkt()){//Cumple con el KT
                            if (t3.checkt()) {//Cumple factor de seguridad
                                t3.setprecio();
                                if (t3.getprecio()<price) {//Se selecciona el precio menor
                                    t3.setvolumen(); 
                                    price=t3.getprecio();
                                    mat=i;
                                    Lar=L;
                                    Dia=D;
                                    gro=g;
                                    vol=t3.getvolumen();
                                    
                                }
                            }
                            
                        }
                        
                    }
                }
            }
        }
        
        t3.setdiametro(Dia);
        t3.setgrosor(gro);
        t3.setlargo(Lar);
        t3.setmaterial(m[mat]);
        System.out.println("     ");
        System.out.println("precio min t3: "+price);
        System.out.println("Material t3: "+m[mat].getnombre());
        System.out.println("G t3: "+m[mat].getG());
        System.out.println("L t3: "+Lar);
        System.out.println("D t3: "+Dia);
        System.out.println("gr t3: "+gro);
        System.out.println("Vol t3: "+vol);
        System.out.println("giro: "+t3.getgiro()*180/Math.PI);
        System.out.println("tys: "+t3.getmaterial().gettys());
        System.out.println("cortante: "+t3.gettreq()/1.1);
        System.out.println("cortante*fs: "+t3.gettreq());
        
        System.out.println("PRECIO MINIMO: "+(t1.getprecio()+t2.getprecio()+t3.getprecio()));
        
        
                
    }
    
}
