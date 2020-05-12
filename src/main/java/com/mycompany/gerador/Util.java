/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

/**
 *
 * @author pedro
 */
public class Util {

    public static void printSolution(Node current, Ponto[] pontos) {
        System.out.println("----------------");
        System.out.println("Nova Solução");
        System.out.println("Numero de guardas: " + current.ord.size());
        for (int i : current.ord) {
            System.out.println("Ponto guardado com id " + i);
            for (Ret ret : pontos[i].ret_list) {
                System.out.println("Retangulo com id " + ret.getId());
            }
        }
        System.out.println("----------------");
    }

    public static void printSolutionWithoutOrd(Node current, Ponto[] pontos) {
        System.out.println("----------------");
        System.out.println("Nova Solução");
        System.out.println("Numero de guardas: " + current.contarGuardas());
        for(int i = 0; i < current.configuracao_atual.length; i++){
            if (current.configuracao_atual[i] == -1) {
                System.out.println("Ponto guardado com id " + i);
                for (Ret ret : pontos[i].ret_list) {
                    System.out.println("Retangulo com id " + ret.getId());
                }
            }

        }
        System.out.println("----------------");
    }
}
