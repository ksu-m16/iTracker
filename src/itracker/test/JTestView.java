/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itracker.test;

import itracker.service.ServiceController;
import itracker.util.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KerneL
 */
public class JTestView extends javax.swing.JFrame {

    /**
     * Creates new form JTestView
     */
    public JTestView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgLoc = new javax.swing.ButtonGroup();
        bgFreq = new javax.swing.ButtonGroup();
        bgSpd = new javax.swing.ButtonGroup();
        bgAcc = new javax.swing.ButtonGroup();
        bgBut = new javax.swing.ButtonGroup();
        bgPwr = new javax.swing.ButtonGroup();
        bgCar = new javax.swing.ButtonGroup();
        bgBat = new javax.swing.ButtonGroup();
        bgNet = new javax.swing.ButtonGroup();
        bgFile = new javax.swing.ButtonGroup();
        bgTime = new javax.swing.ButtonGroup();
        Status = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jlLoc = new javax.swing.JLabel();
        jrbLocOn = new javax.swing.JRadioButton();
        jrbLocOff = new javax.swing.JRadioButton();
        jlFreq = new javax.swing.JLabel();
        jrbFreqNormal = new javax.swing.JRadioButton();
        jrbFreqLow = new javax.swing.JRadioButton();
        jlSpd = new javax.swing.JLabel();
        jrbSpdNormal = new javax.swing.JRadioButton();
        jrbSpdLow = new javax.swing.JRadioButton();
        jlAcc = new javax.swing.JLabel();
        jrbAccHigh = new javax.swing.JRadioButton();
        jrbAccLow = new javax.swing.JRadioButton();
        jlBut = new javax.swing.JLabel();
        jrbButOn = new javax.swing.JRadioButton();
        jrbButOff = new javax.swing.JRadioButton();
        jlPwr = new javax.swing.JLabel();
        jrbPwrOn = new javax.swing.JRadioButton();
        jrbPwrOff = new javax.swing.JRadioButton();
        jlCar = new javax.swing.JLabel();
        jrbCarOn = new javax.swing.JRadioButton();
        jrbCarOff = new javax.swing.JRadioButton();
        jlBat = new javax.swing.JLabel();
        jrbBatNormal = new javax.swing.JRadioButton();
        jrbBatLow = new javax.swing.JRadioButton();
        jlNet = new javax.swing.JLabel();
        jrbNetOn = new javax.swing.JRadioButton();
        jrbNetOff = new javax.swing.JRadioButton();
        jlFile = new javax.swing.JLabel();
        jrbFileOn = new javax.swing.JRadioButton();
        jrbFileOff = new javax.swing.JRadioButton();
        jlTime = new javax.swing.JLabel();
        jrbTime0 = new javax.swing.JRadioButton();
        jrbTime5 = new javax.swing.JRadioButton();
        jrbTime10 = new javax.swing.JRadioButton();
        jrbTime20 = new javax.swing.JRadioButton();
        jrbTime1 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Status.setText("jLabel1");
        Status.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Status.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("jLabel1");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("");
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jlLoc.setText("Location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlLoc, gridBagConstraints);

        bgLoc.add(jrbLocOn);
        jrbLocOn.setText("Enabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbLocOn, gridBagConstraints);

        bgLoc.add(jrbLocOff);
        jrbLocOff.setText("Disabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbLocOff, gridBagConstraints);

        jlFreq.setText("Frequency");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlFreq, gridBagConstraints);

        bgFreq.add(jrbFreqNormal);
        jrbFreqNormal.setText("Normal (1Hz)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbFreqNormal, gridBagConstraints);

        bgFreq.add(jrbFreqLow);
        jrbFreqLow.setText("Low(0.5Hz)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbFreqLow, gridBagConstraints);

        jlSpd.setText("Speed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlSpd, gridBagConstraints);

        bgSpd.add(jrbSpdNormal);
        jrbSpdNormal.setText("Normal (5 m/s)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbSpdNormal, gridBagConstraints);

        bgSpd.add(jrbSpdLow);
        jrbSpdLow.setText("None (0 m/s)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbSpdLow, gridBagConstraints);

        jlAcc.setText("Accuracy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlAcc, gridBagConstraints);

        bgAcc.add(jrbAccHigh);
        jrbAccHigh.setText("High (10)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbAccHigh, gridBagConstraints);

        bgAcc.add(jrbAccLow);
        jrbAccLow.setText("Low (50)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbAccLow, gridBagConstraints);

        jlBut.setText("Button");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlBut, gridBagConstraints);

        bgBut.add(jrbButOn);
        jrbButOn.setText("On");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbButOn, gridBagConstraints);

        bgBut.add(jrbButOff);
        jrbButOff.setText("Off");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbButOff, gridBagConstraints);

        jlPwr.setText("Power");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlPwr, gridBagConstraints);

        bgPwr.add(jrbPwrOn);
        jrbPwrOn.setText("On");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbPwrOn, gridBagConstraints);

        bgPwr.add(jrbPwrOff);
        jrbPwrOff.setText("Off");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbPwrOff, gridBagConstraints);

        jlCar.setText("Carkit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlCar, gridBagConstraints);

        bgCar.add(jrbCarOn);
        jrbCarOn.setText("On");
        jrbCarOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCarOnActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbCarOn, gridBagConstraints);

        bgCar.add(jrbCarOff);
        jrbCarOff.setText("Off");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbCarOff, gridBagConstraints);

        jlBat.setText("Battery");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlBat, gridBagConstraints);

        bgBat.add(jrbBatNormal);
        jrbBatNormal.setText("Normal (50%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbBatNormal, gridBagConstraints);

        bgBat.add(jrbBatLow);
        jrbBatLow.setText("Empty (10%)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbBatLow, gridBagConstraints);

        jlNet.setText("Network");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlNet, gridBagConstraints);

        bgNet.add(jrbNetOn);
        jrbNetOn.setText("Enabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbNetOn, gridBagConstraints);

        bgNet.add(jrbNetOff);
        jrbNetOff.setText("Disabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbNetOff, gridBagConstraints);

        jlFile.setText("Filesystem");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlFile, gridBagConstraints);

        bgFile.add(jrbFileOn);
        jrbFileOn.setText("Enabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbFileOn, gridBagConstraints);

        bgFile.add(jrbFileOff);
        jrbFileOff.setText("Disabled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbFileOff, gridBagConstraints);

        jlTime.setText("Time");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jlTime, gridBagConstraints);

        bgTime.add(jrbTime0);
        jrbTime0.setText("pause");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbTime0, gridBagConstraints);

        bgTime.add(jrbTime5);
        jrbTime5.setText("x5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbTime5, gridBagConstraints);

        bgTime.add(jrbTime10);
        jrbTime10.setText("x10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbTime10, gridBagConstraints);

        bgTime.add(jrbTime20);
        jrbTime20.setText("x20");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbTime20, gridBagConstraints);

        bgTime.add(jrbTime1);
        jrbTime1.setText("x1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel1.add(jrbTime1, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Status, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Status, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jrbCarOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCarOnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbCarOnActionPerformed

    
    ServiceController service = new ServiceController();    
    
    static {
        Time.setTimeProvider(Time.DebugTime.getInstance());        
    }
    Time.DebugTime time = Time.DebugTime.getInstance();
    int timeFactor = 0;
    Thread generator = new Thread() {
        @Override
        public void run() {
            for (int i = 0; i < timeFactor * 4; ++i) {
                try {
                    sleep(25);
                } catch (InterruptedException ex) {                    
                }
                time.time += 250;
            }
            
            
        }        
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JTestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JTestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JTestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JTestView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new JTestView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status;
    private javax.swing.ButtonGroup bgAcc;
    private javax.swing.ButtonGroup bgBat;
    private javax.swing.ButtonGroup bgBut;
    private javax.swing.ButtonGroup bgCar;
    private javax.swing.ButtonGroup bgFile;
    private javax.swing.ButtonGroup bgFreq;
    private javax.swing.ButtonGroup bgLoc;
    private javax.swing.ButtonGroup bgNet;
    private javax.swing.ButtonGroup bgPwr;
    private javax.swing.ButtonGroup bgSpd;
    private javax.swing.ButtonGroup bgTime;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlAcc;
    private javax.swing.JLabel jlBat;
    private javax.swing.JLabel jlBut;
    private javax.swing.JLabel jlCar;
    private javax.swing.JLabel jlFile;
    private javax.swing.JLabel jlFreq;
    private javax.swing.JLabel jlLoc;
    private javax.swing.JLabel jlNet;
    private javax.swing.JLabel jlPwr;
    private javax.swing.JLabel jlSpd;
    private javax.swing.JLabel jlTime;
    private javax.swing.JRadioButton jrbAccHigh;
    private javax.swing.JRadioButton jrbAccLow;
    private javax.swing.JRadioButton jrbBatLow;
    private javax.swing.JRadioButton jrbBatNormal;
    private javax.swing.JRadioButton jrbButOff;
    private javax.swing.JRadioButton jrbButOn;
    private javax.swing.JRadioButton jrbCarOff;
    private javax.swing.JRadioButton jrbCarOn;
    private javax.swing.JRadioButton jrbFileOff;
    private javax.swing.JRadioButton jrbFileOn;
    private javax.swing.JRadioButton jrbFreqLow;
    private javax.swing.JRadioButton jrbFreqNormal;
    private javax.swing.JRadioButton jrbLocOff;
    private javax.swing.JRadioButton jrbLocOn;
    private javax.swing.JRadioButton jrbNetOff;
    private javax.swing.JRadioButton jrbNetOn;
    private javax.swing.JRadioButton jrbPwrOff;
    private javax.swing.JRadioButton jrbPwrOn;
    private javax.swing.JRadioButton jrbSpdLow;
    private javax.swing.JRadioButton jrbSpdNormal;
    private javax.swing.JRadioButton jrbTime0;
    private javax.swing.JRadioButton jrbTime1;
    private javax.swing.JRadioButton jrbTime10;
    private javax.swing.JRadioButton jrbTime20;
    private javax.swing.JRadioButton jrbTime5;
    // End of variables declaration//GEN-END:variables
}
