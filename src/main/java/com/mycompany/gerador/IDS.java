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
import java.util.TreeMap;

/**
 *
 * @author pedro
 */
public class IDS {

    public static void startIDS(Node root) {
        int cur_max_height = 0;
        System.out.println(" altura : " + cur_max_height);
        while (!IDS(root, cur_max_height)) {
            cur_max_height++;
            System.out.println(" altura : " + cur_max_height);
        }
        System.out.println(" altura : " + cur_max_height);
    }

    public static boolean IDS(Node root, int max_height) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = null;
            current = list.remove(0);
            /*if (cur_max_height == max_height) {
                current = list.remove(0);
            } else {
                current = list.remove(0);
                
            }*/
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
                while(current != null){
                    for(int i : current.configuracao_atual)
                        System.out.print(" " + i);
                    System.out.println();
                    current = current.pai;
                }
                return true;
            }
            if (current.altura != max_height) {
                boolean add_start = list.size() > 0;
                for (int i = 1; i < current.configuracao_atual.length; i++) {
                    Node node = new Node(current);
                    node.pai = current;
                    if (node.pontos[i] != null) {
                        if (node.pontos[i].ret_list.size() == 0) {
                            node.configuracao_atual[i] = -2;
                        } else if (node.configuracao_atual[i] != -1) {
                            node.configuracao_atual[i] = -1;
                            node.ord.add(i);
                            if (map.get(node) == null) {
                                for (Ret ret : node.pontos[i].ret_list) {
                                    decrease_ponto_ret(ret, node, node.pontos[i]);
                                }
                                node.pontos[i].ret_list.clear();
                                node.altura = node.pai.altura + 1;
                                //ordem da direita para a esquerda nos filhos se houver pais pendentes na fila
                                if(add_start){
                                    list.add(0, node);
                                } else {
                                    list.add(node);
                                }
                                
                                map.put(node, Boolean.TRUE);
                            }
                        }
                    }
                }
            } 
        }
        return false;
    }
}
