import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;

public class ReadWritePNG
{
   /*Reading the PNG file into a stream, 
   then going through each pixel in the stream to extract RGB*/
   public static int[][][] ReadPNG(String Location)
   {
      int[][][] ImagePixels = null;
              
      try
      {
         File ImageFile = new File(Location);
         BufferedImage ImageStream = ImageIO.read(ImageFile);
         
         ImagePixels = new int[ImageStream.getWidth()][ImageStream.getHeight()][3];

         /*Going through the image stream to extract the Pixel and split it to RGB*/
         for (int i = 0 ; i < ImageStream.getHeight() ; i++)
         {
            for (int j = 0 ; j < ImageStream.getWidth() ; j++)
            {
               Color PixelColor = new Color(ImageStream.getRGB(j,i));
               ImagePixels[j][i][0] = PixelColor.getRed();
               ImagePixels[j][i][1] = PixelColor.getGreen();
               ImagePixels[j][i][2] = PixelColor.getBlue();
            }
         }
      }
      catch (NullPointerException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (IllegalArgumentException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (IOException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         System.out.println("There is an error : " + e);
      }

      return ImagePixels;
   }

   public static void WritePNG(String Location, int[][][] ImagePixels)
   {

      try
      {
         File ImageFile = new File(Location);
         BufferedImage ImageStream = new BufferedImage(ImagePixels.length, ImagePixels[0].length, BufferedImage.TYPE_INT_ARGB);
         
         /*Going through the pixels array, combining the RGB into one pixel and save it in the image buffer*/
         for (int i = 0 ; i < ImagePixels.length ; i++)
         {
            for (int j = 0 ; j < ImagePixels[0].length ; j++)
            {
               Color PixelColor = new Color(ImagePixels[i][j][0],ImagePixels[i][j][1],ImagePixels[i][j][2]);
               ImageStream.setRGB(i, j, PixelColor.getRGB());
            }
         }

         ImageIO.write(ImageStream, "png", ImageFile);
      }
      catch (NullPointerException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (IllegalArgumentException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (IOException e)
      {
         System.out.println("There is an error : " + e);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         System.out.println("There is an error : " + e);
      }
   }
}
