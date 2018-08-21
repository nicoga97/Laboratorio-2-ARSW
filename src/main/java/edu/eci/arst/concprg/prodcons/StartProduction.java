/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

public class StartProduction {
    private static Producer p;
    private static Consumer c;
    private static Queue<Integer> queue = new LinkedBlockingQueue<>();


    public static void main(String[] args) {


        p = new Producer(queue, 10);
        p.start();

        //let the producer create products for 5 seconds (stock).
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartProduction.class.getName()).log(Level.SEVERE, null, ex);
        }

        c = new Consumer(queue);
        c.start();
        try {
            p.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public static void notifyConsumer() {
        if (c != null) {
            synchronized (c){
            c.notify();}
        }
    }
    public static void notifyProducer() {
        if (p!= null) {
            synchronized (p){
                p.notify();}
        }
    }


}