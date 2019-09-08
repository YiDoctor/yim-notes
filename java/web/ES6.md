# ES6新特性 #

## 主要内容

- 什么是ES6
- 新特性

ES6，是ECMAScript 6的简称，它是 JavaScript 语言的下一代标准，己于 2015 年 6 月正式发 布。
它的目标是使 JavaScript语言可以用于编写复杂的大型应用程序，成为企业级开发语言。

### 1 let 和 const 命令

- var 有一个问题，就是定义的变量有时会莫名奇妙的成为全局变量。
- let 所声明的变量，只在 let 命令所在的代码块内有效。
- const 声明的变量是常量，不能被修改，类似于java中final关键字。

### 2 字符串扩展

- includes() ：返回布尔值，表示是否找到了参数字符串。
- startsWith() ：返回布尔值，表示参数字符串是否在原字符串的头部。

- endsWith() ：返回布尔值，表示参数字符串是否在原字符串的尾部。

## 3 结构表达式

什么是解构？ -- ES6中允许按照一定模式从数组和对象中提取值，然后对变量进行赋值，这被称为解构 (Destructuring)。

1. 数组解构

   ```javascript
   let arr = [1,2,3] 
   
   let arr = [1,2,3]
   const [x,y,z] = arr;// x，y，z将与arr中的每个位置对应来取值
   // 然后打印
   console.log(x,y,z);// 1 2 3
   const [a] = arr; //只匹配1个参数
   console.log(a);// 1
   ```

2. 对象解构

   ```javascript
   const person = {
       name:"jack",
       age:21,
       language: ['java','js','css']
   }
   
   // 解构表达式获取值
   const {name,age,language} = person;
   // 打印
   console.log(name);
   console.log(age);
   console.log(language);
   ```

## 4 函数优化

在ES6中，对函数的操作做了优化，使得我们在操作函数时更加的便捷。

### 4.1 函数参数默认值

```javascript
// 之前
function add(a , b) {
    // 判断b是否为空，为空就给默认值1
    b = b || 1;
    return a + b;
}
// 传一个参数
console.log(add(10));
--------------------------------------------------------
// ES6
function add(a , b = 1) {
    return a + b;
}
// 传一个参数
console.log(add(10));
```

### 4.2 箭头函数

1. 一个参数时：

   ```javascript
   var print = function (obj) {
     console.log(obj);
   }
   // 简写为：
   var print2 = obj => console.log(obj);
   ```

2. 多个参数时：

   ```javascript
   //  两个参数的情况：
   var sum = function (a , b) {
     return a + b;
   }
   // 简写为：
   var sum2 = (a,b) => a+b;
   ```

3. 没有参数时：

   ```javascript
   //  没有参数时，需要通过()进行占位，代表参数部分
   let sayHello = () => console.log("hello!");
   sayHello();
   ```

4. 代码不止一行，用{}括起来

   ```javascript
   var sum3 = (a,b) => {
       return a + b;
   }
   // 多行，没有返回值
   let sayHello = () => {
       console.log("hello!");
       console.log("world!");
   }
   sayHello();
   ```

### 4.3 对象的函数属性简写

```javascript
let person = {
  name: "jack",
  // 以前：
  eat: function (food) {
    console.log(this.name + "在吃" + food);
 },
  // 箭头函数版：
  eat2: food => console.log(person.name + "在吃" + food),// 这里拿不到this
  // 简写版：
  eat3(food){
    console.log(this.name + "在吃" + food);
 }
}
```

### 4.4 箭头函数结合解构表达式

```javascript
const person = {
  name:"jack",
  age:21,
  language: ['java','js','css']
}
function hello(person) {
  console.log("hello," + person.name)
}
```

如果用箭头函数和解构表达式:

```javascript
var hi = ({name}) =>  console.log("hello," + name);
hi(person)
```

## 5 map和reduce

ES6中，数组新增了map和reduce方法。

- map() ：接收一个函数，将原数组中的所有元素用这个函数处理后放入新数组返回。

  - 举例：有一个字符串数组，我们希望转为int数组

  ```javascript
  let arr = ['1','20','-5','3'];
  console.log(arr)
  let newArr = arr.map(s => parseInt(s));
  console.log(newArr)
  ```

