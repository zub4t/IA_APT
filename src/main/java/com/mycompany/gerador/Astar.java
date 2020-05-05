/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.decrease_ponto_ret;
import static com.mycompany.gerador.Gerador.pontos;
import static com.mycompany.gerador.Gerador.teste;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author pedro
 */
public class Astar {

    public static void Astar(Node root, Ret[] retangulos, Ponto[] pontos) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            if (list.size() > 1) {
                Node escolhido = null;
                int menor_f = Integer.MAX_VALUE;
                for (Node no : list) {
                    int f = no.funcao_g * (-1) + no.funcao_h;
                    if (f < menor_f) {
                        menor_f = f;
                        escolhido = no;
                    }
                }
                list.clear();
                list.add(escolhido);
            }
            Node current = list.remove(0);
            if (teste(current) == 0) {
                System.out.println("----------------");
                System.out.println("nova solução");
                for (int i : current.ord) {
                    System.out.println("guardar ponto de id " + i);
                    for (Ret ret : pontos[i].ret_list) {
                        System.out.println("ret " + ret.getId());
                    }
                }
                System.out.println("----------------");
                break;
            }
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
                            int[] aux = new int[node.configuracao_atual.length];
                            for (int j = 1; j < node.configuracao_atual.length; j++) {
                                aux[j] = node.configuracao_atual[j];
                            }
                            node.funcao_h = Greedy1.decrease_key(aux, node.configuracao_atual.length, retangulos_copy, pontos_copy);
                            list.add(node);
                            map.put(node, Boolean.TRUE);
                        }
                    }
                }
            }
        }
    }
}
