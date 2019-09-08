# Java编程基础

## 学习目标

- [ ] 了解编程语言分类，Java 在编程语言中位置
- [ ] 了解 Java 语言的发展以及重要版本特性
- [ ] 能够说出 Java 语言相关名词解释
- [ ] 了解计算机中进制之间的转换，掌握二进制和十进制之间的转换
- [ ] 掌握字节相关知识
- [ ] 理解开发一个 Java 程序的流程
- [ ] 掌握变量、常量、基本数据类型并了解引用数据类型
- [ ] 掌握运算符，理解其运算逻辑
- [ ] 了解 Java 代码的执行流程并掌握流程控制语句

## 一 开发基础准备

### 1.1 什么是编程语言

**编程语言**（programming language），是用来定义计算机程序的形式语言。它是一种被标准化的交流技巧，用来向计算机发出指令。一种计算机语言让程序员能够准确地定义计算机所需要使用的数据，并精确地定义在不同情况下所应当采取的行动。

编程语言俗称“计算机语言”，种类非常的多，总的来说可以分成机器语言、汇编语言、高级语言三大类。

Java 语言属于编程语言中的高级语言，Java可以编写[桌面应用程序](https://baike.baidu.com/item/桌面应用程序/2331979)、[Web应用程序](https://baike.baidu.com/item/Web应用程序)、[分布式系统](https://baike.baidu.com/item/分布式系统/4905336)和[嵌入式系统](https://baike.baidu.com/item/嵌入式系统/186978)应用程序等。

### 1.2 Java 语言的发展

以下只列出三个重要版本发布时间。

- 1996年1月，Sun公司发布了 Java 的第一个开发工具包*（JDK 1.0）*，这是 Java 发展历程中的重要里程碑，标志着 Java 成为一种独立的开发工具。

- 2004年9月30日，J2SE1.5发布，成为 Java 语言发展史上的又一里程碑。

- 2014年，甲骨文公司发布了 Java8 正式版

### 1.3 计算机中的进制

#### 1.3.1 理解进制

进制即进位计数制。

十进制是逢十进一，[十六进制](https://baike.baidu.com/item/十六进制/4162457)是逢十六进一，[二进制](https://baike.baidu.com/item/二进制/361457)就是逢二进一，以此类推，x进制就是逢x进位。

#### 1.3.2 进制的表现形式

**二进制**：二进制数据是用0和1两个数码来表示的数。在二进制中操作最高位(最左边)代表符号位，使用0代表非负数，使用1代表负数。

Java 中使用`0b`作为二进制的前缀

**十六进制**：一般用数字0到9和字母A到F（或a~f）表示，其中:A~F表示10~15，这些称作**十六进制数字**。

Java 中使用`0x`作为十六进制的前缀

#### 1.3.4 进制之间的转换

- **十进制数据转成二进制数据**
  - **除2取余法**：让十进制整数不断地除以2记录余数，直到商为0将所有余数逆序排列。
  - **拆分法**：将十进制整数拆分为若干个二进制权重的和，若有该权重则下面写1，否则写0。
- **二进制数据转成十进制数据**
  - **加权法**：让二进制的每个数字乘以当前位的权重，再将所有乘积累加起来即可。
- **负十进制转换为负二进制的方式**：将负十进制整数的绝对值转换为二进制，然后进行`按位取反`再加1。
- **负二进制转换为负十进制的方式**：先减1再按位取反，加权法得到十进制，添加负号

### 1.4 单个字节表示大小

**字节**：是我们常见的计算机中最小存储单元。计算机存储任何的数据，都是以字节的形式存储。

8个bit（二进制位） 0000 0000表示为1个字节，写成**1 byte**或者**1 B**。

在计算机中单个字节占8位二进制位，最高位表符号位，0表非负数，1表负数，具体如下:

```
非负数的表示范围是：0000 0000 ~ 0111 1111 => 0 ~ 127 => 0 ~ 2^7-1
0000 0000 => 0
0111 1111 => 0*2^7 + 1*2^6 + 1*2^5 + 1*2^4 + 1*2^3 + 1*2^2 + 1*2^1 + 1*2^0
=> 64 + 32 + 16 + 8 + 4 + 2 + 1
=> 127

负数的表示范围是：1000 0000 ~ 1111 1111 => -128 ~ -1 => -2^7 ~ -2^0
1000 0000 => 减1：0111 1111
=> 按位取反：1000 0000
=> 转换十进制：128
=> 添加负号： -128

1111 1111 => 减1：1111 1110
=> 按位取反：0000 0001
=> 转换十进制：1
=> 添加负号： -1

综上所述：在此也演示了二进制和十进制之间的转换过程
对于单个字节来说，所能表示的十进制整数范围是：-128 ~ 127 => -2^7 ~ 2^7-1.
```

**思考问题：什么是计算机中原码、补码、反码？**

### 1.5 Java 基础名词概念

- **JVM** （Java Virtual Machine ）：Java 虚拟机，是 Java程序运行的基础
- **Java语言的跨平台性** ：任何软件的运行，都必须要运行在操作系统之上，而我们用Java编写的软件可以运行在任何的操作系统上，这个特性称为Java语言的跨平台特性。该特性是由JVM实现的，我们编写的程序运行在JVM上，而JVM运行在操作系统上。
- **JRE**  (Java Runtime Environment) ：是Java程序的运行时环境，包含 `JVM `和运行时所需要的核心类库 。
- **JDK** (Java Development Kit)：是Java程序开发工具包，包含` JRE`和开发人员使用的工具。
- **javac.exe** ：Java编译器，用于将高级源代码文件编译为字节码文件。
- **java.exe** ：Java解释器，用于启动Java虚拟机对字节码文件进行边解释边执行。
- **环境变量** ：在操作系统中配置环境变量能够让程序在任意位置运行

**思考问题：在 Windows 和 Linux 进行 Java环境的搭建？**

### 1.6 如何开发第一个 Java 程序

1. 编写一个 HelloWorld.java 文件。
2. 在DOS命令行中，进入Java源文件的目录，使用` javac` 命令进行编译。
3. 使用` java` 命令进行运行。

**注意问题:**

- **main方法**：称为主方法。写法是固定格式不可以更改。main方法是程序的入口点或起始点，无论我们编写多
  少程序，JVM在运行的时候，都会从main方法这里开始执行。
- **关键字**：如 public,static,void
- **public 修饰的类名与文件名相同**
- **标识符的命名规则**

### 1.7 标识符的命名规则 ###

- **命名规则：` 硬性要求`**
  - 标识符可以包含 英文字母 `26个(区分大小写)` 、` 0 -9数字` 、 `$ （美元符号）` 和` _ （下划线）` 。
  - 标识符不能以数字开头。
  - 标识符不能是关键字。

- **命名规范： `软性建议`**
  - 类名规范：首字母大写，后面每个单词首字母大写（大驼峰式）。
  - 方法名规范： 首字母小写，后面每个单词首字母大写（小驼峰式）。
  - 变量名规范：全部小写。

## 二 变量和数据类型 ##

### 2.1 变量概述

**变量：常量是固定不变的数据，那么在程序中可以变化的量称为变量。**

**常量：常量是固定不变的数据，在Java中是由`public static final`共同修饰**

Java中要求一个变量每次只能保存一个数据，必须要明确保存的数据类型。

练习：**定义所有基本数据类型的变量**

```java
public class Variable {
    public static void main(String[] args){    
            //定义字节型变量
            byte b = 100;
            System.out.println(b);
            //定义短整型变量
            short s = 1000;
            System.out.println(s);
            //定义整型变量
            int i = 123456;
            System.out.println(i);
            //定义长整型变量
            long l = 12345678900L;
            System.out.println(l);
            //定义单精度浮点型变量
            float f = 5.5F;
            System.out.println(f);
            //定义双精度浮点型变量
            double d = 8.5;
            System.out.println(d);
            //定义布尔型变量
            boolean bool = false;
            System.out.println(bool);
            //定义字符型变量
            char c = 'A';
            System.out.println(c);
    }    
}
```

>long类型：建议数据后加L表示。
>
>float类型：建议数据后加F表示。

### 2.2 数据类型

在Java语言中将数据类型分为两大类：

- **基本数据类型**：byte、short、int、long、float、double、char、boolean。

- **引用数据类型**：数组、类、接口、枚举以及标注。

**基本数据类型**

|   数据类型   | 关键字  | 内存占用 | 取值范围              |
| :----------: | ------- | -------- | --------------------- |
|    字节型    | byte    | 1个字节  | -2^7 ~ 2^7-1          |
|    短整型    | short   | 2个字节  | -2^15 ~ 2^15-1        |
|     整型     | int     | 4个字节  | -2^31 ~ 2^31-1        |
|    长整型    | long    | 8个字节  | -2^63 ~ 2^63-1        |
| 单精度浮点数 | float   | 4个字节  | 1.4013E-45~3.4028E+38 |
| 双精度浮点数 | double  | 8个字节  | 4.9E-324~1.7977E+308  |
|    字符型    | char    | 2个字节  | 0~65535               |
|   布尔类型   | boolean | 1个字节  | ture，false           |

> Java中的默认类型：整数类型是 int 、浮点类型是 double 。

### 2.3 数据类型转换 ###

Java程序中要求参与的计算的数据，必须要保证数据类型的一致性，如果数据类型不一致将发生类型的转换。

- **自动类型转换**：将`取值范围小的类型`自动提升为` 取值范围大的类型` 。

  - Java自动进行的隐形操作

  - **基本数据类型** ：

    ```
    byte、short、char ‐‐> int ‐‐> long ‐‐> float ‐‐> double
    byte 、short、char 运算时默认提升为 int
    float 运算时默认提升为 double
    ```

  - **引用数据类型** ：子类类型向父类类型之间的转换

- **强制类型转换**：` 取值范围大的类型`强制转换成`取值范围小的类型`

  - 需要程序员手动进行的显性操作

  - 基本数据类型：强转问题

    - 浮点转成整数，直接取消小数点，可能造成数据损失精度。
    - int 强制转成 short 砍掉2个字节，可能造成数据丢失。

  - **引用数据类型**：父类类型向子类类型之间的转换,为了避免错误强转之前做以下判断

    ```java
    if(引用变量名 instanceof 目标类型) {
        // 用于判断引用真正指向的对象类型是否为目标类型，若是则返回true，否返回false
        // 所以父类的引用必须指向强转的对象才可以进行转换
    } 
    
    // 示例内容
    public static void main(String[] args) {
        Person person = new User();
        if (person instanceof User){
            System.out.println("类型判断成功！");
        }
    }
    ```
  
- **如何将字符串转换为基本数据类型?**

  - 调用基本数据类型对应的`包装类`中的方法 parseXXX(String)或valueOf(String)即可返回相应基本类型

- **如何将基本数据类型转换为字符串？**

  - 一种方法是：将基本数据类型与空字符串（""）连接（+）即可获得其所对应的字符串；
  -  一种方法是：调用 String 类中的 valueOf()方法返回相应字符串 

### 2.4 ASCII 编码表

在计算机的内部都是二进制的0、1数据，如何让计算机可以直接识别人类文字的问题呢？就产生出了编码表的概念。如下罗列的是需要记住的内容。

**编码表** ：就是将人类的文字和一个十进制数进行对应起来组成一张表格。

| 字符 | 数值 |
| :--: | :--: |
|  0   |  48  |
|  9   |  57  |
|  A   |  65  |
|  Z   |  90  |
|  a   |  97  |
|  z   | 122  |

## 第三章 运算符 ##

### 3.1 算术运算符 ###

| 运算符号 |             定义             |
| :------: | :--------------------------: |
|   `+`    |                              |
|   `-`    |                              |
|   `*`    |                              |
|   `/`    |                              |
|   `%`    | 取模运算，两个数字相除取余数 |
|   `++`   |           自增运算           |
|   `--`   |           自减运算           |

#### 3.1.1 注意事项

- 当两个整数进行除法运算时，结果只取整数部分，丢弃小数部分；

- 若希望保留小数部分则处理方式如下：

	将其中一个操作数强转为double类型后再进行除法运算。
	将其中一个操作数乘以1.0后再进行除法运算(推荐)。

- 0不能做除数，0.0可以做除数但结果是无穷大，因此通常不建议使用

- `+` 可以表示加法运算符 也可以表示字符串连接符

#### 3.1.2 前++和后++区别

- 变量`前 ++`：变量a自己加1，将加1后的结果赋值给b，也就是说a先计算。a和b的结果都是2。

  ```java
  public static void main(String[] args) {
      int a = 1;
      int b = ++a;
      System.out.println(a);//计算结果是2
      System.out.println(b);//计算结果是2
  }
  ```

- 变量 `后 ++` ：变量a先把自己的值1，赋值给变量b，此时变量b的值就是1，变量a自己再加1。a的结果是2，b的结果是1。

  ```java
  public static void main(String[] args) {
      int a = 1;
      int b = a++;
      System.out.println(a);//计算结果是2
      System.out.println(b);//计算结果是1
  }
  ```

### 3.2 关系/比较运算符

| 运算符号 |   定义   |
| :------: | :------: |
|   `>`    |   大于   |
|   `<`    |   小于   |
|   `>=`   | 大于等于 |
|   `<=`   | 小于等于 |
|   `==`   |   等于   |
|   `!=`   |  不等于  |

> 比较运算符，是两个数据之间进行比较的运算，运算结果都是布尔值 true 或者 false 

### 3.3 赋值运算符

| 运算符号 |        定义         |
| :------: | :-----------------: |
|   `=`    | 将=右边的值赋给左边 |
|   `-=`   |  复合赋值：减等于   |
|   `+=`   |       加等于        |
|   `*=`   |       乘等于        |
|   `/=`   |       除等于        |
|   `%=`   |      取模等于       |

> 赋值运算符，就是将符号右边的值，赋给左边的变量

### 3.4 逻辑运算符 ###

| 运算符号 |  定义  |
| :------: | :----: |
|   `&&`   | 逻辑与 |
|   `||`   | 逻辑或 |
|   `！`   | 逻辑非 |

**短路特性**

- 对于逻辑与运算符来说，若第一个条件为假则整个表达式一定为假，第二个条件跳过不执行
- 对于逻辑或运算符来说，若第一个条件为真则整个表达式一定为真，第二个条件跳过不执行

### 3.5 三元运算符 ###

```
数据类型 变量名 = 布尔类型表达式？结果1：结果2
```

- 布尔类型表达式结果是 true，三元运算符整体结果为结果1，赋值给变量。
- 布尔类型表达式结果是 false，三元运算符整体结果为结果2，赋值给变量

```java
public static void main(String[] args) {
    int i = (1==2 ? 100 : 200);
    System.out.println(i);//200
    int j = (3<=4 ? 500 : 600);
    System.out.println(j);//500
}
```

### 3.6 移位运算符（二进制运算） ###

| 运算符号 |                  定义                  |
| :------: | :------------------------------------: |
|   `<<`   |    左移运算符（二进制左移 0补低位）    |
|   `>>`   |   右移运算符（二进制右移 补符号位）    |
|  `>>>`   | 无符号右移运算符（二进制右移 0补高位） |

### 3.7 位运算符 (二进制运算) ###

| 运算符号 |             定义             |
| :------: | :--------------------------: |
|   `&`    |   按位与（同1为1，一0为0）   |
|   `|`    |   按位或（一1为1，同0为0）   |
|   `~`    |    按位取反（1为0，0为1）    |
|   `^`    | 按位异或（相同为0，不同为1） |

## 四 流程控制语句 ##

### 4.1 顺序结构

```java
public static void main(String[] args) {
    //顺序执行，根据编写的顺序，从上到下运行
    System.out.println(1);
    System.out.println(2);
    System.out.println(3);
}
```

### 4.2 判断语句

#### 4.2.1 if判断语句

```
(1)语法格式
    if(条件表达式){
        语句块;
    }	
(2)执行流程
    判断条件表达式是否成立
        => 若成立，则执行语句块；
        => 若不成立，则分支结构结束；
```

#### 4.2.2 if-else判断语句 ####

```
(1)语法格式
    if(条件表达式){
        语句块1;
    }
	else{
    	语句块2;
    }
(2)执行流程
    判断条件表达式是否成立
        => 若成立，则执行语句块1；
        => 若不成立，则执行语句块2；
```

#### 4.2.3 if-else if-else判断语句 ####

```
(1)语法格式
    if(条件表达式1){
    	语句块1;
    }
    else if(条件表达式2){
    	语句块2;
    }
    ... ...
    else{
        语句块n;
    }	
(2)执行流程
    判断条件表达式1是否成立
        => 若成立，则执行语句块1；
        => 若不成立，则判断条件表达式2是否成立
        => 若成立，则执行语句块2；
        => 若不成立，则执行语句块n；
```

### 4.3 选择语句

#### 4.3.1 switch-case选择语句

	(1)语法格式
	   switch(变量/表达式){
	       case 直接量1: 语句块1; break;
	       case 直接量2: 语句块2; break;
	       ... ...
	       default: 语句块n;
	   }
	(2)执行流程
	   计算变量/表达式的数值 => 判断是否与直接量1匹配
	      => 若匹配，则执行语句块1 => 执行break跳出该结构；
	      => 若不匹配，则判断是否与直接量2匹配
	               => 若匹配，则执行语句块2 => 执行break跳出该结构；
	               => 若不匹配，则执行语句块n;
> switch 语句中,表达式的数据类型，可以是byte，short，int，char，enum，JDK7后可以接收String 。

#### 4.3.2 case 的穿透性

在switch语句中，如果case的后面不写break，将出现穿透现象，也就是不会在判断下一个case的值，直接向后运
行，直到遇到break，或者整体switch结束。

### 4.4 循环语句

#### 4.4.1 循环语句1--for ####

```java
(1)语法格式
   for(初始化表达式; 条件表达式; 修改初始值表达式){
       循环体;
   }
(2)执行流程
   执行初始化表达式 => 判断条件表达式是否成立
       => 若成立，则执行循环体 => 执行修改初始值表达式 => 判断条件表达式是否成立
       => 若不成立，则循环结束
```

**死循环**

	for(;;) - 这种没有明确循环条件的循环叫做无限循环，俗称"死循环"。
	该循环通常与break关键字搭配使用

**嵌套循环**

	(1)语法格式
	    for(初始化表达式1; 条件表达式2; 修改初始值表达式3){
	        for(初始化表达式4; 条件表达式5; 修改初始值表达式6){
	            内层循环的循环体;
	        }
	    }
	(2)执行流程
	    执行表达式1 => 判断表达式2是否成立
	        => 若成立，则执行表达式4 => 判断条件表达式5是否成立
	        => 若成立，则执行循环体 => 执行表达式6 => 判断条件表达式5是否成立
	        => 若不成立，则内层循环结束 => 执行表达式3 => 判断表达式2是否成立
	        => 若不成立，则外层循环结束

#### 4.4.2 循环语句2--while

	(1)语法格式
	   while(条件表达式){
	      循环体;
	   }
	
	(2)执行流程
	   判断条件表达式是否成立
	       => 若成立，则执行循环体 => 判断条件表达式是否成立
	       => 若不成立，则循环结束
	
	(3)注意事项
	   a.while循环和for循环可以完全互换；
	   b.while循环通常使用在明确循环条件不明确循环次数的场合中；
	     for循环通常使用在明确循环次数但不明确循环条件的场合中；
	   c.while(true)等价于for(;;)都表示无限循环；

#### 4.4.3 循环语句3--do while

	(1)语法格式
	   do{
	     循环体;
	   }while(条件表达式);
	
	(2)执行流程
	   执行循环体 => 判断条件表达式是否成立
	        => 若成立，则执行循环体 => 判断条件表达式是否成立
	        => 如不成立，则循环结束
	
	(3)注意事项
	   do-while循环主要用于至少执行一次循环体的场合中;

### 4.5 break和continue

**break**

- 使用场景：终止 switch或者循环

  - 在选择结构 switch语句中

  - 在循环语句中

  - 离开使用场景的存在是没有意义的

    ```java
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //需求:打印完两次HelloWorld之后结束循环
            if (i == 3){
                break;
            }
            System.out.println("HelloWorld");
        }
    }
    ```

**continue**

- 使用场景：结束本次循环，继续下一次的循环

  ```java
  public static void main(String[] args) {
      for (int i = 0; i < 10; i++) {
          //需求:不打印第三次HelloWorld
          if (i == 3){
              continue;
          }
          System.out.println("HelloWorld");
      }
  }
  ```

### 4.6 课后练习

**1. 打印九九乘法表（双重for循环）**

```java
public class TestStudy {
	
	/**
	 * 打印九九乘法表
	 * @param args
	 */
	public static void main(String[] args) {
		
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print(i+"x"+j + "=" + i*j +" ");
			}
			System.out.println();
		}
	}
}
```

**2. 打印水仙花数**-

```java
/*
     编程实现所有三位数水仙花数的打印
 */
public class TestWaterFlower{

    public static void main(String[] args){

         //1.打印所有的三位数
         for(int i = 100; i <= 999; i++){

             //2.拆分该三位数中的每个数字   123
             int ia = i / 100;  //获取百位数
             int ib = i % 100 / 10;  //获取十位数
             int ic = i % 10;   //获取个位数

             //3.判断i代表的数值是否为水仙花数，若是则打印
             if((ia*ia*ia + ib*ib*ib + ic*ic*ic) == i){
                 System.out.println(i);
             }
         }
    }
}
```

**3、编程实现双色球**

```java
package cn.xdl.test;

import java.util.Arrays;
import java.util.Random;

public class TestStudy {
	
	public static void main(String[] args) {
		
		int[] arr = new int[6];
		//调用随机函数
		Random random = new Random();
		
		for (int i = 0; i < 6; i++) {
			//调用随机函数生成1~33之间的随机数并对数组进行赋值
			arr[i] = random.nextInt(33)+1;
			//针对每次生成的随机号码进行去重操作
			for (int j = i-1 ; j >=0 ;j--) {
				//如果有相等,i--重新执行生成操作
				if(arr[i] == arr[j]){
					i--;
					break;
				}
			}
		}
		Arrays.sort(arr);//排序
		int red  = random.nextInt(16)+1;
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]+" ");
		}
		System.out.println(red);
	}
}
```

**3. 编程实现五子棋(控制台版):二维数组**

```java
package cn.yimu.goband;

/*
     编程实现控制台版的五子棋游戏，支持两人对战
 */
import java.util.Scanner;

public class Goband {
    //自定义二维数组来描述棋盘，默认初始值为0
    int[][] chessBoard = new int[16][16];

    //自定义成员方法来绘制棋盘
    void paint(){
        //1.先绘制棋盘中第一行的坐标信息，也就是列坐标信息
        for(int i = 0; i < 17; i++){
            if(i == 0){
                System.out.print("  ");
            }
            else{
                //按照十六进制的格式打印i-1的数值
                System.out.printf("%x ", i-1);
            }
        }
        System.out.println();

        //2.绘制棋盘中除了第一行之外的其他部分以及行坐标信息
        for(int i = 0; i < 16; i++){
            //用于打印行坐标信息
            System.out.printf("%x ", i);
            for(int j = 0; j < 16; j++){
                //刚开始棋盘中的所有内容都是+，因此直接打印
                if(chessBoard[i][j] == 0){
                    System.out.print("+ ");
                }
                else if(chessBoard[i][j] == 1){
                    System.out.print("● ");
                }
                else{
                    System.out.print("○ ");
                }
            }
            //打印完毕一行的所有内容之后进行换行
            System.out.println();
        }
    }

    //自定义成员方法来提示黑方和白方分别下棋
    void play(){
        //定义标志位来进行黑方和白方的切换，true代表黑方，false代表白方
        boolean flag = true;
        //不断地分别提示黑方和白方下棋
        while(true){
            System.out.println("请" + (flag ? "黑方": "白方") + "输入落子坐标(x y)：");
            Scanner sc = new Scanner(System.in);
            int ix = sc.nextInt();
            int iy = sc.nextInt();
            //根据用户输入的坐标来调整棋盘中的图案，策略为改变数组的元素值
            if(flag){
                //当黑方落子时就将数组中对应元素值改为1
                chessBoard[ix][iy] = 1;
            }else{
                //当白方落子时就将数组中对应元素改为2
                chessBoard[ix][iy] = 2;
            }
            //重新绘制图案
            paint();
            //判断当前方是否胜利，若胜利就立刻结束游戏
            if(judge(ix, iy)){
                System.out.println("恭喜" + (flag ? "黑方": "白方") + "胜利了！");
                break;
            }
            //此时切换下棋方
            flag = !flag;
        }
    }

    //自定义成员方法来判断用户是否获胜，获胜的规则是：任意相同颜色的5个棋子连成一线
    boolean judge(int ix, int iy){
        //1.判断竖向是否连成一线，则需要以该点为中心向上四个点向下四个点
        //声明变量来统计竖向相同颜色棋子的个数，先统计向上同色棋子的个数
        int count = 1;
        for(int i = ix-1; i >= 0 && i >= ix-4; i--){
            //若当前点代表的棋子与上述某个点代表的棋子不一样，则向上统计结束
            if(chessBoard[ix][iy] != chessBoard[i][iy]){
                break;
            }
            count++;
        }
        System.out.println("count1 = " + count);
        //再统计向下颜色相同的个数
        for(int i = ix+1; i <= 15 && i <= ix+4; i++){
            if(chessBoard[ix][iy] != chessBoard[i][iy]){
                break;
            }
            count++;
        }
        System.out.println("count2 = " + count);
        //... ...
        return count >= 5;

        //当所有可能胜利的情况都排除了，那么肯定是失败了
    }
}
```

**启动类:**

```java
public class TestGoband {

    public static void main(String[] args) {

        //1.声明一个GoBand类型的引用指向该类的对象
        Goband gb = new Goband();
        //2.调用成员方法来绘制棋盘
        gb.paint();
        //3.调用成员方法来进行下棋
        gb.play();
    }

}
```

## 五 真题汇总

1. 单个字节表示大小？
2. 基本数据类型及其包装类？
3. Integer 和 int 自动装箱池？
4. ++ i 和 i ++？
5. &和&&？
6. 自动类型转换和强制类型转换？
7. 流程控制语句执行顺序？
8. 变量的默认值，对比成员变量和局部变量？
9. == 和 equal 对比？
10. 跳出多重循环的方式？
11. switch支持的数据格式？
