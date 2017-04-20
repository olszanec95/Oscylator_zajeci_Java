
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Notebook on 2017-03-30.
 */
public class Vector2D {


    public double x,y;

    public Vector2D addVector(Vector2D parametr )
    {
        return new Vector2D((this.x+parametr.x),(this.y+parametr.y));

    }
    public Vector2D substractVector(Vector2D parametr )
    {
        return new Vector2D((this.x-parametr.x),(this.y-parametr.y));

    }
    public Vector2D multiplyVector(double stala )
    {
        return new Vector2D((stala*this.x),(stala*this.y));

    }

    public double lengthVector()
    {
        return sqrt(((pow(this.x,2))+(pow(this.y,2))));
    }
    //normalizowac znaczy dzielic wspolrzedna przez jego dlugosc
    public Vector2D normalizedVector()
    {
        return new Vector2D((float)(this.x/this.lengthVector()),(float)(this.y/this.lengthVector()));
    }


    Vector2D(double x, double y)
    {
        this.x=x;
        this.y=y;
    }
    Vector2D()
    {
        this.x=0;
        this.y=0;
    }



}
