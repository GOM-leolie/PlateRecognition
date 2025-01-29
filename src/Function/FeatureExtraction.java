package Function;

import java.util.ArrayList;

public class FeatureExtraction {
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

}
