# Java8版本特性

## 主要内容

*   Lambda表达式
*   Stream API
*   接口默认方法
*   JVM元空间替换永久代
*   Data API
*   G1

## 一 lambda表达式

一段代码：理解lambad

```java
package cn.yim.se.lambda;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Create By Yim On 2019-6-14 13:24
 *
 * Lambda省去面向对象的条条框框，格式由3个部分组成：
 *  一些参数
 *  一个箭头
 *  一段代码
 *
 *省略规则
 *  在Lambda标准格式的基础上，使用省略写法的规则为：
 *      1. 小括号内参数的类型可以省略；
 *      2. 如果小括号内有且仅有一个参，则小括号可以省略；
 *      3. 如果大括号内有且仅有一个语句，则无论是否有返回值，都可以省略大括号、return关键字及语句分号。
 *
 *使用前提：使用Lambda必须具有接口，且要求接口中有且仅有一个抽象方法。
 * 
 * 备注：有且仅有一个抽象方法的接口，称为“**函数式接口**”。
 *
 **/
public class LambdaTest {

    // 无参数无返回值写法
    public void noParameter(){
        new Thread(() -> System.out.println("匿名内部类开启多线程")).start();
    }

    // 有返回值有参数写法
    public static void returnParameter(Person[] array){
        Arrays.sort(array,(Person p1,Person p2) -> {
            return p1.getAge() - p2.getAge();
        });
    }

    public static void sortPerson(Person[] array){

        // 重写比较器方法提供按照年龄进行排序的规则
        Comparator<Person> personComparable = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        };

        // 使用数组工具类排序
        Arrays.sort(array,personComparable);
    }

    public static void main(String[] args) {

        Person[] array = {new Person("赵一",26)
                ,new Person("钱二",23),new Person("迪丽热巴",25)};

        // LambdaTest.returnParameter(array);

        LambdaTest.sortPerson(array);

        for (Person person : array) {
            System.out.println(person.getName());
        }
    }
}

```

## 二 函数式接口

### 2.1  概述

函数式接口在Java中是指：**有且仅有一个抽象方法的接口。**

函数式接口，即适用于函数式编程场景的接口。而Java中的函数式编程体现就是Lambda，所以函数式接口就是可以适用于Lambda使用的接口。只有确保接口中有且仅有一个抽象方法，Java中的Lambda才能顺利地进行推导。

> 备注：“语法糖”是指使用更加方便，但是原理不变的代码语法。例如在遍历集合时使用的for-each语法，其实
> 底层的实现原理仍然是迭代器，这便是“语法糖”。从应用层面来讲，Java中的Lambda可以被当做是匿名内部
> 类的“语法糖”，但是二者在原理上是不同的。

### 2.2 @FunctionalInterface注解

与 @Override 注解的作用类似，Java 8中专门为函数式接口引入了一个新的注解： @FunctionalInterface 。该注
解可用于一个接口的定义上：

```java
@FunctionalInterface
public interface MyFunctionalInterface {
	void myMethod();    
}
```

一旦使用该注解来定义接口，编译器将会强制检查该接口是否确实有且仅有一个抽象方法，否则将会报错。需要注

意的是，即使不使用该注解，只要满足函数式接口的定义，这仍然是一个函数式接口，使用起来都一样。

### 2.3 函数式编程

**性能浪费的日志案例**

```java
/**
 * Create By Yim On 2019-6-14 15:29
 **/
public class LoggerDemo {

    // 如下方法中存在的问题，并没有用到str字符，却已经在main方法中进行了拼接，造成性能的浪费
    public static void appendStr(int in,String str){

        if (in == 1){
            System.out.println("性能浪费情况举例！");
            System.out.println(str);
        }
    }

    public static void delay(int in,BuildMessage buildMessage){

        if (in == 1){
            System.out.println("使用lambda表达式简写内部类达到延时执行");
            System.out.println(buildMessage.buildMsg());
        }
    }

    public static void main(String[] args) {
        String ab = "abc";
        String bc = "efg";
        String ef = "hig";

        appendStr(1,ab+bc+ef);
        //delay(2,() -> ab+bc+ef);
        delay(2,() ->{
            System.out.println("证明结果！字符串没有拼接");
            return ab+bc+ef;
        });
    }
}
```

> 备注：SLF4J是应用非常广泛的日志框架，它在记录日志时为了解决这种性能浪费的问题，并不推荐首先进行
> 字符串的拼接，而是将字符串的若干部分作为可变参数传入方法中，仅在日志级别满足要求的情况下才会进
> 行字符串拼接。例如： LOGGER.debug("变量{}的取值为{}。", "os", "macOS") ，其中的大括号 {} 为占位
> 符。如果满足日志级别要求，则会将“os”和“macOS”两个字符串依次拼接到大括号的位置；否则不会进行字
> 符串拼接。这也是一种可行解决方案，但Lambda可以做到更好。

### 2.4 常用的函数式接口

- Supplier接口
  - java.util.function.Supplier<T> 接口仅包含一个无参的方法： T get() 。用来获取一个泛型参数指定类型的对象数据。由于这是一个函数式接口，这也就意味着对应的Lambda表达式需要“对外提供”一个符合泛型类型的对象数据。
