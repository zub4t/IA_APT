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
    boolean guardado = false;
    int pontos_guardados = 0;
    public Ret(int id) {
        this.id = id;
    }

    public Ret(Ret ret) {
        this.id = ret.id;
        for (Integer i : ret.pontos_list) {
            this.pontos_list.add(i);
        }
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ret other = (Ret) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
