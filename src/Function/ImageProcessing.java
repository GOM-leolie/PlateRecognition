package Function;

import Utility.ReadWritePNG;
import java.util.*;

public class ImageProcessing
{    
    /**
     * A method to generate the intensity histogram of the given pixels.
     * This method calculate the intensity of each colour value of the entire pixels.
     * Useful to find the threshold value or to visualise the image colour in histogram.
     * @param imagePixels - 2D array, collection of pixels that represent the image
     * @return - an array with 256 element that represent the colour value (0 - 255).
     */
    public static int[] intensityHistogram(int[][][] imagePixels)
    {
        /*Declaring a histogram to store the count of each pixel colour*/
        int[] resultHistogram = new int[256];  
        int width = imagePixels.length;
        int height = imagePixels[0].length;
        
        /*Initialising Histogram, each colour value starts from 0*/
        for (int i = 0 ; i < 256 ; i++)
            resultHistogram[i] = 0;
        
        try
        {
            /*Iterating through each pixel to record the frequency of appearance*/
            for (int i = 0 ; i < width ; i++)
                for (int j = 0 ; j < height ; j++)
                {
                    int colourValue = imagePixels[i][j][0];
                    resultHistogram[colourValue]++;
                }
        }
        catch (Exception e)
        {
            System.out.println("Exception raised - generate intensity histogram: " + e.getMessage());
        }        
        
        return resultHistogram;
    }
       
   /**
    * One of the popular image filtering algorithms to reduce noise from the image.
    * It uses a mean value of a pixel from 8 of it's neighbouring pixels to remove the noise.
    * The idea is to find a value as a representative in the area around that pixel, which will
    * smoothen the image and reduce the noise.
    * @param greyScalePixel
    * @return - the resulting pixels that has been smoothen by using median filter.
    */
   public static int[][][] meanFilter(int[][][] greyScalePixel)
   {
      int width = greyScalePixel.length;
      int height = greyScalePixel[0].length;
      
      int[][][] resultPixel = new int[width][height][3];

      try
      {
            int cummulativeValue = 0;

            /*Iterating whole greyscale pixels except the left, bottom, top, and right edges.*/
            for (int i = 1 ; i < width - 1 ; i++)
                for (int j = 1 ; j < height - 1 ; j++)
                {
                    cummulativeValue = 0;
                    cummulativeValue += greyScalePixel[i - 1][j - 1][0];
                    cummulativeValue += greyScalePixel[i - 1][j][0];
                    cummulativeValue += greyScalePixel[i - 1][j + 1][0];
                    cummulativeValue += greyScalePixel[i][j - 1][0];
                    cummulativeValue += greyScalePixel[i][j][0];
                    cummulativeValue += greyScalePixel[i][j + 1][0];
                    cummulativeValue += greyScalePixel[i + 1][j - 1][0];
                    cummulativeValue += greyScalePixel[i + 1][j][0];
                    cummulativeValue += greyScalePixel[i + 1][j + 1][0];

                    /*Finding the mean from the neighboring pixels and assign the value to itself.*/
                    cummulativeValue /= 9;
                    resultPixel[i][j][0] = cummulativeValue;
                    resultPixel[i][j][1] = cummulativeValue;
                    resultPixel[i][j][2] = cummulativeValue;
                }  
      }
      catch (IllegalArgumentException e)
      {
      	System.out.println("There is an error in mean filtering : " + e);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
      	System.out.println("There is an error in mean filtering : " + e);
      }
      
      return resultPixel;
   }

