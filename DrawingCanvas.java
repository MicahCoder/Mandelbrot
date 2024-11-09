import java.awt.*;

public class DrawingCanvas{
    private final int width;
    private final int height; 
    private final double domainLength;
    private final double rangeLength;
    private final double yMax;
    private final double xMin;
    private final int maxIterations;
    public DrawingCanvas(int width, int height,double xMin, double xMax, double yMin, double yMax, int maxIterations){
        this.width=width;
        this.height= height; 
        this.xMin = xMin;
        this.yMax = yMax;
        this.maxIterations = maxIterations;
        domainLength= xMax-xMin;
        rangeLength = yMax-yMin;
    }
    protected void render(Graphics2D g){
        for(int px = 0; px<width;px++){
            for(int py = 0; py<height;py++){
                float lightness = (float)mandlebrotValue(px, py);
                g.setColor(HSV(lightness));
                //g.setColor(simpleGradient(23, 255, 240, lightness));
                g.drawLine(px,py,px,py);
            }
        }
    } 
    private double[] pixelToPoint(int pixelX, int pixelY){
        double[] point = new double[2]; 
        point[0]= xMin + (double)pixelX/width*domainLength;
        point[1]= yMax- (double)pixelY/height*rangeLength;

        return point;
    }
    private double mandlebrotValue(int px,int py){
        double[] point = pixelToPoint(px,py);
        double x;
        double y;
        double x2 = 0;
        double y2 = 0;
        double w = 0;
        int iteration = 0;
        while(x2+y2 <= 4 && iteration<maxIterations){
            x = x2 - y2 + point[0];
            y = w -x2 - y2 + point[1];
            x2 = x*x;
            y2=y*y;
            w = (x+y)*(x+y);
            iteration++;
        }
        return (double)iteration/maxIterations;
    }
    private Color HSV(float lightness){
        return new Color(Color.HSBtoRGB(lightness+.8f,1f,lightness*69f));
    }
    private Color simpleGradient(float r, float g, float b,float lightness){
        return new Color(r*lightness/255,g*lightness/255,b*lightness/255);
    }
}
