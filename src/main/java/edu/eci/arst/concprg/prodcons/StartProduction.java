/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

public class StartProduction {
    private  Producer p;
    private  Consumer c;
    
    public static void main(String[] args) {
        
        Queue<Integer> queue=new LinkedBlockingQueue<>();

        c= new Consumer(queue);
        p = new Producer(queue,Long.MAX_VALUE);
        p.start();
        c.setProducer(p);


        
        //let the producer create products for 5 seconds (stock).
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartProduction.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        c.start();
        p.setConsumer(c);
    }

    public synchronized void notifyConsumer(){
        c.notify();
    }

    public synchronized void waitConsumer(){
        try {
            c.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    

}