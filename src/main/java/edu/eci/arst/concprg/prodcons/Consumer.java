/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.Queue;

/**
 *
 * @author hcadavid
 */
public class Consumer extends Thread{

    private Queue<Integer> queue;


    public Consumer(Queue<Integer> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        while (true) {
            if (queue.size() > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int elem = queue.poll();
                System.out.println("Consumer consumes " + elem);
                StartProduction.notifyProducer();
            } else {

                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
