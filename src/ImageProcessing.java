import java.util.*;

public class ImageProcessing
{
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

   /*Converting image to greyscale
   The formula is (red * 0.21), (green * 0.72), (blue * 0.07)*/
   public static void GreyScale(int[][][] ImagePixel)
   {
      for (int i = 0 ; i < ImagePixel.length ; i++)
         for (int j = 0 ; j < ImagePixel[0].length ; j++)
	        for (int k = 0 ; k < 3 ; k++)
               ImagePixel[i][j][k] = (int)( 0.21*ImagePixel[i][j][0] + 0.72*ImagePixel[i][j][1] + 0.07*ImagePixel[i][j][2] + 0.5);    
   }

   /*Convolution process*/
   /*Read more about convolution matrix - https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution*/
   public static void Convolve(int[][][] ImagePixel, int[][][] ResultPixel, int[][] KernelMatrix)
   {
      int CurrentOperationWidth;
      int CurrentOperationHeight;
      int CummulativePixelValue;

      try
      {
         /*Menginisialisasi variable*/
         /*x dan y di inisialisasi 1 karena pojok gambar tidak perlu(bisa) di konvolusi*/
         CurrentOperationWidth = 1;
         CurrentOperationHeight = 1;
         CummulativePixelValue = 0;

         /*Mengerjakan konvolusi untuk semua x (width) dan y (height) selain di pojok gambar*/
         while (CurrentOperationWidth < ImagePixel.length - 1)
         {             
            /*Mengkonvolusi pixel red*/
            /*Mengkonvolusi bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][0] * KernelMatrix[0][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][0] * KernelMatrix[1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][0] * KernelMatrix[2][0];

            /*Mengkonvolusi bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][0] * KernelMatrix[0][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][0] * KernelMatrix[1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][0] * KernelMatrix[2][1];

            /*Mengkonvolusi bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][0] * KernelMatrix[0][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][0] * KernelMatrix[1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][0] * KernelMatrix[2][2];

            /*Menyimpan hasil konvolusi ke dalam pixel dari gambar sumber dan mereset akumulator*/
            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][0] = CummulativePixelValue;
            CummulativePixelValue = 0;

            /*Mengkonvolusi pixel green*/
            /*Mengkonvolusi bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][1] * KernelMatrix[0][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][1] * KernelMatrix[1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][1] * KernelMatrix[2][0];

            /*Mengkonvolusi bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][1] * KernelMatrix[0][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][1] * KernelMatrix[1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][1] * KernelMatrix[2][1];

            /*Mengkonvolusi bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][1] * KernelMatrix[0][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][1] * KernelMatrix[1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][1] * KernelMatrix[2][2];

            /*Menyimpan hasil konvolusi ke dalam pixel dari gambar sumber dan mereset akumulator*/
            ResultPixel[CurrentOperationWidth][CurrentOperationHeight][1] = CummulativePixelValue;
            CummulativePixelValue = 0;

            /*Mengkonvolusi pixel blue*/
            /*Mengkonvolusi bagian atas pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight - 1][2] * KernelMatrix[0][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight - 1][2] * KernelMatrix[1][0];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight - 1][2] * KernelMatrix[2][0];

            /*Mengkonvolusi bagian tengah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight][2] * KernelMatrix[0][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight][2] * KernelMatrix[1][1];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight][2] * KernelMatrix[2][1];

            /*Mengkonvolusi bagian bawah pixel*/
            CummulativePixelValue += ImagePixel[CurrentOperationWidth - 1][CurrentOperationHeight + 1][2] * KernelMatrix[0][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth][CurrentOperationHeight + 1][2] * KernelMatrix[1][2];
            CummulativePixelValue += ImagePixel[CurrentOperationWidth + 1][CurrentOperationHeight + 1][2] * KernelMatrix[2][2];


            /*Menyimpan hasil konvolusi ke dalam pixel dari gambar sumber dan mereset akumulator*/
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
      catch (ArrayIndexOutOfBoundsException e)
      {
         System.out.println("There is an error when convoluting : " + e);
      }
      catch (IllegalArgumentException e)
      {
         System.out.println("There is an error when convoluting : " + e);
      }
   }
   
   public static void MeanFilter(int[][][] ImagePixel, int[][][] ResultPixel)
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

   public static void MedianFilter(int[][][] ImagePixel, int[][][] ResultPixel)
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

   public static void SobelEdgeDetection(int[][][] ImagePixel, int[][][] ResultPixel)
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
         /*yang berhasil*
         KernelMatrixX = new int[][]{{-1,0,1},{-2,0,2},{-1,0,1}};
         KernelMatrixY = new int[][]{{1,2,1},{0,0,0},{-1,-2,-1}};*/
         
         KernelMatrixX = new int[][]{{-1,-2,-1},{0,0,0},{1,2,1}};
         KernelMatrixY = new int[][]{{1,0,-1},{2,0,-2},{1,0,-1}};

         /*Mengkonvolusi Gambar dengan sobel axis-x dan sobel axis-y*/
         Convolve(ImagePixel, PixelResultX, KernelMatrixX);
         Convolve(ImagePixel, PixelResultY, KernelMatrixY);

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

   public static int[][] Blobbing(int[][][] ImagePixel)
   {
      /*Deklarasi variable*/
   	  /*ImageLabel adalah array yang menampung pixel-pixel yang telah terlabel setelah di blobbing*/
   	  /*CurrentLabel adalah variable untuk menyimpan label yang akan digunakan selanjutnya*/
      int[][] ImageLabel;
      int CurrentLabel;
      ArrayList<ArrayList<Integer>> ExistingLabel;

      /*Inisialisasi Variable*/
      /*ImageLabel diinisialisasi dengan panjang dan lebar sama seperti gambar sumber*/
      /*Semua label dinisialisasi 0*/
      /*CurrentLabel diinisialisasi 1*/
      ImageLabel = new int[ImagePixel.length][ImagePixel[0].length]; 

      for (int i = 0 ; i < ImageLabel.length ; i++)
      	for (int j = 0 ; j < ImageLabel[0].length ; j++)
      		ImageLabel[i][j] = 0;

      CurrentLabel = 1;  
      ExistingLabel = new ArrayList<ArrayList<Integer>>();

      /*Mengerjakan algoritma blobbing dengan 8-konektivitas*/
   	  for (int i = 1 ; i < ImagePixel[0].length - 1 ; i++)
   	  {
   	     for (int j = 1 ; j < ImagePixel.length - 1 ; j++)
         {
         	if ((ImagePixel[j][i][0] > 10) && (ImagePixel[j][i][1] > 10) && (ImagePixel[j][i][2] > 10))
         	{
         		if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j-1][i-1] != 0) && (ImageLabel[j][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j-1][i-1] != 0) && (ImageLabel[j][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j-1][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i-1] != 0) && (ImageLabel[j][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j-1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}       		
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i-1] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i] != 0) && (ImageLabel[j+1][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if ((ImageLabel[j-1][i-1] != 0) && (ImageLabel[j][i-1] != 0))
         		{
         			LabelProcessing(ImageLabel, j, i, ExistingLabel);
         		}
         		else if (ImageLabel[j-1][i] != 0)
         		{
         			ImageLabel[j][i] = ImageLabel[j-1][i];
         		}
         		else if (ImageLabel[j-1][i-1] != 0)
         		{
         			ImageLabel[j][i] = ImageLabel[j-1][i-1];
         		}
         		else if (ImageLabel[j][i-1] != 0)
         		{
         			ImageLabel[j][i] = ImageLabel[j][i-1];
         		}
         		else if (ImageLabel[j+1][i-1] != 0)
         		{
         			ImageLabel[j][i] = ImageLabel[j+1][i-1];
         		}
         		else
         		{
         			ImageLabel[j][i] = CurrentLabel;
         			CurrentLabel++;
         		}
         	}
         }
      }

