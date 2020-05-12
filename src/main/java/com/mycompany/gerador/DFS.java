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
public class DFS {

    public static void DFS(Node root) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = list.remove(list.size() - 1);
            if (teste(current) == 0) {
                Util.printSolution(current, pontos);
                break;
            }
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                list.add(list.size(), n);
            }
        }
    }

    public static void DFS_com_prop_restr(Node root, Ponto[] pontos, Ret[] rets) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        Set<Integer> retangulos;
        int total_guardas = 0;
        while (!list.isEmpty()) {
            Node current = list.remove(list.size() - 1);
            if (teste(current) == 0) {
                Util.printSolution(current, pontos);
                break;
            }
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                retangulos = new TreeSet<>();
                total_guardas = 0;
                for (int i = 1; i < n.configuracao_atual.length; i++) {
                    if (n.configuracao_atual[i] == -1) {
                        total_guardas++;
                        for (Ret retangulo : pontos[i].ret_list) {
                            retangulos.add(retangulo.getId());
                        }
                    }
                }
                int nr_ret_faltam = rets.length - retangulos.size();
                if (total_guardas <= retangulos.size() / 2.0 || nr_ret_faltam == 1) {
                    list.add(list.size(), n);
                }
              
            }
        }
    }
}
