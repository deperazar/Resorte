package resorte;

/**
 *
 * @author Usuario
 */
public class Resorte {

    public static double pre(double costo,int D, int g, int L,int den){//precio
        
        return (costo*den*Math.PI*L*(g*D-g*g)*Math.pow(10, -9));
    }
    
    public static double esf(double G,int D, double FS, int L,double gir){//esfuerzo no puede superar al tys
        
        return (FS*G*D*Math.PI*gir/180)/(2*L);
    }
    
    public static double ktf(double G,int D, int g, int L){//Relacion T vs ang
        return (0.001*(Math.PI/32)*G*(Math.pow(D, 4)-Math.pow(D-2*g, 4))/(L));
    }
    
    
    
    public static void main(String[] args) {
        Material [] m= new Material [3];
        m[0]= new Material ("ASTM A913", 77000, 460, 550, 8000, 7850);//nombre,G,lim fluenc,esf ult,precio/kg, densidad kg/m^3
        m[1]= new Material ("AISI 302", 75000, 530, 860, 15000, 7850);
        m[2]= new Material ("7075 T6", 28000, 510, 570, 24000, 2810);
        
        Tubo t1=new Tubo(1,500,3,20);//material/largo en mm/grosor/diámetro
        Tubo t2=new Tubo(2,500,3,20);//material/largo en mm/grosor/diámetro
        Tubo t3=new Tubo(3,500,3,20);//material/largo en mm/grosor/diámetro
        
       
        
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
                for (int D = 4; D < 113; D++) {
                    t1.setdiametro(D); 
                    for (int g = 2; g <= D/2; g++) {
                        t1.setgrosor(g);
                        t1.setvolumen();
                        if(t1.check_kt()){//Cumple con el KT?
                            
                            if (t1.check_ecmax()) {//Cumple factor de seguridad?
                                
                                t1.setprecio();   
                                if (t1.getprecio()<price) {//Se selecciona el precio menor
                                    
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
        t1.setfs();

        mat=0;
        Lar=0;
        Dia=0;
        gro=0;
        vol=0;        
        price=99999999;
        
        for (int i = 0; i < 3; i++) {
            t2.setmaterial(m[i]);
            for (int L = 1; L < 756; L++) {
                t2.setlargo(L);
                for (int D = t1.getdiametro()+4 ; D < 117; D++) {
                    t2.setdiametro(D); 
                    for (int g = 2; g <= (int)(D-t1.getdiametro())/2; g++) {
                        t2.setgrosor(g);
                        t2.setvolumen();
                        
                        if(t2.check_kt()){//Cumple con el KT?
                            
                            
                            if (t2.check_ecmax()) {//Cumple factor de seguridad?
                                
                                t2.setprecio();
                                if (t2.getprecio()<price) {//Se selecciona el precio menor
                                    t2.setvolumen(); 
                                    price=t2.getprecio();
                                    mat=i;
                                    Lar=L;
                                    Dia=D;
                                    gro=g;
                                    vol=t2.getvolumen();
                                    
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
        t2.setfs();
        price=9999999;
        
        
        //TUBO 3
        
        for (int i = 0; i < 3; i++) {
            t3.setmaterial(m[i]);
            for (int L = 1; L < 756; L++) {
                t3.setlargo(L);
                for (int D = t2.getdiametro()+4; D < 121; D++) {
                    t3.setdiametro(D); 
                    for (int g = 2; g <=(int) (D-t2.getdiametro())/2; g++) {
                        t3.setgrosor(g);
                        t3.setvolumen();
                        if(t3.check_kt()){//Cumple con el KT?
                            
                            
                            if (t3.check_ecmax()) {//Cumple factor de seguridad?
                                
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
        t3.setfs();
        
        
        System.out.println("Tubo"+"\t"+"Material"+"\t"+"      Costo ($)"+"\t"+"            Fs"+"\t"+"              L (mm)"+"\t"+"R (mm)"+"\t"+"grosor(mm)"+"\t");
        System.out.println("1"+"\t"+t1.getmaterialnm()+" \t "+t1.getprecio()+"\t"+t1.getfs()+"\t"+t1.getlargo()+"\t"+t1.getdiametro()/2+"\t"+t1.getgrosor()+"\t");
        System.out.println("2"+"\t"+t2.getmaterialnm()+" \t "+t2.getprecio()+"\t"+t2.getfs()+"\t"+t2.getlargo()+"\t"+t2.getdiametro()/2+"\t"+t2.getgrosor()+"\t");
        System.out.println("3"+"\t"+t3.getmaterialnm()+" \t "+t3.getprecio()+"\t"+t3.getfs()+"\t"+t3.getlargo()+"\t"+t3.getdiametro()/2+"\t"+t3.getgrosor()+"\t");
        System.out.println("PRECIO MINIMO: "+(t1.getprecio()+t2.getprecio()+t3.getprecio()));

                
    }
    
}
