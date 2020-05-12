/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.decrease_ponto_ret;
import static com.mycompany.gerador.Gerador.teste;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author pedro
 */
public class BranchBound {

    public static Node branch_bound(Node root, Ret[] retangulos, Ponto[] pontos) {
        Queue<Node> list = new LinkedList<Node>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        int bound = Integer.MAX_VALUE;
        Node solution = null;
        while (!list.isEmpty()) {
            Node current = list.poll();
            int current_bound = current.contarGuardas();
            boolean meter_filhos = true;
            if (teste(current) == 0) {
                System.out.println("aqui");
                if (current_bound < bound) {
                    
                    bound = current_bound;
                    solution = current;
                }
            }
            if (current_bound + current.funcao_h >= bound) {
                meter_filhos = false;
            }
            if (meter_filhos) {
                for (int i = 1; i < current.configuracao_atual.length; i++) {
                    Node node = new Node(current);
                    if (node.pontos[i] != null) {
                        if (node.pontos[i].ret_list.size() == 0) {
                            node.configuracao_atual[i] = -2;
                        } else if (node.configuracao_atual[i] != -1 && node.pontos[i].ret_list.size() > 0) {
                            node.configuracao_atual[i] = -1;
                            node.ord.add(i);
                            if (map.get(node) == null) {
                                for (Ret ret : node.pontos[i].ret_list) {
                                    decrease_ponto_ret(ret, node, node.pontos[i]);
                                }

                                Set<Integer> set = new TreeSet<>();
                                for (int j = 1; j < node.configuracao_atual.length; j++) {
                                    if (node.configuracao_atual[j] == -1) {
                                        for (Ret retangulo : pontos[j].ret_list) {
                                            set.add(retangulo.getId());
                                        }
                                    }
                                }
                                node.pontos[i].ret_list.clear();
                                node.funcao_g = set.size();
                                node.funcao_h = node.calcularHeuristica(retangulos, pontos);
                                list.add(node);
                                map.put(node, Boolean.TRUE);
                            }
                        }
                    }
                }
            }
        }
        return solution;
    }
}