      /*Mengerjakan algoritma blobbing : second pass*/
      for (int i = 1 ; i < ImageLabel[0].length ; i++)
      {
      	 for (int j = 1 ; j < ImageLabel.length ; j++)
      	 {
      		if (ImageLabel[j][i] != 0)
      		{
               for  (int k = 0 ; k < ExistingLabel.size() ; k++)
               {
               	  for (int l = 0 ; l < ExistingLabel.get(k).size() ; l++)
               	  {
               	     if (ImageLabel[j][i] == ExistingLabel.get(k).get(l))
               	        ImageLabel[j][i] = ExistingLabel.get(k).get(0);
               	  }
               }
      		}

      	 }
      }

      return ImageLabel;
   }

   public static void LabelProcessing(int[][] ImageLabel, int Width, int Height, ArrayList<ArrayList<Integer>> ExistingLabel)
   {
   	  /*Deklarasi variable*/
   	  /*SmallestLabel digunakan untuk menyetor label dengan nilai terkecil*/
      /*ExistingLabelIndex digunakan untuk menyetor index dari list dimana terdapat label yang sedang diproses*/
   	  /*TempLabel adalah list untuk menyimpan label untuk dicek apa ada label yang equivalent dengan dia apa tidak*/
   	  /*IsExist dan IsExist2 adalah untuk mengecek apakah label yang berekuivalen telah ada di dalam existinglabel apa belum*/
      int SmallestLabel;
      ArrayList<Integer> ExistingLabelIndex;
      ArrayList<Integer> TempLabel;
      Boolean IsExist;
      Boolean IsExist2;
      Boolean IsExist3;
 
      /*Inisialisasi Variable*/
      /*SmallestLabel dan ExistingLabelIndex diinisialisasi 0*/
      /*TempLabel diinisialisasi sebagai arraylist yang menampung integer*/
      /*IsExist dan IsExist2 diinisialisasi false*/
      SmallestLabel = 0;
      ExistingLabelIndex = new ArrayList<Integer>();
      TempLabel = new ArrayList<Integer>();
      IsExist = false;
      IsExist2 = false;
      IsExist3 = false;

      /*Mengecek apakan piksel ini memiliki tetangga*/
      /*Jika ada, cek tetangga mana yang memiliki label terkecil*/
   	  if (ImageLabel[Width-1][Height] != 0)
   	  {
   	     SmallestLabel = ImageLabel[Width-1][Height];

   	     for (int i = 0 ; i < TempLabel.size() ; i++)
   	        if (TempLabel.get(i) == ImageLabel[Width-1][Height])
   	           IsExist3 = true;

   	     if (!IsExist3)
   	        TempLabel.add(ImageLabel[Width-1][Height]);

   	     IsExist3 = false;
   	  }

   	  if (ImageLabel[Width-1][Height-1] != 0)
   	  {
   	     if ((SmallestLabel == 0) || (SmallestLabel > ImageLabel[Width-1][Height-1]))
   	     	SmallestLabel = ImageLabel[Width-1][Height-1];

         for (int i = 0 ; i < TempLabel.size() ; i++)
   	        if (TempLabel.get(i) == ImageLabel[Width-1][Height-1])
   	           IsExist3 = true;

   	     if (!IsExist3)
   	     	TempLabel.add(ImageLabel[Width-1][Height-1]);

   	     IsExist3 = false;
   	  }

   	  if (ImageLabel[Width][Height-1] != 0)
   	  {
   	     if ((SmallestLabel == 0) || (SmallestLabel > ImageLabel[Width][Height-1]))
   	     	SmallestLabel = ImageLabel[Width][Height-1];

         for (int i = 0 ; i < TempLabel.size() ; i++)
   	        if (TempLabel.get(i) == ImageLabel[Width][Height-1])
   	           IsExist3 = true;

   	     if (!IsExist3)
   	     	TempLabel.add(ImageLabel[Width][Height-1]);

   	     IsExist3 = false;
   	  }

   	  if (ImageLabel[Width+1][Height-1] != 0)
   	  {
   	     if ((SmallestLabel == 0) || (SmallestLabel > ImageLabel[Width+1][Height-1]))
   	     	SmallestLabel = ImageLabel[Width+1][Height-1];

         for (int i = 0 ; i < TempLabel.size() ; i++)
   	        if (TempLabel.get(i) == ImageLabel[Width+1][Height-1])
   	           IsExist3 = true;

   	     if (!IsExist3)
   	     	TempLabel.add(ImageLabel[Width+1][Height-1]);

   	     IsExist3 = false;
   	  }

      /*Memproses label-label yang equivalent*/
      for (int i = 0 ; i < TempLabel.size() ; i++)
      {
   	     for (int j = 0 ; j < ExistingLabel.size() ; j++)
   	     {
   	        for (int k = 0 ; k < ExistingLabel.get(j).size() ; k++)
   	        {
               if (TempLabel.get(i) == ExistingLabel.get(j).get(k))
               {
                  if (IsExist)
                     for (int l = 0 ; l < ExistingLabelIndex.size() ; l++)
                     {
                          if (ExistingLabelIndex.get(l) != j)
                             ExistingLabelIndex.add(j);
                     }
                  else
                  {
                     IsExist = true;
                     ExistingLabelIndex.add(j);
                  }
               }
   	        }
   	     }
   	  }

   	  if (IsExist)
   	  {
   	  	 /*Kalo ada list yang perlu digabungin*/
   	     if (ExistingLabelIndex.size() > 1)
   	     {
            for (int i = 1 ; i < ExistingLabelIndex.size() ; i++)
               for (int j = 0 ; j < ExistingLabel.get(ExistingLabelIndex.get(i)).size() ; j++)
               {
               	  if (!ExistingLabel.get(ExistingLabelIndex.get(0)).contains(ExistingLabel.get(ExistingLabelIndex.get(i)).get(j)))
               	     ExistingLabel.get(ExistingLabelIndex.get(0)).add(ExistingLabel.get(ExistingLabelIndex.get(i)).get(j));
               }

            for (int i = 1 ; i < ExistingLabelIndex.size() ; i++)
            {
               int TempInt;
               TempInt = ExistingLabelIndex.get(i);

               ExistingLabel.remove(TempInt);

               if (TempInt < ExistingLabelIndex.get(0))
                  ExistingLabelIndex.set(0,ExistingLabelIndex.get(0) - 1);
            }
   	     }
   	  	 //yang ini kalo existinglabelindex cuman 1, kalo multiple...
   	     for (int i = 0 ; i < TempLabel.size() ; i++)
   	     {
   	     	for (int j = 0 ; j < ExistingLabel.get(ExistingLabelIndex.get(0)).size() ; j++)
   	     	{
   	     	   if (TempLabel.get(i) == ExistingLabel.get(ExistingLabelIndex.get(0)).get(j))
   	              IsExist2 = true;
   	     	} 

   	     	if (!IsExist2)
   	     	   ExistingLabel.get(ExistingLabelIndex.get(0)).add(TempLabel.get(i));

   	     	IsExist2 = false;
   	     }

   	  }
   	  else
   	  {
   	     ExistingLabel.add(TempLabel);
   	  }


      /*Mengeset piksel dengan label terkecil*/
      ImageLabel[Width][Height] = SmallestLabel;
   }

   public static void LabelCounting(int[][] ImageLabel, ArrayList<Integer> ValidLabel)
   {
      Boolean IsExist;
      IsExist = false;

      /*Menginput label-label yang tersedia ke dalam ValidLabel*/
      for (int i = 0 ; i < ImageLabel.length ; i++)
         for (int j = 0 ; j < ImageLabel[0].length ; j++)
         {
            for (int k = 0 ; k < ValidLabel.size() ; k++)
            {
            	if (ImageLabel[i][j] != 0)
            	   if (ValidLabel.get(k) == ImageLabel[i][j])
            		  IsExist = true;
            }

            if ((!IsExist) && (ImageLabel[i][j] != 0))
            	ValidLabel.add(ImageLabel[i][j]);

            IsExist = false;
         }
   }

   public static void LabelElimination(int[][] ImageLabel, ArrayList<Integer> ValidLabel)
   {
      ArrayList<Integer> LabelArea;
      ArrayList<Integer> LabelEliminationIndex;
      int AverageValue;

      LabelArea = new ArrayList<Integer>();
      LabelEliminationIndex = new ArrayList<Integer>();
      AverageValue = 0;

      /*Menginisialisasi LabelArea*/
      for (int i = 0 ; i < ValidLabel.size() ; i++)
         LabelArea.add(0);

      /*Menghitung total piksel pada 1 label*/
      for (int i = 0 ; i < ValidLabel.size() ; i++)
      	for (int j = 0 ; j < ImageLabel.length ; j++)
      		for (int k = 0 ; k < ImageLabel[0].length ; k++)
      		{
      			if (ImageLabel[j][k] == ValidLabel.get(i))
      			{
      				LabelArea.set(i, LabelArea.get(i) + 1);
      			}
      		}

      /*Menghitung average*/
      for (int i = 0 ; i < LabelArea.size() ; i++)
         AverageValue+= LabelArea.get(i);

      AverageValue = AverageValue / LabelArea.size();

      /*Memasukkan daftar label-label yang akan dibuang*/
      for (int i = 0 ; i < LabelArea.size() ; i++)
      {
         if (LabelArea.get(i) < AverageValue)
            LabelEliminationIndex.add(i);
      }

      /*Mengeliminasi Label*/
      for (int i = ValidLabel.size()-1 ; i > 0 ; i--)
         for (int j = LabelEliminationIndex.size()-1 ; j > 0 ; j--)
         	if (LabelEliminationIndex.get(j) == i)
         	{
         		ValidLabel.remove(i);
         	}
   }

   public static int CountLabelArea(int[][] ImageLabel, int Label, int Type)
   {
      int LabelArea;

      LabelArea = 0;
   
      if (Type == 1)
      {
         for (int i = 0 ; i < ImageLabel.length ; i++)
            for (int j = 0 ; j < ImageLabel[0].length ; j++)
      	       if (ImageLabel[i][j] == Label)
      		      LabelArea++;
      }
      else
      {
      	 for (int i = 0 ; i < ImageLabel.length ; i++)
            for (int j = 0 ; j < ImageLabel[0].length ; j++)
      	       if (ImageLabel[i][j] != 0)
      		      LabelArea++;
      }
      return LabelArea;
   }

   public static int CountPixelArea(int[][][] ImagePixel)
   {
      int PixelArea;

      PixelArea = 0;

      for (int i = 0 ; i < ImagePixel.length ; i++)
         for (int j = 0 ; j < ImagePixel[0].length ; j++)
      	    if (ImagePixel[i][j][0] == 255)
      		   PixelArea++;

      return PixelArea;
   }

   public static void ImageSplit(int[][][]ImagePixel, int[][][]ImagePixel2, int[][] ImageLabel, ArrayList<Integer> ValidLabel, ArrayList<int[][][]> SplitImage, ArrayList<int[][][]> SplitImageHalf, ArrayList<int[][][]> SplitImageFull)
   {
      int SmallestWidth;
      int SmallestHeight;
      int LargestWidth;
      int LargestHeight;
      
      SmallestWidth = ImageLabel.length;
      SmallestHeight = ImageLabel[0].length;
      LargestWidth = 0;
      LargestHeight = 0;

      /*Mensplit gambar sesuai label, menghasilkan gambar yang telah displit*/
      for (int i = 0 ; i < ValidLabel.size() ; i++)
      {
         for (int j = 0 ; j < ImageLabel.length ; j++)
         {
            for (int k = 0 ; k < ImageLabel[0].length ; k++)
            {
               if ((ValidLabel.get(i) == ImageLabel[j][k]) && (ImageLabel[j][k] != 0))
               {
                  if (SmallestWidth > j)
                     SmallestWidth = j;

                  if (SmallestHeight > k)
                  	 SmallestHeight = k;

                  if (LargestWidth < j)
                     LargestWidth = j;

                  if (LargestHeight < k)
                  	 LargestHeight = k;
               }

            }
         }

         int[][][] TempImage = new int[(LargestWidth-SmallestWidth) + 1][(LargestHeight-SmallestHeight) + 1][3];
         int[][][] TempImage2 = new int[(LargestWidth-SmallestWidth) + 1][(LargestHeight-SmallestHeight) + 1][3];
         int[][][] TempImage3 = new int[(LargestWidth-SmallestWidth) + 1][(LargestHeight-SmallestHeight) + 1][3];

         for (int j = SmallestWidth ; j <= LargestWidth ; j++)
         {
         	for (int k = SmallestHeight ; k <= LargestHeight ; k++)
         	{
               if ((ImageLabel[j][k] == ValidLabel.get(i)) && (ImageLabel[j][k] != 0))
               {
                  TempImage[j-SmallestWidth][k-SmallestHeight][0] = 255;
                  TempImage[j-SmallestWidth][k-SmallestHeight][1] = 255;
                  TempImage[j-SmallestWidth][k-SmallestHeight][2] = 255;
               }
 
               if ((ImagePixel[j][k][0] > 50) && (ImagePixel[j][k][1] > 50) && (ImagePixel[j][k][2] > 50))
               {
               	  TempImage2[j-SmallestWidth][k-SmallestHeight][0] = ImagePixel[j][k][0];
                  TempImage2[j-SmallestWidth][k-SmallestHeight][1] = ImagePixel[j][k][1];
                  TempImage2[j-SmallestWidth][k-SmallestHeight][2] = ImagePixel[j][k][2];
               }

               TempImage3[j-SmallestWidth][k-SmallestHeight][0] = ImagePixel2[j][k][0];
               TempImage3[j-SmallestWidth][k-SmallestHeight][1] = ImagePixel2[j][k][1];
               TempImage3[j-SmallestWidth][k-SmallestHeight][2] = ImagePixel2[j][k][2];
         	}
         }

         SplitImage.add(TempImage);
         SplitImageFull.add(TempImage2);
         SplitImageHalf.add(TempImage3);
         SmallestWidth = ImageLabel.length;
         SmallestHeight = ImageLabel[0].length;
         LargestWidth = 0;
         LargestHeight = 0;
      }
   }

   public static int[] FindCenterOfOrigin(int[][] ImageLabel, int Label)
   {
       int SmallestWidth;
       int SmallestHeight;
       int LargestWidth;
       int LargestHeight;
       int[] CenterOfOrigin;
      
       SmallestWidth = ImageLabel.length;
       SmallestHeight = ImageLabel[0].length;
       LargestWidth = 0;
       LargestHeight = 0;
       CenterOfOrigin = new int[2];

   	   for (int i = 0 ; i < ImageLabel.length ; i++)
          for (int j = 0 ; j < ImageLabel[0].length ; j++)
             if ((Label == ImageLabel[i][j]) && (ImageLabel[i][j] != 0))
                {
                   if (SmallestWidth > i)
                      SmallestWidth = i;

                   if (SmallestHeight > j)
                  	  SmallestHeight = j;

                   if (LargestWidth < i)
                      LargestWidth = i;

                   if (LargestHeight < j)
                  	  LargestHeight = j;
                }

        CenterOfOrigin[0] = SmallestWidth + ((LargestWidth - SmallestWidth) / 2);
        CenterOfOrigin[1] = SmallestHeight + ((LargestHeight - SmallestHeight) / 2);

        return CenterOfOrigin;
   }

   public static String ImageClassification(ArrayList<int[][][]> SplitImage, ArrayList<int[][][]> SplitImageHalf, ArrayList<int[][][]> SplitImageFull, int width, int height, int arg)
   {
      int[][][] CurrentImage = SplitImageHalf.get(arg);
      int[][] label;
      int area = 0;
      ArrayList<Integer> exist = new ArrayList<Integer>();
      int object = 0;
      String name = "";

      /*Mendeteksi bulatan di dalam Object Of Interest*/
      /*Perbaiki cara pendeteksian bulatan di dalam*/
      /*Threshold harus diperbaharui*/
      label = ImageProcessing.Blobbing(CurrentImage);
      ImageProcessing.LabelCounting(label,exist);

      for (int i = 0 ; i < exist.size() ; i++)
      {
         area = CountLabelArea(label, exist.get(i), 1);

         if (area < 200) //threshold diperbaharui pake ratio nanti
         {
            exist.remove(i);
            i--;
         }
      }
      
      /*coba*/
      for (int i = 0 ; i < exist.size() ; i++)
      {
         int[] wh = HeightWidth(label, exist.get(i));

         if ((wh[0] < 16) && (wh[1] < 26))
         {
            exist.remove(i);
            i--;
         }
      }
      /*akhir dari coba*/

      if (exist.size() > 0)
         InsideRegionChecking(label, exist);
      
      /*Jika ada 2 bulatan di dalam, antara (B || 8 || O)*/
      /*Untuk case plat 5, itu karena miring dan kontras makanya selalu error, perbaiki kontras*/
      /*Kontras disini adalah threshold (50 / 100)*/
      if ((SplitImageFull.get(arg).length > 30) && (SplitImageFull.get(arg)[0].length > 80) && (SplitImageFull.get(arg).length < 130)) {
      if (exist.size() == 3)
      {
         //object = TwoRegionDetection(SplitImageFull.get(arg), width, height, CountLabelArea(label, exist.get(0), 1));
          object = TwoRegion2Detection(label, exist.get(0));

         if (object == 1)
         	name = "B";
         else if (object == 2)
         	name = "8";
         else
         	name = "O";
      }
      /*Jika ada 1 bulatan di dalam*/
      else if (exist.size() == 2)
      {
         object = OneRegionDetection(SplitImageFull.get(arg), width, height, FindCenterOfOrigin(label, exist.get(1)) );

         if (object == 4)
         	name = "A";
         else if (object == 5)
         	name = "6";
         else if (object == 6)
         	name = "9";
         else if (object == 7)
         	name = "P";
         else
         {
                object = OneRegion2Detection(label, exist.get(1));
         	
                if (object == 8)
                    name = "Q";
                else
                    name = "0";
         }
      }
      else
      {
          object = LineRegionDetection(SplitImageFull.get(arg));
          if (object == 10)
              name = "E";
          else if (object == 11)
              name = "T";
          else if (object == 12)
              name = "H";
          else if (object == 13)
              name = "N";
          else if (object == 14)
              name = "K";
          else if (object == 15)
              name = "5";
          else if (object == 16)
              name = "3";
          else if (object == 17)
              name = "1";
          else if (object == 18)
              name = "7";
          else if (object == 19)
              name = "2";
          else if (object == 20)
              name = "S";
          else if (object == 21)
              name = "J";
          else if (object == 22)
              name = "4";
          else if (object == 24)
              name = "U";
          else if (object == 25)
              name = "Y";
          else
              name = "Unidentified";
      }
      }
      return name;
   }

   public static void InsideRegionChecking(int[][] image, ArrayList<Integer> label)
   {
       int SmallestWidth = image.length;
       int SmallestHeight = image[0].length;
       int LargestWidth = 0;
       int LargestHeight = 0;
       int width = 0;
       int height = 0;
       int[] CenterOfOrigin = new int[2];
       boolean isInside = false;
       
       for (int i = 0 ; i < image.length ; i++)
          for (int j = 0 ; j < image[0].length ; j++)
             if ((label.get(0) == image[i][j]) && (image[i][j] != 0))
             {
                if (SmallestWidth > i)
                    SmallestWidth = i;

                if (SmallestHeight > j)
                    SmallestHeight = j;

                if (LargestWidth < i)
                    LargestWidth = i;

                if (LargestHeight < j)
                    LargestHeight = j;
             }
       
       width = LargestWidth - SmallestWidth;
       height = LargestHeight - SmallestHeight;
       
       LargestHeight -= (height/6);
       SmallestHeight += (height/6);
       LargestWidth -= (width/6);
       SmallestWidth += (width/6);
       
       for (int i = label.size() - 1 ; i > 1 ; i--)
       {
           CenterOfOrigin = FindCenterOfOrigin(image, label.get(i));
           
           if ((CenterOfOrigin[0] <= LargestWidth) && (CenterOfOrigin[0] >= SmallestWidth))
               if ((CenterOfOrigin[1] <= LargestHeight) && (CenterOfOrigin[1] >= SmallestHeight))
                   isInside = true;
           
           if (!isInside)
               label.remove(i);
          
           isInside = false;
       }   
   }
   
   public static int TwoRegionDetection(int[][][] ImagePixel, int totalWidth, int totalHeight, int area)
   {
      int height = ImagePixel[0].length / 2;
      int width = ImagePixel.length;
      int length = 0;
      double ratio = 0.0;
      double ratio2 = 0.0;
      int object = 0;

      for (int i = 0 ; i < width ; i++)
      {
         if ((ImagePixel[i][height][0] > 50) && (ImagePixel[i][height][1] > 50) && (ImagePixel[i][height][2] > 50))
            length++;
      }

      ratio = ((double)length / (double)totalWidth) * 1000;
      
      if (ratio < 65)
         object = 2;
      else 
      {
      	 ratio2 = ((double)area / (double) (totalWidth * totalHeight)) * 10000;

      	 if (ratio2 < 42)
      	 	object = 3;
      	 else
      	 	object = 1;
      }

      return object;
   }

   public static int OneRegionDetection(int[][][] ImagePixel, int totalWidth, int totalHeight, int[] CenterOfOrigin)
   {
      int height = ImagePixel[0].length;
      int width = ImagePixel.length;
      boolean IsExist = true;
      boolean IsExist2 = true;
      boolean IsExist3 = true;
      boolean IsExist4 = true;
      int object = 0;

      for (int i = width/5 ; i < (width * 4) / 5 ; i++)
      {
         if ((ImagePixel[i][(height*75)/100][0] < 50) && (ImagePixel[i][(height*75)/100][1] < 50) && (ImagePixel[i][(height*75)/100][2] < 50))
         	IsExist = false;

         if ((ImagePixel[i][(height*70)/100][0] < 50) && (ImagePixel[i][(height*70)/100][1] < 50) && (ImagePixel[i][(height*70)/100][2] < 50))
         	IsExist2 = false;
      }

      //artinya A
      if ((IsExist) || (IsExist2))
         object = 4;
      else
      {
         IsExist = true;
         IsExist2 = true;

         for (int i = (width *2)/5 ; i < (width * 2) / 3 ; i++)
            if ((ImagePixel[i][(height*4)/10][0] < 50) && (ImagePixel[i][(height*4)/10][1] < 50) && (ImagePixel[i][(height*4)/10][2] < 50))
         	   IsExist = false;

         for (int i = (width *2)/5 ; i < (width * 2) / 3 ; i++)
            if ((ImagePixel[i][(height*5)/10][0] < 50) && (ImagePixel[i][(height*5)/10][1] < 50) && (ImagePixel[i][(height*5)/10][2] < 50))
         	   IsExist2 = false;

         for (int i = (width *2)/5 ; i < (width * 2) / 3 ; i++)
            if ((ImagePixel[i][(height*6)/10][0] < 50) && (ImagePixel[i][(height*6)/10][1] < 50) && (ImagePixel[i][(height*6)/10][2] < 50))
         	   IsExist3 = false;

          //Antara (P || R || 6 || 9)
          if ((IsExist) || (IsExist2) || (IsExist3))
          {
          	 //Kalo bulatannya dibawah, artinya 6
             if (CenterOfOrigin[1] > height/2)
                object = 5;
             else
             {
             	IsExist = true;

                for (int i = (width *2)/5 ; i < (width * 2) / 3 ; i++)
                   if ((ImagePixel[i][(height*48)/100][0] < 100) && (ImagePixel[i][(height*48)/100][1] < 100) && (ImagePixel[i][(height*48)/100][2] < 100))
         	          IsExist = false;
                
                //Kalo garis di bwah origin, artinya 9
                if (!IsExist)
                   object = 6;
                else//Kalo garis di atas origin, antara P atau R. R kurang training set, jadi langsung P.
                {
                   object = 7;
                }
             }
          }
          else
          {
          	  object = 9;
          	  //O sama Q
          	  IsExist = true;
          	  IsExist2 = false;

              //for (int i = width/4 ; i < (width*3)/4 ; i++)
                 //for (int j = (height*3)/4 ; j < height ; j++)
                    
          }
      }

      return object;
   }
   
   public static int TwoRegion2Detection(int[][] image, int label)
   {
       int SmallestWidth = image.length;
       int SmallestHeight = image[0].length;
       int LargestWidth = 0;
       int LargestHeight = 0;
       int width = 0;
       int height = 0;
       int counter = -1;
       int object = 0;
       int longest = 0;
       boolean isExist = false;
       boolean isExist2 = false;
       
       /*Kalo 8 dengan B masi ambigu, gunakan cara perbandingan panjang garis seperti B dengan O*/
       for (int i = 0 ; i < image.length ; i++)
          for (int j = 0 ; j < image[0].length ; j++)
             if ((label == image[i][j]) && (image[i][j] != 0))
             {
                if (SmallestWidth > i)
                    SmallestWidth = i;

                if (SmallestHeight > j)
                    SmallestHeight = j;

                if (LargestWidth < i)
                    LargestWidth = i;

                if (LargestHeight < j)
                    LargestHeight = j;
             }
       
       width = LargestWidth - SmallestWidth;
       height = LargestHeight - SmallestHeight;
       
       for (int i = SmallestWidth ; i <= SmallestWidth + (width/10) ; i++)
       {
          for (int j = LargestHeight - (height*2/10) ; j >= LargestHeight - (height*8/10); j--)
          {
              if ((image[i][j] == 0) && (counter == -1))
                  counter = 0;
              
              if ((image[i][j] == label) && (counter == 0))
                  counter = 1;
              
              if ((image[i][j] == 0) && (counter == 1))
                  counter = 2;
              
              if ((image[i][j] == label) && (counter == 2))
                  counter = 3;
          }
          
          if (counter == 3)
              isExist2 = true;
          
          counter = -1;
       }
       
       counter = 0;
       
       for (int i = SmallestWidth ; i <= SmallestWidth + (width/10) ; i++)
           {
              for (int j = LargestHeight - (height*3/10) ; j >= LargestHeight - (height*7/10); j--)
              {
                if ((image[i][j] == label) && (counter == 0))
                {
                  counter = 1;
                  isExist = true;
                }
              
                if (image[i][j] == label)
                {
                    if (isExist)
                       counter++;
                }
                else
                    isExist = false;

              }
          
               if (longest < counter)
                  longest = counter;
           
               counter = 0;
            }
           
           isExist = longest < height/3;
       
       if ((isExist) && (isExist2))
          object = 2;
       else
       {
           counter = 0;
           isExist = false;
           longest = 0;
           
           for (int i = LargestWidth ; i >= LargestWidth - (width/10) ; i--)
           {
              for (int j = LargestHeight - (height*3/10) ; j >= LargestHeight - (height*7/10); j--)
              {
                if ((image[i][j] == label) && (counter == 0))
                {
                  counter = 1;
                  isExist = true;
                }
              
                if (image[i][j] == label)
                {
                    if (isExist)
                       counter++;
                }
                else
                    isExist = false;

              }
          
               if (longest < counter)
                  longest = counter;
           
               counter = 0;
            }
           
           if (longest < height/3)
               object = 1;
           else
               object = 3;
       }
       
       return object;
   }
   
   public static int OneRegion2Detection(int[][] image, int label)
   {
       int SmallestWidth;
       int SmallestHeight;
       int LargestWidth;
       int LargestHeight;
       int width;
       int height;
       boolean isExist;
       int counter;
       int longest;
       int object;
      
       SmallestWidth = image.length;
       SmallestHeight = image[0].length;
       LargestWidth = 0;
       LargestHeight = 0;
       width = 0;
       height = 0;
       isExist = false;
       counter = -1;
       longest = 0;
       object = 0;

       for (int i = 0 ; i < image.length ; i++)
          for (int j = 0 ; j < image[0].length ; j++)
             if ((label == image[i][j]) && (image[i][j] != 0))
             {
                if (SmallestWidth > i)
                    SmallestWidth = i;

                if (SmallestHeight > j)
                    SmallestHeight = j;

                if (LargestWidth < i)
                    LargestWidth = i;

                if (LargestHeight < j)
                    LargestHeight = j;
             }
       
       width = LargestWidth - SmallestWidth;
       height = LargestHeight - SmallestHeight;
       
       for (int i = LargestHeight ; i >= LargestHeight - (height/10) ; i--)
       {
          for (int j = SmallestWidth ; j <= LargestWidth ; j++)
          {
              if ((image[j][i] == label) && (counter == -1))
                  counter = 0;
              
              if (image[j][i] == 0)
              {
                  if ((!isExist) && (counter == 0))
                  {
                      isExist = true;
                      counter++;
                  }
                  
                  if (isExist)
                      counter++;
              }
              else
                  isExist = false;
          }
          
          if (isExist)
              counter = 0;
          
          if (longest < counter)
              longest = counter;
          
          counter = -1;
       }

       if (longest < (width*3/5))
           object = 8;
       else
           object = 9;
       
       return object;
   }
   
   public static int LineRegionDetection(int[][][] ImagePixel)
   {
      int width = ImagePixel.length;
      int height = ImagePixel[0].length;
      int object = 0;
      boolean isExist = true;
      
      for (int i = (2*width)/5 ; i < (4*width)/5 ; i++)
         if ((ImagePixel[i][height/10][0] < 50) && (ImagePixel[i][height/10][1] < 50) && (ImagePixel[i][height/10][2] < 50))
            isExist = false;
      
      for (int i = (2*width)/5 ; i < (4*width)/5 ; i++)
         if ((ImagePixel[i][height/2][0] < 50) && (ImagePixel[i][height/2][1] < 50) && (ImagePixel[i][height/2][2] < 50))
            isExist = false;
      
      for (int i = (2*width)/5 ; i < (4*width)/5 ; i++)
         if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
            isExist = false;
      
      for (int i = height/10 ; i < (7*height)/10 ; i++)
         if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
             isExist = false;
      
      /*Ngecek 3 garis E*/
      if (isExist)
         object = 10;
      else
      {
         isExist = true;
         for (int i = (width)/10 ; i < (9*width)/10 ; i++)
            if ((ImagePixel[i][height/10][0] < 100) && (ImagePixel[i][height/10][1] < 100) && (ImagePixel[i][height/10][2] < 100))
               isExist = false;
         
         for (int i = height/10 ; i < (5*height)/10 ; i++)
            if ((ImagePixel[width/2][i][0] < 50) && (ImagePixel[width/2][i][1] < 50) && (ImagePixel[width/2][i][2] < 50))
               isExist = false;
         
         /*Ngecek garis T*/
         if (isExist)
            object = 11;
         else
         {
             isExist = true;
             for (int i = (2*width)/10 ; i < (8*width)/10 ; i++)
               if ((ImagePixel[i][height/2][0] < 100) && (ImagePixel[i][height/2][1] < 100) && (ImagePixel[i][height/2][2] < 100))
                  isExist = false;
             
             for (int i = height/10 ; i < (5*height)/10 ; i++)
               if ((ImagePixel[width*3/10][i][0] < 50) && (ImagePixel[width*3/10][i][1] < 50) && (ImagePixel[width*3/10][i][2] < 50))
                  isExist = false;
                 
             for (int i = height/10 ; i < (5*height)/10 ; i++)
               if ((ImagePixel[width*8/10][i][0] < 50) && (ImagePixel[width*8/10][i][1] < 50) && (ImagePixel[width*8/10][i][2] < 50))
                  isExist = false;
      
             /*Ngecek garis tengah H*/
             if (isExist)
                object = 12;
             else
             {
                 isExist = true;
                 for (int i = height/10 ; i < (7*height)/10 ; i++)
                        if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
                           isExist = false;
                     
                     for (int i = (2*width)/5 ; i < (4*width)/5 ; i++)
                        if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
                            isExist = false;
                     
                     for (int i = height/10 ; i < (7*height)/10 ; i++)
                       if ((ImagePixel[width*8/10][i][0] < 50) && (ImagePixel[width*8/10][i][1] < 50) && (ImagePixel[width*8/10][i][2] < 50))
                          isExist = false;           

                     /*U*/
                    if (isExist)
                       object = 24;
                    else
                    {
                 isExist = true;
                 for (int i = height/10 ; i < (8*height)/10 ; i++)
                    if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
                       isExist = false;
                 
                 for (int i = height/10 ; i < (8*height)/10 ; i++)
                    if ((ImagePixel[width*9/10][i][0] < 50) && (ImagePixel[width*9/10][i][1] < 50) && (ImagePixel[width*9/10][i][2] < 50))
                       isExist = false;
                 
                 /*Ngecek garis N, masi belum di test*/
                 if (isExist)
                     object = 13;
                 else
                 {                                          
                     isExist = true;
                     for (int i = height/10 ; i < (8*height)/10 ; i++)
                        if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
                           isExist = false;
                     
                     /*Artinya K, K bisa ditambahin syarat garis kalo perlu*/
                     if (isExist)
                         object = 14;
                     else
                     {
                        isExist = true;
                        boolean isExist2 = true ,isExist3 = true;
                        
                        for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                           if ((ImagePixel[i][height/10][0] < 50) && (ImagePixel[i][height/10][1] < 50) && (ImagePixel[i][height/10][2] < 50))
                              isExist = false;
      
                        for (int i = height/10 ; i < (4*height)/10 ; i++)
                           if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
                              isExist = false;
                        
                        for (int i = height*7/10 ; i < (9*height)/10 ; i++)
                           if ((ImagePixel[width*75/100][i][0] < 50) && (ImagePixel[width*75/100][i][1] < 50) && (ImagePixel[width*75/100][i][2] < 50))
                              isExist = false;
                        
                        for (int i = (2*width)/10 ; i < (8*width)/10 ; i++)
                           if ((ImagePixel[i][height*40/100][0] < 50) && (ImagePixel[i][height*40/100][1] < 50) && (ImagePixel[i][height*40/100][2] < 50))
                              isExist2 = false; 
                        
                        for (int i = (2*width)/10 ; i < (8*width)/10 ; i++)
                           if ((ImagePixel[i][height*35/100][0] < 50) && (ImagePixel[i][height*35/100][1] < 50) && (ImagePixel[i][height*35/100][2] < 50))
                              isExist3 = false; 
      
                        for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                           if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
                              isExist = false; 
                         
                         /*angka 5*/
                         if ((isExist) && ((isExist2) || (isExist3)) )
                             object = 15;
                         else
                         {                      
                            isExist = true;
                            for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                                if ((ImagePixel[i][height/10][0] < 50) && (ImagePixel[i][height/10][1] < 50) && (ImagePixel[i][height/10][2] < 50))
                                    isExist = false;
      
                            for (int i = (2*width)/4 ; i < (8*width)/10 ; i++)
                                if ((ImagePixel[i][height/2][0] < 50) && (ImagePixel[i][height/2][1] < 50) && (ImagePixel[i][height/2][2] < 50))
                                    isExist = false;
      
                            for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                                if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
                                    isExist = false;
                         
                            /*Angka 3*/
                            if (isExist)
                                object = 16;
                            else
                            {
                                isExist = true;
                                for (int i = height/10 ; i < (9*height)/10 ; i++)
                                    if ((ImagePixel[width*8/10][i][0] < 50) && (ImagePixel[width*8/10][i][1] < 50) && (ImagePixel[width*8/10][i][2] < 50))
                                        isExist = false;
                        
                                /*Angka 1*/
                                if (isExist)
                                    object = 17;
                                else
                                {
                                    isExist = true;
                                    for (int i = (width)/10 ; i < (9*width)/10 ; i++)
                                       if ((ImagePixel[i][height/10][0] < 100) && (ImagePixel[i][height/10][1] < 100) && (ImagePixel[i][height/10][2] < 100))
                                          isExist = false;
                                    
                                    /*Angka 7*/
                                    if (isExist)
                                        object = 18;
                                    else
                                    {
                                        isExist = true;
                                        for (int i = (width)/10 ; i < (9*width)/10 ; i++)
                                           if ((ImagePixel[i][height*9/10][0] < 100) && (ImagePixel[i][height*9/10][1] < 100) && (ImagePixel[i][height*9/10][2] < 100))
                                              isExist = false;
                                        
                                        /*Angka 2*/
                                        if (isExist)
                                            object = 19;
                                        else
                                        {
                                            isExist = true;
                                            for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                                                if ((ImagePixel[i][height/10][0] < 50) && (ImagePixel[i][height/10][1] < 50) && (ImagePixel[i][height/10][2] < 50))
                                                    isExist = false;
      
                                            for (int i = height/10 ; i < (4*height)/10 ; i++)
                                                if ((ImagePixel[width*2/10][i][0] < 50) && (ImagePixel[width*2/10][i][1] < 50) && (ImagePixel[width*2/10][i][2] < 50))
                                                    isExist = false;
                        
                                            for (int i = height*7/10 ; i < (9*height)/10 ; i++)
                                                if ((ImagePixel[width*75/100][i][0] < 50) && (ImagePixel[width*75/100][i][1] < 50) && (ImagePixel[width*75/100][i][2] < 50))
                                                    isExist = false;
                             
                                            for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                                                if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
                                                    isExist = false; 
                                            
                                            /*Huruf S*/
                                            if (isExist)
                                                object = 20;
                                            else
                                            {
                                                isExist = true;
                                                for (int i = height*1/10 ; i < (7*height)/10 ; i++)
                                                    if ((ImagePixel[width*75/100][i][0] < 50) && (ImagePixel[width*75/100][i][1] < 50) && (ImagePixel[width*75/100][i][2] < 50))
                                                        isExist = false; 
                                                
                                                for (int i = (3*width)/10 ; i < (6*width)/10 ; i++)
                                                    if ((ImagePixel[i][height*9/10][0] < 50) && (ImagePixel[i][height*9/10][1] < 50) && (ImagePixel[i][height*9/10][2] < 50))
                                                        isExist = false; 
                                                
                                                /*Huruf J*/
                                                if (isExist)
                                                    object = 21;
                                                else
                                                {
                                                    isExist = true;
                                                    for (int i = height*6/10 ; i < height*9/10 ; i++)
                                                        if ((ImagePixel[width/2][i][0] < 50) && (ImagePixel[width/2][i][1] < 50) && (ImagePixel[width/2][i][2] < 50))
                                                            isExist = false;
                                                    
                                                    /*Y*/
                                                    if (isExist)
                                                        object = 25;
                                                    else
                                                    {
                                                    
                                                    isExist = true;
                                                    for (int i = (1*width)/10 ; i < (5*width)/10 ; i++)
                                                        if ((ImagePixel[i][height*8/10][0] < 50) && (ImagePixel[i][height*8/10][1] < 50) && (ImagePixel[i][height*8/10][2] < 50))
                                                            isExist = false;
                                                    
                                                    /*Angka 4*/
                                                    if (isExist)
                                                        object = 22;
                                                    else
                                                        object = 23;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                     }
                     }
                 }
             }
         }
      }
      
      return object;
   }
   
   public static int[] HeightWidth(int[][] image, int label)
   {
       int SmallestWidth;
       int SmallestHeight;
       int LargestWidth;
       int LargestHeight;
       int width;
       int height;
       boolean isExist;
       int counter;
       int longest;
       int object;
      
       SmallestWidth = image.length;
       SmallestHeight = image[0].length;
       LargestWidth = 0;
       LargestHeight = 0;
       width = 0;
       height = 0;
       isExist = false;
       counter = -1;
       longest = 0;
       object = 0;
       
       for (int i = 0 ; i < image.length ; i++)
          for (int j = 0 ; j < image[0].length ; j++)
             if ((label == image[i][j]) && (image[i][j] != 0))
             {
                if (SmallestWidth > i)
                    SmallestWidth = i;

                if (SmallestHeight > j)
                    SmallestHeight = j;

                if (LargestWidth < i)
                    LargestWidth = i;

                if (LargestHeight < j)
                    LargestHeight = j;
             }
       
       int[] wh = new int[2];
       
       wh[0] = LargestWidth - SmallestWidth;
       wh[1] = LargestHeight - SmallestHeight;
       
       return wh;
   }
}
