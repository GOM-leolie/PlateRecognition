
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainWindowGUI extends javax.swing.JFrame {

    private int[][][] imagePixel;
    private int[][][] imagePixelCopy;
    private int[][][] imagePixelResult;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
    private ListSelectionListener listener;
    private String output;
    private BufferedImage resultImage;
            
    public MainWindowGUI() {
        initComponents();
        imagePixel = null;
        imagePixelCopy = null;
        imagePixelResult = null;
        list_label.setModel(model);
        listener = null;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        model2.addElement("Blob Image");
        model2.addElement("Grayscale Image");
        model2.addElement("Sobel Image");
        cmb_image.setModel(model2);
        
        output = "";
        resultImage = null;
        
        btnShowHistogram.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        img_src_output = new javax.swing.JLabel();
        img_src_input = new javax.swing.JLabel();
        proc_greyscale = new javax.swing.JCheckBox();
        proc_mean_filter = new javax.swing.JCheckBox();
        proc_median_filter = new javax.swing.JCheckBox();
        proc_sobel_edge = new javax.swing.JCheckBox();
        proc_blobbing = new javax.swing.JCheckBox();
        proc_image_split = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        btn_proc = new javax.swing.JButton();
        list_label_scroll = new javax.swing.JScrollPane();
        list_label = new javax.swing.JList();
        lbl_list = new javax.swing.JLabel();
        btn_reset = new javax.swing.JButton();
        proc_feature_extraction = new javax.swing.JCheckBox();
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
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plate Recognitioin");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Input Image");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Output Image ");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        proc_greyscale.setText("Gray Scale");
        proc_greyscale.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proc_greyscaleItemStateChanged(evt);
            }
        });

        proc_mean_filter.setText("Mean Filter");

        proc_median_filter.setText("Median Filter");

        proc_sobel_edge.setText("Sobel Edge Detectioin");
        proc_sobel_edge.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proc_sobel_edgeItemStateChanged(evt);
            }
        });

        proc_blobbing.setText("Blobbing");
        proc_blobbing.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proc_blobbingItemStateChanged(evt);
            }
        });

        proc_image_split.setText("Region Splitting");
        proc_image_split.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proc_image_splitItemStateChanged(evt);
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

        proc_feature_extraction.setText("Feature Extraction");

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

        jMenuItem2.setText("Simpan Gambar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setText("Muat Gambar");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator5);

        jMenuItem4.setText("Cetak Piksel (.txt)");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator6);

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(proc_mean_filter)
                                .addComponent(proc_median_filter)
                                .addComponent(proc_sobel_edge)
                                .addComponent(proc_blobbing)
                                .addComponent(proc_image_split)
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
                                .addComponent(proc_feature_extraction)
                                .addComponent(cmb_image, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(slider_red, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_red_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slider_green, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_green_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(slider_blue, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_blue_color, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(proc_greyscale)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnShowHistogram)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(img_src_output, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(img_src_input, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(img_src_input, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(img_src_output, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(proc_greyscale)
                            .addComponent(btnShowHistogram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_mean_filter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_median_filter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_sobel_edge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_blobbing)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_image_split)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proc_feature_extraction)
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

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       if (imagePixel == null)
       {
          proc_greyscale.setEnabled(false);
          proc_mean_filter.setEnabled(false);
          proc_median_filter.setEnabled(false);
          proc_sobel_edge.setEnabled(false);
          proc_blobbing.setEnabled(false);
          proc_image_split.setEnabled(false);
          proc_feature_extraction.setEnabled(false);
          list_label.setVisible(false);
          list_label_scroll.setVisible(false);
          lbl_list.setVisible(false);
          btn_reset.setEnabled(false);
          slider_red.setVisible(false);
          slider_green.setVisible(false);
          slider_blue.setVisible(false);
          lbl_red_color.setVisible(false);
          lbl_green_color.setVisible(false);
          lbl_blue_color.setVisible(false);
          lbl_red_color.setText("R");
          lbl_green_color.setText("G");
          lbl_blue_color.setText("B");
          cmb_image.setVisible(false);
       }
    }//GEN-LAST:event_formWindowActivated

    private void proc_greyscaleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proc_greyscaleItemStateChanged
        if (proc_greyscale.isSelected())
        {
            proc_mean_filter.setEnabled(true);
            proc_median_filter.setEnabled(true);
            proc_sobel_edge.setEnabled(true);
        }
        else
        {
            proc_mean_filter.setSelected(false);
            proc_median_filter.setSelected(false);
            proc_sobel_edge.setSelected(false);
            proc_mean_filter.setEnabled(false);
            proc_median_filter.setEnabled(false);
            proc_sobel_edge.setEnabled(false);
        }
    }//GEN-LAST:event_proc_greyscaleItemStateChanged

    private void proc_sobel_edgeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proc_sobel_edgeItemStateChanged
        if (proc_sobel_edge.isSelected())
            proc_blobbing.setEnabled(true);
        else
        {
            proc_blobbing.setSelected(false);
            proc_blobbing.setEnabled(false);
        }
    }//GEN-LAST:event_proc_sobel_edgeItemStateChanged

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked

    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (img_src_output.getIcon() != null)
            btn_resetActionPerformed(evt);
        
        String currPath = Paths.get("").toAbsolutePath().toString();
        
        JFileChooser fileChooser = new JFileChooser(currPath);
        int returnValue = fileChooser.showOpenDialog(this);
        File ImageLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           ImageLocation = fileChooser.getSelectedFile();
           InputImage(ImageLocation);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if (img_src_output.getIcon() != null)
        {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        File ImageLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           ImageLocation = fileChooser.getSelectedFile();
           WriteImage(ImageLocation);
           JOptionPane.showMessageDialog(this,"Simpan File Berhasil !", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
        }else {JOptionPane.showMessageDialog(this, "Belum ada gambar yang dapat di save, proses gambar terlebih dahulu !", "Error", JOptionPane.ERROR_MESSAGE );}
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        int userChoice = JOptionPane.showConfirmDialog(this, "Anda Yakin ingin Keluar ?", "Keluar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (userChoice == 0)
        {
        String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
        String s2 = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev2.png";
        File f = new File(s);
        f.delete();
        f = new File(s2);
        f.delete();
        System.exit(0);}
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        String Message = "Dibuat oleh Leo Lie" + '\n' + "Indonesian Plate Recognition Beta V.5.0";
        JOptionPane.showMessageDialog(this, Message);
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        String Message = "Dibuat oleh Leo Lie" + '\n' + "Indonesian Plate Recognition Beta V.5.0";
        JOptionPane.showMessageDialog(this, Message);
    }//GEN-LAST:event_jMenu2MouseClicked

    private void btn_procActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_procActionPerformed
        if (imagePixel != null)
        {
           Process();
           btn_reset.setEnabled(true);
           btn_proc.setEnabled(false);
        }
        else
           JOptionPane.showMessageDialog(this, "Masukkan lokasi gambar terlebih dahulu !", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btn_procActionPerformed

    private void proc_blobbingItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proc_blobbingItemStateChanged
        if (proc_blobbing.isSelected())
        {
            proc_image_split.setEnabled(true);
        }
        else
        {
            proc_image_split.setSelected(false);
            proc_image_split.setEnabled(false);
            list_label.setVisible(false);
            lbl_list.setVisible(false);
            list_label_scroll.setVisible(false);
        }
    }//GEN-LAST:event_proc_blobbingItemStateChanged

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        btn_proc.setEnabled(true);
        resultImage = null;
        list_label.setEnabled(true);
        btn_reset.setEnabled(false);
        proc_greyscale.setSelected(false);
        list_label.removeListSelectionListener(listener);
        model.removeAllElements();
        proc_mean_filter.setEnabled(false);
        proc_median_filter.setEnabled(false);
        proc_sobel_edge.setEnabled(false);
        proc_blobbing.setEnabled(false);
        proc_image_split.setEnabled(false);
        proc_feature_extraction.setEnabled(false);
        list_label.setVisible(false);
        list_label_scroll.setVisible(false);
        lbl_list.setVisible(false);
        img_src_output.setIcon(null);
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
        output = "";
        
        if (model2.getSize() == 4)
           model2.removeElementAt(3);
    }//GEN-LAST:event_btn_resetActionPerformed

    private void proc_image_splitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proc_image_splitItemStateChanged
        if (proc_image_split.isSelected())
           proc_feature_extraction.setEnabled(true);
        else
        {
           proc_feature_extraction.setSelected(false);
           proc_feature_extraction.setEnabled(false);
        }
    }//GEN-LAST:event_proc_image_splitItemStateChanged

    private void slider_redStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_redStateChanged
        lbl_red_color.setText(Integer.toString(slider_red.getValue()));
    }//GEN-LAST:event_slider_redStateChanged

    private void slider_greenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_greenStateChanged
        lbl_green_color.setText(Integer.toString(slider_green.getValue()));
    }//GEN-LAST:event_slider_greenStateChanged

    private void slider_blueStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_blueStateChanged
        lbl_blue_color.setText(Integer.toString(slider_blue.getValue()));
    }//GEN-LAST:event_slider_blueStateChanged

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        if (img_src_output.getIcon() != null)
        {
        
        try{
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        File FileLocation = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
           FileLocation = fileChooser.getSelectedFile();
           String location = FileLocation.getAbsolutePath();         
            
           PrintWriter stream = new PrintWriter(location + ".txt");
           int counter = 0;
        
           stream.println("Piksel akan berurut dari kiri atas ke kanan bawah dengan urutan piksel merah, piksel biru, dan piksel hijau.");
           stream.println("Setelah mencapai kolom ke 100, piksel dilanjutkan di baris selanjutnya.");
           stream.println("By : Agusalim");
        
           for (int i = 0 ; i < imagePixelCopy.length ; i++)
              for (int j = 0 ; j < imagePixelCopy[0].length ; j++)
                for (int k = 0 ; k < 3 ; k++)
                {   
                   if (counter < 100)
                       stream.print(imagePixelCopy[i][j][k] + " ");
                   else
                       stream.println(imagePixelCopy[i][j][k]);
                }
        stream.close();
        JOptionPane.showMessageDialog(this,"Simpan File Berhasil !", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file !", "Error", JOptionPane.ERROR_MESSAGE );
        }
        }
        else
            JOptionPane.showMessageDialog(this, "Tidak ada yang bisa dicetak, proses gambar terlebih dahulu !", "Error", JOptionPane.ERROR_MESSAGE );
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void cmb_imageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_imageItemStateChanged
        if (cmb_image.getSelectedItem().equals("Result"))
        {
            cmb_image.setEnabled(false);

            list_label.setSelectedIndex(-1);
            list_label.setEnabled(false);
            model.removeAllElements();
            model.addElement(output);
            
            img_src_output.setIcon(null);
            
            ImageIcon newImg = new ImageIcon(resultImage);

            img_src_output.setIcon(newImg);
            
            Color PixelColor;
            imagePixelCopy = new int[imagePixel.length][imagePixel[0].length][3];
            
            for (int i = 0 ; i < resultImage.getHeight() ; i++)
            {
               for (int j = 0 ; j < resultImage.getWidth() ; j++)
               {
                  PixelColor = new Color(resultImage.getRGB(j,i));
                  imagePixelCopy[j][i][0] = PixelColor.getRed();
                  imagePixelCopy[j][i][1] = PixelColor.getGreen();
                  imagePixelCopy[j][i][2] = PixelColor.getBlue();
               }
            }

        }
    }//GEN-LAST:event_cmb_imageItemStateChanged

    private void btnShowHistogramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowHistogramActionPerformed
        
        //Open Histogram
        
        
    }//GEN-LAST:event_btnShowHistogramActionPerformed

    public static void main(String args[]) {
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
                new MainWindowGUI().setVisible(true);
            }
        });        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowHistogram;
    private javax.swing.JButton btn_proc;
    private javax.swing.JButton btn_reset;
    private javax.swing.JComboBox cmb_image;
    private javax.swing.JLabel img_src_input;
    private javax.swing.JLabel img_src_output;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
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
    private javax.swing.JCheckBox proc_blobbing;
    private javax.swing.JCheckBox proc_feature_extraction;
    private javax.swing.JCheckBox proc_greyscale;
    private javax.swing.JCheckBox proc_image_split;
    private javax.swing.JCheckBox proc_mean_filter;
    private javax.swing.JCheckBox proc_median_filter;
    private javax.swing.JCheckBox proc_sobel_edge;
    private javax.swing.JSlider slider_blue;
    private javax.swing.JSlider slider_green;
    private javax.swing.JSlider slider_red;
    // End of variables declaration//GEN-END:variables

    /*code --------------------- below*/
    
    public void InputImage(File Location)
    {
       try
       {
          Image rawInputImage = ImageIO.read(Location);
          Image inputImage = rawInputImage.getScaledInstance(this.img_src_input.getWidth(), this.img_src_input.getHeight(),Image.SCALE_SMOOTH);
          this.img_src_input.setIcon(new ImageIcon(inputImage));
          imagePixel = ReadWritePNG.ReadPNG(Location.toPath().toString());
          proc_greyscale.setEnabled(true);
       }
       catch (Exception e)
       {
          JOptionPane.showMessageDialog(this, "File tidak dapat dibuka !", "Error", JOptionPane.ERROR_MESSAGE );
       }
    }
    
    public void OutputImage(int type)
    {
       try
       {
          if (type == 1)
          {
             String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
             ReadWritePNG.WritePNG(s, imagePixelCopy);
             Image rawOutputImage = ImageIO.read(new File(s));
             Image outputImage = rawOutputImage.getScaledInstance(this.img_src_output.getWidth(), this.img_src_output.getHeight(),Image.SCALE_SMOOTH);
             this.img_src_output.setIcon(new ImageIcon(outputImage));
          }
          else
          {
             String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
             ReadWritePNG.WritePNG(s, imagePixelCopy);
             Image rawOutputImage = ImageIO.read(new File(s));
             Image outputImage = rawOutputImage.getScaledInstance(rawOutputImage.getWidth(this)*1, rawOutputImage.getHeight(this)*1,Image.SCALE_SMOOTH);
             this.img_src_output.setIcon(new ImageIcon(outputImage));
          }
       }
       catch (Exception e)
       {
          JOptionPane.showMessageDialog(this, "Tidak dapat menulis file !", "Error", JOptionPane.ERROR_MESSAGE );
       }
    }
    
    public void WriteImage(File Location)
    {
        try
        {
           ReadWritePNG.WritePNG(Location.toPath().toString()+".png", imagePixelCopy);
        }
        catch (Exception e)
        {
           JOptionPane.showMessageDialog(this, "Tidak dapat menulis file !", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }
    
    public void Process()
    {
        imagePixelCopy = new int[imagePixel.length][imagePixel[0].length][3];
        imagePixelResult = new int[imagePixel.length][imagePixel[0].length][3];
        int[][][] tempPixel = new int[imagePixel.length][imagePixel[0].length][3];
        int[][][] meanFilter = new int[imagePixel.length][imagePixel[0].length][3];
        int[][][] medianFilter = new int[imagePixel.length][imagePixel[0].length][3];
        int[][][] sobel = new int[imagePixel.length][imagePixel[0].length][3];
        final int[][][] split = new int[imagePixel.length][imagePixel[0].length][3];
        int[][] label = null;
        ArrayList<Integer> exist = new ArrayList<Integer>();
        ArrayList<int[][][]> imageBlob = new ArrayList<int[][][]>();
        ArrayList<int[][][]> imageGreyscale = new ArrayList<int[][][]>();
        ArrayList<int[][][]> imageSobel = new ArrayList<int[][][]>();
        tempPixel = imagePixel.clone();
        
        if (proc_greyscale.isSelected())
        {
            ImageProcessing.GreyScale(tempPixel);
            imagePixelCopy = tempPixel;
            btnShowHistogram.setVisible(true);
        }
        
        if (proc_median_filter.isSelected())
        {
            ImageProcessing.MedianFilter(tempPixel, medianFilter);
            imagePixelCopy = medianFilter;
        }
        
        if (proc_mean_filter.isSelected())
            if (proc_median_filter.isSelected())
            {
                ImageProcessing.MeanFilter(medianFilter, meanFilter);
                imagePixelCopy = meanFilter;
            }
            else
            {
                ImageProcessing.MeanFilter(imagePixelCopy, meanFilter);
                imagePixelCopy = meanFilter;
            }
        
        if (proc_sobel_edge.isSelected())
        {
            if ((proc_median_filter.isSelected()) && (proc_mean_filter.isSelected()))
                ImageProcessing.SobelEdgeDetection(meanFilter, sobel);
            else if (proc_median_filter.isSelected())
                    ImageProcessing.SobelEdgeDetection(medianFilter, sobel);
            else if (proc_mean_filter.isSelected())
                    ImageProcessing.SobelEdgeDetection(meanFilter, sobel);
            else
                ImageProcessing.SobelEdgeDetection(tempPixel, sobel);
            
            imagePixelCopy = sobel;
        }
        
        if (proc_blobbing.isSelected())
        {           
            label = FeatureExtraction.Blobbing(sobel);
            ObjectClassification.LabelCounting(label, exist);
            ObjectClassification.LabelElimination(label, exist);
            
            if (exist.size() > 13)
                ObjectClassification.LabelElimination(label, exist);
            
            final int[][] label2 = new int[label.length][label[0].length];
            
            for (int i = 0 ; i < label.length ; i++)
               for (int j = 0 ; j < label[0].length ; j++)
                   label2[i][j] = label[i][j];
            
            lbl_list.setVisible(true);
            lbl_list.setText("List of Region");
            list_label.setVisible(true);
            list_label_scroll.setVisible(true);
            
            for (int i = 0 ; i < exist.size() ; i++)
               model.addElement(Integer.toString(exist.get(i)));
            
            if (!proc_image_split.isSelected())
            {
               slider_red.setVisible(true);
               slider_green.setVisible(true);
               slider_blue.setVisible(true);
               lbl_red_color.setVisible(true);
               lbl_green_color.setVisible(true);
               lbl_blue_color.setVisible(true);
                
               listener = new ListSelectionListener()
               {
                  public void valueChanged(ListSelectionEvent ev)
                  {
                     int option;
                     Object[] list = ((JList)ev.getSource()).getSelectedValues();
                     option = Integer.parseInt((String)list[0]);
                  
                     for (int i = 0 ; i < label2.length ; i++)
                        for (int j = 0 ; j < label2[0].length ; j++)
                           if (label2[i][j] == option)
                           {
                              split[i][j][0] = slider_red.getValue();
                              split[i][j][1] = slider_green.getValue();
                              split[i][j][2] = slider_blue.getValue();
                           }
                  
                     imagePixelCopy = split;
                     OutputImage(1);
                  
                   }
               }; 
                
               list_label.addListSelectionListener(listener);              
            }
        }          
        
        if (proc_image_split.isSelected())
        {
            ObjectClassification.ImageSplit(tempPixel, imagePixelCopy, label, exist, imageBlob, imageSobel, imageGreyscale);
            model.removeAllElements();
            
            for (int i = 0 ; i < imageBlob.size() ; i++)
                model.addElement(Integer.toString(i+1));
            
            cmb_image.setVisible(true);
            
            final ArrayList<int[][][]> image2 = new ArrayList<int[][][]>(imageSobel);
            final ArrayList<int[][][]> image3 = new ArrayList<int[][][]>(imageBlob);
            final ArrayList<int[][][]> image4 = new ArrayList<int[][][]>(imageGreyscale);
            
            if (!proc_feature_extraction.isSelected())
            {                
               listener = new ListSelectionListener()
               {
               public void valueChanged(ListSelectionEvent ev)
               {
                     int option;
                     Object[] list = ((JList)ev.getSource()).getSelectedValues();
                     option = Integer.parseInt((String)list[0]);
                  
                     if (cmb_image.getSelectedIndex() == 0)
                        imagePixelCopy = image3.get(option-1);
                     else if (cmb_image.getSelectedIndex() == 1)
                        imagePixelCopy = image4.get(option-1);
                     else if (cmb_image.getSelectedIndex() == 2)
                         imagePixelCopy = image2.get(option-1);
                     
                     OutputImage(2);
                  
               }
               };
                
               list_label.addListSelectionListener(listener);
            }
            
        }
        
        if (proc_feature_extraction.isSelected())
        {
            ArrayList<String> name = new ArrayList<String>(); 
            final ArrayList<String> identifiedName = new ArrayList<String>();
            model.removeAllElements();
            
            /*New section to add image together*/            
            model2.addElement("Result");
            
            for (int i = 0 ; i < imageSobel.size() ; i++)
            {
               String tempName;
               tempName = ObjectClassification.ImageClassification(imageBlob, imageSobel, imageGreyscale, tempPixel.length, tempPixel[0].length, i);
               name.add(tempName);
               
               if (!tempName.equals("Unidentified") && (!tempName.equals("")))
                   identifiedName.add(tempName);
            }
            
            for (int i = 0 ; i < identifiedName.size() ; i++)
                output += identifiedName.get(i);
            
            for (int i = 0 ; i < name.size() ; i++)
                model.addElement(name.get(i));
            
            for (int i = 0 ; i < name.size() ; i++)
            {
                if ((!name.get(i).equals("Unidentified")) && (!name.get(i).equals("")))
                {
                    imagePixelResult = imageGreyscale.get(i);
                    if (resultImage == null)
                    {
                        try{
                        String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
                        ReadWritePNG.WritePNG(s, imagePixelResult);
                        resultImage = ImageIO.read(new File(s));}
                        catch(Exception e) {System.out.println("Error");}
                    }
                    else
                       resultImage = test(resultImage);
                }
            }
            
            /*end of new section*/
            
            cmb_image.setVisible(true);
            
            final ArrayList<int[][][]> image2 = new ArrayList<int[][][]>(imageSobel);
            final ArrayList<int[][][]> image3 = new ArrayList<int[][][]>(imageBlob);
            final ArrayList<int[][][]> image4 = new ArrayList<int[][][]>(imageGreyscale);
             
            listener = new ListSelectionListener()
            {
               public void valueChanged(ListSelectionEvent ev)
               {
                  int option;
                  option = ((JList)ev.getSource()).getSelectedIndex();
                  
                  if (cmb_image.getSelectedIndex() == 0)  
                     imagePixelCopy = image3.get(option);
                  else if (cmb_image.getSelectedIndex() == 1)
                     imagePixelCopy = image4.get(option);
                  else if (cmb_image.getSelectedIndex() == 2)
                     imagePixelCopy = image2.get(option);
                  
                  
                  OutputImage(2);
                  
                  //test();
                  
               }
            };
            
            list_label.addListSelectionListener(listener);
        }
        
        OutputImage(1);
    }

    public BufferedImage test(BufferedImage temp)
    {  
        BufferedImage image = null;
        
        try{
        //String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev3.png";
        String s2 = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev2.png";
        //ReadWritePNG.WritePNG(s, imagePixelResult);
        ReadWritePNG.WritePNG(s2, imagePixelResult);
        //Image rawOutputImage = ImageIO.read(new File(s));
        Image rawOutputImage = (Image) temp;
        Image rawOutputImage2 = ImageIO.read(new File(s2));
        
        Image outputImage = rawOutputImage.getScaledInstance(rawOutputImage.getWidth(this)*1, rawOutputImage.getHeight(this)*1,Image.SCALE_SMOOTH);
        Image outputImage2 = rawOutputImage2.getScaledInstance(rawOutputImage2.getWidth(this)*1, rawOutputImage2.getHeight(this)*1,Image.SCALE_SMOOTH);
        
        int w = outputImage.getWidth(this) + outputImage2.getWidth(this);
        int h = Math.max(outputImage.getHeight(this), outputImage2.getHeight(this));
        image = new BufferedImage(w, h,  BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.drawImage(outputImage, 0, 0, null);
        g2.drawImage(outputImage2, outputImage.getWidth(this), 0, null);
        g2.dispose();

        //ImageIcon newImg = new ImageIcon(image);

        //img_src_output.setIcon(newImg);
        }
        
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan di dalam sistem, silahkan mencoba me-reload kembali gambar dan ulangi memproses !", "Error", JOptionPane.ERROR_MESSAGE );
        }
        
        return image;
    }
}
