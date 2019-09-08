# 多线程编程高级（偏面试）

## 1、请谈谈你对volatile的理解？

### 1.1 volatile的理解

volatile是Java虚拟机的轻量级的同步机制，它具有三个特性：

1. 保证可见性（如何保证可见性）
2. 不保证原子性
3. 禁止指令重排(什么是指令重排)

### 1.2 JMM内存模型

 JMM的三大特性：（多线程编程三大原则）

1. 可见性
2. 原子性
3. 有序性

各个线程对共享变量的操作都是各个子线程从主内存拷贝到各个线程的内存空间进行操作，然后放回主内存。并且这种操作对变量的改变对其它线程是可见的。（可见性）

### 1.3 volatile可见性代码演示

```java
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        // 1. 创建的子线程，操作number变量分别掩饰 加和不加volatile
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.changeNumber();
            System.out.println(Thread.currentThread().getName() + "\t update number value = " + myData.number);
        },"AAAA").start();

        // 2. 如果number的值没有发生改变则进入循环，不打印结果，说明AAAA线程的操作，主线程不可见。相反则可见
        while (myData.number == 0){

        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over");
    }
}

class MyData{
    volatile int number;
    void changeNumber(){
        this.number = 60;
    }
}
```

### 1.4 volatile不保证原子性

```java
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20 ; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                        myData.number ++;
                }
            },String.valueOf(i)).start();
        }
        
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value = " + myData.number);
    }
}

class MyData{
    volatile int number;
}
```

演示结果表示添加不添加volatile结果都是一样每次结果都不相同

如何保证原子性呢，可以通过synchronized关键字的使用，实现，但是synchronized关键字相对volatile较重量级。

如何解决volatile不保证原子性问题，可以通过JUC中的原子封装的内容进行使用；代码如下：

```java
public class VolatileDemo {

    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20 ; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                    myData.atomicNumber();
                    myData.volaNumber();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number value = " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally number value = " + myData.atomicInteger);
    }
}

class MyData{

    volatile int number;

    void volaNumber(){
        number ++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    void atomicNumber(){
        atomicInteger.getAndIncrement();
    }
}
```

此处引用了JUC中的`AtomicInteger`保证了原子性；

**思考AtomicInteger是如何保证原子性的？**

### 1.5 volatile禁止指令重排

 计算机在执行程序时，为了提高性能，编译器和处理器常常会对指令做重排；

多线程环境下线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能

否保证一致性是无法确定的，结果无法预测；

```java
public class ReSortSeqDemo {

    int anInt;
    boolean flag;
    
    void method01(){
        anInt = 1;//语句1
        flag = true;//语句2
    }
    void method02(){
        if (flag){
            anInt = anInt + 5;
            System.out.println("*****retValue = " + anInt);
        } 
    }
}
```

如上面代码：在多线程环境下出现指令重排：可能会出现如下情况

1. flag = true;  先被执行；
2. 其它线程抢占了资源执行了method02()，导致anInt最终结果不是预想的结果

此时在变量上添加`volatile`关键字，可以禁止指令重排

### 1.6 单例模式在多线程环境下使用volatile

正常的单例模式在多线程环境下会存在创建多个实例的情况，这样违背单例模式的初衷；

演示代码如下：

``` java
public class SingletonDemo {

    public static SingletonDemo instance = null;

    /**
     * 多线程环境下验证创建单例的情况
     */
    private SingletonDemo(){
        System.out.println("我是单例模式构造方法 \t" + Thread.currentThread().getName());
    }

    public static SingletonDemo getInstance(){
        if (instance == null){
            instance = new SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50 ; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }).start();
        }
    }
}
```

#### 1.6.1 解决方案（DCL 双重检查锁）：

不建议使用`synchronized`关键字锁方法的形式，使用`synchronized`锁代码块的方式，在`synchronized`关键字的前后进行检查。

代码演示如下：

```java
public class SingletonDemo {

    public static volatile SingletonDemo instance = null;

    /**
     * 多线程环境下验证创建单例的情况
     */
    private SingletonDemo(){
        System.out.println("我是单例模式构造方法 \t" + Thread.currentThread().getName());
    }

    public static SingletonDemo getInstance(){
        if (instance == null){
            synchronized (SingletonDemo.class){
              if (instance == null){
                  instance = new SingletonDemo();
              }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50 ; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }).start();
        }
    }
}
```

