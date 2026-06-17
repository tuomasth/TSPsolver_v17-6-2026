package tsp_solver_uef;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 * TSP Solver by Tuomas Hyvönen, Java file 7 of 7 (also notice the ".form" file / "Design" tab on NetBeans IDE) 
 * 
 * The user interface class. The application has 4 areas of text, one of them is editable. 
 * The editable one is, obviously, for editing the graph. The others are results, history and instructions. 
 * Main commands: new file, open file, save file, exit, run the algorithms, about the software. 
 * 
 * Open source Java code, feel free to edit and try your own improvements. 
 * Tested with Windows 11 
 * Apache NetBeans 
 * Java JDK 17.0.12 64bit 
 * 
 * @author Tuomas Hyvönen 
 * @version 2.3
 */
public class User_interface extends javax.swing.JFrame {
    private final String VERSION = "v2.3 (2026)";
    
    /**
     * Constructor that creates a new form "User_interface".
     */
    public User_interface() {
        initComponents();
        jTextArea3.setText(" Choose FILE - NEW to begin creating your new"
                + "\n mathematical network (=graph)."
                + "\n\n If you already have a graph stored somewhere,"
                + "\n choose FILE - OPEN or copy-paste the text"
                + "\n to the Editor field on the left."
                + "\n\n Use keys F2...F6 to run the algorithms. "
                + "\n FN-key needs to be pressed down on some laptops. "
                + "\n\n Please note that for huge graphs, computing"
                + "\n the TSP solution will consume a lot of time"
                + "\n and the window will freeze."
                + "\n\n Use the Operating System's own commands"
                + "\n to terminate this application in extreme situations."
                + "\n - WINDOWS: you can press Control + Alt + Delete "
                + "\n   and then choose the Task Manager."
                + "\n - LINUX: you can press Alt + F2 or Ctrl + Alt + F1...F6 "
                + "\n   and then type kill commands."
                + "\n - MAC OS: you can press Option + Command + Esc.");
        setWindow();
    }
    
