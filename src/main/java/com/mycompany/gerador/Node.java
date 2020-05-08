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
    public int funcao_g;
    public int funcao_h;
    public List<Node> ligacoes = new ArrayList<>();
    public Node pai;
    public Queue<Integer> ord = new LinkedList<>();
    public int altura = 0;

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

    public int calcularHeuristica(Ret[] retangulos, Ponto[] pontos) {
        Ret[] retangulos_copy = new Ret[retangulos.length];
        for (int j = 1; j < retangulos.length; j++) {
            retangulos_copy[j] = new Ret(retangulos[j]);
        }
        Ponto[] pontos_copy = new Ponto[pontos.length];
        for (int j = 1; j < pontos.length; j++) {
            if (pontos[j] == null) {
                break;
            }
            pontos_copy[j] = new Ponto(pontos[j]);
        }
        int[] aux = new int[this.configuracao_atual.length];
        for (int j = 1; j < this.configuracao_atual.length; j++) {
            aux[j] = this.configuracao_atual[j];
        }
        return Greedy1.decrease_key(aux, this.configuracao_atual.length, retangulos_copy, pontos_copy);
    }

    public int contarGuardas() {
        int total = 0;
        for (int i = 1; i < this.configuracao_atual.length; i++) {
            if (this.configuracao_atual[i] == -1) {
                total++;
            }
        }
        return total;
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
