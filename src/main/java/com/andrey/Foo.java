package main.java.com.andrey;

import java.sql.SQLOutput;
import java.util.concurrent.*;

public class Foo {
    public void first(Runnable r){
        System.out.println("first");
    }
    public void second(Runnable r){
        System.out.println("second");
    }
    public void third(Runnable r){
        System.out.println("third");

    }
}
class test{
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
       es.execute(new MyThread(cb,"C"));
        es.execute(new MyThread(cb,"A"));
        es.execute(new MyThread(cb,"B"));

        es.shutdown();
    }
}
class MyThread implements Runnable{
    CyclicBarrier cb;
    String name;
    MyThread(CyclicBarrier c, String n){
        cb = c;
         name = n;
        new Thread(this);
    }
    public void run(){
        try {
            cb.await();
        }catch (BrokenBarrierException e){
            System.out.println(e);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }
}
class  BarAction implements Runnable{
Foo f = new Foo();
    @Override
    public void run() {
      f.first(this::run);
        f.second(this::run);
        f.third(this::run);

    }
}
