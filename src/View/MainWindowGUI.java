package View;

import Controller.*;
import Utility.ReadWritePNG;
import Function.FeatureExtraction;
import Function.ObjectClassification;
import Function.ImageProcessing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import org.dcm4che3.io.DicomInputStream;

public class MainWindowGUI extends javax.swing.JFrame {
            
    MainController controller;
    
    public MainWindowGUI(MainController newController)
    {
        controller = newController;
        initialiseUI();   
    }
    
    void showUI()
    {
        initComponents();       
    
        /*Getting the screen size and relocate window to centre screen*/
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    /**
     * Function to initialise the look and feel of the UI. 
     * Java Swing framework handle this code.
     */
    void initialiseUI()
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindowGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MainWindowGUI().setVisible(true);
                MainWindowGUI.this.setVisible(true);
                MainWindowGUI.this.showUI();
                resetUIElement();
                btnShowHistogram.setVisible(false);
            }
        });
    }
    
    /**
     * Resetting all UI element assuming no image has been selected yet.
     * This means no processing can be performed.
     */
    void resetUIElement()
    {
        btn_proc.setEnabled(true);
        list_label.setEnabled(true);
        btn_reset.setEnabled(false);
        chkGreyscale.setSelected(false);
        chkMeanFilter.setEnabled(false);
        chkMedianFilter.setEnabled(false);
        chkSobel.setEnabled(false);
        chkBlobbing.setEnabled(false);
        chkRegionSplitting.setEnabled(false);
        chkFeatureExtraction.setEnabled(false);
        list_label.setVisible(false);
        list_label_scroll.setVisible(false);
        lbl_list.setVisible(false);
        jLabelImageOutput.setIcon(null);
        slider_red.setVisible(false);
        slider_green.setVisible(false);
        slider_blue.setVisible(false);
        lbl_red_color.setVisible(false);
        lbl_green_color.setVisible(false);
        lbl_blue_color.setVisible(false);
        cmb_image.setVisible(false);
        slider_red.setValue(0);
        slider_green.setValue(0);
        slider_blue.setValue(0);
        cmb_image.setEnabled(true);
        cmb_image.setSelectedIndex(0);
        lbl_red_color.setText("R");
        lbl_green_color.setText("G");
        lbl_blue_color.setText("B");
     
    }

    public void showComboBox(ArrayList<String> options)
    {
        cmb_image.setVisible(true);

        DefaultComboBoxModel<String> cmbOptions = new DefaultComboBoxModel<>();

        cmbOptions.addAll(options);
        cmb_image.setModel(cmbOptions);
    }

    public void showSlider(boolean isVisible)
    {
        slider_red.setVisible(isVisible);
        slider_green.setVisible(isVisible);
        slider_blue.setVisible(isVisible);
        lbl_red_color.setVisible(isVisible);
        lbl_green_color.setVisible(isVisible);
        lbl_blue_color.setVisible(isVisible);
    }

    public void showList(ArrayList<String> modelContents)
    {
        /*Show ListView*/
        lbl_list.setVisible(true);
        lbl_list.setText("List of Region");
        list_label.setVisible(true);
        list_label_scroll.setVisible(true);

        /*Populating ListView*/
        DefaultListModel<String> model = new DefaultListModel<String>();
        model.clear();
        model.addAll(modelContents);
        list_label.setModel(model);
        //for (int i = 0 ; i < modelContents.size() ; i++)
           
        /*Show Slider*/
        showSlider(true);

        /*Add Listener when the label is clicked*/
        list_label.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int option = ((JList)listSelectionEvent.getSource()).getSelectedIndex();
                List<String> list = ((JList)listSelectionEvent.getSource()).getSelectedValuesList();
                
                if (list.size() > 0)
                {
                    String selectedItem = list.get(0);
                    controller.listItemOnClick(selectedItem, option);
                }

            }
        });
    }

    public int[] getSliderValue()
    {
        int[] sliderValue = new int[3];
        sliderValue[0] = slider_red.getValue();
        sliderValue[1] = slider_green.getValue();
        sliderValue[2] = slider_blue.getValue();

        return sliderValue;
    }

    public int getComboBoxSelectedItemPosition()
    {
        return cmb_image.getSelectedIndex();
    }
    
    public void populateInputImage(int[][][] imagePixels)
    {
       try
       {
           BufferedImage imageStream = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_ARGB);
         
            /*Going through the pixels array, combining the RGB into one pixel and save it in the image buffer*/
            for (int i = 0 ; i < imagePixels.length ; i++)
               for (int j = 0 ; j < imagePixels[0].length ; j++)
               {
                  Color PixelColor = new Color(imagePixels[i][j][0],imagePixels[i][j][1],imagePixels[i][j][2]);
                  imageStream.setRGB(i, j, PixelColor.getRGB());
               }
            
            Image outputImage = imageStream.getScaledInstance(this.jLabelImageOutput.getWidth(), this.jLabelImageOutput.getHeight(),Image.SCALE_SMOOTH);
            this.jLabelImageInput.setIcon(new ImageIcon(outputImage));
       }
       catch (Exception e)
       {
          JOptionPane.showMessageDialog(this, "Tidak dapat menulis file !", "Error", JOptionPane.ERROR_MESSAGE );
       }
    }
    
    public void populateOutputImage(int[][][] imagePixels, boolean scaleAgainstLabel)
    {
       try
       {
           BufferedImage imageStream = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_ARGB);
         
            /*Going through the pixels array, combining the RGB into one pixel and save it in the image buffer*/
            for (int i = 0 ; i < imagePixels.length ; i++)
               for (int j = 0 ; j < imagePixels[0].length ; j++)
               {
                  Color PixelColor = new Color(imagePixels[i][j][0],imagePixels[i][j][1],imagePixels[i][j][2]);
                  imageStream.setRGB(i, j, PixelColor.getRGB());
               }
            
            Image outputImage = null;
            if (scaleAgainstLabel)
                outputImage = imageStream.getScaledInstance(this.jLabelImageOutput.getWidth(), this.jLabelImageOutput.getHeight(),Image.SCALE_SMOOTH);
            else
                outputImage = imageStream.getScaledInstance(imageStream.getWidth() * 1, imageStream.getHeight() * 1,Image.SCALE_SMOOTH);
            
            this.jLabelImageOutput.setIcon(new ImageIcon(outputImage));
       }
       catch (Exception e)
       {
          JOptionPane.showMessageDialog(this, "Tidak dapat menulis file !", "Error", JOptionPane.ERROR_MESSAGE );
       }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelImageOutput = new javax.swing.JLabel();
        jLabelImageInput = new javax.swing.JLabel();
        chkGreyscale = new javax.swing.JCheckBox();
        chkMeanFilter = new javax.swing.JCheckBox();
        chkMedianFilter = new javax.swing.JCheckBox();
        chkSobel = new javax.swing.JCheckBox();
        chkBlobbing = new javax.swing.JCheckBox();
        chkRegionSplitting = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btn_proc = new javax.swing.JButton();
        list_label_scroll = new javax.swing.JScrollPane();
        list_label = new javax.swing.JList();
        lbl_list = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        chkFeatureExtraction = new javax.swing.JCheckBox();
        slider_red = new javax.swing.JSlider();
        lbl_red_color = new javax.swing.JLabel();
        slider_green = new javax.swing.JSlider();
        slider_blue = new javax.swing.JSlider();
        cmb_image = new javax.swing.JComboBox();
        lbl_green_color = new javax.swing.JLabel();
        lbl_blue_color = new javax.swing.JLabel();
        btnShowHistogram = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuSaveImage = new javax.swing.JMenuItem();
        jMenuLoadImage = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jLoadDicom = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuExit = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenu();

        jMenu2.setText("jMenu2");

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plate Recognitioin");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Input Image");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Output Image ");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        chkGreyscale.setText("Gray Scale");
        chkGreyscale.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkGreyscaleItemStateChanged(evt);
            }
        });

        chkMeanFilter.setText("Mean Filter");

        chkMedianFilter.setText("Median Filter");

        chkSobel.setText("Sobel Edge Detectioin");
        chkSobel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkSobelItemStateChanged(evt);
            }
        });

        chkBlobbing.setText("Blobbing");
        chkBlobbing.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBlobbingItemStateChanged(evt);
            }
        });

        chkRegionSplitting.setText("Region Splitting");
        chkRegionSplitting.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkRegionSplittingItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("List Of Process");

        btn_proc.setText("Process");
        btn_proc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_procActionPerformed(evt);
            }
        });

        list_label.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list_label_scroll.setViewportView(list_label);

        lbl_list.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_list.setText("None");

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        chkFeatureExtraction.setText("Feature Extraction");

        slider_red.setMaximum(255);
        slider_red.setValue(0);
        slider_red.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_redStateChanged(evt);
            }
        });

        slider_green.setMaximum(255);
        slider_green.setValue(0);
        slider_green.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_greenStateChanged(evt);
            }
        });

        slider_blue.setMaximum(255);
        slider_blue.setValue(0);
        slider_blue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_blueStateChanged(evt);
            }
        });

        cmb_image.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_image.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_imageItemStateChanged(evt);
            }
        });

        btnShowHistogram.setText("Histogram");
        btnShowHistogram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowHistogramActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jMenuSaveImage.setText("Save Image");
        jMenuSaveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSaveImageActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuSaveImage);

        jMenuLoadImage.setText("Load Image");
        jMenuLoadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLoadImageActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuLoadImage);
        jMenu1.add(jSeparator5);

        jLoadDicom.setText("Load DICOM");
        jLoadDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoadDicomActionPerformed(evt);
            }
        });
        jMenu1.add(jLoadDicom);
        jMenu1.add(jSeparator6);

        jMenuExit.setText("Exit");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuExit);

        jMenuBar1.add(jMenu1);

        jMenuAbout.setText("About");
        jMenuAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuAboutMousePressed(evt);
            }
        });
        jMenuBar1.add(jMenuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkMeanFilter)
                            .addComponent(chkMedianFilter)
                            .addComponent(chkSobel)
                            .addComponent(chkBlobbing)
                            .addComponent(chkRegionSplitting)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(list_label_scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator3)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lbl_list)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_proc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_reset))
                            .addComponent(chkFeatureExtraction)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slider_red, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_red_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slider_green, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_green_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slider_blue, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_blue_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmb_image, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chkGreyscale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnShowHistogram)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabelImageOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jLabelImageInput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelImageInput, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelImageOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkGreyscale)
                            .addComponent(btnShowHistogram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkMeanFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkMedianFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkSobel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkBlobbing)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkRegionSplitting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkFeatureExtraction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_list)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(slider_red, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_red_color, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lbl_green_color, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(slider_green, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slider_blue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_blue_color, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmb_image, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(list_label_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_proc)
                            .addComponent(btn_reset))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkGreyscaleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkGreyscaleItemStateChanged
        if (chkGreyscale.isSelected())
        {
            chkMeanFilter.setEnabled(true);
            chkMedianFilter.setEnabled(true);
            chkSobel.setEnabled(true);
        }
        else
        {
            chkMeanFilter.setSelected(false);
            chkMedianFilter.setSelected(false);
            chkSobel.setSelected(false);
            chkMeanFilter.setEnabled(false);
            chkMedianFilter.setEnabled(false);
            chkSobel.setEnabled(false);
        }
    }//GEN-LAST:event_chkGreyscaleItemStateChanged

    private void chkSobelItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkSobelItemStateChanged
        if (chkSobel.isSelected())
            chkBlobbing.setEnabled(true);
        else
        {
            chkBlobbing.setSelected(false);
            chkBlobbing.setEnabled(false);
        }
    }//GEN-LAST:event_chkSobelItemStateChanged

    private void jMenuLoadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoadImageActionPerformed
        if (jLabelImageOutput.getIcon() != null)
            btn_resetActionPerformed(evt);
        
        String currPath = Paths.get("").toAbsolutePath().toString();
        
        JFileChooser fileChooser = new JFileChooser(currPath);
        int returnValue = fileChooser.showOpenDialog(this);
        File ImageLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           ImageLocation = fileChooser.getSelectedFile();
           controller.imageLoaded(ImageLocation);
        }
    }//GEN-LAST:event_jMenuLoadImageActionPerformed

    private void jMenuSaveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSaveImageActionPerformed
        if (jLabelImageOutput.getIcon() != null)
        {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        File ImageLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           ImageLocation = fileChooser.getSelectedFile();
           Icon icon = jLabelImageOutput.getIcon();
           controller.WriteImage(ImageLocation, icon);
           JOptionPane.showMessageDialog(this,"Save Succeed!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
        }else {JOptionPane.showMessageDialog(this, "No Output image to be saved.", "Error", JOptionPane.ERROR_MESSAGE );}
    }//GEN-LAST:event_jMenuSaveImageActionPerformed

    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        
        int userChoice = JOptionPane.showConfirmDialog(this, "Are you sure?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (userChoice == 0)
        {
        String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
        String s2 = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev2.png";
        File f = new File(s);
        f.delete();
        f = new File(s2);
        f.delete();
        System.exit(0);}
    }//GEN-LAST:event_jMenuExitActionPerformed

    private void btn_procActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_procActionPerformed
        
        int imagePixel = 1;
        if (controller.isImageLoaded())
        {
            controller.performImageProcessing(chkGreyscale.isSelected(),
                                                chkMeanFilter.isSelected(),
                                                chkMedianFilter.isSelected(),
                                                chkSobel.isSelected(),
                                                chkBlobbing.isSelected(),
                                                chkRegionSplitting.isSelected(),
                                                chkFeatureExtraction.isSelected());/**/
           
           btn_reset.setEnabled(true);
           btn_proc.setEnabled(false);
        }
        else
           JOptionPane.showMessageDialog(this, "Please input image first", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btn_procActionPerformed

    private void chkBlobbingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBlobbingItemStateChanged
        if (chkBlobbing.isSelected())
        {
            chkRegionSplitting.setEnabled(true);
        }
        else
        {
            chkRegionSplitting.setSelected(false);
            chkRegionSplitting.setEnabled(false);
            list_label.setVisible(false);
            lbl_list.setVisible(false);
            list_label_scroll.setVisible(false);
        }
    }//GEN-LAST:event_chkBlobbingItemStateChanged

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        resetUIElement();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void chkRegionSplittingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkRegionSplittingItemStateChanged
        if (chkRegionSplitting.isSelected())
           chkFeatureExtraction.setEnabled(true);
        else
        {
           chkFeatureExtraction.setSelected(false);
           chkFeatureExtraction.setEnabled(false);
        }
    }//GEN-LAST:event_chkRegionSplittingItemStateChanged

    private void slider_redStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_redStateChanged
        lbl_red_color.setText(Integer.toString(slider_red.getValue()));
    }//GEN-LAST:event_slider_redStateChanged

    private void slider_greenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_greenStateChanged
        lbl_green_color.setText(Integer.toString(slider_green.getValue()));
    }//GEN-LAST:event_slider_greenStateChanged

    private void slider_blueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_blueStateChanged
        lbl_blue_color.setText(Integer.toString(slider_blue.getValue()));
    }//GEN-LAST:event_slider_blueStateChanged

    private void cmb_imageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_imageItemStateChanged
        if (cmb_image.getSelectedItem().equals("Result"))
        {
            controller.showRecognisedPlateNumber();
        }
    }//GEN-LAST:event_cmb_imageItemStateChanged

    private void btnShowHistogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowHistogramActionPerformed
        
        //Open Histogram
        //int[] histogramData = intensityHistogram[0];
        int totalBin = 20;
        
        //HistogramUI.showHistogram(histogramData, totalBin);        
    }//GEN-LAST:event_btnShowHistogramActionPerformed

    private void jMenuAboutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuAboutMousePressed
        String Message = "Made by Leo Lie" + '\n' + "Indonesian Plate Recognition Beta V.6.0";
        JOptionPane.showMessageDialog(this, Message);
    }//GEN-LAST:event_jMenuAboutMousePressed

    private void jLoadDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoadDicomActionPerformed
        
        String currPath = Paths.get("").toAbsolutePath().toString();
        
        JFileChooser fileChooser = new JFileChooser(currPath);
        int returnValue = fileChooser.showOpenDialog(this);
        File ImageLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           ImageLocation = fileChooser.getSelectedFile();
           controller.loadDICOMImage(ImageLocation.getAbsolutePath());
        }
    }//GEN-LAST:event_jLoadDicomActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowHistogram;
    private javax.swing.JButton btn_proc;
    private javax.swing.JButton btn_reset;
    private javax.swing.JCheckBox chkBlobbing;
    private javax.swing.JCheckBox chkFeatureExtraction;
    private javax.swing.JCheckBox chkGreyscale;
    private javax.swing.JCheckBox chkMeanFilter;
    private javax.swing.JCheckBox chkMedianFilter;
    private javax.swing.JCheckBox chkRegionSplitting;
    private javax.swing.JCheckBox chkSobel;
    private javax.swing.JComboBox cmb_image;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelImageInput;
    private javax.swing.JLabel jLabelImageOutput;
    private javax.swing.JMenuItem jLoadDicom;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenuAbout;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuLoadImage;
    private javax.swing.JMenuItem jMenuSaveImage;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JLabel lbl_blue_color;
    private javax.swing.JLabel lbl_green_color;
    private javax.swing.JLabel lbl_list;
    private javax.swing.JLabel lbl_red_color;
    private javax.swing.JList list_label;
    private javax.swing.JScrollPane list_label_scroll;
    private javax.swing.JSlider slider_blue;
    private javax.swing.JSlider slider_green;
    private javax.swing.JSlider slider_red;
    // End of variables declaration//GEN-END:variables
    
    
}
