/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import static com.mycompany.gerador.Gerador.decrease_ponto_ret;
import static com.mycompany.gerador.Gerador.pontos;
import static com.mycompany.gerador.Gerador.teste;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author pedro
 */
public class BFS {

    public static void BFS(Node root) {
        List<Node> list = new ArrayList<>();
        Map<Node, Boolean> map = new TreeMap<>();
        root.altura = 0;
        list.add(root);
        while (!list.isEmpty()) {
            
            // Get current size of heap in bytes
            long heapSize = Runtime.getRuntime().totalMemory();
        

// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
            long heapMaxSize = Runtime.getRuntime().maxMemory();
            Node current = null;
            if(heapSize > heapMaxSize - 500){
                Random rand = new Random();
                current = list.remove(rand.nextInt(list.size()));
                list.clear();
            }  
            else
                current = list.remove(0);
            // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
            long heapFreeSize = Runtime.getRuntime().freeMemory();
            
            if (teste(current) == 0) {
                Util.printSolution(current);
                break;
            }
            List<Node> aux_list = null;
            try {
                aux_list = current.gerarFilhos(map);
            } catch (OutOfMemoryError e) {
                System.out.println("tamanho : " + list.size());
                System.out.println("merda :)");
            }

            for (Node n : aux_list) {
                list.add(list.size(), n);

            }
        }
    }
}
