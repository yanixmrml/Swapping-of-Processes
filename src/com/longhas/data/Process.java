/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MemorySimulationFrame.java
 * Author : LONGHAS, MARK RYAN M.
 * Created on Oct 3, 2010, 10:33:35 PM
 */


package com.longhas.data;

import java.awt.Color;

/**
 *
 * @author YANIX_MRML
 */
public class Process {

    private String processName;
    private String state;
    private int memorySize;
    private int processTime;
    private int id;
    private Color color;

    public Process() {
        this(null,null,0,0);
    }

    public Process(String processName, String state,int memorySize,int id) {
        this.processName = processName;
        this.state = state;
        this.memorySize = memorySize;
        this.id = id;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(String processName) {
        this.processName = processName;
    }

    /**
     * @return the memorySize
     */
    public int getMemorySize() {
        return memorySize;
    }

    /**
     * @param memorySize the memorySize to set
     */
    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the processTime
     */
    public int getProcessTime() {
        return processTime;
    }

    /**
     * @param processTime the processTime to set
     */
    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

}
