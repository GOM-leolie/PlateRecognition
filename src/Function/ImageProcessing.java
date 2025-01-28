package Function;

import Utility.ReadWritePNG;
import java.util.*;

public class ImageProcessing implements IImageProcessing
{
    public static void main(String[] args)
    {
        String path = "C:\\Users\\leo\\Desktop\\Plate-example.jpg";
        int[][][] imagePixels = ReadWritePNG.ReadPNG(path);
        
        IImageProcessing imageProcessing = new ImageProcessing();
        int[][] result = imageProcessing.generateIntensityHistogram(imagePixels);
        
        for (int i = 0 ; i < result.length ; i++)
        {
            String colour = "";
            
            switch (i)
            {
                case 0 -> colour = "Red";
                case 1 -> colour = "Green";
                case 2 -> colour = "Blue";
            }
            
            System.out.println(colour);           
            
            for (int j = 0 ; j < 256 ; j++)
            {
                int pixelCount = result[i][j];
                if (pixelCount > 0)
                    System.out.println(j + ": " + pixelCount);
            }
            
            try{
                System.in.read();
            }
            catch (Exception e)
            {
            }
        }
    }
     
    @Override
    public int[][] generateIntensityHistogram(int[][][] imagePixels)
    {
        /*Declaring a historgram to store the count of rgb colours*/
        int[][] resultHistogram = new int[3][256];
        
        /*Initialising Histogram*/
        for (int i = 0 ; i < 3 ; i ++)
            for (int j = 0 ; j < 256 ; j++)
                resultHistogram [i][j] = 0;
        
        try
        {
            /*Iterating through each colour pixel to generate historgram*/
            for (int[][] width : imagePixels)
            {
                for (int[] height : width)
                {
                    if (height.length == 3)
                    {
                        int red = height[0];
                        int green = height[1];
                        int blue = height[2];
                        
                        resultHistogram[0][red]++;
                        resultHistogram[1][green]++;
                        resultHistogram[2][blue]++;                        
                    }
                    
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Exception raised - generate intensity histogram: " + e.getMessage());
        }        
        
        return resultHistogram;
    }
    
   /*Image processing technique to remove background noise by 
    differentiating the colour. 
    Any pixel above threshold will be changed to white, otherwise black.*/
   public static void ColorFiltering(int[][][] ImagePixel)
   {
        /*Visit semua node dan mengecek warnanya*/
        /*Jika warnanya kurang dari threshold, jadikan hitam*/
        /*Jika warnanya lebih dari threshold, jadikan putih*/
   	for (int i = 0 ; i < ImagePixel.length ; i++)
            for (int j = 0 ; j < ImagePixel[0].length ; j++)
	        for (int k = 0 ; k < 3 ; k++)
	        {
                    if ((ImagePixel[i][j][0] > 50) && (ImagePixel[i][j][1] > 50) && (ImagePixel[i][j][2] > 50)) 
                    {
                       ImagePixel[i][j][0] = 255;
                       ImagePixel[i][j][1] = 255;
                       ImagePixel[i][j][2] = 255;
                    }
                    else
                    {
                       ImagePixel[i][j][0] = 0;
                       ImagePixel[i][j][1] = 0;
                       ImagePixel[i][j][2] = 0;
                    }
	        }
   }
   
   public static void meanFilter(int[][][] ImagePixel, int[][][] ResultPixel)
   {
   	  /*Deklarasi variable*/
      /*CurrentOperationWidth dengan CurrentOperationHeight adalah variable sementara untuk menyetor x dan y yang sedang dikerjakan sekarang*/
      /*CummulativePixelValue adalah akumulator untuk menjumlahkan pixel yang sudah diproses*/
      int CurrentOperationHeight;
      int CurrentOperationWidth;
      int CummulativePixelValue;

      try
      {
      	/*Menginisialisasi variable*/
         /*x dan y di inisialisasi 1 karena pojok gambar tidak perlu(bisa) di proses*/
      	CurrentOperationWidth = 1;
      	CurrentOperationHeight = 1;
      	CummulativePixelValue = 0;

         /*Memproses mean filter untuk semua x (width) dan y (height) selain di pojok gambar*/
      	while (CurrentOperationWidth < ImagePixel.length - 1)
      	{
      		/*Memproses mean value untuk pixel red*/
      		/*Memproses bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][0];

            /*Memproses bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][0];

            /*Memproses bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][0];

            /*Mencari nilai mean, kemudian menyimpan nya di array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            CummulativePixelValue = CummulativePixelValue / 9;
            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][0] = CummulativePixelValue;
            CummulativePixelValue = 0;

            /*Memproses mean value untuk pixel green*/
            /*Memproses bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][1];

            /*Memproses bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][1];

            /*Memproses bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][1];

            /*Mencari nilai mean, kemudian menyimpan nya di array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            CummulativePixelValue = CummulativePixelValue / 9;
            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][1] = CummulativePixelValue;
            CummulativePixelValue = 0;

            /*Memproses mean value untuk pixel blue*/
            /*Memproses bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][2];

            /*Memproses bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][2];

            /*Memproses bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][2];

            /*Mencari nilai mean, kemudian menyimpan nya di array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            CummulativePixelValue = CummulativePixelValue / 9;
            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][2] = CummulativePixelValue;
            CummulativePixelValue = 0;

            /*Mengeset CurrentOperationHeight untuk mengerjakan height selanjutnya*/
            CurrentOperationHeight++;

            /*Mengecek apakah semua y(height) di koordinat x(width yang sekarang) sudah dikerjakan semua apa belum*/
            /*Jika sudah, kerjakan x(width) selanjuntya*/
            if (CurrentOperationHeight == ImagePixel[0].length - 1)
            {
               CurrentOperationHeight = 1;
               CurrentOperationWidth++;
            }
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
   }

   public static void medianFilter(int[][][] ImagePixel, int[][][] ResultPixel)
   {
      /*Deklarasi variable*/
      /*CurrentOperationWidth dengan CurrentOperationHeight adalah variable sementara untuk menyetor x dan y yang sedang dikerjakan sekarang*/
      /*LargestValueIndex adalah integer untuk menyetor index yang mempunyai value terbesar dalam array tersebut*/
      /*TempPixel adalah array untuk menyimpan pixel sementara sebelum di sorting*/
      /*StoredPixel adalah array untuk menyimpan pixel sementara setelah di sorting*/
      int CurrentOperationHeight;
      int CurrentOperationWidth;
      int LargestValueIndex;
      int[] StoredPixel;
      int[] TempPixel;

      try
      {
         /*Menginisialisasi variable*/
         /*x dan y di inisialisasi 1 karena pojok gambar tidak perlu(bisa) di proses*/
         /*LargestValueIndex di inisialisasi 0 karena tidak diketahui yang mana merupakan yang terbesar*/
         /*StoredPixel dan TempPixel berjumlah 9 karena memakai sistem median 3x3*/
         CurrentOperationWidth = 1;
         CurrentOperationHeight = 1;
         LargestValueIndex = 0;
         StoredPixel = new int[9];
         TempPixel = new int[9];

         /*Memproses median filter untuk semua x (width) dan y (height) selain di pojok gambar*/
         while (CurrentOperationWidth < ImagePixel.length - 1)
         {
            /*Memproses median value untuk pixel red*/
            /*Memproses bagian atas pixel*/
            TempPixel[0] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][0];
            TempPixel[1] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][0];
            TempPixel[2] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][0];

            /*Memproses bagian tengah pixel*/
            TempPixel[3] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][0];
            TempPixel[4] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight][0];
            TempPixel[5] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][0];

            /*Memproses bagian bawah pixel*/
            TempPixel[6] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][0];
            TempPixel[7] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][0];
            TempPixel[8] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][0];

            /*Mencari nilai median dengan cara melakukan sorting*/
            /*Mengeset nilai median ke array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            for (int i = 8 ; i >= 0 ; i--)
            {
               for (int j = 0 ; j <= i ; j++)
               {
                  if (TempPixel[LargestValueIndex] <= TempPixel[j])
                     LargestValueIndex = j;
               }

               StoredPixel[i] = TempPixel[LargestValueIndex];
               LargestValueIndex = 0;
            }

            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][0] = StoredPixel[4];

            /*Memproses median value untuk pixel green*/
            /*Memproses bagian atas pixel*/
            TempPixel[0] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][1];
            TempPixel[1] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][1];
            TempPixel[2] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][1];

            /*Memproses bagian tengah pixel*/
            TempPixel[3] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][1];
            TempPixel[4] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight][1];
            TempPixel[5] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][1];

            /*Memproses bagian bawah pixel*/
            TempPixel[6] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][1];
            TempPixel[7] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][1];
            TempPixel[8] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][1];

            /*Mencari nilai median dengan cara melakukan sorting*/
            /*Mengeset nilai median ke array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            for (int i = 8 ; i >= 0 ; i--)
            {
               for (int j = 0 ; j <= i ; j++)
               {
                  if (TempPixel[LargestValueIndex] <= TempPixel[j])
                     LargestValueIndex = j;
               }

               StoredPixel[i] = TempPixel[LargestValueIndex];
               LargestValueIndex = 0;
            }

            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][1] = StoredPixel[4];

            /*Memproses median value untuk pixel blue*/
            /*Memproses bagian atas pixel*/
            TempPixel[0] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][2];
            TempPixel[1] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][2];
            TempPixel[2] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][2];

            /*Memproses bagian tengah pixel*/
            TempPixel[3] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][2];
            TempPixel[4] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight][2];
            TempPixel[5] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][2];

            /*Memproses bagian bawah pixel*/
            TempPixel[6] = ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][2];
            TempPixel[7] = ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][2];
            TempPixel[8] = ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][2];

            /*Mencari nilai median dengan cara melakukan sorting*/
            /*Mengeset nilai median ke array pixel destinasi*/
            /*Mereset akumulator menjadi 0*/
            for (int i = 8 ; i >= 0 ; i--)
            {
               for (int j = 0 ; j <= i ; j++)
               {
                  if (TempPixel[LargestValueIndex] <= TempPixel[j])
                     LargestValueIndex = j;
               }

               StoredPixel[i] = TempPixel[LargestValueIndex];
               LargestValueIndex = 0;
            }

            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][2] = StoredPixel[4];

            /*Mengeset CurrentOperationHeight untuk mengerjakan height selanjutnya*/
            CurrentOperationHeight++;

            /*Mengecek apakah semua y(height) di koordinat x(width yang sekarang) sudah dikerjakan semua apa belum*/
            /*Jika sudah, kerjakan x(width) selanjuntya*/
            if (CurrentOperationHeight == ImagePixel[0].length - 1)
            {
               CurrentOperationHeight = 1;
               CurrentOperationWidth++;
            }
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
   }

   /**
    * An edge-detection algorithm to detect the edge of objects of interest in the image.
    * The image need to be in greyscale for this method to run effectively.
    * Using a separate convolution process for x-axis and y-axis before combining them 
    * with a formula: G = squareroot{(x-axis convolution)^2 + (y-axis convolution)^2}.
    * @param ImagePixel
    * @param ResultPixel 
    */
   public static void sobelEdgeDetection(int[][][] ImagePixel, int[][][] ResultPixel)
   {
      /*Deklarasi variable*/
      /*PixelResultX dan PixelResultY adalah array sementara untuk menampung hasil dari konvolusi sobel axis-x dan sobel axis-y*/
      /*KernelMatrixX dan KernelMatrixY adalah kernel matrix untuk konvolusi sobel axis-x dan sobel axis-y*/
      int[][][] PixelResultX;
      int[][][] PixelResultY;
      int[][] KernelMatrixX;
      int[][] KernelMatrixY;

      try
      {
         /*Menginisialisasi variable*/
         /*PixelResult array harus mempunyai dimensi yang sama dengan ImagePixel(Sumber gambar)*/
         /*KernelMatrix diinisialisasi dengan value dari kernel matrix sobel*/
         PixelResultX = new int[ImagePixel.length][ImagePixel[0].length][3];
         PixelResultY = new int[ImagePixel.length][ImagePixel[0].length][3];
         
         /**
          * X-axis Kernel     Y-axis Kernel     X-axis (Alt)        Y-axis (Alt)
          * [ -1 -2 -1]       [ 1 0 -1 ]          [ -1 0 1 ]        [  1  2  1 ]
          * [  0  0  0]       [ 2 0 -2 ]          [ -2 0 2 ]        [  0  0  0 ]
          * [  1  2  1]       [ 1 0 -1 ]          [ -1 0 1 ]        [ -1 -2 -1 ]
          */
         KernelMatrixX = new int[][]{{-1,-2,-1},{0,0,0},{1,2,1}};
         KernelMatrixY = new int[][]{{1,0,-1},{2,0,-2},{1,0,-1}};

         /*Mengkonvolusi Gambar dengan sobel axis-x dan sobel axis-y*/
         PixelResultX = convolutionProcess(ImagePixel, KernelMatrixX);
         PixelResultY = convolutionProcess(ImagePixel, KernelMatrixY);

         /*Sesuai dengan algoritma dari sobel edge detection, kedua hasil gambar dari konvolusi sobel axis-x dan sobel axis-y harus digabungkan*/
         /*Penggabungan memakai rumus G = {(Gambar dengan konvolusi sobel axis-x)^2 + (Gambar dengan konvolusi sobel axis-y)^2}^1/2*/
         for (int i = 0 ; i < ImagePixel.length ; i++)
            for (int j = 0 ; j < ImagePixel[0].length ; j++)
               for (int k = 0 ; k < 3 ; k++)
                  ResultPixel[i][j][k] = (int)Math.sqrt(Math.pow(PixelResultX[i][j][k],2) + Math.pow(PixelResultY[i][j][k],2));

         /*Setelah gambar digabungkan dan menghasilkan pixel yang merupakan hasil dari sobel edge detection, perlu dilakukan thresholding*/
         /*Thresholding adalah mengklasifikasi pixel-pixel menjadi hitam/putih dengan menggunakan threshold tertentu agar mudah untuk diproses selanjutnya*/
         for (int i = 0 ; i < ResultPixel.length ; i++)
            for (int j = 0 ; j < ResultPixel[0].length ; j++)
            {
               if (ResultPixel[i][j][0] < 230)
               {
                  ResultPixel[i][j][0] = 0;
                  ResultPixel[i][j][1] = 0;
                  ResultPixel[i][j][2] = 0;
               }
               else
               {
                  ResultPixel[i][j][0] = 255;
                  ResultPixel[i][j][1] = 255;
                  ResultPixel[i][j][2] = 255;
               }
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
   }

   /**
    * Changing the tone of an image to greyscale.
    * Usually this is the first method to call before doing any filtering of the image.
    * Formula to use is ((red * 0.21) + (green * 0.72) + (blue * 0.07) + 0.5)
    * @param imagePixel 
    * @return - image pixels in greyscale
    */
   public static int[][] greyScale(int[][][] imagePixel)
   {
       int[][] greyscaleImage = null;
       
       if (imagePixel.length > 0 && imagePixel[0].length > 0)
       {
           greyscaleImage = new int[imagePixel.length][imagePixel[0].length];
           
           for (int i = 0 ; i < imagePixel.length ; i++)
               for (int j = 0 ; j < imagePixel[i].length ; j++)
                   greyscaleImage[i][j] = (int)( 0.21*imagePixel[i][j][0] + 0.72*imagePixel[i][j][1] + 0.07*imagePixel[i][j][2] + 0.5);    
       }
       
       return greyscaleImage;       
   }

   /**
    * Convolution process - Applying source matrix with the kernel matrix to get
    * the resulting matrix.
    * This process is used to manipulate the image.
    * Read more about convolution matrix - https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution
    * @param imagePixel - the pixels of the originating image, [w][h][r,g,b]
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
                for (int i = 1 ; i < width ; i++)
                    for (int j = 1 ; j < height ; j++)
                    {
                        /*Red Pixel*/
                        resultingMatrix[i][j][0] = convolve(imagePixel, i, j, 0, kernelMatrix);

                         /*Green Pixel*/
                         resultingMatrix[i][j][1] = convolve(imagePixel, i, j, 1, kernelMatrix);

                         /*Blue Pixel*/
                         resultingMatrix[i][j][2] = convolve(imagePixel, i, j, 2, kernelMatrix);
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
   static int convolve(int[][][] imagePixel, int currWidth, int currHeight, int currColour, int[][] kernelMatrix)
   {
       int cummulativeValue = 0;
       
       /**
        * [x x x] <- Operations
        * [x x x]
        * [x x x]
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight - 1][currColour] * kernelMatrix[0][0];
       cummulativeValue += imagePixel[currWidth][currHeight - 1][currColour] * kernelMatrix[1][0];
       cummulativeValue += imagePixel[currWidth + 1][currHeight - 1][currColour] * kernelMatrix[2][0];

       /**
        * [x x x]
        * [x x x] <- Operations
        * [x x x]
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight][currColour] * kernelMatrix[0][1];
       cummulativeValue += imagePixel[currWidth][currHeight][currColour] * kernelMatrix[1][1];
       cummulativeValue += imagePixel[currWidth + 1][currHeight][currColour] * kernelMatrix[2][1];

       /**
        * [x x x]
        * [x x x] 
        * [x x x] <- Operations
        */
       cummulativeValue += imagePixel[currWidth - 1][currHeight + 1][currColour] * kernelMatrix[0][2];
       cummulativeValue += imagePixel[currWidth][currHeight + 1][currColour] * kernelMatrix[1][2];
       cummulativeValue += imagePixel[currWidth + 1][currHeight + 1][currColour] * kernelMatrix[2][2];

       return cummulativeValue;
   }   
}
