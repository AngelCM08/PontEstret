import java.util.Random;

public class Pont {
    public static final int MAX_CAPACITAT = 3;
    public static final int MAX_FINS_CANVI_SENTIT = 5;
    public enum Sentit{
        DRETA,ESQUERRA
    }
    private Sentit sentit;
    private int capacitat;
    private int vehicles_han_travesat;
    private boolean stop;

    public Pont() {
        this.sentit = sentitInicial();
        capacitat = 0;
        vehicles_han_travesat = 0;
    }

    public synchronized void arribar(Vehicle vehicle) {
        while(true) {
            if(stop){
                try {
                    System.out.println("Esperant a que els vehicles buidin el pont...");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                if(capacitat == 0){
                    this.sentit = vehicle.getSentit();
                    System.out.println("*************************+");
                    System.out.println(this.sentit);
                    System.out.println("*************************+");
                    vehicles_han_travesat = 1;
                    System.out.println(vehicle.getName()+" travesa el pont");
                    System.out.println("Vehícles que han travessant el pont en la mateixa direcció: "+vehicles_han_travesat);
                    capacitat++;
                    notifyAll();
                    break;
                } else if (vehicle.getSentit().equals(this.sentit) && capacitat < MAX_CAPACITAT) {
                    vehicles_han_travesat++;
                    System.out.println(vehicle.getName()+" travesa el pont");
                    System.out.println("Vehícles que han travessant el pont en la mateixa direcció: "+vehicles_han_travesat);
                    capacitat++;
                    break;
                } else {
                    try {
                        System.out.println(vehicle.getName() + " esperant...");
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public synchronized void sortir(String name) {
        if(vehicles_han_travesat == MAX_FINS_CANVI_SENTIT){
            stop = true;
            vehicles_han_travesat = 0;
            if(sentit.equals(Sentit.DRETA)) sentit = Sentit.ESQUERRA;
            else sentit = Sentit.DRETA;
            System.out.println("\n***** CANVI DE SENTIT *****");
        }else{
            if(stop && capacitat == 1){
                capacitat--;
                stop = false;
            }else if(capacitat == 1){
                capacitat--;
                sentit = null;
            }else{
                capacitat--;
            }
            System.out.println("Surt "+name);
        }
        notifyAll();
    }

    public static Sentit sentitInicial(){
        Random rd = new Random();
        if(rd.nextInt(2) == 0) return Sentit.DRETA;
        else return Sentit.ESQUERRA;
    }
}
