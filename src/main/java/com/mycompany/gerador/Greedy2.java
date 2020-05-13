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
public class Greedy2 {

    public static int increase_key(int[] ret_quant_ponto, int size, Ret[] retangulos, Ponto[] pontos) {
        int guardas = 0;
        Heapmin heap = new Heapmin(ret_quant_ponto, size);
        while (!heap.isEmpty()) {
            for (int i = 1; i <= heap.size; i++) {
                heap.heapify(i);
            }
            int ret_candidato_id = heap.extractMin();
            if (!retangulos[ret_candidato_id].guardado) {
                Ret ret_candidato = retangulos[ret_candidato_id];
                ret_candidato.guardado = true;
                ret_candidato.pontos_guardados++;
                Ponto ponto_escolhido = null;
                int retangulos_guardados_ponto = 0;
                for (int ponto_id : ret_candidato.pontos_list) {
                    Ponto ponto_candidato = pontos[ponto_id];
                    int tamanho = ponto_candidato.ret_list.size() - ponto_candidato.ret_guardados;
                    if (tamanho > retangulos_guardados_ponto) {
                        retangulos_guardados_ponto = tamanho;
                        ponto_escolhido = ponto_candidato;
                    }
                }
                ponto_escolhido.guardado = true;
                guardas++;
                for (Ret retangulo : ponto_escolhido.ret_list) {
                    if (!retangulo.guardado) {
                        retangulo.guardado = true;
                        ponto_escolhido.ret_guardados++;
                        retangulo.pontos_guardados++;
                        if (heap.pos_a[retangulo.getId()] != 0) {
                            heap.decreaseKey(retangulo.getId(), retangulo.pontos_list.size() - retangulo.pontos_guardados);
                        }
                    }

                }

            }
        }

        return guardas;
    }
}
