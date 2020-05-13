/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author marco
 */
public class Node implements Comparable<Node> {

    public Ponto[] pontos;
    public int[] configuracao_atual;
    public int funcao_g;
    public int funcao_h;
    public List<Node> filhos = new ArrayList<>();
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

    public List<Node> gerarFilhos(Map<Node, Boolean> map) {
        for (int i = 1; i < this.configuracao_atual.length; i++) {
            Node node = new Node(this);
            node.pai = this;
            if (node.pontos[i] != null) {
                if (!node.pontos[i].ret_list.isEmpty() && node.configuracao_atual[i] != -1) {
                    node.configuracao_atual[i] = -1;
                    node.ord.add(i);
                    if (map.get(node) == null) {
                        for (Ret ret : node.pontos[i].ret_list) {
                            Util.decrease_ponto_ret(ret, node, node.pontos[i]);
                        }
                        node.pontos[i].ret_list.clear();
                        node.altura = this.altura + 1;
                        Set<Integer> retanguloSet = new TreeSet<>();
                        for (int ponto_id : this.ord) {
                            if (Gerador.pontos[ponto_id] != null) {
                                for (Ret retangulo : Gerador.pontos[ponto_id].ret_list) {
                                    retanguloSet.add(retangulo.getId());
                                }
                            }
                        }
                        Set<Integer> retanguloNodeSet = new TreeSet<>();
                        for (int ponto_id : node.ord) {
                            if (Gerador.pontos[ponto_id] != null) {
                                for (Ret retangulo : Gerador.pontos[ponto_id].ret_list) {
                                    retanguloNodeSet.add(retangulo.getId());
                                }
                            }
                        }
                        Util.endireitarConfiguracao(this, retanguloSet);
                        int n = 0;
                        for(int j = 1; j < this.configuracao_atual.length; j++){
                            if(configuracao_atual[j] > n)
                                n = configuracao_atual[j];
                        }
                        if (retanguloNodeSet.size() - retanguloSet.size() >= n) {
                            filhos.add(node);
                            map.put(node, Boolean.TRUE);
                        }
                    }
                }
            }
        }
        return filhos;
    }

}
