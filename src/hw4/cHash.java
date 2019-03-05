/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v1
 */
class llNode{
    llNode link;
    String word;
    int reps;
    llNode(String word){
        this.word=word;
        this.reps=1;
        link=null;
    }
}
class Node{
    Node link;
    int hKey;
    int position;
    String data;
    Node(int hKey,String data,int position){
        this.hKey=hKey;
        this.data=data;
        this.position=position;
        link=null;
    }
}
  class sortProp implements Comparator<llNode>{
 
            @Override
            public int compare(llNode e1, llNode e2) {
                if(e1.reps < e2.reps){
                    return -1;
                } else {
                    return 1;
                }
            }
        }
public class cHash implements HW4_Interface {
    int gSize=0;
    int pos=0;
    int numOfWord=0;
    llNode head=null;
    Node[] hTable;
    Stack<Integer> stack = new Stack<>();
    LinkedList<llNode> list = new LinkedList<llNode>();

    
    @Override
    public void BuildHash(String filename, int size) {
        gSize=size;
        hTable=new Node[size];
        for(int i=0;i<size;i++){
            hTable[i]=null;
        }
        String word;
        String path="C:\\"+filename+".txt";
        File file = new File(path);
        try {
            Scanner sc2=new Scanner(new FileReader(file));
            sc2.useDelimiter("[^A-Za-z]+");
            while(sc2.hasNext()){
                pos++;
                word=sc2.next();
                //System.out.println(word);
                InsertHash(word);
                
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(cHash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public long ConvertInt(String mystring) {
        long number=0;
        for(int i=0;i<mystring.length();i++){
            number=number+(long)(Math.pow((int)mystring.charAt(i),i+1)); 
            //System.out.println((int)mystring.charAt(i)+","+(long)(Math.pow((int)mystring.charAt(i),i+1))+","+number);
        }
        return number;
    }

    @Override
    public int FindHash(long myvalue) {
        int hash;
        hash=(int)Math.abs(myvalue%gSize);
        //System.out.println(gSize+","+hash);
        return hash;
    }

    @Override
    public void InsertHash(String mystring) {
        long number=ConvertInt(mystring);
        int hashValue=FindHash(number);
        Node word=new Node(hashValue,mystring,pos);
        llNode llword=new llNode(mystring);
        boolean flag=false;
        
        if(head==null){
            head=llword;
        }else{
            llNode Dummy=head;
            while(Dummy!=null){
                if(Dummy.word.equals(mystring)){
                    Dummy.reps++;
                    flag=true;
                    break;
                }
                Dummy=Dummy.link;
            }
            if(flag==false){
                llword.link=head;
                head=llword;
            }
        }
        if(hTable[hashValue]==null){
            hTable[hashValue]=word;
            return;
        }
        else{
            Node temp=hTable[hashValue];
            while(temp.link!=null){
               
                temp=temp.link;
            }
            temp.link=word;
            return;
        }
        
    }

    @Override
    public void Display(String Outputfile) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(Outputfile+".txt", "UTF-8");
            llNode Dummy=head;
            while(Dummy!=null){
                writer.println(Dummy.word+" : "+Dummy.reps);
                //System.out.println("Word : "+Dummy.word+"  Reps : "+Dummy.reps);
                Dummy=Dummy.link;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(cHash.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(cHash.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
            System.out.println(Outputfile+".txt file is created on the project directory path!");
        }
    }

    @Override
    public int NumofWord(String myword) {
       stack.clear();
        long numberMyWord=ConvertInt(myword);
        int hashMyWord=FindHash(numberMyWord);
        int repetition;
        Node tempCheck=hTable[hashMyWord];
        if(tempCheck==null){
            System.out.println(myword + " is not found!");
            repetition=0;
            return -1;
        }else{
            while(tempCheck!=null){
                if(tempCheck.data.equals(myword)){
                    stack.push(tempCheck.position);
                }
                
                tempCheck=tempCheck.link;
            }
            if(stack.isEmpty()==true){
                System.out.println(myword + " is not found!");
                repetition=0;
                return -1;
            }else{
                repetition=stack.size();
                System.out.println("NumofWord("+myword+") = "+repetition);
             
            }
        }
        return repetition;
    }

    @Override
    public String ShowMax() {
        String max=list.getLast().word;
        System.out.println("Most repeated word = "+ max);
        return max;
    }

    @Override
    public int CheckWord(String myword) {
        stack.clear();
        long numberMyWord=ConvertInt(myword);
        int hashMyWord=FindHash(numberMyWord);
        int repetition;
        int firstposition=0;
        Node tempCheck=hTable[hashMyWord];
        if(tempCheck==null){
            System.out.println(myword + " is not found!");
            return -1;
        }else{
            while(tempCheck!=null){
                if(tempCheck.data.equals(myword)){
                    stack.push(tempCheck.position);
                }
                
                tempCheck=tempCheck.link;
            }
            if(stack.isEmpty()==true){
                System.out.println(myword + " is not found!");
                return -1;
            }else{
                //System.out.println(stack);
                repetition=stack.size();
                numOfWord=repetition;
                //System.out.println("Repetition :"+repetition);
                firstposition=stack.remove(0);
                System.out.println(myword+" is found at position "+firstposition+" . Number of repetition = "+repetition);
                if(stack.isEmpty()==false){
                    System.out.println("Other positions :"+stack);  
                }
            }
        }
        
        return firstposition;
    }

    @Override
    public void Sort(String Outfile) {
        PrintWriter writer=null;
        try {
            llNode Dummy=head;
            writer = new PrintWriter(Outfile+".txt", "UTF-8");
            //System.out.println("---------------------------------");
            while(Dummy!=null){
                list.add(Dummy);
                Dummy=Dummy.link;
                
            }   
            Collections.sort(list,new sortProp());
            for(int i=0;i<list.size();i++){
                //System.out.println(list.get(i).reps);
                writer.println(list.get(i).word+" : "+list.get(i).reps);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(cHash.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(cHash.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
            System.out.println("Sorted "+Outfile+ ".txt file is created on the project directory path!");
        }
      
    }
    
}
