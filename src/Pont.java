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

    public Pont() {
        this.sentit = sentitInicial();
        capacitat = 0;
        vehicles_han_travesat = 0;
    }

    public synchronized void arribar(Vehicle vehicle) {
        while(true) {
            if(capacitat == 0){
                this.sentit = vehicle.getSentit();
                System.out.println("*************************+");
                System.out.println(this.sentit);
                System.out.println("*************************+");
                vehicles_han_travesat = 0;
                capacitat++;
                notifyAll();
                break;
            } else if (vehicle.getSentit().equals(this.sentit) && capacitat < MAX_CAPACITAT) {
                vehicles_han_travesat++;
                System.out.println(vehicles_han_travesat);
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

    public synchronized void sortir(String name) {
        if(vehicles_han_travesat == MAX_FINS_CANVI_SENTIT){
            vehicles_han_travesat = 0;
            capacitat = 0;
            if(sentit.equals(Sentit.DRETA)) sentit = Sentit.ESQUERRA;
            else sentit = Sentit.DRETA;
            System.out.println("***** CANVI DE SENTIT *****");
            //Simulació espera a que els vehicles que hi ha creuant el pont surtin d'aquest per evitar colisions
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }else{
            if(capacitat == 1){
                capacitat--;
                sentit = null;
                notifyAll();
            }else{
                capacitat--;
            }
            System.out.println("Surt "+name);
        }
    }

    public static Sentit sentitInicial(){
        Random rd = new Random();
        if(rd.nextInt(2) == 0) return Sentit.DRETA;
        else return Sentit.ESQUERRA;
    }
}
