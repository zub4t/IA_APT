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
public class BFS {

    public static void BFS(Node root, Ret[] rets) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = list.remove(0);
            if (teste(current) == 0) {
                Util.printSolution(current, pontos);
                break;
            }
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                    list.add(n);
            }
        }
    }
}
