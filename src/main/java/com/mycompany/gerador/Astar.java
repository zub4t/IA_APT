package com.mycompany.gerador;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
public class Astar {
    public static void Astar(Node root, Ret[] retangulos, Ponto[] pontos) {
        Stack <Node> list = new Stack<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            if (list.size() > 1) {
                Node escolhido = null;
                int menor_f = Integer.MAX_VALUE;
                //verificar na stack qual o que tem menor f(x) (g(x) + h(x)) e apagar o resto da stack para depois só meter o menor
                for (Node no : list) {
                    int f = no.funcao_g  + no.funcao_h;
                    if (f <= menor_f) {
                        menor_f = f;
                        escolhido = no;
                    }
                }
                list.clear();
                list.push(escolhido);
            }
            Node current = list.pop();
            //fazer teste para verificar se é uma solução, se for dar print e sair
            if (Util.teste(current) == 0) {
                Util.printSolution(current, pontos);
                break;
            }
            List<Node> aux = current.gerarFilhos(map);
            for (Node node : aux) {
                //o set serve para contabilizar os retangulos guardados para cada node
                Set<Integer> set = new TreeSet<>();
                for (int j = 1; j < node.configuracao_atual.length; j++) {
                    if (node.configuracao_atual[j] == -1) {
                        for (Ret retangulo : pontos[j].ret_list) {
                            set.add(retangulo.getId());
                        }
                    }
                }
                //funcao g = numero de guardas
                node.funcao_g = node.contarGuardas();
                //funcao h = numero de retangulos que faltam guardar / 3 com aproximação para cima
                node.funcao_h = (int)Math.ceil((retangulos.length - set.size()) / 3.0);
                list.push(node);
            }
        }
    }
}