## 2、CAS你知道吗？

### 2.1 CAS是什么？

CAS是什么？==》 Compare-And-Set 简称

比较并交换

```java
public class CASDemo {
    public static void main(String[] args) {
        // 设置初始值。默认为0,存放在主物理内存中
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2016) + "\t current data:" + atomicInteger.get());
    }
}
```

用希望值和主物理内存的值进行比较：

相等：修改成功

不等：修改失败

### 2.2 CAS底层原理

原子性类的执行不用添加`synchronized`就可以执行原子性操作是因为

CSA的执行依赖于`sun.misc.Unsafe`类， 该类中的各个方法，是用native修饰的

主要调用`getAndAddInt`方法

源码内容如下：

```java
public final int getAndAddInt(Object var1, long var2, int var4) {
    int var5;
    do {
        var5 = this.getIntVolatile(var1, var2);
    } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

    return var5;
}
```

### 2.3 CAS缺点

1. 循环时间长，开销大
2. 只能保证一个共享变量的原子性操作
3. 可能导致ABA问题

## 3、原子类AtomicInteger的ABA问题谈谈？原子更新引用你知道吗？

### 3.1 什么是ABA问题？

CAS算法实现一个重要前提需要取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差里会导致数据的变化。

假设有两个线程（T1，T2），同时操作一个共享变量A，假设T1线程运行时间为10s，T2线程操作时间较短，则会存在在T1运行的时间间隔里，线程T2，操作多次变量A，将变量值改为B，并改回A。

### 3.2 原子引用

即借用原子引用类实现对对象的原子封装

```java
@Data
@AllArgsConstructor
@ToString
class User{
    String userName;
    int age;
}

/**
 * @author yim create time 2019-8-6
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {

        User zhangsan = new User("zhangsan",26);
        User lisi = new User("lisi",24);

        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(zhangsan);

        System.out.println(userAtomicReference.compareAndSet(zhangsan,lisi) + "\t" + userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(zhangsan,lisi) + "\t" + userAtomicReference.get().toString());
    }
}
```

### 3.3 ABA问题的解决（时间戳原子引用）

时间戳原子引用，变量每被修改一次，则会记录一次.

```java
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<Integer>(100,1);

    public static void main(String[] args) {

        System.out.println("-----------以下演示ABA问题--------------");
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t" + atomicReference.compareAndSet(100,120) + "\t");
            System.out.println(atomicReference.get());
            System.out.println(Thread.currentThread().getName() + "\t" + atomicReference.compareAndSet(120,100) + "\t");
        },"t1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + atomicReference.compareAndSet(100,110) + "\t" + atomicReference.get().toString());
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----------以下演示ABA问题的解决------------");

        new Thread(() ->{
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号\t" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100,110,stampedReference.getStamp(),stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第二次版本号\t" + stampedReference.getStamp());
            stampedReference.compareAndSet(110,100,stampedReference.getStamp(),stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第三次版本号\t" + stampedReference.getStamp());
        },"t3").start();

        new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100,127,stampedReference.getStamp(),stampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号\t" + stampedReference.getStamp());
        },"t4").start();
    }
}
```

## 4、我们知道ArrayList是线程不安全的，请编写一个不安全的案例并给出解决方案。

### 4.1 ArrayList 线程不安全分析解决

ArrayList底层数组实现的，是线程不安全的。为了保证线程效率，没有加锁

LinkedList底层是链表实现的

ArrayList支持动态扩容，动态扩容机制1.5倍

多线程环境下测试代码问题：

```java
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
```

此时输出结果会报`ConcurrentModificationException`异常：并发修改异常

#### 4.1.1 解决方案

1. 使用线程安全的集合类代替线程不安全的工具类，如：Vector替换`ArrayList`

2. 使用`Collections`集合工具类

   ```java
   List<String> strings = Collections.synchronizedList(new ArrayList<String>());
   ```

3. ```
   new CopyOnWriteArrayList<>();
   ```

#### 4.1.2 `CopyOnWriteArrayList`原理

