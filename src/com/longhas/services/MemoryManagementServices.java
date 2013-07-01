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

import com.longhas.data.Memory;
import java.util.ArrayList;
import com.longhas.data.Process;
/**
 *
 * @author YANIX_MRML
 */
public class MemoryManagementServices {


    private int totalFreeMemory;
    private Process currentProcess;
    private int numberOfProcessFinished;
    private ArrayList<Process> processList;

    public MemoryManagementServices(){
        totalFreeMemory = 0;
        currentProcess = null;
        numberOfProcessFinished = 0;
        processList = new ArrayList<Process>();
    }

    public int getTotalSizes(ArrayList<Process> processList){

        int size = 0;
        for(Process process : processList){
            size += process.getMemorySize();
        }
        return size;

    }

    public void setMemorySizeProcessList(int totalFreeMemory, ArrayList<Process> processList){
        this.totalFreeMemory = totalFreeMemory;
        this.processList = processList;
    }

    public ArrayList<Memory> updateMemoryList(ArrayList<Memory> memoryList, int frameWidth, int frameHeight,int memorySize){

        while(totalFreeMemory>0){

                Process process = getNextProcess();
                double ratio = 0;

                if(process==null){
                        break;
                }else{

                    if(memoryList.isEmpty()){

                            Memory memory1 = new Memory();
                            memory1.setProcess(process);
                            memory1.setSpace(process.getMemorySize());
                            memory1.setStartingX(0);
                            memory1.setStartingY(frameHeight);
                            ratio = process.getMemorySize() /(memorySize * 1.0);
                            memory1.setHeight((int) (frameHeight * ratio));
                            memory1.setWidth(frameWidth);
                            memory1.setState("Occupied");

                            totalFreeMemory -= process.getMemorySize();
                            memoryList.add(memory1);

                            Memory memory2 = new Memory();
                            memory2.setProcess(null);
                            memory2.setSpace(totalFreeMemory);
                            memory2.setStartingX(0);
                            ratio = totalFreeMemory /(memorySize * 1.0);
                            memory2.setHeight((int) (frameHeight * ratio));
                            memory2.setWidth(frameWidth);
                            memory2.setState("Vacant");


                            memoryList.add(memory2);

                    }else{

                            int tempSize = process.getMemorySize();
                            totalFreeMemory -= tempSize;


                            for(int i = 0; i < memoryList.size();i++){

                                    Memory memory = memoryList.get(i);
                                    
                                    if(memory.getProcess() == null){
                                        
                                            int tempTotalFreeMemory = 0;

                                            if(memory.getSpace()>tempSize){
                                                    Memory memory1 = new Memory();
                                                    memory1.setProcess(process);
                                                    tempTotalFreeMemory = memory.getSpace() - tempSize;

                                                    memory1.setSpace(tempSize);
                                                    memory1.setStartingX(0);
                                                    memory1.setStartingY(frameHeight);
                                                    ratio = tempSize/(memorySize * 1.0);
                                                    memory1.setHeight((int) (frameHeight * ratio));
                                                    memory1.setWidth(frameWidth);
                                                    memory1.setState("Occupied");
                                                    // all process size are allocated already
                                                    tempSize = 0;

                                                    if(tempTotalFreeMemory!=0){


                                                        memoryList.set(i, memory1);

                                                        Memory memory2 = new Memory();
                                                        memory2.setProcess(null);
                                                        memory2.setSpace(tempTotalFreeMemory);
                                                        memory2.setStartingX(0);
                                                        ratio = tempTotalFreeMemory /(memorySize * 1.0);
                                                        memory2.setHeight((int) (frameHeight * ratio));
                                                        memory2.setWidth(frameWidth);
                                                        memory2.setState("Vacant");

                                                        memoryList.add(i+1, memory2);

                                                    }

                                                    break;

                                            }else{
                                                    Memory memory1 = new Memory();
                                                    memory1.setProcess(process);
                                                    memory1.setSpace(memory.getSpace());
                                                    memory1.setStartingX(0);
                                                    ratio = memory1.getSpace() /(memorySize * 1.0);
                                                    memory1.setHeight((int) (frameHeight * ratio));
                                                    memory1.setWidth(frameWidth);
                                                    memory1.setState("Occupied");
                                                    //small parts of process size are allocated only
                                                    tempSize -= memory.getSpace();

                                                    memoryList.set(i,memory1);
                                            }

                                    }


                                    if(tempSize == 0){
                                         break;
                                    }


                            }

                    }

                }

	}



       return memoryList;

    }


    public Process getNextProcess(){
        
        for(int i=0;i<getProcessList().size();i++){

            Process process = getProcessList().get(i);

            if(process.getState().equalsIgnoreCase("New") && process.getMemorySize() <= totalFreeMemory){

                process.setState("Ready");

                getProcessList().set(i, process);

                return process;
            }
        }
        
        return null;
    }

    public ArrayList<Memory> runProcessInMemory(ArrayList<Memory> memoryList,int numberOfProcessFinished){
        this.numberOfProcessFinished = numberOfProcessFinished;

        for(int i = 0; i< memoryList.size();i++){

                    Memory memory = memoryList.get(i);

                    if(memory.getProcess() != null){
                            currentProcess = memory.getProcess();
                            this.numberOfProcessFinished++;

                            for(int j = i+1; j<memoryList.size();j++){
                                    Memory otherPart = memoryList.get(j);
                                    if(otherPart.getProcess()!= null && memory.getProcess().getId() == otherPart.getProcess().getId()){
                                            otherPart.setProcess(null);
                                            otherPart.setState("Vacant");
                                            memoryList.set(j, otherPart);
                                    }
                            }

                            memory.setProcess(null);
                            memory.setState("Vacant");
                            totalFreeMemory += currentProcess.getMemorySize();

                            memoryList.set(i, memory);

                            terminateCurrentProcess(currentProcess.getId());

                            break;
                    }
       }
       return memoryList;
    }

    private void terminateCurrentProcess(int id){
        for(int i=0;i<getProcessList().size();i++){
            Process process = getProcessList().get(i);
            if(process.getId()==id){
                process.setState("Terminated");
                getProcessList().set(i, process);
            }
        }
    }

    public Process getCurrentProcess(){
        return currentProcess;
    }

    public int getNumberOfProcessFinished(){
        return numberOfProcessFinished;
    }

    public ArrayList<Memory> mergeFragmentedFreeMemory(ArrayList<Memory> memoryList, int memorySize,int frameWidth,int frameHeight){

        for(int i=0;i<memoryList.size();i++){

            int counter = 1;
            Memory memory = memoryList.get(i);
            int size = memory.getSpace();

            if(memory.getProcess() == null){

                    for(int j=i+1;j<memoryList.size();j++){

                            if(memoryList.get(j).getProcess()==null){
                                    counter++;
                                    size += memoryList.get(j).getSpace();
                                    memoryList.remove(j);
                                    j--;
                            }else{
                                break;

                            }
                    }


                    if(counter>1){

                        memory.setSpace(size);
                        memory.setStartingX(0);
                        double ratio = size /(memorySize * 1.0);
                        memory.setHeight((int) (frameHeight * ratio));
                        memory.setWidth(frameWidth);
                        memory.setState("Vacant");

                        memoryList.set(i, memory);
                    }

            }

        }

        return memoryList;

    }

    /**
     * @return the processList
     */
    public ArrayList<Process> getProcessList() {
        return processList;
    }

    public int getTotalFreeMemory(){
        return totalFreeMemory;
    }

}
