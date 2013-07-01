/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MemorySimulationFrame.java
 * Author : LONGHAS, MARK RYAN M.
 * Created on Oct 3, 2010, 10:33:35 PM
 */

package com.longhas.services;



import com.longhas.data.GraphicsCoordinate;
import com.longhas.data.Memory;
import com.longhas.data.Process;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

 public class RunnableObject implements Runnable
 {
     private Lock lockObject; // application lock; passed in to constructor
     private Condition suspend; // used to suspend and resume thread
     private boolean suspended = false; // true if thread suspended
     private javax.swing.JTextArea textArea; // JLabel for output
     private javax.swing.JScrollPane scroll;
     private String text;
     private MemoryManagementServices memoryService = new MemoryManagementServices();
     private int delay = 1500;
     private int currentTime;
     private DefaultTableModel memoryTableModel;
     private javax.swing.JTable processTable;
     private javax.swing.JPanel percentagePanel;
     private Graphics graphicsPercentage;
     private javax.swing.JLabel percentageLabel;
     private javax.swing.JPanel graphProcessPanel;
     private Graphics graphicsProcess;
     private javax.swing.JMenuItem menuInputItem;
     private javax.swing.JButton buttonInput;
     private boolean checker = false;
     private javax.swing.JMenuItem menuRestartItem;
     private javax.swing.JMenuItem menuStartItem;
     private ArrayList<GraphicsCoordinate> coordinates;
     private javax.swing.JButton buttonStart;
     private Graphics memoryGraphics;
     private javax.swing.JProgressBar progressBar;
     private javax.swing.JLabel progressLabel;

     private int memorySize;
     private int numberOfProcessFinished;
     private ArrayList<Process> processList;
     private ArrayList<Memory> memoryList;
     private int height;
     private int width;


    public RunnableObject( Lock theLock, javax.swing.JTextArea textArea, javax.swing.JScrollPane scroll,String text,javax.swing.JTable processTable)
    {
        lockObject = theLock; // store the Lock for the application
        suspend = lockObject.newCondition(); // create new Condition
        this.textArea = textArea; // store JLabel for outputting character
        this.scroll = scroll;
        this.text = text;
        coordinates = new ArrayList<GraphicsCoordinate>();
        memoryList = new ArrayList<Memory>();
        processList = new ArrayList<Process>();
        memorySize = 0;
        currentTime = 0;
        numberOfProcessFinished = 0;
        height = 0;
        width = 0;
    } // end RunnableObject constructor


    public void setInputItem(javax.swing.JMenuItem menuItem, javax.swing.JButton button){
        this.menuInputItem = menuItem;
        this.buttonInput = button;
    }

    public void setDelay(int delay){
        this.delay = delay;
    }

    public void setPercentageDisplayProperties(javax.swing.JPanel percentagePanel,javax.swing.JLabel percentageLabel ){
        this.percentagePanel = percentagePanel;
        this.percentageLabel = percentageLabel;
        this.graphicsPercentage = this.percentagePanel.getGraphics();
    }


    public void setProcessTableProperties(javax.swing.JTable processTable){
        this.processTable = processTable;
    }

    public void setMemoryPanel(javax.swing.JPanel memoryPanel){
        this.width = memoryPanel.getWidth();
        this.height = memoryPanel.getHeight();
        this.memoryGraphics = memoryPanel.getGraphics();
    }

    public void setGraphProcessPanel(javax.swing.JPanel graphProcessPanel){
        this.graphProcessPanel = graphProcessPanel;
        this.graphicsProcess = graphProcessPanel.getGraphics();
    }

    public void setMemorySize(int memorySize){
        this.memorySize = memorySize;
    }

    public void initializeProcess(ArrayList<Process> processList, int memorySize){
        this.processList = processList;
        this.memorySize = memorySize;
        memoryService.setMemorySizeProcessList(this.memorySize,this.processList);
        memoryList = new ArrayList<Memory>();
        progressBar.setMaximum(processList.size());
    }

    private void displayMemoryPanel(){


        if(memoryList.size()>0){

            int startingY = 0;


            for(int i = memoryList.size() - 1; i>=0; i--){

                int deductionX = 0;
                Memory memory = memoryList.get(i);
                String outputLabel = "";
                
                if(memory.getProcess()!=null){
                    memoryGraphics.setColor(memory.getProcess().getColor());
                    outputLabel = "P" + memory.getProcess().getId();
                }else{
                    memoryGraphics.setColor(Color.GRAY);
                    outputLabel = "Vacant";
                    deductionX = 10;
                }

                memoryGraphics.setFont(new java.awt.Font("Calibri",java.awt.Font.BOLD,14));
                memoryGraphics.fillRect(memory.getStartingX(), startingY, memory.getWidth(), memory.getHeight());

                memoryGraphics.setColor(Color.BLACK);
                memoryGraphics.drawString(outputLabel,(memory.getWidth()/2)-deductionX, startingY + (memory.getHeight()/2));

                startingY += memory.getHeight();

                memoryGraphics.drawLine(memory.getStartingX(), startingY-1, memory.getWidth(), startingY-1);

            }

        }

    }



    private void displayPercentageGrid(){

        int unitHeight = percentagePanel.getHeight() - 5;
        int unitWidth = percentagePanel.getWidth() - 5;
        graphicsPercentage.setColor(Color.getHSBColor(110, 20, 100));
        graphicsPercentage.drawLine(5, (int)(unitHeight/2), unitWidth, (int)(unitHeight/2));
        graphicsPercentage.drawLine(5, (int)(unitHeight/4), unitWidth, (int)(unitHeight/4));
        graphicsPercentage.drawLine(5, (int)(3*(unitHeight/4)), unitWidth, (int)(3*(unitHeight/4)));
        graphicsPercentage.drawLine((int)(unitWidth/2), 5 , (int)(unitWidth/2), unitHeight);

        graphicsPercentage.setColor(Color.GREEN);
        for(int i = 10; i<unitHeight;i+=10){
            graphicsPercentage.drawLine(5, i , unitWidth, i);
        }

    }

    private void displayProcessGrid(){
       graphicsProcess.setColor(Color.getHSBColor(204, 255, 204));
       for(int i=20;i<graphProcessPanel.getHeight();i+=20){
          graphicsProcess.drawLine(0, i , graphProcessPanel.getWidth(), i);
       }

       for(int i=20;i<graphProcessPanel.getWidth();i+=20){
          graphicsProcess.drawLine(i, 0 , i, graphProcessPanel.getHeight());
       }
    }

    private void clearMemoryTable(){

        for(int i = memoryTableModel.getRowCount() - 1; i>=0;i--){
            memoryTableModel.removeRow(i);
        }

    }

    private void displayMemoryTable(){

        clearMemoryTable();
        int i =0;
        for(Memory memory : memoryList){

            if(memory.getProcess()!=null){
                memoryTableModel.addRow(new Object[]{memory.getState() + " : P" + memory.getProcess().getId(), memory.getSpace(),
                memory.getProcess().getColor()});
            }else{
                memoryTableModel.addRow(new Object[]{memory.getState(), memory.getSpace(),Color.GRAY});
            }
            i++;
        }

    }

    private void displayProcessGraph(){

        //clear background
        graphicsProcess.setColor(graphProcessPanel.getBackground());
        graphicsProcess.fillRect(0, 0, graphProcessPanel.getWidth(), graphProcessPanel.getHeight());

        displayProcessGrid();

        if(coordinates.size()>0){

            int prevX = graphProcessPanel.getWidth()-5;
            int prevY = coordinates.get(coordinates.size()-1).getY();
            int currentX = graphProcessPanel.getWidth()-1;
            int unitHeight = graphProcessPanel.getHeight()-5;
            graphicsProcess.setColor(Color.GREEN);

            for(int i=coordinates.size()-1;i>=0;i--){

                GraphicsCoordinate coordinate = coordinates.get(i);
                currentX -= coordinate.getX();

                if(currentX<-60){
                    coordinates.remove(i);
                }else{
                    graphicsProcess.drawLine(currentX, unitHeight - coordinate.getY(), prevX, unitHeight - prevY);
                }

                prevX = currentX;
                prevY = coordinate.getY();
                coordinate.setX(coordinate.getX());

            }

        }

    }

    private void addGraphicsCoordinate(int x, int y){
        //since y is cpu percentage; satisfy the height of the panel
        double time = memorySize * 1.0;
        double ratio = y / time;
        int newY = (int)((graphProcessPanel.getHeight()-5) * ratio);
        GraphicsCoordinate coordinate = new GraphicsCoordinate(x,newY);
        coordinates.add(coordinate);
    }

    private void displayMemoryPercentage(int size){
        //clear up previous output
        graphicsPercentage.setColor(percentagePanel.getBackground());
        int finalWidth = percentagePanel.getWidth();
        int finalHeight = percentagePanel.getHeight();
        graphicsPercentage.fillRect(0, 0 ,finalWidth , finalHeight );


        //displayPercentage
        double space = memorySize * 1.0;
        double ratio = size / space;
        int percentage = (int) (ratio * 100);
        int unitHeight = (int)(finalHeight * ratio);
        graphicsPercentage.setColor(Color.GREEN);
        graphicsPercentage.fillRect(0, finalHeight-unitHeight , finalWidth, finalHeight);
        percentageLabel.setText("MEMORY USAGE : " + percentage + "%");


        displayPercentageGrid();

    }

   
    public void setPanelDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }

    private void displayMemoryList(){
        for(Memory memory : memoryList){
            if(memory.getProcess()!=null){
                 text += ("\nMRML >> OCCUPIED MEMORY : Process : " + memory.getProcess().getProcessName() + "  Size : " + memory.getSpace() + " ...");
            }else{
                 text += ("\nMRML >> VACANT MEMORY : (No Process) Size : " + memory.getSpace() + " ...");
            }
       }
    }


     // place random characters in GUI
     public void run()
     {

            // get name of executing thread
         while ( true ) // infinite loop; will be terminated from outside
         {

            displayPercentageGrid();
            displayMemoryPanel();
            displayProcessGraph();

            if(numberOfProcessFinished==0){
                text = ("Memory Manager [Version 1.1] \nLONGHAS, MARK RYAN M. - Operating System\n" +
                    "To Start, go to EDIT > MANAGE INPUTS to add processess\nC-O-N-S-O-L-E \n-------------------------------------------------------------\n");
                callInvokeLater();
            }
       

            if(numberOfProcessFinished==0 && !processList.isEmpty()){
                text += "MRML >> Starting Memory Management ... \n\n-------------------------------------------------------------\n";
                

                addGraphicsCoordinate(10,memorySize - memoryService.getTotalFreeMemory());
                displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                displayMemoryPanel();
                displayProcessGraph();
                progressLabel.setText("Starting Memory Management ...");
                callInvokeLater();
            }

            
           while(numberOfProcessFinished<processList.size()){

                memoryList = memoryService.updateMemoryList(memoryList,width, height, memorySize);

                text +=("\n\nMRML >> MEMORY (AFTER LOADING NEW PROCESSESS) ...\n");
                callInvokeLater();

                //Output
                addGraphicsCoordinate(10,memorySize - memoryService.getTotalFreeMemory());
                displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                displayMemoryTable();
                displayMemoryPanel();
                displayProcessGraph();
                displayMemoryList();
                callInvokeLater();


                //picking the first process, running
                memoryList = memoryService.runProcessInMemory(memoryList,numberOfProcessFinished);

                Process currentProcess = memoryService.getCurrentProcess();
                if(currentProcess!=null){
                    text += ("\n\nMRML >> READY : Process : " + currentProcess.getProcessName() + " Size : " + currentProcess.getMemorySize() + " ...");
                    processTable.setValueAt("Ready", currentProcess.getId() - 1, 3);
                    callInvokeLater();
                    text += ("\n\nMRML >> RUNNING : Process : " + currentProcess.getProcessName() + " Size : " + currentProcess.getMemorySize() + " ...");
                    processTable.setValueAt("Running", currentProcess.getId() - 1, 3);



                    displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                    displayMemoryTable();
                    displayMemoryPanel();
                    displayProcessGraph();
                    displayMemoryList();
                    callInvokeLater();
                    
                    String prevText = text;
                    int procTime = currentProcess.getProcessTime();

                    for(int counter=procTime; counter>=0; counter--){

                            text = prevText;
                            text += ("\n\nMRML >> TOTAL TIME : " + procTime + " msecs || REMAINING TIME : " + counter + " msecs ...\n" +
                                    "\n-------------------------------------------------------------\n");
                            delay = 10;
                            callInvokeLater();

                    }

                    delay = 1500;

                    text += ("\n\nMRML >> TERMINATED : Process : " + currentProcess.getProcessName() + " Size : " + currentProcess.getMemorySize() + " ...");
                    processTable.setValueAt("Terminated", currentProcess.getId() - 1, 3);


                    setNumberOfProcessFinished(memoryService.getNumberOfProcessFinished());
                    progressBar.setValue(numberOfProcessFinished);
                    progressLabel.setText(numberOfProcessFinished + " out of " + processList.size() + " processess are terminated ...");

                    callInvokeLater();
                    
                }

                text += ("\n\nMRML >> MEMORY (AFTER RUNNING A PROCESS) ...\n");
                callInvokeLater();

                //Output
                addGraphicsCoordinate(10,memorySize - memoryService.getTotalFreeMemory());
                displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                displayMemoryTable();
                displayMemoryPanel();
                displayProcessGraph();
                displayMemoryList();
                callInvokeLater();

                text += ("\n\nMRML >> MEMORY (AFTER MEMORY COMPACTION) ...\n");
                callInvokeLater();

                memoryList = memoryService.mergeFragmentedFreeMemory(memoryList,memorySize,width,height);

                //Output
                addGraphicsCoordinate(10,memorySize - memoryService.getTotalFreeMemory());
                displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                displayMemoryTable();
                displayMemoryPanel();
                displayProcessGraph();
                displayMemoryList();
                callInvokeLater();

            }


            if(numberOfProcessFinished >= processList.size() && !processList.isEmpty()){

                if(!checker){
                    text += ("\n\nMRML >> Scheduler can't find any process ...\n");
                    text += ("\nMRML >> All processess are terminated ...\n\n-------------------------------------------------------------\n");


                    menuInputItem.setEnabled(true);
                    buttonInput.setEnabled(true);
                    menuRestartItem.setEnabled(true);
                    menuStartItem.setEnabled(false);
                    buttonStart.setEnabled(false);
                    menuStartItem.setText("Start Simulation");
                    buttonStart.setText("Start Simulation");
                    progressBar.setValue(0);
                    progressLabel.setText("All process are terminated ...");

                    addGraphicsCoordinate(10,memorySize - memoryService.getTotalFreeMemory());
                    displayMemoryPercentage(memorySize - memoryService.getTotalFreeMemory());
                    displayMemoryPanel();
                    displayProcessGraph();
                    callInvokeLater();

                    progressLabel.setText("Running Memory Manager ...");
                    setChecker(true);

                }

            }

            callInvokeLater();
            
        }


     } // end method run

     public boolean getChecker(){
         return checker;
     }

     private void callInvokeLater(){

         try
           {
              // sleep for up to 1 second
              Thread.sleep( delay );

             lockObject.lock(); // obtain the lock
              try
              {
                 while ( suspended ) // loop until not suspended
                 {
                    suspend.await(); // suspend thread execution
                } // end while
              } // end try
              finally
             {
                 lockObject.unlock(); // unlock the lock
             } // end finally
          } // end try
           // if thread interrupted during wait/sleep
          catch ( InterruptedException exception )
           {
             exception.printStackTrace(); // print stack trace
           } // end catch

         SwingUtilities.invokeLater(
             new Runnable()
             {
                // pick random character and display it
                public void run()
                {
                   // select random uppercase letter
                   textArea.setText(text );
                   scroll.setViewportView(textArea);
                   // --------------------
                   //displayPercentageGrid();
                 } // end method run
              } // end inner class
           );
     }
    // change the suspended/running state
     public void toggle()
     {
        suspended = !suspended; // toggle boolean controlling state

        // change label color on suspend/resume
      // output.setBackground( suspended ? Color.RED : Color.GREEN );
        lockObject.lock(); // obtain lock
       try
        {
           if ( !suspended ) // if thread resumed
          {
              suspend.signal(); // resume thread
           } // end if
        } // end try
        finally
        {
          lockObject.unlock(); // release lock
        } // end finally
     } // end method toggle

    /**
     * @param checker the checker to set
     */
    public void setChecker(boolean checker) {
        this.checker = checker;
    }

    /**
     * @param menuRestartItem the menuRestartItem to set
     */
    public void setMenuRestartItem(javax.swing.JMenuItem menuRestartItem) {
        this.menuRestartItem = menuRestartItem;
    }

    /**
     * @param menuStartItem the menuStartItem to set
     */
    public void setMenuButtonStartItem(javax.swing.JMenuItem menuStartItem, javax.swing.JButton buttonStart) {
        this.menuStartItem = menuStartItem;
        this.buttonStart = buttonStart;
    }

    public int getCurrentTime(){
        return currentTime;
    }

    /**
     * @param numberOfProcessFinished the numberOfProcessFinished to set
     */
    public void setNumberOfProcessFinished(int numberOfProcessFinished) {
        this.numberOfProcessFinished = numberOfProcessFinished;
    }

    /**
     * @param memoryTableModel the memoryTableModel to set
     */
    public void setMemoryTableModel(DefaultTableModel memoryTableModel) {
        this.memoryTableModel = memoryTableModel;
    }

    /**
     * @param progressBar the progressBar to set
     */
    public void setProgressDisplay(javax.swing.JProgressBar progressBar, javax.swing.JLabel progressLabel) {
        this.progressBar = progressBar;
        this.progressLabel = progressLabel;
    }
    
  } // end class RunnableObject