   /**
    * One of the popular image filtering algorithms to reduce noise from the image.
    * It uses a median value of a pixel from 8 of it's neighbouring pixels to remove the noise.
    * The idea is to find a value as a representative in the area around that pixel, which will
    * smoothen the image and reduce the noise.
    * @param greyScalePixel
    * @return - the resulting pixels that has been smoothen by using median filter.
    */
   public static int[][][] medianFilter(int[][][] greyScalePixel)
   {  
      int width = greyScalePixel.length;
      int height = greyScalePixel[0].length;
      
      int[][][] resultPixel = new int[width][height][3];

      try
      {         
         int[] pixelArrayToSort = new int[9];

         /*Iterating whole greyscale pixels except the left, bottom, top, and right edges.*/
         for (int i = 1 ; i < width - 1 ; i++)
             for (int j = 1 ; j < height - 1 ; j++)
             {
                 pixelArrayToSort[0] = greyScalePixel[i - 1][j - 1][0];
                 pixelArrayToSort[1] = greyScalePixel[i - 1][j][0];
                 pixelArrayToSort[2] = greyScalePixel[i - 1][j + 1][0];
                 pixelArrayToSort[3] = greyScalePixel[i][j - 1][0];
                 pixelArrayToSort[4] = greyScalePixel[i][j][0];
                 pixelArrayToSort[5] = greyScalePixel[i][j + 1][0];
                 pixelArrayToSort[6] = greyScalePixel[i + 1][j - 1][0];
                 pixelArrayToSort[7] = greyScalePixel[i + 1][j][0];
                 pixelArrayToSort[8] = greyScalePixel[i + 1][j + 1][0];
                 
                 /*Sorting the array, then assigning the median from the neighboring pixel as its value*/
                 Arrays.sort(pixelArrayToSort);
                 int value = pixelArrayToSort[4];
                 resultPixel[i][j][0] = value;
                 resultPixel[i][j][1] = value;
                 resultPixel[i][j][2] = value;
             }  
      }
      catch (IllegalArgumentException e)
      {
         System.out.println("There is an error in median filtering : " + e);
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         System.out.println("There is an error in median filtering : " + e);
      }
      
      return resultPixel;
   }

   /**
    * An edge-detection algorithm to detect the edge of objects of interest in the image.
    * The image need to be in greyscale for this method to run effectively.
    * Using a separate convolution process for x-axis and y-axis before combining them 
    * with a formula: G = squareroot{(x-axis convolution)^2 + (y-axis convolution)^2}.
    * @param greyScaleImagePixel
    * @return - 3D array of the image pixels as the result of Sobel Edge Detection
    */
   public static int[][][] sobelEdgeDetection(int[][][] greyScaleImagePixel)
   {
      int[][][] resultPixel = null;
       
      int[][][] convolvedPixelsXAxis;
      int[][][] convolvedPixelsYAxis;
      int[][] kernelMatrixXAxis;
      int[][] kernelMatrixYAxis;
      
      int width = greyScaleImagePixel.length;
      int height = greyScaleImagePixel[0].length;

      try
      {
         convolvedPixelsXAxis = new int[width][height][3];
         convolvedPixelsYAxis = new int[width][height][3];
         resultPixel = new int[width][height][3];
         
         /**
          * X-axis Kernel     Y-axis Kernel     X-axis (Alt)        Y-axis (Alt)
          * [ -1 -2 -1]       [ 1 0 -1 ]          [ -1 0 1 ]        [  1  2  1 ]
          * [  0  0  0]       [ 2 0 -2 ]          [ -2 0 2 ]        [  0  0  0 ]
          * [  1  2  1]       [ 1 0 -1 ]          [ -1 0 1 ]        [ -1 -2 -1 ]
          */
         kernelMatrixXAxis = new int[][]{{-1,-2,-1},{0,0,0},{1,2,1}};
         kernelMatrixYAxis = new int[][]{{1,0,-1},{2,0,-2},{1,0,-1}};

         /*First Process - Convolving x and y axis separately with their respective kernel matrix*/
         convolvedPixelsXAxis = convolutionProcess(greyScaleImagePixel, kernelMatrixXAxis);
         convolvedPixelsYAxis = convolutionProcess(greyScaleImagePixel, kernelMatrixYAxis);

         /*Second Process - Combining the x and y axis*/
         /*Formula: G = squareroot{(x-axis convolution)^2 + (y-axis convolution)^2}*/
         for (int i = 0 ; i < width ; i++)
            for (int j = 0 ; j < height ; j++)
            {
                int value = (int)Math.sqrt(Math.pow(convolvedPixelsXAxis[i][j][0],2) + Math.pow(convolvedPixelsYAxis[i][j][0],2));
                resultPixel[i][j][0] = value;
                resultPixel[i][j][1] = value;
                resultPixel[i][j][2] = value;
            }

         /*Third Process - Thresholding the resulting pixel*/
         /*Default threshold value is 150. This value is used to classify the pixel to either black and white.*/
         int thresholdValue = 230;
         for (int i = 0 ; i < width ; i++)
            for (int j = 0 ; j < height ; j++)
            {
                int value = 0;
                if (resultPixel[i][j][0] < 230)
                    value = 0;
                else
                    value = 255;
                
                resultPixel[i][j][0] = value;
                resultPixel[i][j][1] = value;
                resultPixel[i][j][2] = value;
            }
                
               
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
         System.out.println("There is an error in sobel edge detection : " + e);
      }
      catch (IllegalArgumentException e)
      {
         System.out.println("There is an error in sobel edge detection : " + e);
      }
      
      return resultPixel;
   }

