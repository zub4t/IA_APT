package com.mycompany.gerador;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
public class BranchBound {
    public static Node branch_bound_stack(Node root, Ret[] retangulos, Ponto[] pontos) {
        Stack<Node> list = new Stack<Node>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        int bound = Integer.MAX_VALUE;
        Node solution = null;
        // enquanto não achar a melhor solução possivel fazer  
        while (!list.isEmpty()) {
            Node current = list.pop();
            int current_bound = current.contarGuardas();
            boolean meter_filhos = true;
            // verificando se o novo node é uma solução melhor que a atual e se for substituir
            if (Util.teste(current) == 0) {
                if (current_bound < bound) {
                    bound = current_bound;
                    solution = current;
                }
            }
            if (current_bound + current.funcao_h > bound) {
                // caso  meter filhos seja igual a falso não validar  o resto das instancias que esse node poderia gerar
                meter_filhos = false;
            }
            if (meter_filhos) {
                List<Node> aux = current.gerarFilhos(map);
                for (Node node : aux) {
                    Set<Integer> set = new TreeSet<>();
                    for (int j = 1; j < node.configuracao_atual.length; j++) {
                        if (node.configuracao_atual[j] == -1) {
                            for (Ret retangulo : pontos[j].ret_list) {
                                set.add(retangulo.getId());
                            }
                        }
                    }
                    node.funcao_g = node.contarGuardas();
                    node.funcao_h = (int)Math.ceil((retangulos.length - set.size()) / 3.0);
                    list.push(node);
                    map.put(node, Boolean.TRUE);
                }
            }
        }
        return solution;
    }
    public static Node branch_bound_queue(Node root, Ret[] retangulos, Ponto[] pontos) {
     
        Queue<Node> list = new LinkedList<Node>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        int bound = Integer.MAX_VALUE;
        Node solution = null;
        while (!list.isEmpty()) {
            Node current = list.poll();
            int current_bound = current.contarGuardas();
            boolean meter_filhos = true;
            if (Util.teste(current) == 0) {
                if (current_bound < bound) {
                    bound = current_bound;
                    solution = current;
                }
            }
            if (current_bound + current.funcao_h > bound) {
                meter_filhos = false;
            }
            if (meter_filhos) {
                List<Node> aux = current.gerarFilhos(map);
                for (Node node : aux) {
                    Set<Integer> set = new TreeSet<>();
                    for (int j = 1; j < node.configuracao_atual.length; j++) {
                        if (node.configuracao_atual[j] == -1) {
                            for (Ret retangulo : pontos[j].ret_list) {
                                set.add(retangulo.getId());
                            }
                        }
                    }
                    node.funcao_g = node.contarGuardas();
                    node.funcao_h = (int)Math.ceil((retangulos.length - set.size()) / 3.0);
                    list.add(node);
                    map.put(node, Boolean.TRUE);
                }
            }
        }
        return solution;
    }
}
