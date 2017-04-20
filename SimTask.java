import java.util.TimerTask;

/**
 * Created by Notebook on 2017-04-04.
 */
public class SimTask extends TimerTask {
    private SimEngine silnik;
    private SpringApplet aplet;
    private double odstCzas;

    SimTask(SimEngine E,SpringApplet S, double time)
    {
        silnik=E;
        aplet=S;
        odstCzas=time;
    }
    @Override
    public void run() {
        silnik.simulation(odstCzas);//uruchomienie obliczenia kolejnego kroku
        aplet.repaint(); // namalowanie kroku silnika
    }

}
