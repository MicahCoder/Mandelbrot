import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
class Main{
    public static void main(String[] args) {
        int width = 4000;
        int height = 4000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        DrawingCanvas dc = new DrawingCanvas(width,height,-1.3497477362596 ,-1.3495952819014,  0.06142806962796, 0.06158052398616,500);
        try{
            Graphics2D graphics2D = image.createGraphics();
            dc.render(graphics2D);
            ImageIO.write(image,"jpeg", new File("/Users/micahgruenwald/Desktop/java/Mandelbrot/Renders/render.jpeg"));
        }catch(Exception exception){
            System.out.println("Render didn't work");
        }
        System.out.println("Done Rendering");
    }
}