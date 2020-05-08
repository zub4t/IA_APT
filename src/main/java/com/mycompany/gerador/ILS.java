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
        stack.add(currentSolution);
        //normal
        while (!stack.isEmpty()) {
            currentSolution = stack.pop();
            for (int i = 1; i < currentSolution.configuracao_atual.length; i++) {
                if (currentSolution.configuracao_atual[i] == -1) {
                    list.add(i);
                }
            }
            boolean flag = false;
            for (int i = 0; i < list.size(); i++) {
                int pos = list.get(i);
                for (int j = 1; j < currentSolution.configuracao_atual.length; j++) {
                    Node testNode = new Node(currentSolution);
                    testNode.configuracao_atual[pos] = 0;
                    if (testNode.configuracao_atual[j] == 0 && j != pos) {
                        testNode.configuracao_atual[j] = -1;
                        if (mapa.get(testNode) == null) {
                            for (int k = 1; k < testNode.configuracao_atual.length; k++) {
                                //System.out.print(" " + testNode.configuracao_atual[k]);
                            }
                            mapa.put(testNode, true);
                            testNode = ILS_heuristic(testNode, pontos, retangulos.length - 1);
                            if (testNode == null) {
                                //System.out.println(" - nao e solucao");
                            } else {
                                //System.out.println(" - e solucao");
                            }
                            if (testNode != null && testNode.contarGuardas() < currentSolution.contarGuardas()) {
                                stack.push(testNode);
                                flag = true;
                                break;
                            }
                            if (testNode != null) {
                                testNode.configuracao_atual[j] = 0;
                            }
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return currentSolution;
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
        stack.add(currentSolution);
        //normal
        while (!stack.isEmpty()) {
            currentSolution = stack.pop();
            for (int i = 1; i < currentSolution.configuracao_atual.length; i++) {
                if (currentSolution.configuracao_atual[i] == -1) {
                    list.add(i);
                }
            }
            boolean flag = false;
            for (int i = 0; i < list.size(); i++) {
                int pos = list.get(i);
                Set<Integer> set_check = new TreeSet<>();
                while (set_check.size() != currentSolution.configuracao_atual.length - 1) {
                    int j = rand.nextInt(currentSolution.configuracao_atual.length - 1) + 1;
                    if (!set_check.contains(j)) {
                        Node testNode = new Node(currentSolution);
                        testNode.configuracao_atual[pos] = 0;
                        if (testNode.configuracao_atual[j] == 0 && j != pos) {
                            testNode.configuracao_atual[j] = -1;
                            if (mapa.get(testNode) == null) {
                                for (int k = 1; k < testNode.configuracao_atual.length; k++) {
                                    //System.out.print(" " + testNode.configuracao_atual[k]);
                                }
                                mapa.put(testNode, true);
                                testNode = ILS_heuristic(testNode, pontos, retangulos.length - 1);
                                if (testNode == null) {
                                    //System.out.println(" - nao e solucao");
                                } else {
                                    //System.out.println(" - e solucao");
                                }
                                if (testNode != null && testNode.contarGuardas() < currentSolution.contarGuardas()) {
                                    stack.push(testNode);
                                    flag = true;
                                    break;
                                }
                                if (testNode != null) {
                                    testNode.configuracao_atual[j] = 0;
                                }
                            }
                        }
                        set_check.add(j);
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        //random
        return currentSolution;
    }

    public static Node ILS_heuristic(Node node, Ponto[] pontos, int num_ret) {
        boolean flag = false;
        Set<Integer> set = new TreeSet<>();
        for (int i = 1; i < node.configuracao_atual.length; i++) {
            if (node.configuracao_atual[i] == -1 && !flag) {
                for (Ret retangulo : pontos[i].ret_list) {
                    set.add(retangulo.getId());
                }
            } else if (flag) {
                node.configuracao_atual[i] = 0;
            }
            if (set.size() == num_ret) {
                flag = true;
            }
        }
        if (set.size() != num_ret) {
            return null;
        }
        return node;
    }
}
