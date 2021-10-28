package main.java.com.andrey;
import java.sql.SQLOutput;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public  class Foo  {
    public static  boolean flag = false;
    public static  int n = 0;
     static Thread A = new Thread(()->{
         first();
        flag = true;
     });
   static Thread B = new Thread(()->{
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       if (flag==true ){
           second();
       n =1;}
   });
   static Thread C = new Thread(()->{
       try {
           Thread.sleep(2000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       if(flag == true && n ==1)
           third();
   });
  public  static void first(){
     System.out.println("first");
    }
    public static void second(){
      System.out.println("second");
    }
    public static void third(){
        System.out.println("third");
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Foo foo = new Foo();
       C.start();
       B.start();
       A.start();
    }
}


