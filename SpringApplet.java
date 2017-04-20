
/**
 * Created by Notebook on 2017-04-04.
 */
import java.awt.Graphics;
import java.util.Timer;
import javax.swing.JApplet;
import java.awt.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;



public class SpringApplet extends JApplet{
    private static final long serialVersionUID=1L;

    Graphics bufferGraphics;//Grafika do rysowania poza ekranem nie miga
    Image offscreen;//wirtualny obraz

    private SimEngine sEng;
    private SimTask sTask;
    private Timer tim;

    @Override
    public void init(){
        this.setSize(800,700);
        setBackground(Color.LIGHT_GRAY);
        offscreen=createImage(800,700);
        bufferGraphics=offscreen.getGraphics();//rysujemy na offscreennie
        // parametry         M            yM      K      C         g           yZ         l0
        sEng = new SimEngine(4.5,100 , 0.5,0.1, 9.81, 50,100);
        sTask= new SimTask(sEng,this,0.01);
        tim = new Timer();
        tim.scheduleAtFixedRate(sTask,1000,1);

        
        
    }

    public void paint(Graphics g)
    {
        // rysowanie tla
        bufferGraphics.setColor(Color.LIGHT_GRAY);
        bufferGraphics.fillRect(0,0,getSize().width,getSize().height);
        //rysowanie siatki
        bufferGraphics.setColor(Color.WHITE);
        for (int i =0;i<getSize().width;i+=10)
        {
            bufferGraphics.drawLine(i,0,i,getSize().height);
        }
        for (int i =0;i<getSize().width;i+=10)
        {
            bufferGraphics.drawLine(0,i,getSize().width ,i);
        }
        //rysowanie zawieszenia
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawLine(0,(int)sEng.get_yZaw(),getSize().width,(int)sEng.get_yZaw());
        for(int i =0;i<getSize().width;i+=20)
        {
            bufferGraphics.drawLine(i,(int)sEng.get_yZaw(),i+30,(int)sEng.get_yZaw()-20);
        }


        //rysowanie masy
        int promien =20;
        bufferGraphics.setColor(Color.GREEN);
        bufferGraphics.fillOval(getSize().width/2-promien,(int)sEng.get_yMas()-promien,promien*2,promien*2);


        //rysowanie sprezyny

        bufferGraphics.setColor(Color.BLACK);
        int l=70; //dlugosc pojedynczego zlacza sprezyny
        int ilosc=(int)(getSize().height-2*promien-20-sEng.get_yZaw())/l;//ilosc zlaczy w zaleznosci od dlugosci apletu
        double A=(sEng.get_yMas()-promien-sEng.get_yZaw()-20);//dlugosc robocza sprezyny
        double y =A/ilosc;//podzial dlugosci na ilosc zlaczy o ile kazdy w pionie sie przesunie
        double x0 = sqrt((pow(l/2,2)-pow(y/2,2)));// wzory na obliczenia przesuniecia w "x" (pitagoras)
        double x = sqrt((pow(l,2)-pow(y,2)));
        double x1=getSize().width/2;//pozycje poczatkowe i koncowe
        double y1=sEng.get_yZaw()+10;
        double x2=x1+x0;
        double y2=y1+(y/2);

        for(int i =0; i<=ilosc;i++)
        {
            if(i==0)
            {
                bufferGraphics.drawLine(getSize().width/2,(int)sEng.get_yZaw(),(int)x1,(int)y1);
                bufferGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
            }
            else if(i==ilosc)
            {
                bufferGraphics.drawLine((int)x1,(int)y1,getSize().width/2,(int)(sEng.get_yMas()-promien-10));
                bufferGraphics.drawLine(getSize().width/2,(int)(sEng.get_yMas()-promien-10),
                           getSize().width/2,(int)(sEng.get_yMas()-promien));

            }
            else
            {
                bufferGraphics.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
            }
            x1=x2;
            y1=y2;
            x=x*(-1);
            x2=x1+x;
            y2=y1+y;

        }

        //rysowanie wektorow
        //grawitacja
        //wektor grawitacji nie zwiekszany 2 krotnie
        bufferGraphics.setColor(Color.RED);
        bufferGraphics.fillRect(10,130,20,20);
        bufferGraphics.drawString("Grawitacja",40,150);
        bufferGraphics.drawLine(getSize().width/2,(int)sEng.get_yMas(),
                getSize().width/2,(int)sEng.get_yMas()+2*(int)sEng.get_grawitacja());
        //rysowanie grotow
        int grot;
        grot=(int)sEng.get_yMas()+2*(int)sEng.get_grawitacja();
        bufferGraphics.drawLine(getSize().width/2,grot,getSize().width/2-4 ,grot-7);
        bufferGraphics.drawLine(getSize().width/2,grot,getSize().width/2+4 ,grot-7);

        // sprezystosc
        //wektor grawitacji nie zwiekszany 2 krotnie
        bufferGraphics.setColor(Color.BLUE);
        bufferGraphics.fillRect(10,160,20,20);
        bufferGraphics.drawLine(getSize().width/2-2,(int)sEng.get_yMas(),
                getSize().width/2-2,(int)sEng.get_yMas()+2*(int)sEng.get_sprezystosc());
        bufferGraphics.drawString("Sprezystosc",40,180);

        //rysowanie grotow
        grot=(int)sEng.get_yMas()+2*(int)sEng.get_sprezystosc();
        if(grot>(int)sEng.get_yMas())
        {
            bufferGraphics.drawLine(getSize().width/2-2,grot,getSize().width/2-6 ,grot-7);
            bufferGraphics.drawLine(getSize().width/2-2,grot,getSize().width/2+2 ,grot-7);
        }
        else
        {
            bufferGraphics.drawLine(getSize().width/2-2,grot,getSize().width/2-6 ,grot+7);
            bufferGraphics.drawLine(getSize().width/2-2,grot,getSize().width/2+2 ,grot+7);
        }

        //tlumienie
        //wektor tlumienie zwiekszany 10 krotnie
        bufferGraphics.setColor(Color.YELLOW);
        bufferGraphics.fillRect(10,100,20,20);
        bufferGraphics.drawString("Tlumienie",40,120);
        bufferGraphics.drawLine(getSize().width/2+2,(int)sEng.get_yMas(),
                                getSize().width/2+2,(int)sEng.get_yMas()+10*(int)sEng.get_tlumienie());
        //rysowanie grotow
         grot=(int)sEng.get_yMas()+10*(int)sEng.get_tlumienie();
        if(sEng.get_predkosc()<0)
        {
            bufferGraphics.drawLine(getSize().width/2+2,grot,getSize().width/2-2 ,grot-7);
            bufferGraphics.drawLine(getSize().width/2+2,grot,getSize().width/2+6 ,grot-7);
        }
        else
        {
            bufferGraphics.drawLine(getSize().width/2+2,grot,getSize().width/2-2 ,grot+7);
            bufferGraphics.drawLine(getSize().width/2+2,grot,getSize().width/2+6 ,grot+7);
        }
        g.drawImage(offscreen,0,0,this);
    }






}