- reduce() ：接收一个函数（必须）和一个初始值（可选）,该函数接收两个参数：

  - 第一个参数是上一次 reduce处理的结果
  - 第二个参数是数组中要处理的下一个元素
  - reduce() 会从左到右依次把数组中的元素用reduce处理，并把处理的结果作为下次reduce的第一个参数。如果是第一次，会把前两个元素作为计算参数，或者把用户指定的初始值作为起始参数

  ```javascript
  const arr = [1,20,-5,3] 
  arr.produce((a,b) => a+b);//没有初始值
  arr.produce((a,b) => a*b,-1);//指定初始值
  ```

## 6 扩展运算符

扩展运算符(spread)是三个点(...)， 将一个数组转为用逗号分隔的参数序列 。

```javascript
console.log (...[1, 2, 3]); //1 2 3
console.log(1, ...[2, 3, 4], 5); // 1 2 3 4 5
function add(x, y) {
    return x + y;
}
var numbers = [1, 2];
console.log(add(...numbers)); // 3
// 数组合并
let arr = [...[1,2,3],...[4,5,6]];
console.log(arr); //[1, 2, 3, 4, 5, 6]
// 与解构表达式结合
const [first, ...rest] = [1, 2, 3, 4, 5];
console.log(first, rest) //1  [2, 3, 4, 5]
//将字符串转成数组
console.log([...'hello']) //["h", "e", "l", "l", "o"]
```

## 7 Promise

所谓Promise，简单说就是一个容器，里面保存着某个未来才会结束的事件（通常是一个异步操作）的结果。从语法上说，Promise 是一个对象，从它可以获取异步操作的消息。Promise 提供统一的 API，各种异步操作都可以用同样的方法进行处理。

我们可以通过Promise的构造函数来创建Promise对象，并在内部封装一个异步执行的结果。

```javascript
const promise = new Promise(function(resolve, reject) {
 // ... 执行异步操作
 if (/* 异步操作成功 */){
  resolve(value);// 调用resolve，代表Promise将返回成功的结果
} else {
  reject(error);// 调用reject，代表Promise会返回失败结果
}
});
```

如果我们想要等待异步执行完成，做一些事情，我们可以通过 promise的then方法来实现,语法：

```javascript
promise .then(function(value){
  // 异步执行成功后的回调
});
```

如果想要处理promise异步执行失败的事件，还可以跟上catch：

```javascript
promise .then(function(value){
  // 异步执行成功后的回调
}).catch(function(error){
  // 异步执行失败后的回调
})
```

## 8 set和map

ES6提供了Set和Map的数据结构。

1. Set，本质与数组类似。不同在于Set中只能保存不同元素，如果元素相同会被忽略。和java中的Set集合非常相似。

- 构造函数

  ```javascript
  // Set 构造函数可以接收一个数组或空
  let set = new Set();
  set.add(1);// [1]
  // 接收数组
  let set2 = new Set([2,3,4,5,5]);// 得到[2,3,4,5]
  ```

- 方法:

  ```javascript
  set.add(1);//  添加
  set.clear();// 清空
  set.delete(2);// 删除指定元素
  set.has(2); // 判断是否存在
  set.forEach(function(){})//遍历元素
  set.size; // 元素个数。是属性，不是方法。
  ```

2. map，本质是与Object类似的结构。不同在于，Object强制规定key只能是字符串。而Map结构的key可以是任意对象。即：

- object 是 <string,object>集合
- map 是<object,object>集合

- 构造函数:

  ```java
  // map 接收一个数组，数组中的元素是键值对数组
  const map = new Map([
   ['key1','value1'],
   ['key2','value2'],
  ])
  // 或者接收一个set
  const set = new Set([
   ['key1','value1'],
   ['key2','value2'],
  ])
  const map2 = new Map(set)
  // 或者其它map
  const map3 = new Map(map);
  ```

- 方法:

  ```javascript
  map .set(key, value);// 添加
  map.clear();// 清空
  map.delete(key);// 删除指定元素
  map.has(key); // 判断是否存在
  map.forEach(function(key,value){})//遍历元素
  map.size; // 元素个数。是属性，不是方法
  map.values() //获取value的迭代器
  map.keys() //获取key的迭代器
  map.entries() //获取entry的迭代器
  用法：
  for (let key of map.keys()) {
  console.log(key);
  }
  或：
  console.log(...map.values()); //通过扩展运算符进行展开
  ```

