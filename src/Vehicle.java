public class Vehicle extends Thread{
    private final Pont.Sentit sentit;
    private final Pont pont;
    private boolean creuant;

    public Vehicle(String name, Pont.Sentit sentit, Pont pont) {
        setName(name);
        this.sentit = sentit;
        this.pont = pont;
        creuant = false;
    }

    @Override
    public void run() {
        for(;;){
            System.out.println(getName()+" arriba al pont");
            pont.arribar(this);
            //Simulació de temps creuant el pont
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pont.sortir(this);

            //Simulació del vehicle allunyant-se del pont per deixar un marge de temps
            System.out.println(getName()+" s'allunya del pont");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isCreuant() {
        return creuant;
    }

    public void setCreuant(boolean creuant) {
        this.creuant = creuant;
    }

    public Pont.Sentit getSentit() {
        return sentit;
    }
}