写时复制

读写分离的思想

```java
public boolean add(E e) {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    } finally {
        lock.unlock();
    }
}
```

#### 4.1.2 `CopyOnWriteArraySet`

底部还是用的CopyOnWriteArrayList

### 4.2 HashMap不安全问题

这个问题报错和上面一样

#### 4.2.1 HashSet和HashMap

HashSet的底层是HashMap实现的，源码如下:

```java
public boolean add(E e) {    return map.put(e, PRESENT)==null;}
```

调用HashMap的put方法，传入的值作为key，value是Object对象的常量PRESENT

#### 4.2.2 传值小练习

```java
public class TestTransferValue {

    public void changeValue01(int age){
        age = 30;
    }
    public void changeValue02(Person person){
        person.setName("qianer");
    }
    public void changeValue03(String str){
        str = "xxx";
    }
    
    public static void main(String[] args) {

        TestTransferValue transferValue = new TestTransferValue();
        int age = 20;
        transferValue.changeValue01(age);
        System.out.println("age---" + age);

        Person person = new Person("zhaoyi");
        transferValue.changeValue02(person);
        System.out.println("person name------" + person.getName());

        String str = "abc";
        transferValue.changeValue03(str);
        System.out.println(str);
    }
}
```

打印结果：

age---20

person name------qianer

abc

#### 4.2.3 HashMap不安全问题解决

1. ```
   Collections.synchronizedMap(new HashMap<>());
   ```

2. ```
   new ConcurrentHashMap<>();
   ```

## 5、公平锁/非公平锁/可重入锁/递归锁/自旋锁谈谈你的理解？请手写一个自旋锁

### 5.1 公平/非公平锁

公平锁：是指多个线程按照申请锁的顺序来获取锁，类似于排队，先到先得

非公平锁：是指多个线程获取锁的顺序不是按照申请锁的顺序。在高并发的情况下可能会造成优先级反转或者饥饿现象。

饥饿现象：存在某个线程长时间未获得锁的情况

ReentrantLock ：默认是非公平锁，优点吞吐量大比公平锁大

synchronized：也是非公平锁

### 5.2 可重入锁（递归锁）

指的是同一线程外层函数获得锁之后，内层递归函数仍能获取该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁。

也就是说，线程可以进入任何一个它已经拥有锁的锁所同步着的代码块

ReentrantLock 

synchronized

最大作用就是避免死锁

### 5.3 自旋锁

是指尝试获取锁的线程不会立刻阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU

#### 5.3.1 手写自旋锁如下

```java
public class SpinLockDemo {
    /**
     * 原子引用线程
     */
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo lockDemo = new SpinLockDemo();

        new Thread(() -> {
            lockDemo.myLock();

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lockDemo.myUnLock();
        },"t1").start();
    }

    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in thread");
        while (!atomicReference.compareAndSet(null,thread)){
        }
    }

    public void myUnLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
    }
}
```

###  5.4 读写锁

- 独占锁：一次只能被一个线程持有
- 共享锁：可以被多个线程之间共享

```java
// 读写锁
private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
```

读写锁：保证写操作同一时间只有一个线程操作，读操作可以被多个线程同时操作。即保证了操作的统一性和完整性，又提高了并发效率

使用读写锁：演示缓存案例

```java
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "",tempInt + "");
            },String.valueOf(i)).start();
        }
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            },String.valueOf(i)).start();
        }
    }
}

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入 \t" + key);
            TimeUnit.MICROSECONDS.sleep(300);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() +  "\t 写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            TimeUnit.MICROSECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取结果 = " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }
}
```

### 5.5 synchronized和Lock的区别？

- 原始构成
  - synchronized是Java关键字，属于JVM层面
  - Lock是具体的类

- 使用方法

  - synchronized 不需要手动释放
  - ReentrantLock：需要手动释放锁，若没有释放则可能导致死锁

- 等待是否可中断

  - synchronized，不能中断，除非运行完成或抛出异常
  - 可中断
    - 设置超时方法tryLock（Long timeout，TimeUnit unit）
    - lockInterruptibly()放在代码块中，调用interrupt()方法即可

