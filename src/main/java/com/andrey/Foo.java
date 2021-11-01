package main.java.com.andrey;
import java.util.concurrent.*;
import java.util.function.Consumer;

public  class Foo {
    Semaphore sem2;
    Semaphore sem3;
    Foo(){
        sem2 = new Semaphore(0);
        sem3 = new Semaphore(0);
    }

     public void first(Runnable r) {

        System.out.println("first");
        sem2.release();
    }
    public void second(Runnable r){
        try {
            sem2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("second");
        sem3.release();
          }

    public  void third(Runnable r){
        try {
            sem3.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("third");
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Foo foo = new Foo();
        CompletableFuture<Void> printFirst = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                foo.first(this::run);
            }
        });
        CompletableFuture<Void> printSecond = CompletableFuture
                .runAsync(new Runnable() {
                    @Override
                    public void run() {

                        foo.second(this::run);
                    }
                });
        CompletableFuture<Void> printThird = CompletableFuture
                .runAsync(new Runnable() {
                    @Override
                    public void run() {
                        foo.third(this::run);
                    }
                });

       printFirst.get();
       printSecond.get();
       printThird.get();

    }
}

