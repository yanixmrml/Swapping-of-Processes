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
public class Memory {

    private Process process;
    private int space;
    private int startingX;
    private int startingY;
    private int width;
    private int height;
    private Color color;
    private String state;

    public Memory() {
    }

    public Memory(Process process, int space, int startingX, int startingY, int width, int height,Color color, String state) {
        this.process = process;
        this.space = space;
        this.startingX = startingX;
        this.startingY = startingY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.state = state;
    }

    /**
     * @return the process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(Process process) {
        this.process = process;
    }

    /**
     * @return the space
     */
    public int getSpace() {
        return space;
    }

    /**
     * @param space the space to set
     */
    public void setSpace(int space) {
        this.space = space;
    }

    /**
     * @return the startingX
     */
    public int getStartingX() {
        return startingX;
    }

    /**
     * @param startingX the startingX to set
     */
    public void setStartingX(int startingX) {
        this.startingX = startingX;
    }

    /**
     * @return the startingY
     */
    public int getStartingY() {
        return startingY;
    }

    /**
     * @param startingY the startingY to set
     */
    public void setStartingY(int startingY) {
        this.startingY = startingY;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
