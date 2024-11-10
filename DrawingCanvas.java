import java.awt.*;
import java.util.Random;

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
        // Color[] colors = new Color[] {new Color(0,5,71),new Color(255,255,255),new Color(255,167,7),new Color(0,0,0),new Color(0,30,147),new Color(124,124,124),new Color(0,0,0)};
        // float[] positions = new float[] {0,.01f,.015f,.02f,.025f,.075f,1};
        //Color color1 = new Color(3, 101, 110);
        //Color color2 = new Color(23, 173, 138);
        for(int px = 0; px<width;px++){
            for(int py = 0; py<height;py++){
                float lightness = (float)mandlebrotValue(px, py);
                //g.setColor(HSV(lightness));
                //g.setColor(simpleGradient(23, 255, 240, lightness));
                //g.setColor(complexGradient(colors, positions, lightness));
                //g.setColor(gradient(color1,color2,0,1,lightness));
                g.setColor(randomColor(lightness*maxIterations));
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
    private Color complexGradient(Color[] colors, float[] positions, float position){
        Color out = colors[0];
        for(int i =1; i<positions.length;i++){
            if(position > positions[i-1] && position<=positions[i]){
                out = gradient(colors[i-1],colors[i],positions[i],positions[i-1],position);
            }
        }
        return out;
    }
    private Color gradient(Color start, Color end, float startX, float endX, float position){
        float length = endX - startX;
        float startWeight = (position-startX)/length;
        float endWeight = 1f-startWeight;
        float r = (start.getRed()*startWeight + end.getRed()*endWeight)/255f;
        float g = (start.getGreen()*startWeight + end.getGreen()*endWeight)/255f;
        float b = (start.getBlue()*startWeight + end.getBlue()*endWeight)/255f;
        float a = (start.getAlpha()*startWeight + end.getAlpha()*endWeight)/255f;
        return new Color(r,g,b,a);
    }
    private Color randomColor(float seed){
        Random random = new Random((long)seed);
        if(seed == 0){
            return new Color(0,0,0);
        }
        return new Color(Color.HSBtoRGB(256*random.nextFloat(),1f,random.nextFloat()));
    }
}
