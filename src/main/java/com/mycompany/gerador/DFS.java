package com.mycompany.gerador;
import static com.mycompany.gerador.Gerador.pontos;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class DFS {
    public static void DFS(Node root) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = list.remove(list.size() - 1);
            if (Util.teste(current) == 0) {
                System.out.println("Guardas: " + current.ord.size());
                //Util.printSolution(current, pontos);
                break;
            }
            List<Node> aux_list = current.gerarFilhos(map);
            for (Node n : aux_list) {
                list.add(list.size(), n);
            }
        }
    }
}
