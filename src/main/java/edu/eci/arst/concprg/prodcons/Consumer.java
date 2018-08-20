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
    private Producer producer;
    private StartProduction startProduction;
    
    public Consumer(Queue<Integer> queue){
        this.startProduction=startProduction;
        this.queue=queue;        
    }
    
    @Override
    public void run() {
        while (true) {

            if (queue.size() > 0) {
                int elem=queue.poll();
                System.out.println("Consumer consumes "+elem);                                
            }else{
                startProduction.waitConsumer(); }

            }

            

    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
