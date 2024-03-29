/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.pontos;
import static com.mycompany.gerador.Gerador.retangulos;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
        for (int i = 0; i < current.configuracao_atual.length; i++) {
            if (current.configuracao_atual[i] == -1) {
                System.out.println("Ponto guardado com id " + i);
                for (Ret ret : pontos[i].ret_list) {
                    System.out.println("Retangulo com id " + ret.getId());
                }
            }

        }
        System.out.println("----------------");
    }

    public static void printRetToPonto(Ret[] retangulos, Ponto[] pontos, int nr_ret) {
        System.out.println("Retangulo para ponto:");
        for (int i = 1; i <= nr_ret; i++) {
            System.out.println("Retangulo numero " + i);
            for (int ponto_id : retangulos[i].pontos_list) {
                System.out.println("Ponto: x - " + pontos[ponto_id].x + " ; y - " + pontos[ponto_id].y);
            }
        }
    }

    public static void printPontoToRet(Ponto[] pontos, Ret[] retangulos, int nr_pontos) {
        System.out.println("Pontos para retangulos:");
        for (int i = 1; i <= nr_pontos; i++) {

            System.out.println("Ponto com x - " + pontos[i].x + " ; y - " + pontos[i].y + " id =" + i);

            for (Ret retangulo : pontos[i].ret_list) {
                System.out.println("Retangulo com id " + retangulo.getId());
            }
        }
    }

    public static int teste(Node node) {
        int aux = 0;
        for (Ponto p : node.pontos) {
            if (p != null) {
                aux += p.ret_list.size();
            }
        }
        return aux;
    }

    public static void endireitarConfiguracao(Node node, Set<Integer> rets) {
        for (int i = 1; i < node.configuracao_atual.length; i++) {
            int total = 0;
            if (node.configuracao_atual[i] != -1) {
                for (Ret retangulo : pontos[i].ret_list) {
                    if (!rets.contains(retangulo.getId())) {
                        total++;
                    }
                }
                node.configuracao_atual[i] = total;
            }
        }
    }

    public static void decrease_ponto_ret(Ret ret, Node node, Ponto ponto) {
        for (Ponto p : node.pontos) {
            if (p != null && p.compareTo(ponto) != 0) {
                p.ret_list.remove(ret);
            }
        }
    }

    public static Ponto[] copyPontos() {
        Ponto[] pontos_copy = new Ponto[Gerador.pontos.length];
        for (int j = 1; j < Gerador.pontos.length; j++) {
            if (Gerador.pontos[j] == null) {
                break;
            }
            pontos_copy[j] = new Ponto(Gerador.pontos[j]);
        }
        return pontos_copy;

    }

    public static Ret[] copyRet() {
        Ret[] retangulos_copy = new Ret[Gerador.retangulos.length];
        for (int j = 1; j < Gerador.retangulos.length; j++) {
            retangulos_copy[j] = new Ret(Gerador.retangulos[j]);
        }
        return retangulos_copy;

    }

    public static int[] makeInstancePonto(int cur_ponto_id) {

        int[] ponto_quant_ret = new int[cur_ponto_id];
        for (int i = 1; i <= cur_ponto_id - 1; i++) {
            ponto_quant_ret[i] = pontos[i].ret_list.size();
        }
        return ponto_quant_ret;
    }

    public static int[] makeInstanceRet(int num_ret) {
        int[] ret_quant_ponto = new int[num_ret + 1];
        for (int i = 1; i <= num_ret; i++) {
            ret_quant_ponto[i] = retangulos[i].pontos_list.size() - retangulos[i].pontos_guardados;
        }
        return ret_quant_ponto;
    }

}
