/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author pedro
 */
public class Greedy3 {

   public static int increase_key(int[] ret_quant_ponto, int size, Ret[] rets, Ponto[] pts) {
        int guardas = 0;
        Heapmin heap = new Heapmin(ret_quant_ponto, size);
        Map<Ret, Boolean> removido = new TreeMap<Ret, Boolean>();
        while (!heap.isEmpty()) {
            for (int i = 1; i <= heap.size; i++) {
                heap.heapify(i);
            }
            int ret_candidato_id = heap.extractMin();
            if (!rets[ret_candidato_id].pontos_list.isEmpty()) {
                removido.put(rets[ret_candidato_id], Boolean.TRUE);
                Ret ret_candidato = rets[ret_candidato_id];
                Ponto ponto_escolhido = null;
                int retangulos_guardados_ponto = 0;
                for (int ponto_id : ret_candidato.pontos_list) {
                    Ponto ponto_candidato = pts[ponto_id];
                    int tamanho = ponto_candidato.ret_list.size();
                    if (tamanho > retangulos_guardados_ponto) {
                        retangulos_guardados_ponto = tamanho;
                        ponto_escolhido = ponto_candidato;
                    } else if (tamanho == retangulos_guardados_ponto){
                        int total_vertices_incidentes = 0;
                        for(Ret retangulo : ponto_escolhido.ret_list){
                            total_vertices_incidentes += retangulo.pontos_list.size();
                        }
                        int total_vertices_incidentes_2 = 0;
                        for(Ret retangulo : ponto_candidato.ret_list){
                            total_vertices_incidentes_2 += retangulo.pontos_list.size();
                        }
                        if(total_vertices_incidentes_2 > total_vertices_incidentes){
                            ponto_escolhido = ponto_candidato;
                        }
                    }
                }
                if (ponto_escolhido != null) {
                    for (Ret retangulo : ponto_escolhido.ret_list) {
                        for (int ponto : retangulo.pontos_list) {
                            if (ponto != ponto_escolhido.id) {
                                pts[ponto].ret_list.remove(retangulo);
                            }
                        }
                        rets[retangulo.getId()].pontos_list.clear();
                    }
                    ponto_escolhido.ret_list.clear();
                    System.out.println("Ponto escolhido : " + ponto_escolhido.id);
                    guardas++;
                    for (int i = 1; i < rets.length; i++) {
                        if (!rets[i].pontos_list.isEmpty() && removido.get(rets[i]) == null) {
                            heap.decreaseKey(rets[i].getId(), rets[i].pontos_list.size());
                        }
                    }
                }
            }
        }

        return guardas;
    }
}
