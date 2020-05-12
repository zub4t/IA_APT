/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.decrease_ponto_ret;
import static com.mycompany.gerador.Gerador.teste;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author pedro
 */
public class ILS {

    public static Node nodeSolution;

    public static void fazerTeste(Node node, Node currentSolution, Stack<Node> stack, Map<Node, Boolean> mapa) {
        if (node != null && mapa.get(node) == null) {
            mapa.put(node, Boolean.TRUE);
            if (node != null && ((node.contarGuardas() <= nodeSolution.contarGuardas()))) {
                nodeSolution = node;
                stack.push(node);
            }
        }
    }

    public static Node ILS_deterministico(int[] ponto_quant_ret, int size, Ret[] retangulos, Ponto[] pontos) {
        Ret[] retangulos_copy = new Ret[retangulos.length];
        for (int j = 1; j < retangulos.length; j++) {
            retangulos_copy[j] = new Ret(retangulos[j]);
        }
        Ponto[] pontos_copy = new Ponto[pontos.length];
        for (int j = 1; j < pontos.length; j++) {
            if (pontos[j] == null) {
                break;
            }
            pontos_copy[j] = new Ponto(pontos[j]);
        }
        Node currentSolution = Greedy1.decrease_key_com_node(ponto_quant_ret, size, retangulos_copy, pontos_copy);
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<Node>();
        Map<Node, Boolean> mapa = new TreeMap<>();
        nodeSolution = currentSolution;
        stack.add(currentSolution);
        //normal
        while (!stack.isEmpty()) {

            list.clear();
            currentSolution = stack.pop();
            //if(currentSolution.ord.contains(2) && currentSolution.ord.contains(10) && currentSolution.ord.contains(15) && currentSolution.ord.contains(20) && currentSolution.ord.size() == 4)
            //System.out.println();
            for (int i = 1; i < currentSolution.configuracao_atual.length; i++) {
                if (currentSolution.configuracao_atual[i] == -1) {
                    list.add(i);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                int pos = list.get(i);
                currentSolution.configuracao_atual[pos] = 0;
                Node testNodeComZero = ILS_heuristic(currentSolution, pontos, retangulos.length - 1);
                fazerTeste(testNodeComZero, currentSolution, stack, mapa);
                currentSolution.configuracao_atual[pos] = -1;
                for (int j = 1; j < currentSolution.configuracao_atual.length; j++) {
                    Node testNode = new Node(currentSolution);
                    testNode.configuracao_atual[pos] = 0;
                    if (testNode.configuracao_atual[j] == 0 && j != pos) {
                        testNode.configuracao_atual[j] = -1;
                        Node testNodeSemZero = ILS_heuristic(testNode, pontos, retangulos.length - 1);
                        fazerTeste(testNodeSemZero, currentSolution, stack, mapa);
                    }
                }
            }
        }
        return nodeSolution;
    }

    public static Node ILS_random(int[] ponto_quant_ret, int size, Ret[] retangulos, Ponto[] pontos) {
        Random rand = new Random();
        Ret[] retangulos_copy = new Ret[retangulos.length];
        for (int j = 1; j < retangulos.length; j++) {
            retangulos_copy[j] = new Ret(retangulos[j]);
        }
        Ponto[] pontos_copy = new Ponto[pontos.length];
        for (int j = 1; j < pontos.length; j++) {
            if (pontos[j] == null) {
                break;
            }
            pontos_copy[j] = new Ponto(pontos[j]);
        }
        Node currentSolution = Greedy1.decrease_key_com_node(ponto_quant_ret, size, retangulos_copy, pontos_copy);
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<Node>();
        Map<Node, Boolean> mapa = new TreeMap<>();
        nodeSolution = currentSolution;
        stack.add(currentSolution);
        //normal
        while (!stack.isEmpty()) {

            list.clear();
            currentSolution = stack.pop();
            //if(currentSolution.ord.contains(2) && currentSolution.ord.contains(10) && currentSolution.ord.contains(15) && currentSolution.ord.contains(20) && currentSolution.ord.size() == 4)
            //System.out.println();
            for (int i = 1; i < currentSolution.configuracao_atual.length; i++) {
                if (currentSolution.configuracao_atual[i] == -1) {
                    list.add(i);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                int pos = list.get(i);
                currentSolution.configuracao_atual[pos] = 0;
                Node testNodeComZero = ILS_heuristic(currentSolution, pontos, retangulos.length - 1);
                fazerTeste(testNodeComZero, currentSolution, stack, mapa);
                currentSolution.configuracao_atual[pos] = -1;
                Set<Integer> numeros_random = new TreeSet<Integer>();
                while (numeros_random.size() < currentSolution.configuracao_atual.length - currentSolution.contarGuardas()) {
                    int j = rand.nextInt(currentSolution.configuracao_atual.length - 1) + 1;
                    if (!numeros_random.contains(j)) {
                        numeros_random.add(j);
                        Node testNode = new Node(currentSolution);
                        testNode.configuracao_atual[pos] = 0;
                        if (testNode.configuracao_atual[j] == 0 && j != pos) {
                            testNode.configuracao_atual[j] = -1;
                            Node testNodeSemZero = ILS_heuristic(testNode, pontos, retangulos.length - 1);
                            fazerTeste(testNodeSemZero, currentSolution, stack, mapa);
                        }
                    }
                }

            }
        }
        return nodeSolution;
    }

    public static Node ILS_heuristic(Node node, Ponto[] pontos, int num_ret) {
        boolean flag = false;
        Set<Integer> set = new TreeSet<>();
        Node node_final = new Node(node);
        node_final.ord.clear();
        for (int i = 1; i < node_final.configuracao_atual.length; i++) {
            if (node_final.configuracao_atual[i] == -1 && !flag) {
                node_final.ord.add(i);
                for (Ret retangulo : pontos[i].ret_list) {
                    set.add(retangulo.getId());
                }
            } else if (flag) {
                node_final.configuracao_atual[i] = 0;
            }
            if (set.size() == num_ret) {
                flag = true;
            }
        }
        if (set.size() != num_ret) {
            return null;
        }
        return node_final;
    }
}
