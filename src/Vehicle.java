public class Vehicle extends Thread{
    private Pont.Sentit sentit;
    private Pont pont;

    public Vehicle(String name, Pont.Sentit sentit, Pont pont) {
        setName(name);
        this.sentit = sentit;
        this.pont = pont;
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
            pont.sortir(getName());

            //Simulació del vehicle allunyant-se del pont per deixar un marge de temps
            System.out.println(getName()+" s'allunya del pont");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Pont.Sentit getSentit() {
        return sentit;
    }
}