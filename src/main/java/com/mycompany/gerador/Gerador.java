package com.mycompany.gerador;

import colonia_formigas.Formiga;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gerador {

    static Ret[] retangulos = null;
    static Ponto[] pontos = null;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int num_ret = 0;
        int cur_ponto_id;

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
                    
                    System.out.println("Deseja vizualizao a configuração importada  ? 1/0");
                    int conf = teclado.nextInt();
                    if (conf == 1) {
                        String conf_res = "";
                        Util.printRetToPonto(retangulos, pontos, num_ret);
                        Util.printPontoToRet(pontos, retangulos, cur_ponto_id - 1);
                        System.out.println(conf_res);
                    }

                    int escolhido = 0;
                    while (escolhido != 13) {
                        System.out.println("Escolha qual  algoritimo quer ver");
                        System.out.println(""
                                + "1-Colonia de Formigas"
                                + "\n2-BFS"
                                + "\n3-DFS"
                                + "\n4-Greedy1"
                                + "\n5-Greedy2"
                                + "\n6-Greedy3"
                                + "\n7-BranchAndBound_queue"
                                + "\n8-BranchAndBound_stack"
                                + "\n9-Astar"
                                + "\n10-IDS"
                                + "\n11-ILS_Random"
                                + "\n12-ILS_Deterministico"
                                + "\n13-ir para proxima instancia do input");
                        escolhido = teclado.nextInt();

                        //array em que cada posição é o ponto id e que por exemplo ponto_quant_ret[2] = quantidade de retangulos que o ponto 2 cobre
                        int ponto_quant_ret[] = Util.makeInstancePonto(cur_ponto_id);
                        //array em que cada posição é o retangulo id e que por exemplo ret_quant_ponto[2] = quantidade de pontos que o retangulo 2 tem
                        int ret_quant_ponto[] = Util.makeInstanceRet(num_ret);
                        // criar node root 
                        Node root = new Node(ponto_quant_ret, pontos);

                        Ponto pontos_copy[] = Util.copyPontos();
                        Ret retangulos_copy[] = Util.copyRet();
                        long startTime = System.nanoTime();

                        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
                        long heapMaxSize = Runtime.getRuntime().maxMemory();
                        //System.out.println("Tamnho em gigas da heap disponivel:" + heapMaxSize / 1000000000.0);

                        switch (escolhido) {
                            case 1:
                                //Formiga
                                startTime = System.nanoTime();
                                Formiga formiga = new Formiga(1, pontos_copy, retangulos_copy, root);
                                formiga.run();
                                break;
                            case 2:
                                //BFS
                                startTime = System.nanoTime();
                                BFS.BFS(root, retangulos_copy);
                                break;
                            case 3:
                                //DFS
                                startTime = System.nanoTime();
                                DFS.DFS(root);
                                break;
                            case 4:
                                startTime = System.nanoTime();
                                // Greedy 1 = (orientada por vértices)  colocar um guarda no vértice que é partilhado por mais retângulos ainda não cobertos
                                System.out.println("Greedy 1 = " + Greedy1.decrease_key(ponto_quant_ret, cur_ponto_id, retangulos_copy, pontos_copy));
                                break;
                            case 5:
                                startTime = System.nanoTime();
                                //Greedy 2 = (orientada por retângulos) escolher o retângulo ainda não coberto que tenha menos vértices incidentes e colocar um guarda num desses vértices que seja partilhado por mais retângulos ainda não cobertos
                                System.out.println("Greedy 2 = " + Greedy2.increase_key(ret_quant_ponto, num_ret, retangulos_copy, pontos_copy));
                                break;
                            case 6:
                                startTime = System.nanoTime();
                                // Greedy 3 = (orientada por retângulos) variante de 2. em que, em caso de igualdade entre vértices, opta pelo que cobre retângulos que globalmente tenham mais vértices incidentes
                                System.out.println("Greedy 3 = " + Greedy3.increase_key(ret_quant_ponto, num_ret, retangulos_copy, pontos_copy));
                                break;
                            case 7:
                                startTime = System.nanoTime();
                                Node BB = BranchBound.branch_bound_stack(root, retangulos_copy, pontos_copy);
                                System.out.println("Guardas: " + BB.ord.size());
                                //Util.printSolution(BB, pontos);
                                break;
                            case 8:
                                startTime = System.nanoTime();
                                Node B = BranchBound.branch_bound_queue(root, retangulos_copy, pontos_copy);
                                System.out.println("Guardas: " + B.ord.size());
                                //Util.printSolution(B, pontos);
                                break;
                            case 9:
                                //A*
                                startTime = System.nanoTime();
                                Astar.Astar(root, retangulos_copy, pontos_copy);
                                break;
                            case 10:
                                //IDS
                                startTime = System.nanoTime();
                                IDS.startIDS(root);
                                break;
                            case 11:
                                //ILS RANDOMs
                                startTime = System.nanoTime();
                                Node nn = ILS.ILS_random(root.configuracao_atual, root.configuracao_atual.length, retangulos_copy, pontos_copy);
                                System.out.println("Guardas: " + nn.ord.size());
                                //Util.printSolutionWithoutOrd(nn, pontos);
                                break;
                            case 12:
                                //ILS NORMAL
                                startTime = System.nanoTime();
                                Node n = ILS.ILS_deterministico(root.configuracao_atual, root.configuracao_atual.length, retangulos_copy, pontos_copy);
                                System.out.println("Guardas: " + n.ord.size());
                                //Util.printSolutionWithoutOrd(n, pontos);
                                break;
                            case 13:
                                startTime = System.nanoTime();
                                System.out.println("Avançando");
                                break;

                        }

                    }

                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gerador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
