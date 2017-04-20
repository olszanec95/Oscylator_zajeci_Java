import java.awt.*;

/**
 * Created by Notebook on 2017-04-04.
 */
public class SimEngine {
    private double masa,wspSprez,wspTlum, yMas,yZaw,przysGrawit;
    private int l0;//dlugosc swobodna sprezyny
    public Vector2D wektorZaw;
    public Vector2D wektorMasy;
    private double predkosc;
    private double a; //przysp
    private double zaokr;
    //sily
    private Vector2D grawitacja;
    private Vector2D tlumienie;
    private Vector2D sprezystosc;

    //akcesory potrzebne w programie
    public double get_yMas()
    {
        return yMas;
    }
    public double get_yZaw() {return yZaw;}
    public double get_predkosc() {return predkosc;}
    public double get_tlumienie(){return tlumienie.y;}
    public double get_grawitacja(){return grawitacja.y;}
    public double get_sprezystosc(){return sprezystosc.y;}



    SimEngine(double masa,double yMas,double K,double C, double przys,double yZaw, int l0 )
    {
        this.masa=masa;
        this.yMas=yMas;
        this.wspSprez=K;
        this.wspTlum=C;
        this.yZaw=yZaw;
        this.przysGrawit=przys;
        this.l0=l0;
        this.wektorMasy = new Vector2D(400,yMas);
        this.wektorZaw = new Vector2D(400,yZaw);
        this.predkosc=0;
        this.a=0;
        grawitacja = new Vector2D(400,0);
        tlumienie = new Vector2D(400,0);
        sprezystosc = new Vector2D(400,0);
    }
    public void simulation(double t)
    {
        grawitacja.y =masa*przysGrawit;
        tlumienie.y =-predkosc*wspTlum;
        sprezystosc.y = -wspSprez*(wektorMasy.y-wektorZaw.y-l0);
       //obliczanie parmetrow rownania
        a=(grawitacja.y + tlumienie.y + sprezystosc.y)/masa;
        predkosc=predkosc + a*t;
        zaokr=predkosc*t; // dla tak malego t zakladamy ze jest to ruch jednostajny prostoliniowy
        wektorMasy.y=wektorMasy.y+zaokr;
        yMas=wektorMasy.y;

    }
    public void resetSim()
    {
        predkosc = 0;
        a=0;
    }


}

