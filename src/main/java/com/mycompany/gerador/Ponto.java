/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.util.List;

/**
 *
 * @author marco
 */
public class Ponto implements Comparable<Ponto> {

    public Ponto(int x, int y, int ret_list_size) {
        this.x = x;
        this.y = y;
        ret_list = new int[ret_list_size];
    }

    int x;
    int y;
    public int[] ret_list;
    int curr_ret;

    public void addRet() {

    }

    @Override
    public String toString() {
        return "Ponto{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public int compareTo(Ponto o) {
        return (o.x - this.x) + (o.y - this.y);
    }

}
