/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.pontos;

/**
 *
 * @author pedro
 */
public class Util {

    public static void printSolution(Node current) {
        System.out.println("----------------");
        System.out.println("Nova solução");
        System.out.println("Numero de guardas = " + current.ord.size());
        for (int i : current.ord) {
            System.out.println("Guarda colocado no ponto " + i);
            for (Ret ret : pontos[i].ret_list) {
                System.out.println("Retangulo: " + ret.getId());
            }
        }
        System.out.println("----------------");
    }
}
