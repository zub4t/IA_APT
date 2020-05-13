package com.mycompany.gerador;
import static com.mycompany.gerador.Gerador.pontos;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
public class BFS {
    public static void BFS(Node root, Ret[] rets) {
        Queue<Node> list = new LinkedList<Node>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            //retirar o primeiro elemento da queue
            Node current = list.poll();
            //fazer teste para verificar se é uma solução, se for dar print e sair
            if (Util.teste(current) == 0) {
                Util.printSolution(current, pontos);
                break;
            }
            //gerar os filhos do no que retiramos da queue e inserir na queue
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                    list.add(n);
            }
        }
    }
}
