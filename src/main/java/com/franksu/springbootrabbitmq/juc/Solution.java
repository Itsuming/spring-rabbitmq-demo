package com.franksu.springbootrabbitmq.juc;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.juc
 * @Author: suming9
 * @CreateTime: 2024-08-31  16:35
 * @Description: TODO
 * @Version: 1.0
 * //线程不安全
 *         //当执行instance = new SingletonExample4();这行代码时，CPU会执行如下指令：
 *         //1.memory = allocate() 分配对象的内存空间
 *         //2.ctorInstance() 初始化对象
 *         //3.instance = memory 设置instance指向刚分配的内存
 *         //单纯执行以上三步没啥问题，但是在多线程情况下，可能会发生指令重排序。
 *         // 指令重排序对单线程没有影响，单线程下CPU可以按照顺序执行以上三个步骤，但是在多线程下，如果发生了指令重排序，则会打乱上面的三个步骤。
 *         //如果发生了JVM和CPU优化，发生重排序时，可能会按照下面的顺序执行：
 *         //1.memory = allocate() 分配对象的内存空间
 *         //3.instance = memory 设置instance指向刚分配的内存
 *         //2.ctorInstance() 初始化对象
 *         //假设目前有两个线程A和B同时执行getInstance()方法，A线程执行到instance = new SingletonExample4(); B线程刚执行到第一个 if (instance == null){处，
 *         //如果按照1.3.2的顺序，假设线程A执行到3.instance = memory 设置instance指向刚分配的内存，此时，线程B判断instance已经有值，就会直接return instance;
 *         //而实际上，线程A还未执行2.ctorInstance() 初始化对象，也就是说线程B拿到的instance对象还未进行初始化，这个未初始化的instance对象一旦被线程B使用，就会出现问题。
 */
class SingletonExample4 {

    private SingletonExample4(){}
    private static SingletonExample4 instance = null;

    public static SingletonExample4 getInstance(){
        if (instance == null){
            synchronized (SingletonExample4.class){
                if(instance == null){
                    instance = new SingletonExample4();
                }
            }
        }
        return instance;
    }

}