- 加锁是否公平

  - 默认都是非公平锁
  - ReentrantLock可以设置成公平锁

- 绑定多个条件Condition

  - ReentrantLock用来实现分组唤醒需要唤醒的线程，可以精准唤醒。

  ```java
  /**
   * @author yim create time 2019-8-9
   *
   * 三个线程A B C
   * A 打印3次
   * B 打印6次
   * C 打印9次
   * 按照以上步骤循环打印10次
   */
  public class Lock_Condition {
      public static void main(String[] args) {
  
          ShareResource shareResource = new ShareResource();
  
          new Thread(() -> {
              for (int i = 0; i < 10; i++) {
                  shareResource.print01(3);
              }
          },"A").start();
  
          new Thread(() -> {
              for (int i = 0; i < 10; i++) {
                  shareResource.print02(6);
              }
          },"B").start();
  
          new Thread(() -> {
              for (int i = 0; i < 10; i++) {
                  shareResource.print03(9);
              }
          },"C").start();
      }
  }
  
  class ShareResource {
  
      private int number = 1;
      Lock lock = new ReentrantLock();
      Condition condition01 = lock.newCondition();
      Condition condition02 = lock.newCondition();
      Condition condition03 = lock.newCondition();
  
      public void print01(int count){
  
          lock.lock();
          try {
              // 判断防止线程虚假唤醒
              while (number != 1){
                  condition01.await();
              }
              for (int i = 1; i <= count; i++) {
                  System.out.println(Thread.currentThread().getName() + "\t" + i);
              }
              // 通知
              number = 2;
              condition02.signal();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }finally {
              lock.unlock();
          }
      }
      public void print02(int count){
  
          lock.lock();
          try {
              while (number != 2){
                  condition02.await();
              }
              for (int i = 1; i <= count; i++) {
                  System.out.println(Thread.currentThread().getName() + "\t" + i);
              }
              number = 3;
              condition03.signal();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }finally {
              lock.unlock();
          }
      }
      public void print03(int count){
  
          lock.lock();
          try {
              while (number != 3){
                  condition03.await();
              }
              for (int i = 1; i <= count; i++) {
                  System.out.println(Thread.currentThread().getName() + "\t" + i);
              }
              number = 1;
              condition01.signal();
          } catch (InterruptedException e) {
              e.printStackTrace();
          }finally {
              lock.unlock();
          }
      }
  }
  
  ```

## 6、CountDownLatch/CyclicBarrier/Semaphore使用过吗？

### 6.1 `CountDownLatch`

`java.util.concurrent.CountDownLatch`：让一些线程阻塞直到另外一些线程完成一系列操作之后才被唤醒

主要方法：

- countDown
- await：当计数结果为零时，因调用此方法阻塞的线程才会被唤醒

倒计数进行关闭：demo

```java
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <=6 ; i++) {
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName() + "\t 创建一个新线程，计数减一");		// 倒数计数减一	
                countDownLatch.countDown();
            }).start();
        }
        // 主线程等待，直到倒数计数完成
        countDownLatch.await();
        System.out.println("计数完成，执行此代码");
    }
}
```

### 6.2 `CyclicBarrier`

Barrier：屏障

`java.util.concurrent.CyclicBarrier`

作用：设置一个屏障，直到所有被要求到达的线程完成相应操作，屏障才会放行。

demo如下：

```java
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println("----收集7颗龙珠---召唤神龙");
        });

        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(()->{
                System.out.println("收集到---第" + tempInt + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
```

### 6.3 `Semaphore`

Semaphore：信号标、旗标

`java.util.concurrent.Semaphore`

作用：

1. 多个共享资源的互斥使用
2. 用于并发线程的控制

demo如下：

```
/**
 * 多个线程进行资源抢占的案例
 * @author yim create time 2019-8-8
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 停车场有3个停车位
        Semaphore semaphore = new Semaphore(3);
        // 总共有6辆车需要停车
        for (int i = 1; i <= 6; i++) {
            final int stayTime = 3;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位");
                    TimeUnit.SECONDS.sleep(stayTime);
                    System.out.println(Thread.currentThread().getName() + "停车" + stayTime + "秒后离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
```

## 7、阻塞队列知道吗？