## 9 class（类）的基本语法

JavaScript 语言的传统方法是通过构造函数定义井生成新对象。ES6中引入了class的概念，通过class关键字自定义
类。

基本用法：

```javascript
< script>
    class User{
        constructor(name, age = 20){ // 构造方法
            this.name = name; // 添加属性并且赋值
            this.age = age;
        }
        sayHello(){ // 定义方法
            return "hello";
        }
        static isAdult(age){ //静态方法
            if(age >= 18){
                return "成年人";
            }
            return "未成年人";
        }
    }
    class ZhangSan extends User{//类的继承
        constructor(){
            super("张三", 30); //如果父类中的构造方法有参数，那么子类必须通过super调用父类的构造
            方法
            this.address = "上海";//设置子类中的属性，位置必须处于super下面
        }
    }
let user = new User("张三");
// 测试
console.log(user); // User {name: "张三", age: 20}
console.log(user.sayHello()); // hello
console.log(User.isAdult(20)); // 成年人
</script>
```

## 10 Generator函数

- Generator 函数是 ES6 提供的 一种异步编程解决方案，语法行为与传统函数完全不同 。

- Generator函数有两个特征: 一是 function命令与函数名 之间有一个星号: 二是 函数体内部使用 yield吾句定义不同的内部状态。

  ```javascript
  < script>
    function* hello () {
      yield "hello";
      yield "world";
      return "done";
   }
    let h = hello();
   
    console.log(h.next()); //{value: "hello", done: false}
    console.log(h.next()); //{value: "world", done: false}
    console.log(h.next()); //{value: "done", done: true}
    console.log(h.next()); //{value: undefined, done: true}
  </script>
  ```

  可以看到，通过hello()返回的h对象，每调用一次next()方法返回一个对象，该对象包含了value值和done状态。直到
  遇到return关键字或者函数执行完毕，这个时候返回的状态为ture，表示已经执行结束了。

 **for...of循环**

通过for...of可以循环遍历Generator函数返回的迭代器。

```javascript
< script>
    function* hello () {
    yield "hello";
    yield "world";
    return "done";
}
let h = hello();
for (let obj of h) {
    console.log(obj);
}
< /script>
// 输出：
hello
world
```

## 11 修饰器(Decorator)

修饰器(Decorator)是一个函数， 用来修改类的行为。 ES2017 引入了这项功能， 目前 Babel 转码器己经支持。

```javascript
< script>
    @T //通过@符号进行引用该方法，类似java中的注解
class User {
    constructor(name, age = 20){
        this.name = name;
        this.age = age;
    }
}

function T(target) { //定义一个普通的方法
    console.log(target); //target对象为修饰的目标对象，这里是User对象
    target.country = "中国"; //为User类添加一个静态属性country
}
console.log(User.country); //打印出country属性值
</script>
```

运行报错：原因是，在 ES6中，并没有支持该用法，在ES2017中才有，所以我们不能直接运行了，需要进行编码后再运行。转码的意思是：将ES6或ES2017转为ES5执行。类似这样：

```javascript
// 转码前
input .map(item =>item + 1);
//转码后
input.map(function (item) {
  return item + 1;
})
```

## 12 转码器

- Babel (babeljs.io) 是一个广为使用的 ES6 转码器，可以将 ES6 代码转为 ES5 代码，从而 在浏览器或其他环境执行 。
- Google  公司的 Traceur 转码器 Cgithub.com/google/traceur-compiler)， 也可 以将 ES6 代码转为ES5的代
  码。

## 13 模块化

模块化就是把代码进行拆分，方便重复利用。类似java中的导包：要使用一个包，必须先导包。而JS中没有包的概念，换来的是 模块。

模块功能主要由两个命令构成： export 和 import 。

- export 命令用于规定模块的对外接口，
- import 命令用于导入其他模块提供的功能。

使用：

- 定义一个utils.js

  ```javascript
  class Util {
      static sum = (a, b) => a + b;
  }
  //导出该类
  export default Util;	
  ```

- 使用 export 命令定义了模块的对外接口以后，其他 JS 文件就可以通过 import 命令加载这个模块。
  ```javascript
  /Index.js
  //导入Util类
  import Util from './Util'
  //使用Util中的sum方法
  console.log(Util.sum(1, 2));
  ```

  

