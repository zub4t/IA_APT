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
public class Greedy1 {
    public static int decrease_key(int[] ponto_quant_ret, int size, Ret[] retangulos, Ponto[] pontos) {
        int guardas = 0;
        Heapmax heap = new Heapmax(ponto_quant_ret, size - 1);
        while (!heap.isEmpty()) {
            for(int i = 1; i <= heap.size; i++)
                heap.heapify(i);
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
}