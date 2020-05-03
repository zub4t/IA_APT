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
public class Greedy3 {
     public static int increase_key(int[] ret_quant_ponto, int size, Ret[] retangulos, Ponto[] pontos) {
        int guardas = 0;
        Heapmin heap = new Heapmin(ret_quant_ponto, size);
        while(!heap.isEmpty()){
            for(int i = 1; i <= heap.size; i++)
                heap.heapify(i);
            heap.write_heap();
            int ret_candidato_id = heap.extractMin();
            if(!retangulos[ret_candidato_id].guardado){
                Ret ret_candidato = retangulos[ret_candidato_id];
                ret_candidato.guardado = true;
                ret_candidato.pontos_guardados++;
                System.out.println("Retangulo escolhido = " + ret_candidato_id);
                Ponto ponto_escolhido = null;
                int retangulos_guardados_ponto = 0;
                int vert_incidentes = 0;
                for(int ponto_id : ret_candidato.pontos_list){
                    Ponto ponto_candidato = pontos[ponto_id];
                    int tamanho = ponto_candidato.ret_list.size() - ponto_candidato.ret_guardados;
                    if(tamanho > retangulos_guardados_ponto){
                        tamanho = retangulos_guardados_ponto;
                        ponto_escolhido = ponto_candidato;
                        vert_incidentes = 0;
                        for(Ret retangulo : ponto_escolhido.ret_list){
                            vert_incidentes += retangulo.pontos_list.size() - retangulo.pontos_guardados;
                        }
                    } else if(tamanho == retangulos_guardados_ponto){
                        int vert_incidentes_comp = 0;
                        for(Ret retangulo : ponto_candidato.ret_list){
                            vert_incidentes_comp += retangulo.pontos_list.size() - retangulo.pontos_guardados;
                        }
                        if(vert_incidentes_comp > vert_incidentes)
                            ponto_escolhido = ponto_candidato;
                    }
                }
                System.out.println("Ponto escolhido - x = " + ponto_escolhido.x + " ; y = " + ponto_escolhido.y);
                ponto_escolhido.guardado = true;
                guardas++;
                for(Ret retangulo : ponto_escolhido.ret_list){
                    if(!retangulo.guardado){
                        retangulo.guardado = true;
                        ponto_escolhido.ret_guardados++;
                        System.out.println("Retangulo guardado : " + retangulo.getId());
                        retangulo.pontos_guardados ++;
                        heap.decreaseKey(retangulo.getId(), retangulo.pontos_list.size() - retangulo.pontos_guardados);
                    }
                    
                }
                
            }
        }
        
        return guardas;
     }
}
