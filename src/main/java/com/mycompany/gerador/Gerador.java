/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author marco
 */
public class Gerador {

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
            System.out.println("Ponto com x - " + pontos[i].x + " ; y - " + pontos[i].y + " id = " + i);
            for (Ret retangulo : pontos[i].ret_list) {
                System.out.println("Retangulo com id " + retangulo.getId());
            }
        }
    }

    public static void main(String[] args) {
        Ret[] retangulos = null;
        Ponto[] pontos = null;
        int num_ret = 0;
        int cur_ponto_id = 0;
        try {
            File myObj = new File("C:/Users/pedro/Documents/NetBeansProjects/IA_APT/src/main/java/com/mycompany/gerador/input.txt");
            Scanner myReader = new Scanner(myObj);
            num_ret = myReader.nextInt();
            retangulos = new Ret[num_ret + 1];
            pontos = new Ponto[num_ret * 4];
            cur_ponto_id = 1;
            Map<Ponto, Integer> check = new TreeMap<>();
            while (myReader.hasNext()) {
                int id_ret = myReader.nextInt();
                int nr_pontos = myReader.nextInt();
                Ret retangulo = new Ret(id_ret);
                if (id_ret == 2) {
                    System.out.println("teste");
                }
                for (int i = 0; i < nr_pontos; i++) {
                    int x = myReader.nextInt();
                    int y = myReader.nextInt();
                    Ponto ponto = new Ponto(x, y);
                    if (check.get(ponto) == null) {
                        ponto.setId(cur_ponto_id);
                        pontos[cur_ponto_id] = ponto;
                        pontos[cur_ponto_id].addRet(retangulo);
                        retangulo.addPonto(cur_ponto_id);
                        check.put(ponto, cur_ponto_id);
                        cur_ponto_id++;
                    } else {
                        pontos[check.get(ponto)].addRet(retangulo);
                        retangulo.addPonto(check.get(ponto));
                    }
                }
                retangulos[id_ret] = retangulo;
            }
        } catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }
        printRetToPonto(retangulos, pontos, num_ret);
        System.out.println();
        printPontoToRet(pontos, retangulos, cur_ponto_id - 1);
        System.out.println();
        int[] ponto_quant_ret = new int[cur_ponto_id];
        for (int i = 1; i <= cur_ponto_id - 1; i++) {
            ponto_quant_ret[i] = pontos[i].ret_list.size();
            System.out.print(ponto_quant_ret[i] + " ");
        }
        
        int[] ret_quant_ponto = new int[num_ret + 1];
        for(int i = 1; i <= num_ret; i++){
            ret_quant_ponto[i] = retangulos[i].pontos_list.size() - retangulos[i].pontos_guardados;
        }
        System.out.println();
        ponto_quant_ret[2] = -1;
        ponto_quant_ret[12] = -1;
        // Greedy 1 = (orientada por vértices)  colocar um guarda no vértice que é partilhado por mais retângulos ainda não cobertos
        System.out.println("Greedy 1 = " + Greedy1.decrease_key(ponto_quant_ret, cur_ponto_id, retangulos, pontos));
         //Greedy 2 = (orientada por retângulos) escolher o retângulo ainda não coberto que tenha menos vértices incidentes e colocar um guarda num desses vértices que seja partilhado por mais retângulos ainda não cobertos
        //System.out.println("Greedy 2 = " + Greedy2.increase_key(ret_quant_ponto, num_ret, retangulos, pontos));
        // Greedy 3 = (orientada por retângulos) variante de 2. em que, em caso de igualdade entre vértices, opta pelo que cobre retângulos que globalmente tenham mais vértices incidentes
        //System.out.println("Greedy 3 = " + Greedy3.increase_key(ret_quant_ponto, num_ret, retangulos, pontos));
    }

}