   /**
    * Changing the tone of an image to greyscale.
    * Usually this is the first method to call before doing any filtering of the image.
    * Formula to use is ((red * 0.21) + (green * 0.72) + (blue * 0.07) + 0.5)
    * @param imagePixel 
    * @return - image pixels in greyscale
    */
   public static int[][][] greyScale(int[][][] imagePixel)
   {
       int[][][] greyscaleImage = null;
       
       if (imagePixel.length > 0 && imagePixel[0].length > 0)
       {
           greyscaleImage = new int[imagePixel.length][imagePixel[0].length][3];
           
           for (int i = 0 ; i < imagePixel.length ; i++)
               for (int j = 0 ; j < imagePixel[i].length ; j++)
               {
                   int value = (int)( 0.21*imagePixel[i][j][0] + 0.72*imagePixel[i][j][1] + 0.07*imagePixel[i][j][2] + 0.5); 
                   greyscaleImage[i][j][0] = value;
                   greyscaleImage[i][j][1] = value;
                   greyscaleImage[i][j][2] = value;
                   
               }   
       }
       
       return greyscaleImage;       
   }

   /**
    * Convolution process - Applying source matrix with the kernel matrix to get
    * the resulting matrix.
    * This process is used to manipulate the image.
    * Read more about convolution matrix - https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution
    * @param imagePixel - the pixels of the originating image, [w][h]
    * @param kernelMatrix - the kernel matrix to be applied to.
    * @return - the resulting matrix after the operation.
    **/
   public static int[][][] convolutionProcess(int[][][] imagePixel, int[][] kernelMatrix)
   {
       /***
        * Initialise the width and height marker as 1 because the edge of 
        * the pixel cannot be convolved.
        */    
        int columnMarker = 1;
        int rowMarker = 1;

        int[][][] resultingMatrix = null;
      
        /*Checking if the input is valid or not*/
        if (imagePixel.length > 0 && imagePixel[0].length > 0)
        {
            int width = imagePixel.length;
            int height = imagePixel[0].length;
            resultingMatrix = new int[width][height][3];

            try
            {
                for (int i = 1 ; i < width - 1 ; i++)
                    for (int j = 1 ; j < height - 1 ; j++)
                    {
                        int value = convolve(imagePixel, i, j, kernelMatrix);
                        resultingMatrix[i][j][0] = value;
                        resultingMatrix[i][j][1] = value;
                        resultingMatrix[i][j][2] = value;
                        
                    }
                    
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
               System.out.println("There is an error when convoluting : " + e);
            }
            catch (IllegalArgumentException e)
            {
               System.out.println("There is an error when convoluting : " + e);
            }
        }

        return resultingMatrix;
   }
   
   /**
    * The actual convolution process for each pixel
    */
   static int convolve(int[][][] imagePixel, int currWidth, int currHeight, int[][] kernelMatrix)
   {
       int cummulativeValue = 0;
       
       /**
        * [x x x] <- Operations
        * [x x x]
        * [x x x]
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight - 1][0] * kernelMatrix[0][0];
       cummulativeValue += imagePixel[currWidth][currHeight - 1][0] * kernelMatrix[1][0];
       cummulativeValue += imagePixel[currWidth + 1][currHeight - 1][0] * kernelMatrix[2][0];

       /**
        * [x x x]
        * [x x x] <- Operations
        * [x x x]
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight][0] * kernelMatrix[0][1];
       cummulativeValue += imagePixel[currWidth][currHeight][0] * kernelMatrix[1][1];
       cummulativeValue += imagePixel[currWidth + 1][currHeight][0] * kernelMatrix[2][1];

       /**
        * [x x x]
        * [x x x] 
        * [x x x] <- Operations
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight + 1][0] * kernelMatrix[0][2];
       cummulativeValue += imagePixel[currWidth][currHeight + 1][0] * kernelMatrix[1][2];
       cummulativeValue += imagePixel[currWidth + 1][currHeight + 1][0] * kernelMatrix[2][2];

       return cummulativeValue;
   }   
}
