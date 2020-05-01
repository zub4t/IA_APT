/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */
public class Ret implements Comparable<Ret> {

    public int[] pontos_list;
    private int id;
    private int cur_ponto = 0;

    public Ret(int id, int pontos_list_size) {
        pontos_list = new int[pontos_list_size];
        this.id = id;
    }

    public void addPonto(int ponto_id) {
        pontos_list[cur_ponto] = ponto_id;
        cur_ponto++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ret{" + "points=" + pontos_list + ", id=" + id + '}';
    }

    @Override
    public int compareTo(Ret o) {
        return o.id - this.id;

    }

}