- Consumer接口
  - java.util.function.Consumer<T> 接口则正好与Supplier接口相反，它不是生产一个数据，而是消费一个数据，其数据类型由泛型决定。
  - Consumer 接口中包含抽象方法 void accept(T t) ，意为消费一个指定泛型的数据
  - 默认方法：andThen
- Predicate接口
  - java.util.function.Predicate<T> 接口
  - Predicate 接口中包含一个抽象方法： boolean test(T t) ，用于条件判断的场景
  - 默认方法：and
  - 默认方法：or
  - 默认方法：negate（取反）
- Function接口
  - java.util.function.Function<T,R> 接口用来根据一个类型的数据得到另一个类型的数据，前者称为前置条件，后者称为后置条件。
  - Function 接口中最主要的抽象方法为： R apply(T t) ，根据类型T的参数获取类型R的结果

## 三 Stream流

说到Stream便容易想到I/O Stream，而实际上，谁规定“流”就一定是“IO流”呢？在Java 8中，得益于Lambda所带
来的函数式编程，引入了一个全新的Stream概念，用于解决已有集合类库既有的弊端。

### 3.1  使用Stream流解决传统集合多步循环遍历

```java
/**
 * Create By Yim On 2019-6-14 16:02
 **/
public class StreamFilter {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("张三");
        arrayList.add("刘四浓");
        arrayList.add("王五");
        arrayList.add("刘大爷");
        arrayList.add("张保长");
        arrayList.add("郝运");

        arrayList.stream().filter(sh -> sh.startsWith("刘")).filter(s -> s.length() == 3).forEach(System.out::println);
        arrayList.stream().forEach(s -> System.out.println(s));
        arrayList.stream().forEach(System.out::println); // ::为方法的引用
    }
}
```

### 3.2 流式思想概述

Stream（流）是一个来自数据源的元素队列

- 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
- 数据源 流的来源。 可以是集合，数组 等。

和以前的Collection操作不同， Stream操作还有两个基础的特征：

- **Pipelining**: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluentstyle）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
- **内部迭代**： 以前对集合遍历都是通过Iterator或者增强for的方式, 显式的在集合外部进行迭代， 这叫做外部迭
  代。 Stream提供了内部迭代的方式，流可以直接调用遍历方法。

当使用一个流的时候，通常包括三个基本步骤：获取一个数据源（source）→ 数据转换→执行操作获取想要的结
果，每次转换原有 Stream 对象不改变，返回一个新的 Stream 对象（可以有多次转换），这就允许对其操作可以
像链条一样排列，变成一个管道。

> 备注：“Stream流”其实是一个集合元素的函数模型，它并不是集合，也不是数据结构，其本身并不存储任何
> 元素（或其地址值）。

### 3.3 获取流

java.util.stream.Stream<T> 是Java 8新加入的最常用的流接口。（这并不是一个函数式接口。）

获取一个流非常简单，有以下几种常用的方式：

- 所有的 Collection 集合都可以通过 stream 默认方法获取流；
- Stream 接口的静态方法 of 可以获取数组对应的流。

```java
public class TestGetStream {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();// Collection获取流的方式
        Stream<String> stream = list.stream();

        Set<String> hashSet = new HashSet<>();
        Stream<String> stream5 = hashSet.stream();

        Map<String, String> hashMap = new HashMap<>();// Map获取流的方法
        Stream<String> stream1 = hashMap.keySet().stream();
        Stream<String> stream2 = hashMap.values().stream();
        Stream<Map.Entry<String, String>> stream3 = hashMap.entrySet().stream();

        String[] arr = {"方木","陈希","廖亚凡","宿管阿姨"};// 数组获取流的方法
        Stream<String> stream4 = Stream.of(arr);
    }
}
```

### 4.4 常用方法

流模型的操作很丰富，这里介绍一些常用的API。这些方法可以被分成两种：

- 延迟方法：返回值类型仍然是 Stream 接口自身类型的方法，因此支持链式调用。（除了终结方法外，其余方
  法均为延迟方法。）
- 终结方法：返回值类型不再是 Stream 接口自身类型的方法，因此不再支持类似 StringBuilder 那样的链式调
  用。本小节中，终结方法包括 count 和 forEach 方法。

逐一处理：forEach

过滤：filter

映射：map

统计个数：count

取用前几个：limit

跳过前几个：skip

组合：concat

## 四 方法引用

### 4.1 方法引用符

双冒号 `:: `为引用运算符，而它所在的表达式被称为方法引用。如果Lambda要表达的函数方案已经存在于某个方
法的实现中，那么则可以通过双冒号来引用该方法作为Lambda的替代者。

### 4.2 使用

例如上例中， System.out 对象中有一个重载的 println(String) 方法恰好就是我们所需要的。那么对于
printString 方法的函数式接口参数，对比下面两种写法，完全等效：

- Lambda表达式写法： s -> System.out.println(s);
- 方法引用写法： System.out::println

第一种语义是指：拿到参数之后经Lambda之手，继而传递给 System.out.println 方法去处理。

第二种等效写法的语义是指：直接让 System.out 中的 println 方法来取代Lambda。两种写法的执行效果完全一
样，而第二种方法引用的写法复用了已有方案，更加简洁。

> 注:Lambda 中 传递的参数 一定是方法引用中 的那个方法可以接收的类型,否则会抛出异常 

