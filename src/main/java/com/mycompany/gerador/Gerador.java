/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gerador;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author marco
 */
public class Gerador {

    static List<Ret> list;
    static Map<Ponto, List<Ret>> map = new TreeMap<>();
    static Map<Integer, Ponto> dicionario = new TreeMap<>();

    public static void main(String[] args) {
        list = new ArrayList<Ret>();
        try {
            File myObj = new File("C:/Users/marco/Documents/NetBeansProjects/Gerador/src/main/java/com/mycompany/gerador/input.txt");
            Scanner myReader = new Scanner(myObj);
            int num_ret = myReader.nextInt();
            while (myReader.hasNext()) {
                int id = Integer.parseInt(myReader.next());
                int n_points = Integer.parseInt(myReader.next());
                Ret ret = new Ret(n_points, id);
                list.add(ret);
                //System.out.println("numero de pontos " + n_points);
                for (int i = 0; i < n_points; i++) {
                    ret.points[0][i] = Integer.parseInt(myReader.next());
                    ret.points[1][i] = Integer.parseInt(myReader.next());
                    Ponto ponto = new Ponto(ret.points[0][i], ret.points[1][i]);
                    map.put(ponto, new ArrayList());
                    // System.out.printf("par de pontos [%d][%d] ", ret.points[0][i], ret.points[1][i]);
                }
                for (Ret model : list) {
                    for (int i = 0; i < model.points[0].length; i++) {
                        Ponto p = new Ponto(model.points[0][i], model.points[1][i]);
                        List<Ret> list = map.get(p);
                        if (list != null) {
                            boolean flag = true;
                            for (Ret aux : list) {
                                if (aux == model) {
                                    flag = false;
                                }

                            }
                            if (flag) {
                                list.add(model);
                            }
                        }
                    }
                }

            }
            int id_pontos = 1;
            int arr[] = new int[13];
            for (Entry<Ponto, List<Ret>> entry : map.entrySet()) {
                Ponto key = entry.getKey();
                List<Ret> value = entry.getValue();
                arr[id_pontos] = value.size();
                dicionario.put(id_pontos, key);
                id_pontos++;
                System.out.printf("%s\n", key);
                for (Ret model : value) {
                    System.out.println(model);
                }

            }
            greedy(arr, (num_ret + 1));
            myReader.close();
        } catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }
    }

    public static void greedy(int arr[], int n) {
        Heapmax heap = new Heapmax(arr, n);
        heap.write_heap();
        int id_ponto = heap.extractMax();
        decrease(id_ponto, heap);
    }

    public static void decrease(int id_ponto, Heapmax heap) {
        Ponto p = dicionario.get(id_ponto);
        List<Ret> list = map.get(p);
        for (Ret ret : list) {
            for (Entry<Integer, Ponto> entry : dicionario.entrySet()) {
                int ponto = entry.getKey();
                List<Ret> list_aux = map.get(dicionario.get(ponto));
                for (int i = 0; i < list_aux.size(); i++) {
                    if (list_aux.get(i) == ret) {
                        i--;
                        list_aux.remove(ret);
                        heap.increaseKey(ponto, list_aux.size());
                    }

                }
                map.put(dicionario.get(ponto), list_aux);

            }
        }
    }
}

class Ret implements Comparable<Ret> {

    public int points[][];
    private int id;

    public Ret(int n_points, int id) {
        points = new int[2][n_points];
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[][] getPoints() {
        return points;
    }

    public void setPoints(int[][] points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Ret{" + "points=" + points + ", id=" + id + '}';
    }

    @Override
    public int compareTo(Ret o) {
        return o.id - this.id;

    }

}

class Ponto implements Comparable<Ponto> {

    public Ponto(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x;
    int y;

    @Override
    public String toString() {
        return "Ponto{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public int compareTo(Ponto o) {
        return (o.x - this.x) + (o.y - this.y);
    }

}
