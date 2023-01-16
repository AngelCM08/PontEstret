import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Pont pont = new Pont();

        Vehicle[] vehicles = new Vehicle[10];

        for (int i = 0, j = 1; i < 10; i=i+2, j++) {
            vehicles[i] = new Vehicle("VehicleDret"+j, Pont.Sentit.DRETA, pont);
            vehicles[i+1] = new Vehicle("VehicleEsq"+j, Pont.Sentit.ESQUERRA, pont);
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.start();
        }
    }
}