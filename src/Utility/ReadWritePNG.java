package Utility;

import java.io.*;
import java.util.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.ImageIcon;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.io.DicomInputStream;

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
   
   /**
    * Reading and extracting DICOM File.
    * Using dcm4che library.
    * Special thanks to:
    * https://stackoverflow.com/questions/12674064/how-to-save-a-bufferedimage-as-a-file
    * https://github.com/dcm4che/dcm4che
    * https://stackoverflow.com/questions/1580038/byte-array-to-image-object
    * https://stackoverflow.com/questions/34328928/how-to-convert-imageinputstream-to-bufferedimage-in-java
    * https://www.pixelcv.com/2022/12/how-to-read-dicom-image-metadata-using_21.html
    * https://stackoverflow.com/questions/31351753/how-to-read-the-content-of-a-dicom-file-in-java
    *
    * DICOM demo samples taken from: 
    * https://rubomedical.com/dicom_files/index.html
    * 
    * @param path
    * @return 
    */
   public static BufferedImage readDicomImageFile(String path)
   {
       BufferedImage img = null;
       
       try
        {
            InputStream stream = new FileInputStream(path);
            DicomInputStream dicomStream = new DicomInputStream(stream);
                        
            java.io.File f = new java.io.File(path);
            javax.imageio.stream.ImageInputStream dicomImage = javax.imageio.ImageIO.createImageInputStream(f);
                       
            img = ImageIO.read(dicomImage);            
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
       
       return img;
   }
   
   public static Attributes readDICOMAttribute(String path)
   {
       Attributes attributes = null;
               
         try
        {
            InputStream stream = new FileInputStream(path);
            DicomInputStream dicomStream = new DicomInputStream(stream);
            attributes = dicomStream.readDataset();
      
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
         
        return attributes;
   }
}
