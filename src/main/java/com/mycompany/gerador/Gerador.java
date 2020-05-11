/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import colonia_formigas.Formiga;
import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author marco
 */
public class Gerador {

    static Ret[] retangulos = null;
    static Ponto[] pontos = null;

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

    public static int decrease_key(int[] ponto_quant_ret, int size, Ret[] retangulos, Ponto[] pontos) {
        int guardas = 0;
        Heapmax heap = new Heapmax(ponto_quant_ret, size - 1);
        while (!heap.isEmpty()) {
            for (int i = 1; i <= heap.size; i++) {
                heap.heapify(i);
            }
            int ponto_candidato = heap.extractMax();

            if (!pontos[ponto_candidato].ret_list.isEmpty()) {
                System.out.println("Ponto guardado = x - " + pontos[ponto_candidato].x + " ; y - " + pontos[ponto_candidato].y);
                for (Ret retangulo : pontos[ponto_candidato].ret_list) {
                    System.out.print(retangulo.getId() + " ");
                }
                System.out.println();
                guardas++;
                for (Ret retangulo : pontos[ponto_candidato].ret_list) {
                    retangulo.pontos_list.remove((Integer) ponto_candidato);
                    for (int ponto_id : retangulo.pontos_list) {
                        pontos[ponto_id].ret_list.remove(retangulo);
                        heap.increaseKey(ponto_id, pontos[ponto_id].ret_list.size());
                    }
                }
            }
        }
        return guardas;
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

    public static void decrease_ponto_ret(Ret ret, Node node, Ponto ponto) {
        for (Ponto p : node.pontos) {
            if (p != null && p.compareTo(ponto) != 0) {
                p.ret_list.remove(ret);

            }

        }
    }

    public static void main(String[] args) {

        int num_ret = 0;
        int cur_ponto_id;
        long startTime = System.nanoTime();
        try {
            File myObj = new File("C:/Users/marco/Documents/NetBeansProjects/Gerador/src/main/java/com/mycompany/gerador/input.txt");
            Scanner myReader = new Scanner(myObj);
            int numero_instancias = myReader.nextInt();

            //cur_ponto_id = 1;
            Map<Ponto, Integer> check;

            while (myReader.hasNext()) {
                for (int k = 0; k < numero_instancias; k++) {
                    cur_ponto_id = 1;
                    check = new TreeMap<>();
                    num_ret = myReader.nextInt();
                    retangulos = new Ret[num_ret + 1];
                    pontos = new Ponto[num_ret * 4];
                    for (int j = 1; j <= num_ret; j++) {
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

                    printRetToPonto(retangulos, pontos, num_ret);
                    System.out.println();
                    printPontoToRet(pontos, retangulos, cur_ponto_id - 1);
                    System.out.println();
                    int[] ponto_quant_ret = new int[cur_ponto_id];
                    for (int i = 1; i <= cur_ponto_id - 1; i++) {
                        ponto_quant_ret[i] = pontos[i].ret_list.size();
                        //System.out.print(ponto_quant_ret[i] + " ");
                    }

                    int[] ret_quant_ponto = new int[num_ret + 1];
                    for (int i = 1; i <= num_ret; i++) {
                        ret_quant_ponto[i] = retangulos[i].pontos_list.size() - retangulos[i].pontos_guardados;
                    }
                    System.out.println();

                    //System.out.print(decrease_key(ponto_quant_ret, cur_ponto_id, retangulos, pontos));
                    Node root = new Node(ponto_quant_ret, pontos);
                    //Formiga
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
                    Formiga formiga = new Formiga(1, pontos_copy, retangulos_copy,root);
                    formiga.run();
                    //BFS
                    // BFS.BFS(root);
                    //DFS
                    //DFS.DFS(root);
                    //IDS
                    //IDS.startIDS(root);
                    //A*
                    //Astar.Astar(root, retangulos, pontos);
                    //Branch and bound
                    //Node BB = BranchBound.branch_bound(root, retangulos, pontos);
                    //ILS NORMAL
                   // Node n = ILS.ILS_deterministico(root.configuracao_atual, root.configuracao_atual.length, retangulos, pontos);
                    //ILS RANDOMs
                   // Node nn = ILS.ILS_random(root.configuracao_atual, root.configuracao_atual.length, retangulos, pontos);
                    /*for(int ponto_id : BB.ord){
                        Ponto ponto = pontos[ponto_id];
                        System.out.println("Ponto com id " + ponto.id + " x - " + ponto.x + " y - " + ponto.y);
                        for(Ret retangulo : ponto.ret_list){
                            System.out.println("Retangulo id : " + retangulo.getId());
                        }
                    }*/
                    // Greedy 1 = (orientada por vértices)  colocar um guarda no vértice que é partilhado por mais retângulos ainda não cobertos
                    //System.out.println("Greedy 1 = " + Greedy1.decrease_key(ponto_quant_ret, cur_ponto_id, retangulos, pontos));
                    //Greedy 2 = (orientada por retângulos) escolher o retângulo ainda não coberto que tenha menos vértices incidentes e colocar um guarda num desses vértices que seja partilhado por mais retângulos ainda não cobertos
                    //System.out.println("Greedy 2 = " + Greedy2.increase_key(ret_quant_ponto, num_ret, retangulos, pontos));
                    // Greedy 3 = (orientada por retângulos) variante de 2. em que, em caso de igualdade entre vértices, opta pelo que cobre retângulos que globalmente tenham mais vértices incidentes
                    //System.out.println("Greedy 3 = " + Greedy3.increase_key(ret_quant_ponto, num_ret, retangulos, pontos));

                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000000000.0);
    }

}
