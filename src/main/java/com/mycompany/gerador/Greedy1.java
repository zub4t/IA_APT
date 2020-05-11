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
        for (int i = 1; i < ponto_quant_ret.length; i++) {
            if (ponto_quant_ret[i] == -1) {
                for (Ret retangulo : pontos[i].ret_list) {
                    for (int ponto_id : retangulo.pontos_list) {
                        if (ponto_quant_ret[ponto_id] != -1) {
                            if (pontos[ponto_id].ret_list.contains(retangulo)) {
                                pontos[ponto_id].ret_list.remove(retangulo);
                                ponto_quant_ret[ponto_id]--;
                            }
                        }
                    }
                    retangulo.pontos_list.remove((Integer) i);
                }
                pontos[i].ret_list.clear();
            }
        }
        Heapmax heap = new Heapmax(ponto_quant_ret, size - 1);
        while (!heap.isEmpty()) {
            int maior = 0;
            int pos_maior = 0;
            heap.fixHeap();
            int ponto_candidato = heap.extractMax();
            if (!pontos[ponto_candidato].ret_list.isEmpty()) {
                guardas++;
                for (Ret retangulo : pontos[ponto_candidato].ret_list) {
                    retangulo.pontos_list.remove((Integer) ponto_candidato);
                    for (int ponto_id : retangulo.pontos_list) {
                        if (pontos[ponto_id] != null) {
                            pontos[ponto_id].ret_list.remove(retangulo);
                            if (heap.pos_a[ponto_id] != 0) {
                                heap.increaseKey(ponto_id, pontos[ponto_id].ret_list.size());
                            }

                        }
 d
                    }
                }
            }
        }
        return guardas;
    }

    public static Node decrease_key_com_node(int[] ponto_quant_ret, int size, Ret[] retangulos, Ponto[] pontos) {
        int guardas = 0;
        for (int i = 1; i < ponto_quant_ret.length; i++) {
            if (ponto_quant_ret[i] == -1) {
                for (Ret retangulo : pontos[i].ret_list) {
                    for (int ponto_id : retangulo.pontos_list) {
                        if (ponto_quant_ret[ponto_id] != -1) {
                            if (pontos[ponto_id].ret_list.contains(retangulo)) {
                                pontos[ponto_id].ret_list.remove(retangulo);
                                ponto_quant_ret[ponto_id]--;
                            }
                        }
                    }
                    retangulo.pontos_list.remove((Integer) i);
                }
                pontos[i].ret_list.clear();
            }
        }
        Node final_node = new Node(ponto_quant_ret, pontos);
        Heapmax heap = new Heapmax(ponto_quant_ret, size - 1);
        while (!heap.isEmpty()) {
            heap.fixHeap();
            int ponto_candidato = heap.extractMax();

            if (!pontos[ponto_candidato].ret_list.isEmpty()) {
                final_node.configuracao_atual[ponto_candidato] = -1;
                guardas++;
                for (Ret retangulo : pontos[ponto_candidato].ret_list) {
                    retangulo.pontos_list.remove((Integer) ponto_candidato);
                    for (int ponto_id : retangulo.pontos_list) {
                        if (pontos[ponto_id] != null) {
                            pontos[ponto_id].ret_list.remove(retangulo);
                            if (final_node.configuracao_atual[ponto_id] > 0) {
                                final_node.configuracao_atual[ponto_id]--;
                            }
                            heap.increaseKey(ponto_id, pontos[ponto_id].ret_list.size());
                        }

                    }
                }
            }
        }
        final_node.pontos = pontos;
        return final_node;
    }
}
