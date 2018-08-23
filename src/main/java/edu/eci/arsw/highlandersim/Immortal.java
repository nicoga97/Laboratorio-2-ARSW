package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;

public class Immortal extends Thread {
    public static boolean pause = false;

    private ImmortalUpdateReportCallback updateCallback = null;

    private int health;

    private int defaultDamageValue;

    private int myIndex;

    private boolean stop=false;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());


    public Immortal(String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue = defaultDamageValue;
    }

    public void run() {
        stop=false;
        while (!stop && health>0) {
            if (pause) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;
            myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }
            im=null;
            try{
            im = immortalsPopulation.get(nextFighterIndex);}
            catch(Exception e){
                if(nextFighterIndex>=immortalsPopulation.size()){
                    im = immortalsPopulation.get(nextFighterIndex-1);
                }
            }
            synchronized(Immortal.class){
                this.fight(im);
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        if(health<=0){immortalsPopulation.remove(this);}


    }

    public void fight(Immortal i2) {

                if (i2.getHealth() > 0) {
                    i2.changeHealth(i2.getHealth() - defaultDamageValue);
                    
                    
                    this.health += defaultDamageValue;
                    updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
                } else {
                    updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");
                }



    }

    public synchronized void changeHealth(int v) {

        health = v;
    }

    public synchronized int getHealth() {
        return health;
    }

    public  void kill(){

        immortalsPopulation.remove(this);

    }

    public void notificate() {
        synchronized (this) {
            notify();
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

}