队列：Queue  ---描述的是一种先进先出的数据结构

栈：Stack  ---描述的是一种先进后出的数据结构

### 7.1  阻塞队列`BlockingQueue`

- 阻塞队列有没有好的一面？
- 不得不阻塞，你如何管理？

为什么要使用`BlockingQueue`：好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程

#### 7.1.1 架构体系：

- `ArrayBlockingQueue`：由数组组成的有界阻塞队列
- `LinkedBlockingQueue`：由链表结构组成的有界阻塞队列（但大小默认值：Integer.MAX_VALUE）
- `SynchronousQueue`：不存储元素的阻塞队列，也即单个元素的队列

#### 7.1.2 API

| 方法类型 | 抛出异常  | 特殊值   | 阻塞   |        超时        |
| :------: | --------- | -------- | ------ | :----------------: |
|   插入   | add(e)    | offer(e) | put(e) | offer(e,time,unit) |
|   移除   | remove()  | poll()   | take() |  poll(time,unit)   |
|   检查   | element() | peek()   | 不可用 |       不可用       |

```java
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(2);
        blockingQueue.add("a");
        blockingQueue.add("b");
        // IllegalStateException 此时添加抛出队列已满异常
        //System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // NoSuchElementException
        //System.out.println(blockingQueue.remove());

        System.out.println("================================");

        ArrayBlockingQueue<String> blockingQueue01 = new ArrayBlockingQueue<>(2);
        blockingQueue01.put("a");
        blockingQueue01.put("b");
        // 插入元素过多，陷入阻塞状态，一直等待容器空余插入
        //blockingQueue01.put("c");
        blockingQueue01.take();
        blockingQueue01.take();

        ArrayBlockingQueue<String> blockingQueue02 = new ArrayBlockingQueue<>(2);
        blockingQueue02.offer("a",2, TimeUnit.SECONDS);
        blockingQueue02.offer("b",2, TimeUnit.SECONDS);
        // 设置超时时间，超过2s，插入失败，return false
        System.out.println(blockingQueue02.offer("c",2, TimeUnit.SECONDS));
    }
}
```

`SynchronousQueue`代码示例：

```java
public class SynchronousQueueDemo {
    public static void main(String[] args) {
    	// 该队列只同时存储一个元素
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread 01").start();

        new Thread(() -> {
            try {
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take 1");
                TimeUnit.SECONDS.sleep(3);
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take 2");
                TimeUnit.SECONDS.sleep(3);
                blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread 02").start();
    }
}
```

### 7.2 线程通信之生产者消费者（知识串联）

```java
public class Producer_BlockingQueue {
    public static void main(String[] args) throws Exception {
        MyResource resource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                resource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"producer").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                resource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"consumer").start();

        TimeUnit.SECONDS.sleep(5);
        resource.stop();
    }
}

class MyResource{
    /**
     * 默认开启，进行生产+消费
     */
    private boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception{

        String data = null;
        boolean retValue = false;

        while (flag){
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data,2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
    public void myConsumer() throws Exception{

        String result = null;

        while (flag){
            result = blockingQueue.poll(2,TimeUnit.SECONDS);
            if (result == null || result.equalsIgnoreCase("")) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过两秒钟没有取到");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }
    }

    public void stop() throws Exception{
        this.flag = false;
    }
}
```

## 8、线程池用过吗？ThreadPoolExecutor谈谈你的理解？

### 8.1 Callable接口创建线程的方式

```javascript
/**
 * @author yim create time 2019-8-8
 *
 * 创建线程的方式
 * 1. 继承Thread类
 * 2. 实现Runnable接口
 * 3. 使用Callable接口
 *
 * 4. 使用线程池创建
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread());
        new Thread(futureTask,"t1").start();
        // 同一个futureTask再次调用，不执行
        new Thread(futureTask,"t2").start();

        // futureTask.get()建议放在最后面，为了避免call()计算时间过长
        System.out.println("result****" + futureTask.get());
    }

}

class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("come in callable");
        return 1024;
    }
}
```

### 8.2 为什么使用线程池，它的优势是什么？