    /**
     * Sets properties of the user interface window and detects pressing enter.
     */
    public final void setWindow() {
        setLocationRelativeTo(null);
        setTitle("TSP Solver " + VERSION);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter(){
            /**
             * windowClosing
             * @param we WindowEvent
             */
            @Override
            public void windowClosing(WindowEvent we){
                if(!jTextArea1.getText().equals("")) { 
                    int choice = JOptionPane.showConfirmDialog(null, 
                                "Exit TSP Solver? Unsaved data will be lost.", 
                                "Exit", 
                                JOptionPane.YES_NO_OPTION);
                    if(choice == 0)
                        System.exit(0);
                }
                else {
                    System.exit(0); // No need to ask if the user wants to exit 
                }                   // since the textarea is empty. 
            }
    	});
        jTextArea1.addKeyListener(new KeyAdapter() {
            /**
             * keyPressed
             * @param e KeyEvent
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER && 
                        jTextArea1.getText().length() > 0 ) {
                    int number = 0;
                    try {
                        int offset=jTextArea1.getLineOfOffset(
                                jTextArea1.getCaretPosition());
                        int start=jTextArea1.getLineStartOffset(offset);
                        int end=jTextArea1.getLineEndOffset(offset);
                        if(jTextArea1.getText(start,(end-start)).length() < 1) {
                            return;
                        }
                        if(jTextArea1.getText(start, 
                                (end-start)).charAt(0) > 48 &&      // from '1' 
                           jTextArea1.getText(start, 
                                   (end-start)).charAt(0) < 58) {   // to '9' 
                            String s = jTextArea1.getText(start, (end-start));
                            Matcher matcher = Pattern.compile("\\d+").matcher(s);
                            matcher.find();
                            try{
                                number = Integer.parseInt(matcher.group());
                                if(number < Integer.MAX_VALUE) {
                                    number++;
                                }
                            }
                            catch(NumberFormatException ex) {
                                System.out.println(ex.getMessage());
                            }
                            jTextArea1.insert(number + " \n", 
                                    jTextArea1.getCaretPosition()+1);
                        }
                    }
                    catch (BadLocationException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            /**
             * keyTyped
             * @param e KeyEvent
             */
            @Override
            public void keyTyped(KeyEvent e) {
            }
            /**
             * keyReleased
             * @param e KeyEvent
             */
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    if(e.getKeyCode() == KeyEvent.VK_ENTER && 
                       jTextArea1.getCaretPosition() != 
                       jTextArea1.getDocument().getLength()) {
                                jTextArea1.replaceRange("", jTextArea1.getCaretPosition(), 
                                jTextArea1.getCaretPosition()+1);

                                int offset=jTextArea1.getLineOfOffset(
                                    jTextArea1.getCaretPosition());

                            int start=jTextArea1.getLineStartOffset(offset);
                            int end=jTextArea1.getLineEndOffset(offset);
                            if(jTextArea1.getText(start, 
                               (end-start)).charAt(0) > 48 &&      // from '1' 
                               jTextArea1.getText(start, 
                               (end-start)).charAt(0) < 58) {   // to '9' 
                                    jTextArea1.setCaretPosition(jTextArea1.getText().length()-4);
                            }
                            else {
                                jTextArea1.replaceRange("\n", jTextArea1.getCaretPosition(), 
                                jTextArea1.getCaretPosition());
                            }
                    }
                }
                catch(BadLocationException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaHistory = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemSave = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenuRun = new javax.swing.JMenu();
        jMenuItemNNH = new javax.swing.JMenuItem();
        jMenuItem2MST = new javax.swing.JMenuItem();
        jMenuItemCHH = new javax.swing.JMenuItem();
        jMenuItemSOM_CH_NN = new javax.swing.JMenuItem();
        jMenuItemLK3 = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.addTab("Editor", jScrollPane1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jTextAreaHistory.setEditable(false);
        jTextAreaHistory.setColumns(20);
        jTextAreaHistory.setRows(5);
        jScrollPane4.setViewportView(jTextAreaHistory);

        jMenuFile.setText("File");

        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemNew.setText("New");
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpen);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSave);
        jMenuFile.add(jSeparator2);

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBar1.add(jMenuFile);

        jMenuRun.setText("Run");

        jMenuItemNNH.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItemNNH.setText("\"Nearest neighbor heuristic\" (NNH); get a quick solution without complex tricks");
        jMenuItemNNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNNHActionPerformed(evt);
            }
        });
        jMenuRun.add(jMenuItemNNH);

        jMenuItem2MST.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem2MST.setText("\"Double minimum spanning tree heuristic with Prim\" (2MST); tour around the Minimum Spanning Tree, max 2 times the optimal tour");
        jMenuItem2MST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2MSTActionPerformed(evt);
            }
        });
        jMenuRun.add(jMenuItem2MST);

        jMenuItemCHH.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItemCHH.setText("\"Convex hull heuristic\" (CHH); rubber band around everything, then connect the inner nodes, same input = same result");
        jMenuItemCHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCHHActionPerformed(evt);
            }
        });
        jMenuRun.add(jMenuItemCHH);

        jMenuItemSOM_CH_NN.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItemSOM_CH_NN.setText("\"Kohonen self-organizing map with at least the hull centroids and NNH\" (SOM-NN-OPT); very random - form SOM, then NN for clusters, finally opt moves");
        jMenuItemSOM_CH_NN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSOM_CH_NNActionPerformed(evt);
            }
        });
        jMenuRun.add(jMenuItemSOM_CH_NN);

        jMenuItemLK3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItemLK3.setText("\"Super-OPT\"; try some NNHs, CHH and SOM-NN-OPT, choose the best and try to improve with opt moves");
        jMenuItemLK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLK3ActionPerformed(evt);
            }
        });
        jMenuRun.add(jMenuItemLK3);

        jMenuBar1.add(jMenuRun);

        jMenuHelp.setText("Help");

        jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAbout.setText("About");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane4)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Exit option.
     * @param evt ActionEvent
     */
    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        int choice = JOptionPane.showConfirmDialog(null,
                                "Exit TSP Solver? Unsaved data will be lost.",
                                "Exit",
                                JOptionPane.YES_NO_OPTION);
        switch(choice){
            case 0:
                System.exit(0);
                break;
            case 1:
                break;
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemExitActionPerformed
    /**
     * New option.
     * @param evt ActionEvent
     */
    private void jMenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewActionPerformed
        if(!jTextArea1.getText().equals("")) {
            int choice = JOptionPane.showConfirmDialog(null,
                                "Unsaved data will be lost. Create a new file?",
                                "New",
                                JOptionPane.YES_NO_OPTION);
            switch(choice){
                case 0:
                        String content = makeDefaultNewGraph();
                        jTextArea1.setText(content);
                    break;
                case 1:
                    break;
            }
        }
        else {
            String content = makeDefaultNewGraph();
            jTextArea1.setText(content);
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemNewActionPerformed
    /**
     * Open option. 
     * 
     * If a Waterloo University website ".tsp" file cannot be opened, open 
     * the file with Notepad, then copy-paste the text to the jTextArea. 
     * 
     * The implementation here works but does not open all types of foreign TSP files.
     * 
     * @param evt ActionEvent
     */
    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        int x;
    	File selectedFile;// = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); // the directory with the Jar is wanted 
        x = fileChooser.showOpenDialog(this);
        if (x == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            if(!jTextArea1.getText().equals("")) {
                int choice = JOptionPane.showConfirmDialog(null,
                                    "Unsaved data will be lost. Open the new file?",
                                    "Open",
                                    JOptionPane.YES_NO_OPTION);
                switch(choice){
                    case 0:
                            try(FileInputStream fis_file = new FileInputStream(
                                String.valueOf(selectedFile)); 
                                ObjectInputStream ois_file = 
                                new ObjectInputStream(fis_file)) {
                                jTextArea1.setText((String)ois_file.readObject());
                            }
                            catch(IOException | ClassNotFoundException e){
                                System.err.println(e);
                            }
                        break;
                    case 1: // do nothing, something could be added if wanted 
                            // like "Save the file before opening another?" could be the question 
                        break;
                }
            }
            else {
                try(FileInputStream fis_file = new FileInputStream(
                    String.valueOf(selectedFile)); 
                    ObjectInputStream ois_file = 
                    new ObjectInputStream(fis_file)) {
                    jTextArea1.setText((String)ois_file.readObject());
                }
                catch(IOException | ClassNotFoundException e){
                    System.err.println(e);
                }
            }
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemOpenActionPerformed
    /**
     * Save option.
     * @param evt ActionEvent
     */
    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveActionPerformed
        int x;							
    	JTextField txtfield = new JTextField(44);
    	Object[] choiceobj = {txtfield, "Save tsp"};
    	do{
            int z = JOptionPane.showOptionDialog(null,
                "Directory will be the default home directory. Please write a file name without the extension (.tsp):\n", 
                "Save tsp",
                JOptionPane.PLAIN_MESSAGE, 
                3, null,
                choiceobj,
                choiceobj[0]);
            x = -1;
            if (z == JOptionPane.CLOSED_OPTION) {
                return;
            }
            if (txtfield.getText().length() < 1){
                x = 0;
                JOptionPane.showMessageDialog(null,
                "File name cannot be 0 characters long.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            }
    	} while(x!=-1);
    	File file = new File(txtfield.getText() + ".tsp");
    	int sav_choice;
    	if(file.exists()){
            sav_choice = JOptionPane.showConfirmDialog(null,
            "File already exists. Overwrite?",
            "Save",
            JOptionPane.YES_NO_OPTION);
    	}
    	else{
            try(FileOutputStream fosFile = 
                    new FileOutputStream(file); 
                    ObjectOutputStream oosFile = 
                        new ObjectOutputStream(fosFile)) {
                        oosFile.writeObject(jTextArea1.getText());
                        oosFile.flush();
            }
            catch(Exception e){
                System.err.println(e);
            }
            return;
    	}
        switch(sav_choice){
            case 0:
                try(FileOutputStream fosFile = 
                    new FileOutputStream(file); 
                    ObjectOutputStream oosFile = 
                        new ObjectOutputStream(fosFile)) {
                        oosFile.writeObject(
                            jTextArea1.getText());
                        oosFile.flush();
                }
                catch(Exception e){
                    System.err.println(e);
                }
                break;
            case 1: // do nothing, intentionally
                break;
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemSaveActionPerformed
    /**
     * Calling the NNH.
     * @param evt ActionEvent
     */
    private void jMenuItemNNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNNHActionPerformed
        String result = TSP_Solver_UEF.NearestNeighbour_Algorithm(
            jTextArea1.getText());
        if(TSP_Solver_UEF.hamiltonian) {
            jTextArea2.setText(result);
        }
        else {
            jTextArea2.setText("Error in checking if the tour is a Hamiltonian circuit!");
        }
        // https://stackoverflow.com/questions/3481828/how-do-i-split-a-string-in-java Google searches helped on 2/June/2024 
        // https://stackoverflow.com/questions/29987971/selecting-last-line-of-text-area on 2/June/2024 
        // https://www.javatpoint.com/java-get-current-date on 2/June/2024       Also, the book by Y. Daniel Liang: 
                                                                                     // Introduction to Java Programming 
        if(result.contains("Solution")) {
            String[] resultSplitted = result.split("Solution:");
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date timeNow = new Date();
            String resultForHistory = resultSplitted[1].concat("\n F2 \t" + form.format(timeNow) + "\n\n----------\n");
            jTextAreaHistory.insert(resultForHistory, jTextAreaHistory.getDocument().getLength());
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemNNHActionPerformed
    /**
     * Calling the 2MST (Prim, since Kruskal and others are not implemented in this version).
     * @param evt ActionEvent
     */
    private void jMenuItem2MSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2MSTActionPerformed
        String result = TSP_Solver_UEF.DoubleMST_Algorithm_Prim(
                jTextArea1.getText());
        if(TSP_Solver_UEF.hamiltonian) {
            jTextArea2.setText(result);
        }
        else {
            jTextArea2.setText("Error in checking if the tour is a Hamiltonian circuit!");
        }
        if(result.contains("Solution")) {
            String[] resultSplitted = result.split("Solution:");
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date timeNow = new Date();
            String resultForHistory = resultSplitted[1].concat("\n F3 \t" + form.format(timeNow) + "\n\n----------\n");
            jTextAreaHistory.insert(resultForHistory, jTextAreaHistory.getDocument().getLength());
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItem2MSTActionPerformed
    /**
     * Calling the CHH.
     * @param evt ActionEvent
     */
    private void jMenuItemCHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCHHActionPerformed
        String result = TSP_Solver_UEF.ConvexHull_Algorithm(
                jTextArea1.getText());
        if(TSP_Solver_UEF.hamiltonian) {
            jTextArea2.setText(result);
        }
        else {
            jTextArea2.setText("Error in checking if the tour is a Hamiltonian circuit!");
        }
        if(result.contains("Solution")) {
            String[] resultSplitted = result.split("Solution:");
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date timeNow = new Date();
            String resultForHistory = resultSplitted[1].concat("\n F4 \t" + form.format(timeNow) + "\n\n----------\n");
            jTextAreaHistory.insert(resultForHistory, jTextAreaHistory.getDocument().getLength());
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemCHHActionPerformed
    /**
     * About option.
     * @param evt ActionEvent
     */
    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
            JDialog about = new JDialog();
            about.setSize(980, 120);
            about.setLocationRelativeTo(null);
            about.setResizable(false);
            about.setTitle("About");
            JTextArea txt = new JTextArea(
                    "  TSP Solver " + VERSION + " for solving the 2D Traveling Salesman Problem"
                        + " © Tuomas Hyvönen, orcid.org/0009-0003-8060-8314"
                        + "\n  University of Eastern Finland, Lumeto"
                        + "        uef.fi/en/unit/school-of-computing        github.com/tuomasth"
                        + "\n  Open source, developed with Apache NetBeans.");
            about.add(txt, BorderLayout.CENTER);
            about.setAlwaysOnTop(true);
            about.setVisible(true);
            txt.setEditable(false);
            about.requestFocusInWindow();
            
            about.addWindowListener(new WindowListener() {
                /**
                 * windowClosed
                 */
                @Override
                public void windowClosed(WindowEvent e) {
                }
                /**
                 * windowOpened
                 */
                @Override
                public void windowOpened(WindowEvent e) {
                }
                /**
                 * windowClosing, has enabling code 
                 */
                @Override
                public void windowClosing(WindowEvent e) {
                    jMenuFile.setEnabled(true);
                    jMenuRun.setEnabled(true);
                    jMenuHelp.setEnabled(true);
                    //System.out.println("about window closed");
                }
                /**
                 * windowIconified
                 */
                @Override
                public void windowIconified(WindowEvent e) {
                }
                /**
                 * windowDeiconified
                 */
                @Override
                public void windowDeiconified(WindowEvent e) {
                }
                /**
                 * windowActivated
                 */
                @Override
                public void windowActivated(WindowEvent e) {
                }
                /**
                 * windowDeactivated
                 */
                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            });
            jMenuFile.setEnabled(false);
            jMenuRun.setEnabled(false);
            jMenuHelp.setEnabled(false);
            about.addKeyListener(new KeyListener() {
                /**
                 * keyPressed (escape button on about window) 
                 */
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        jMenuFile.setEnabled(true);
                        jMenuRun.setEnabled(true);
                        jMenuHelp.setEnabled(true);
                        about.dispose();
                    }
                }
                /**
                 * keyTyped
                 */
                @Override
                public void keyTyped(KeyEvent e) {
                }
                /**
                 * keyReleased
                 */
                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
            System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

   /**
     * Calling SOM CH NN.
     * @param evt ActionEvent
     */
    private void jMenuItemSOM_CH_NNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSOM_CH_NNActionPerformed
        String result = TSP_Solver_UEF.SOM_NN_Algorithm(
                jTextArea1.getText());
        if(TSP_Solver_UEF.hamiltonian) {
            jTextArea2.setText(result);
        }
        else {
            jTextArea2.setText("Error in checking if the tour is a Hamiltonian circuit!");
        }
        if(result.contains("Solution")) {
            String[] resultSplitted = result.split("Solution:");
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date timeNow = new Date();
            String resultForHistory = resultSplitted[1].concat("\n F5 \t" + form.format(timeNow) + "\n\n----------\n");
            jTextAreaHistory.insert(resultForHistory, jTextAreaHistory.getDocument().getLength());
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemSOM_CH_NNActionPerformed
    /**
     * Calling OPT-triple. (The original idea was to make an exact Lin-Kernighan heuristic, that's why "LK" in jMenuItemLK3ActionPerformed.)
     * @param evt ActionEvent
     */
    private void jMenuItemLK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLK3ActionPerformed
        String result = TSP_Solver_UEF.OPT3_Algorithm(
                jTextArea1.getText());
        if(TSP_Solver_UEF.hamiltonian) {
            jTextArea2.setText(result);
        }
        else {
            jTextArea2.setText("Error in checking if the tour is a Hamiltonian circuit!");
        }
        if(result.contains("Solution")) {
            String[] resultSplitted = result.split("Solution:");
            SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date timeNow = new Date();
            String resultForHistory = resultSplitted[1].concat("\n F6 \t" + form.format(timeNow) + "\n\n----------\n");
            jTextAreaHistory.insert(resultForHistory, jTextAreaHistory.getDocument().getLength());
        }
        System.out.println(evt.toString());
    }//GEN-LAST:event_jMenuItemLK3ActionPerformed

    /**
     * Call when the new default graph with 6 vertices is wanted. 
     * The optimal tour is 1-2-6-3-4-5-1,   tour length: 17.77, 
     * The graph was designed to have 2 inner vertices (not in the convex hull) so that they might cause interesting results 
     * when comparing all of the algorithms. The convex hull has those 4 vertices just because a rectangle is a nice shape. 
     * (3.1 & 3.0), (3.2 & 3.9), (1.0 & 5.0), (1.0 & 1.0), (5.0 & 1.0), (5.0 & 5.0) 
     * The CHH algorithm does not find the optimal tour for this because it adds always the closest inner vertex to the tour.
     * This means that something more complex should be used for the "stretching-search".
     * 
     * @return graph String
     */
    private String makeDefaultNewGraph() {
        String graph = "NAME : \n" + 
                            "COMMENT : \n" + 
                            "TYPE : TSP\n" + 
                            //"DIMENSION : 6\n" + 
                            "EDGE_WEIGHT_TYPE : EUC_2D\n" + 
                            "NODE_COORD_SECTION \n" + 
                            "1 3.1 3.0\n" + 
                            "2 3.2 3.9\n" + 
                            "3 1.0 5.0\n" + 
                            "4 1.0 1.0\n" + 
                            "5 5.0 1.0\n" + 
                            "6 5.0 5.0\n" + 
                            "EOF";
        return graph;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItem2MST;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemCHH;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemLK3;
    private javax.swing.JMenuItem jMenuItemNNH;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemSOM_CH_NN;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenu jMenuRun;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextAreaHistory;
    // End of variables declaration//GEN-END:variables
}