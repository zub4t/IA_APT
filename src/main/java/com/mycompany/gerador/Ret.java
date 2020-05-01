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

    public List<Integer> pontos_list = new ArrayList<>();
    private int id;

    public Ret(int id) {
        this.id = id;
    }

    public void addPonto(int ponto_id) {
        pontos_list.add(ponto_id);
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
