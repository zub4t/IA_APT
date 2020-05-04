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
public class DFS {
    public static void DFS(Node root) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = list.remove(list.size() - 1);
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

            }
            for (int i = 1; i < current.configuracao_atual.length; i++) {
                Node node = new Node(current);
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
                            list.add(node);
                            map.put(node, Boolean.TRUE);
                        }

                    }
                }
            }
        }
    }
}
