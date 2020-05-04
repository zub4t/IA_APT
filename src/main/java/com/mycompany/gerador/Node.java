/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author marco
 */
public class Node implements Comparable<Node> {

    public Ponto[] pontos;
    public int[] configuracao_atual;
    public List<Node> ligacoes = new ArrayList<>();
    public Node pai;
    public Queue<Integer> ord = new LinkedList<>();

    public Node(int n[], Ponto[] pontos) {
        this.configuracao_atual = new int[n.length];
        this.pontos = new Ponto[pontos.length];
        int i = 0;
        for (Integer v : n) {
            this.configuracao_atual[i] = v;
            i++;
        }
        i = 0;
        for (Ponto p : pontos) {
            this.pontos[i] = p;
            i++;
        }
    }

    public Node(Node n) {
        this.pontos = new Ponto[n.pontos.length];
        this.configuracao_atual = new int[n.configuracao_atual.length];

        int i = 0;
        for (Ponto p : n.pontos) {
            if (p != null) {
                pontos[i] = new Ponto(p);
            } else {
                pontos[i] = null;
            }

            i++;

        }
        i = 0;
        for (Integer v : n.configuracao_atual) {
            this.configuracao_atual[i] = v;
            i++;
        }
        for (Node node : n.ligacoes) {
            this.ligacoes.add(node);
        }
        for (Integer v : n.ord) {
            this.ord.add(v);
        }

    }

    @Override
    public int compareTo(Node o) {

        for (int i = 0; i < this.configuracao_atual.length; i++) {
            if (o.configuracao_atual[i] != this.configuracao_atual[i]) {
                return o.configuracao_atual[i] - this.configuracao_atual[i];
            }
        }
        return 0;
    }
}