线程池，池化思想，是预先创建一定数量的线程，把创建线程的任务放进队列，如果线程数量超过了最大的预创建的最大数量，任务排队等候，等待其他线程执行完成，再执行。

主要特点：线程复用；通过对线程池的管理控制最大并发数

优点：

1. 降低资源消耗：复用:降低频繁创建和销毁线程的消耗
2. 提高相应速度：线程预创建，无需创建线程的操作
3. 提高了线程的可管理性：控制线程池数量，进行分配，调优和监控

### 8.3 线程池如何使用？

#### 8.3.1 线程池组织架构图

![executor](images/executor.png)

#### 8.3.2 线程池使用的3个常用方式

`ThreadPoolExecutor`

```java
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        // 固定线程数量的线程池
        ExecutorService threadPool01 = newFixedThreadPool(5);
        // 一池一线程
        ExecutorService threadPool02 = newSingleThreadExecutor();
        // 一池多线程
        ExecutorService threadPool03 = newCachedThreadPool();

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool03.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool01.shutdown();
        }
    }
}
```

### 8.4 线程池几个重要的参数介绍？

`ThreadPoolExecutor`：提供了四种构造方法，我们查看其中参数最多构造函数，来看看其参数作用：源码如下：

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.acc = System.getSecurityManager() == null ?
        null :
    AccessController.getContext();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

- corePoolSize：线程池中的常驻核心线程数
- maximumPoolSize：线程池能够容纳溶蚀执行的最大线程数，此值大于等于1
- keepAliveTime：多余的空闲线程的存活时间。
- unit：keepAliveTime的时间单位
- workQueue：任务队列，被提交但尚未被执行的任务
- threadFactory：表示生成线程池中工作线程的线程工厂
- handler：拒绝策略，便是当队列满了并且工作线程大于等于线程池的最大线程数（maximumPoolSize）如何拒绝

### 8.5 线程池的底层工作原理？

1. 创建线程池后，等待提交过来的任务请求
2. 当调用execute()方法添加一个请求时，线程池会做如下判断：
   1. 如果当前运行的线程数小于corePoolSize，那么马上创建运行这个任务
   2. 如果大于等于corePoolSize，那么将任务放入队列
   3. 如果阻塞队列满了，且正在运行的线程数量小于maximumPoolSize，那么还是要创建非核心线程立刻运行这个任务
   4. 如果阻塞队列满了，且运行的线程数大于等于maximumPoolSize，那么线程池会使用拒绝策略来执行。
3. 当一个线程完成任务时，它会从队列中取下一个任务来执行
4. 当一个线程无事可做，超过其存活时间keepAliveTime，线程池会做判断：如果当前线程数量大于corePoolSize，那么这个线程就会被停掉。

### 8.6 线程池的四种拒绝策略（内置）

1. `AbortPolicy`（中止策略，默认方式）：直接抛出RejectExecutionException异常阻止系统正常运行

2. `CallerRunsPolicy`：“调用者运行”一种调节机制，该策略不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量。
3. `DiscardOldestPolicy`：抛弃队列中等待最久的任务，然后把当前任务加入队列中重新尝试再次提交当前任务
4. `DiscardPolicy`：直接丢弃任务，不予任何处理也不抛出异常。如果任务允许丢失，这是最好的 一种方案。 

### 8.7 线程池的使用（手动创建）

```
/**
 * @author yim create time 2019-8-9
 *
 * 手动创建线程池的方式，Execcutors创建的线程池的几种方式都是根据ThreadPoolExecutor来的
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2
                        ,5
                        ,1L
                        , TimeUnit.SECONDS
                        ,new LinkedBlockingDeque<Runnable>(3)
                        , Executors.defaultThreadFactory()
                ,new ThreadPoolExecutor.DiscardPolicy());

        for (int i = 0; i < 12; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t check in");
            });
        }
        threadPool.shutdown();
    }
}
```

### 8.8 如何合理配置线程池

- CPU密集型：意思是任务需要大量的运算，而没有阻塞，cpu一直全速运行，参考公式
  - 最大线程数池公式：cpu核数+1
- IO密集型：即任务需要大量的IO，即大量的阻塞
  - 参考公式：cpu核数/(1-阻塞系数)
  - 阻塞系数0.8~0.9之间
