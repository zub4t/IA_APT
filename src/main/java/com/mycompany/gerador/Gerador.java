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

    public static void main(String[] args) {
        try {
            File myObj = new File("C:/Users/marco/Documents/NetBeansProjects/Gerador/src/main/java/com/mycompany/gerador/input.txt");
            Scanner myReader = new Scanner(myObj);
            int num_ret = myReader.nextInt();

            while (myReader.hasNext()) {
                int id_ret = myReader.nextInt();
                System.out.println(myReader.nextInt());
            }

        } catch (Exception e) {
            System.out.println("An error occurred." + e);
            e.printStackTrace();
        }
    }

}
