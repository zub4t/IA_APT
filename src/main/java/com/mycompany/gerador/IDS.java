/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;
import static com.mycompany.gerador.Gerador.pontos;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 *
 * @author pedro
 */
public class IDS {

    public static void startIDS(Node root) {
        int cur_max_height = 9;
        Map<Node, Boolean> map = new TreeMap<>();
        while (!IDS(root, cur_max_height, map)) {
            cur_max_height++;
        }
    }

    public static boolean IDS(Node root, int max_height, Map<Node, Boolean> map) {
        Stack<Node> list = new Stack<>();
        list.add(root);
        while (!list.isEmpty()) {
            Node current = null;
            current = list.pop();
            if (Util.teste(current) == 0) {
                //Util.printSolution(current, pontos);
                System.out.println("Guardas: " + current.ord.size());
                return true;
            }
            if (current.altura + 1 <= max_height) {
                List<Node> aux = current.gerarFilhos(map);
                for (Node node : aux) {
                    list.add(node);
                }
            }

        }
        return false;
    }
}
