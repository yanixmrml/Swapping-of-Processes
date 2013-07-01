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

/**
 *
 * @author YANIX_MRML
 */
public class GraphicsCoordinate {

    private int x;
    private int y;

    public GraphicsCoordinate(){
        this(0,0);
    }

    public GraphicsCoordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }


}
