package com.mycompany.gerador;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gerador {

    static Ret[] retangulos = null;
    static Ponto[] pontos = null;

    public static void main(String[] args) {
        int num_ret = 0;
        int cur_ponto_id;
        long startTime = System.nanoTime();
        try {
            System.out.println("Incializando programa com o input que está no txt");
            //alterar nesta linha o path do txt
            File myObj = new File("C:/Users/pedro/Documents/NetBeansProjects/IA_APT2/src/main/java/com/mycompany/gerador/input.txt");
            Scanner myReader = new Scanner(myObj);
            int numero_instancias = myReader.nextInt();
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
                        //inicialização de cada retangulo
                        Ret retangulo = new Ret(id_ret);
                        //atribuição de cada conjunto de pontos ao seu respetivo retângulo
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

                    Util.printRetToPonto(retangulos, pontos, num_ret);
                    System.out.println();
                    Util.printPontoToRet(pontos, retangulos, cur_ponto_id - 1);
                    System.out.println();
                    //array em que cada posição é o ponto id e que por exemplo ponto_quant_ret[2] = quantidade de retangulos que o ponto 2 cobre
                    int[] ponto_quant_ret = new int[cur_ponto_id];
                    for (int i = 1; i <= cur_ponto_id - 1; i++) {
                        ponto_quant_ret[i] = pontos[i].ret_list.size();
                    }
                    //array em que cada posição é o retangulo id e que por exemplo ret_quant_ponto[2] = quantidade de pontos que o retangulo 2 tem
                    int[] ret_quant_ponto = new int[num_ret + 1];
                    for (int i = 1; i <= num_ret; i++) {
                        ret_quant_ponto[i] = retangulos[i].pontos_list.size() - retangulos[i].pontos_guardados;
                    }
                    System.out.println();
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
                    //Formiga formiga = new Formiga(1, pontos_copy, retangulos_copy,root);
                    //formiga.run();
                    //BFS
                    //BFS.BFS(root, retangulos_copy);
                    //DFS
                    //DFS.DFS(root);
                    //DFS com restricoes
                    //DFS.DFS_com_prop_restr(root, pontos, retangulos);
                    //IDS
                    //IDS.startIDS(root);
                    //A*
                    Astar.Astar(root, retangulos, pontos);
                    //Branch and bound
                    //Node BB = BranchBound.branch_bound(root, retangulos, pontos);
                    //Util.printSolution(BB, pontos);
                    //ILS NORMAL
                    //Node n = ILS.ILS_deterministico(root.configuracao_atual, root.configuracao_atual.length, retangulos, pontos);
                    //Util.printSolutionWithoutOrd(n, pontos);
                    //ILS RANDOMs
                    //Node nn = ILS.ILS_random(root.configuracao_atual, root.configuracao_atual.length, retangulos, pontos);
                    //Util.printSolutionWithoutOrd(nn, pontos);
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gerador.class.getName()).log(Level.SEVERE, null, ex);
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000000000.0);
    }

}
