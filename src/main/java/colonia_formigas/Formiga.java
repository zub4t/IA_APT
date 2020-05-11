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
    private int id;
    private List<Integer> pontos_escolhidos = new ArrayList<>();
    private boolean on_anthill;
    private Node node;
    private Ponto[] pontos;
    private Ret[] retangulos;
    Set<Integer> set_ret = new TreeSet<>();
    private List<Integer> arr_random = new ArrayList<>();

    public Formiga(int id, Ponto[] pontos, Ret[] retangulos, Node node) {
        this.id = id;
        this.pontos = pontos;
        this.retangulos = retangulos;
        this.node = node;
        this.on_anthill = false;
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

    public int maxParaFormigasRoot() {
        int total = 0;
        for (int i = 1; i < node.pontos.length; i++) {
            if (pontos[i] == null) {
                break;
            }
            total += node.pontos[i].ret_list.size();
        }
        return total;
    }

    public void startRootProbabilites() {
        mapa_probabilidade.clear();

        float max = maxParaFormigasRoot();
        for (int i = 1; i < node.configuracao_atual.length; i++) {
            this.mapa_probabilidade.put(i, node.pontos[i].ret_list.size() / max);
        }
    }

    public void startProbabilites() {
        mapa_probabilidade.clear();
        float max = maxParaFormigas();
        for (int i = 1; i < node.configuracao_atual.length; i++) {
            this.mapa_probabilidade.put(i, pontos[i].ret_list.size() / max);
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

    private Ponto[] createCopyPontos() {
        Ponto pontos[] = new Ponto[this.node.pontos.length];
        int ponto_id = 1;
        for (Ponto p : node.pontos) {
            if (p != null) {

                pontos[ponto_id] = new Ponto(p);
                ponto_id++;
            }
        }
        return pontos;
    }

    private int pontoIdEscolha(boolean first_interation) {
        arr_random.clear();
        if (!first_interation) {
            for (int i = 1; i <= this.mapa_probabilidade.size(); i++) {
                int count = (int) Math.ceil(this.mapa_probabilidade.get((Integer) i) / 0.0001);
                for (int j = 0; j < count * this.pontos[i].ret_list.size(); j++) {
                    arr_random.add(i);
                }
            }
        } else {
            for(int i = 1; i < this.node.pontos.length; i++){
                if(node.pontos[i] == null)
                    break;
                if(node.pontos[i].ret_list.size() == 3)
                    return i;
            }
        }

        Random rand = new Random();
        return arr_random.get(rand.nextInt(arr_random.size()));

    }

    private void verificarCaminho() {
        set_ret.clear();
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

    private int contarProbalidadesDiferenteZero() {
        int total = 0;
        for (int i = 1; i <= mapa_probabilidade.size(); i++) {
            if (mapa_probabilidade.get((Integer) i) != 0) {
                total++;
            }
        }
        return total;
    }

    private Map<Integer, Float> copyMap() {
        Map<Integer, Float> mapa_probabilidade_copia = new TreeMap<>();
        for (int i = 1; i <= mapa_probabilidade.size(); i++) {
            mapa_probabilidade_copia.put(i, mapa_probabilidade.get((Integer) i));
        }
        return mapa_probabilidade_copia;
    }

    @Override
    public void run() {

        for (int k = 1; k <= 200000; k++) {
            boolean first_interation = true;
            this.on_anthill = false;
            pontos_escolhidos.clear();
            int id_escolhido_passado = 0;
            startRootProbabilites();
            this.pontos = createCopyPontos();
            while (!this.on_anthill) {
                // att do feromonio por cada ponto
                Map<Integer, Float> map_copy = copyMap();
                for (int i = 1; i <= mapa_probabilidade.size(); i++) {
                    if (Formiga.prob_feromonios[id_escolhido_passado][i] != 0 && mapa_probabilidade.get((Integer) i) > 0) {
                        float aux = mapa_probabilidade.get((Integer) i) + Formiga.prob_feromonios[id_escolhido_passado][i];
                        mapa_probabilidade.put(i, aux);
                        float total_dever = 0;
                        for (int j = 1; j <= mapa_probabilidade.size(); j++) {
                            if (i != j && mapa_probabilidade.get((Integer) j) > 0) {
                                float aux2 = mapa_probabilidade.get((Integer) j) - (Formiga.prob_feromonios[id_escolhido_passado][i] / (contarProbalidadesDiferenteZero() - 1));
                                if (aux2 > 0) {
                                    mapa_probabilidade.put(j, aux2);
                                } else {
                                    mapa_probabilidade.put(j, 0.0f);
                                    total_dever -= aux2;
                                }
                            }
                        }
                        float total_prob = 0;
                        for (int l = 1; l <= mapa_probabilidade.size(); l++) {
                            total_prob += mapa_probabilidade.get((Integer) l);
                        }
                        while (total_dever > 0) {
                            float total_dever_2 = total_dever;
                            for (int j = 1; j <= mapa_probabilidade.size(); j++) {
                                if (i != j && mapa_probabilidade.get((Integer) j) > 0) {
                                    float aux2 = mapa_probabilidade.get((Integer) j) - (total_dever / (contarProbalidadesDiferenteZero() - 1));
                                    if (aux2 > 0) {
                                        mapa_probabilidade.put(j, aux2);
                                    } else {
                                        mapa_probabilidade.put(j, 0.0f);
                                        total_dever_2 += aux2;
                                    }
                                }
                            }
                            total_dever -= total_dever_2;
                        }
                        total_prob = 0;
                        for (int l = 1; l <= mapa_probabilidade.size(); l++) {
                            total_prob += mapa_probabilidade.get((Integer) l);
                        }
                    }
                }
                int id_escolhido = pontoIdEscolha(first_interation);
                if (first_interation) {
                    first_interation = false;
                }
                mapa_probabilidade = map_copy;
                fazerCaminho(id_escolhido);
                verificarCaminho();
                startProbabilites();
                id_escolhido_passado = id_escolhido;
            }
            for (int i = 1; i < pontos_escolhidos.size(); i++) {
                if (Formiga.prob_feromonios[pontos_escolhidos.get(i - 1)][pontos_escolhidos.get(i)] < 1) {
                    Formiga.prob_feromonios[pontos_escolhidos.get(i - 1)][pontos_escolhidos.get(i)] += 0.001;
                }
            }
            for (int i = 0; i < pontos_escolhidos.size(); i++) {
                System.out.print(pontos_escolhidos.get(i) + " ");
            }
            System.out.print("------>" + set_ret.size() + " ------ >");

            if (k == 200000) {
                System.out.print("");
            }
            System.out.println(" " + set_ret.toString());

        }

    }

}