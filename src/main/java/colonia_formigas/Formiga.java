/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colonia_formigas;

import com.mycompany.gerador.Node;
import com.mycompany.gerador.Ponto;
import com.mycompany.gerador.Ret;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author pedro
 */
public class Formiga implements Runnable {

    private static float[][] prob_feromonios = new float[200][200];
    private Map<Integer, Float> mapa_probabilidade = new TreeMap<>();
    private Set<Float> set_probabilidade = new TreeSet<>();
    private int id;
    private List<Integer> pontos_escolhidos = new ArrayList<>();
    private boolean on_anthill;
    private Node node;
    private Ponto[] pontos;
    private Ret[] retangulos;

    public Formiga(int id, Ponto[] pontos, Ret[] retangulos, Node node) {
        this.id = id;
        this.pontos = pontos;
        this.retangulos = retangulos;
        this.node = node;
        this.on_anthill = false;
        this.startProbabilites();
    }

    public int maxParaFormigas() {
        int total = 0;
        for (int i = 1; i < pontos.length; i++) {
            if (pontos[i] == null) {
                break;
            }
            total += pontos[i].ret_list.size();
        }
        return total;
    }

    public void startProbabilites() {
        mapa_probabilidade.clear();
        set_probabilidade.clear();
        float max = maxParaFormigas();
        for (int i = 1; i < node.configuracao_atual.length; i++) {
            this.mapa_probabilidade.put(i, pontos[i].ret_list.size() / max);
            this.set_probabilidade.add(pontos[i].ret_list.size() / max);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getPontos_escolhidos() {
        return pontos_escolhidos;
    }

    public void setPontos_escolhidos(List<Integer> pontos_escolhidos) {
        this.pontos_escolhidos = pontos_escolhidos;
    }

    public boolean isOn_anthill() {
        return on_anthill;
    }

    public void setOn_anthill(boolean on_anthill) {
        this.on_anthill = on_anthill;
    }

    private int pontoIdEscolha() {
        List<Integer> numeros = new ArrayList<>();
        List<Float> set_probabilidade_lista = new ArrayList<>(set_probabilidade);
        for (int i = 1; i <= this.mapa_probabilidade.size(); i++) {
            float probabilidade = this.mapa_probabilidade.get((Integer) i);
            int posicao = 0;
            if (probabilidade != 0) {
                posicao = set_probabilidade_lista.indexOf(probabilidade) + 1;
            }

            for (int j = 1; j <= posicao; j++) {
                numeros.add(i);
            }
        }
        Random rand = new Random();
        return numeros.get(rand.nextInt(numeros.size()));
    }

    private void verificarCaminho() {
        Set<Integer> set_ret = new TreeSet<>();
        for (int ponto_id : pontos_escolhidos) {
            for (Ret retangulo : node.pontos[ponto_id].ret_list) {
                set_ret.add(retangulo.getId());
            }
        }
        if (set_ret.size() == retangulos.length - 1) {
            this.on_anthill = true;
            return;
        }

        this.on_anthill = false;
    }

    private void fazerCaminho(int id_escolhido) {
        for (Ret retangulo : pontos[id_escolhido].ret_list) {
            for (int ponto_id : retangulo.pontos_list) {
                if (ponto_id != id_escolhido) {
                    pontos[ponto_id].ret_list.remove(retangulo);
                }
            }
        }
        pontos[id_escolhido].ret_list.clear();
        pontos_escolhidos.add(id_escolhido);
    }

    private void atualizarProbabilidades() {
        startProbabilites();
    }

    @Override
    public void run() {
        for (int k = 1; k <= 2; k++) {
            int id_escolhido_passado = 0;
            while (!this.on_anthill) {
                Map<Integer, Float> mapa_probabilidade_copia = new TreeMap<>();
                for (int i = 1; i <= mapa_probabilidade.size(); i++) {
                    mapa_probabilidade_copia.put(i, mapa_probabilidade.get((Integer) i));
                }
                float total = 0;
                int indices_mexemos = 0;
                for (int i = 1; i <= mapa_probabilidade.size(); i++) {
                    if (Formiga.prob_feromonios[id_escolhido_passado][i] != 0) {
                        total += Formiga.prob_feromonios[id_escolhido_passado][i];
                        indices_mexemos++;
                        float aux = mapa_probabilidade.get((Integer) i) + Formiga.prob_feromonios[id_escolhido_passado][i];
                        mapa_probabilidade.put(i, aux);
                    }
                }
                for (int i = 1; i <= mapa_probabilidade.size(); i++) {
                    if (Formiga.prob_feromonios[id_escolhido_passado][i] == 0) {
                        mapa_probabilidade.put(i, mapa_probabilidade.get((Integer) i) - (total / (node.pontos.length - indices_mexemos)));
                    }
                }
                int id_escolhido = pontoIdEscolha();
                mapa_probabilidade = mapa_probabilidade_copia;
                fazerCaminho(id_escolhido);
                atualizarProbabilidades();
                verificarCaminho();
                id_escolhido_passado = id_escolhido;
            }
            for (int i = 1; i < pontos_escolhidos.size(); i++) {
                Formiga.prob_feromonios[pontos_escolhidos.get(i - 1)][pontos_escolhidos.get(i)] += 0.01;
            }
            System.out.println("teste");
        }

    }

}
