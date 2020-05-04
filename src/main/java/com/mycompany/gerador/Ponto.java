/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marco
 */
public class Ponto implements Comparable<Ponto> {

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }
    int id;
    int x;
    int y;
    public List<Ret> ret_list = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public void addRet(Ret ret) {
        ret_list.add(ret);
    }

    public Ponto(Ponto p) {
        this.id = p.id;
        this.x = p.x;
        this.y = p.y;

        for (Ret ret : p.ret_list) {
            this.ret_list.add(new Ret(ret));
        }

    }

    @Override
    public String toString() {
        return "Ponto{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public int compareTo(Ponto o) {
        int aux = 0;
        if (o.x == this.x) {
            if (o.y == this.y) {
                return aux;
            } else {
                return o.y - this.y;
            }
        } else {
            return o.x - this.x;
        }
    }

}
