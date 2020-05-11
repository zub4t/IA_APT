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
                Util.printSolution(current);
                break;
            }
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                list.add(list.size(), n);
            }
        }
    }
}
