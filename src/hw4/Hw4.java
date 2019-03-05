/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author v1
 */
public class Hw4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        cHash myHash=new cHash();
        myHash.BuildHash("test2", 530);
        System.out.println("Write the word which you want to find the position in the text then press Enter :");
        String word=sc.nextLine();
        myHash.CheckWord(word);
        myHash.NumofWord(word);
        myHash.Display("list");
        myHash.Sort("sortedlist");
        myHash.ShowMax();
    }
    
}
