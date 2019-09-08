# JavaScript基础和JQuery

# 主要内容：

-  JavaScript
- JQuery

## 一 JavaScript ##

JavaScript：是基于事件和对象的解释性脚本语言

**Java和JavaScript**

JavaScript 和 Java 有一些共性但是在另一些方面有着根本性区别，。ava是一种强类型和静态类型的语言，基于类系统。JavaScript只有少量的数据类型，编程语言相对自由，不在在意声明变量，类和对象，也不必关系私有公有，可以动态添加对象。JavaScript在语法上类似Java，也有很多通用的地方。

### 1.1 声明

JavaScript有三种声明变量的方式

```javascript
var
//声明一个变量，可选初始化一个值。
let
//声明一个块作用域的局部变量，可选初始化一个值。
const
//声明一个块作用域的只读常量。
```

#### 1.1.1 变量

变量又称标识符，JavaScript 标识符必须以字母、下划线（_）或者美元符号（$）开头；后续的字符也可以是数字（0-9）。在Java中数字和$不能开头

#### 1.1.2 声明变量

你可以用以下三种方式声明变量：

- 使用关键词 var 。例如 var x = 42。这个语法可以用来声明局部变量和全局变量。
- 直接赋值。例如x = 42。在函数外使用这种形式赋值，会产生一个全局变量。在严格模式下会产生错误。因此你不应该使用这种方式来声明变量。
- 使用关键词 let 。例如 let y = 13。这个语法可以用来声明块作用域的局部变量。

#### 1.1.3 变量求值

用 var 或 let 语句声明的变量，如果没有赋初始值，则其值为 undefined 。

如果访问一个未声明的变量会导致抛出一个引用错误（ReferenceError）异常：

你可以使用 undefined 来判断一个变量是否已赋值。在以下的代码中，变量input未被赋值，因此 if 条件语句的求值结果是 true 。

- undefined 值在布尔类型环境中会被当作 false 

- 数值类型环境中 undefined 值会被转换为 NaN

- 当你对一个 null 变量求值时，空值 null 在数值类型环境中会被当作0来对待，而布尔类型环境中会被当作 false。

```javascript
		例如：
		var n = null;
		console.log(n * 32); // 在控制台中会显示 0
```

#### 1.1.4 变量作用域

在函数之外声明的变量，叫做全局变量，因为它可被当前文档中的任何其他代码所访问。在函数内部声明的变量，叫做局部变量，因为它只能在当前函数的内部访问。

```javascript
if (true) {
  var x = 5;
}
console.log(x); // 5

//如果使用 ECMAScript 6 中的 let 声明，上述行为将发生变化。ES6之前没有语句块。

if (true) {
  let y = 5;
}
console.log(y); // ReferenceError: y 没有被声明
```

全局变量是全局对象的属性，在网页中，（译注：缺省的）全局对象是 window ，所以你可以用形如 window.variable 的语法来设置和访问全局变量

#### 1.1.5 变量提升：

JavaScript 变量的另一个不同寻常的地方是，你可以先使用变量稍后再声明变量而不会引发异常。这一概念称为变量提升；JavaScript 变量感觉上是被“提升”或移到了函数或语句的最前面。但是，提升后的变量将返回 undefined 值。因此在使用或引用某个变量之后进行声明和初始化操作，这个被提升的变量仍将返回 undefined 值。

ES6中使用,let（const）将不会提升变量到代码块的顶部

```javascript
console.log(x); // ReferenceError
let x = 3;
```

#### 1.1.6 函数提升：

对于函数来说，只有函数声明会被提升到顶部，而函数表达式不会被提升。

```javascript
/* 函数声明 */

foo(); // "bar"

function foo() {
  console.log("bar");
}
```

```javascript
/* 函数表达式 */

baz(); // 类型错误：baz 不是一个函数

var baz = function() {
  console.log("bar2");
};
```

6、常量

```javascript
const PI = 3.14;
const #修饰的量表示常量，所谓常量就是被初始化，且不能修改的量 
```

### 1.2 数据结构和类型 ###

最新的 ECMAScript 标准定义了7种数据类型：

#### 1.2.1 六种基本数据类型:

```javascript
布尔值（Boolean），//有2个值分别是：true 和 false.

null ，// 一个表明 null 值的特殊关键字。 JavaScript 是大小写敏感的，因此 null 与 Null、NULL或变体完全不同。

undefined ，//和 null 一样是一个特殊的关键字，undefined 表示变量未定义时的属性。

数字（Number），//整数或浮点数，例如： 42 或者 3.14159。

字符串（String），//字符串是一串表示文本值的字符序列，例如："Howdy" 。

代表（Symbol） //( 在 ECMAScript 6 中新添加的类型).。一种实例是唯一且不可改变的数据类型。

以及对象（Object）
```

#### 1.2.2 数据类型转换 ####

JavaScript是一种动态语言，这意味着你不必声明声明变量指定的类型，而数据类型会在代码执行时根据需要自动转换。

**字符串转数字：**

```javascript
parseInt(); //只返回整数部分
parsefloat();

而数字转字符则使用字符连接符: +
```

**字面量:**

字面量是由语法表达式定义的常量；或通过一定字词组成的词语表达式定义的常量	

### 1.3 流程控制和错误处理 ###

#### 1.3.1 语句块

```javascript
{ }内的语句
```

#### 1.3.2 条件判断语句	

```javascript
if(){}else{}
if(){}else if(){}...else{}
```

False 等效值

下面这些值将被计算出 false (also known as Falsy values):

- false
- undefined
- null
- 0
- NaN
- 空字符串（""）

#### 1.3.3 switch语句

```javascript
switch(){
	case "":
		语句
		break;
	...
	default:
		语句
}
```

#### 1.3.4 异常错误处理

JavaScript可以抛出任意异常：

```javascript
throw Exception;

try{
	...
}catch(){
	...
}finally{
	...
}
```

使用throw语句抛出一个异常。当你抛出异常，你规定一个含有值的表达式要被抛出。

#### 1.3.5 Error对象

在抛出你个人所为的异常时，为了充分利用那些属性（比如你的catch块不能分辨是你个人所为的异常还是系统的异常时），你可以使用 Error 构造函数

### 1.4 Promise ###

ES新特性了增加了对Promise对象的支持，它允许你异步和延时操作控制语句

Promise 对象有以下几种状态：

- pending：初始的状态，即正在执行，不处于 fulfilled 或 rejected 状态。
- fulfilled：成功的完成了操作。
- rejected：失败，没有完成操作。
- settled：Promise 处于 fulfilled 或 rejected 二者中的任意一个状态, 不会是 pending

![](https://i.imgur.com/z0dvJpo.png)

示例：使用XMLHTTPRequest和Promise对象加载图片

```javascript
function imgLoad(url) {
  return new Promise(function(resolve, reject) {//Promise对象
    var request = new XMLHttpRequest();//XHR对象
    request.open('GET', url);//请求
    request.responseType = 'blob';//响应类型
    request.onload = function() {
      if (request.status === 200) {//状态判断
        resolve(request.response);
      } else {
        reject(Error('Image didn\'t load successfully; error code:' 
                     + request.statusText));
      }
    };
    request.onerror = function() {
      reject(Error('There was a network error.'));
    };
    request.send();//执行send()
  });
}
```

[深入了解Promise对象](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Promise)


### 1.5 循环和迭代 ###

JavaScript中提供了这些循环语句：

- for 语句
- do...while 语句
- while 语句
- labeled 语句
- break 语句
- continue 语句
- for...in 语句

这个 for...in 语句循环一个指定的变量来循环一个对象所有可枚举的属性。JavaScript 会为每一个不同的属性执行指定的语句。

```javascript
for (variable in object) {
  statements
}
```

- for...of 语句

for...of语句在可迭代的对象上创建了一个循环(包括Array, Map, Set, 参数对象（ arguments） 等等) ，对值的每一个独特的属性调用一个将被执行的自定义的和语句挂钩的迭代。

```javascript
for (variable of object) {
  statement
}
```


### 1.6 函数 ###

一个函数定义（也称为函数声明，或函数语句）由一系列的function关键字组成，依次为：

- 函数的名称。
- 函数参数列表，包围在括号中并由逗号分隔。
- 定义函数的 JavaScript 语句，用大括号{}括起来。


	function square(number) {
	  return number * number;
	}

#### 1.6.1 函数表达式 ####

函数也可以由函数表达式创建。这样的函数可以是匿名的；它不必有一个名称。例如：

	var square = function(number) { return number * number; };
	
	var x = square(4); // x gets the value 16

然而，函数表达式也可以提供函数名，并且可以用于在函数内部代指其本身，或者在调试器堆栈跟踪中识别该函数：
	
	var factorial = function fac(n) {return n<2 ? 1 : n*fac(n-1)};
	
	console.log(factorial(3));

当将函数作为参数传递给另一个函数时，函数表达式很方便

可以根据条件来定义一个函数：

	var myFunc;
	if (num == 0){
	  myFunc = function(theObject) {
	    theObject.make = "Toyota"
	  }
	}

当一个函数是一个对象的属性时，称之为方法[了解更多关于对象和方法的知识](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Guide/Working_with_Objects)

**函数的调用：**

square函数的调用方式：

	square(5);

#### 1.6.2 函数的作用域 ####

在函数内定义的变量不能在函数之外的任何地方访问，因为变量仅仅在该函数的域的内部有定义。相对应的，一个函数可以访问定义在其范围内的任何变量和函数。换言之，定义在全局域中的函数可以访问所有定义在全局域中的变量。在另一个函数中定义的函数也可以访问在其父函数中定义的所有变量和父函数有权访问的任何其他变量。

#### 1.6.3 作用域和函数堆栈 ####

**1、递归-自己调用自己**

一个函数可以指向并调用自身。有三种方法可以达到这个目的：

- 函数名
- arguments.callee
- 作用域下的一个指向该函数的变量名

例如：思考一下函数的定义

	var foo = function bar() {
	   // statements go here
	};
	
	在这个函数体内，以下的语句是等价的：
	
	bar()
	arguments.callee()
	foo()


**2、嵌套函数和闭包**

你可以在一个函数里面嵌套另外一个函数。嵌套（内部）函数对其容器（外部）函数是私有的。它自身也形成了一个闭包。一个闭包是一个可以自己拥有独立的环境与变量的的表达式（通常是函数）。

既然嵌套函数是一个闭包，就意味着一个嵌套函数可以”继承“容器函数的参数和变量。换句话说，内部函数包含外部函数的作用域。

**3、多层嵌套函数**

函数可以被多层嵌套	

**4、命名冲突**

当同一个闭包作用域下两个参数或者变量同名时，就会产生命名冲突。更近的作用域有更高的优先权，所以最近的优先级最高，最远的优先级最低。这就是作用域链。链的第一个元素就是最里面的作用域，最后一个元素便是最外层的作用域

#### 1.6.4  闭包

闭包是 JavaScript 中最强大的特性之一。JavaScript 允许函数嵌套，并且内部函数可以访问定义在外部函数中的所有变量和函数，以及外部函数能访问的所有变量和函数。但是，外部函数却不能够访问定义在内部函数中的变量和函数。这给内部函数的变量提供了一定的安全性。此外，由于内部函数可以访问外部函数的作用域，因此当内部函数生存周期大于外部函数时，外部函数中定义的变量和函数将的生存周期比内部函数执行时间长。当内部函数以某一种方式被任何一个外部函数作用域访问时，一个闭包就产生了。

#### 1.6.5 arguments对象 ####

函数的实际参数会被保存在一个类似数组的arguments对象中

	arguments[i]

#### 1.6.6 函数参数 ####

从ECMAScript 6开始，有两个新的类型的参数：默认参数，剩余参数。

在JavaScript中，函数参数的默认值是undefined

剩余参数语法允许将不确定数量的参数表示为数组

#### 1.6.7 箭头函数 ####

箭头函数表达式（也称胖箭头函数）相比函数表达式具有较短的语法并以词法的方式绑定 this。箭头函数总是匿名的。

**深入了解javaScript ES6的箭头函数和解构赋值**

```javascript
var a = [
  "Hydrogen",
  "Helium",
  "Lithium",
  "Beryllium"
];

var a2 = a.map(function(s){ return s.length });

console.log(a2); // logs [ 8, 6, 7, 9 ]

var a3 = a.map( s => s.length );

console.log(a3); // logs [ 8, 6, 7, 9 ]
```

#### 1.6.8 预定义函数 ####

- eval()方法会对一串字符串形式的JavaScript代码字符求值。
- uneval()方法创建的一个Object的源代码的字符串表示。
- isFinite()函数判断传入的值是否是有限的数值。 如果需要的话，其参数首先被转换为一个数值。
- isNaN()函数判断一个值是否是NaN。注意：isNaN函数内部的强制转换规则十分有趣； 另一个可供选择的是ECMAScript 6 中定义Number.isNaN() , 或者使用 typeof来判断数值类型。
- parseFloat() 函数解析字符串参数，并返回一个浮点数。
- parseInt() 函数解析字符串参数，并返回指定的基数（基础数学中的数制）的整数。
- decodeURI() 函数对先前经过encodeURI函数或者其他类似方法编码过的字符串进行解码。
- decodeURIComponent()方法对先前经过encodeURIComponent函数或者其他类似方法编码过的字符串进行解码。
- encodeURI()方法通过用以一个，两个，三个或四个转义序列表示字符的UTF-8编码替换统一资源标识符（URI）的某些字符来进行编码（每个字符对应四个转义序列，这四个序列组了两个”替代“字符）。
- encodeURIComponent() 方法通过用以一个，两个，三个或四个转义序列表示字符的UTF-8编码替换统一资源标识符（URI）的每个字符来进行编码（每个字符对应四个转义序列，这四个序列组了两个”替代“字符）。

### 1.7 表达式和运算符 ###

#### 1.7.1 表达式

this关键字被用于指代当前的对象，通常，this指代的是方法中正在被调用的对象

new关键字 你可以使用new operator 创建一个自定义类型或者是预置类型的对象实例

#### 1.7.2 运算符

- 赋值运算符(Assignment operators)

		- 对于更复杂的赋值，解构赋值语法是一个能从数组或对象对应的数组结构或对象字面量里提取数	
	
	```javascript
	var foo = ["one", "two", "three"];	
	// 不使用解构
	var one   = foo[0];
	var two   = foo[1];
	var three = foo[2];
	
	// 使用解构
	var [one, two, three] = foo;	
	```
	
- 比较运算符(Comparison operators)
	
		- == 两边操作数相等
	
	- 严格相等（两边参数相等且类型相同） Strict equal (===)
	
- 算数运算符(Arithmetic operators)（同）

- 位运算符(Bitwise operators)

		```javascript
	按位与 AND	-> a & b	//在a,b的位表示中，每一个对应的位都为1则返回1， 否则返回0.
	
	按位或 OR ->	a | b	//在a,b的位表示中，每一个对应的位，只要有一个为1则返回1， 否则返回0.
	
	按位异或 XOR ->	a ^ b	//在a,b的位表示中，每一个对应的位，两个不相同则返回1，相同则返回0.
	
	按位非 NOT ->	~ a	//反转被操作数的位。
	
	左移 shift ->	a << b	//将a的二进制串向左移动b位,右边移入0.
	
	算术右移 ->	a >> b	//把a的二进制表示向右移动b位，丢弃被移出的所有位.(译注:算术右移左边空出的位是根据
	最高位是0和1来进行填充的)
	
	无符号右移 (左边空出位用0填充) -> a >>> b	//把a的二进制表示向右移动b位，丢弃被移出的所有位，并把左边空出的位都填充为0
	```

- 逻辑运算符(Logical operators)（同）

- 字符串运算符(String operators)（+）

- 条件（三元）运算符(Conditional operator)（同）

- 一元操作符(Unary operators)（一元操作符只对应一个操作数）

		-  delete操作符，删除一个对象或一个对象的属性或者一个数组中某一个键值
	- typeof 操作符返回一个表示 operand 类型的字符串值。operand 可为字符串、变量、关键词或对象，其类型将被返回	
	- void运算符,表明一个运算没有返回值 <a href="javascript:void(0)">Click here to do nothing</a>

- 关系操作符(Relational operator): 关系操作符对操作数进行比较，根据比较结果真或假，返回相应的布尔值。

		in操作符，如果所指定的属性确实存在于所指定的对象中，则会返回true
	
	```javascript
propNameOrNumber in objectName
	
	在这里 propNameOrNumber可以是一个代表着属性名的字符串或者是一个代表着数组索引的数值表达式，而objectName则是一个对象名
	
	// Arrays
	var trees = new Array("redwood", "bay", "cedar", "oak", "maple");
	0 in trees;        // returns true
	3 in trees;        // returns true
	6 in trees;        // returns false
	"bay" in trees;    // returns false (you must specify the index number,
	// not the value at that index)
	"length" in trees; // returns true (length is an Array property)
	
	// Predefined objects
	"PI" in Math;          // returns true
	var myString = new String("coral");
	"length" in myString;  // returns true
	
	// Custom objects
	var mycar = {make: "Honda", model: "Accord", year: 1998};
	"make" in mycar;  // returns true
	"model" in mycar; // returns true
	instanceof 如果所判别的对象确实是所指定的类型，则返回true
	
	var theDay = new Date(1995, 12, 17);
	if (theDay instanceof Date) {
	    // statements to execute
	}
	```

- 运算符优先级


### 1.8 数字和日期 ###

#### 1.8.1 数字对象number ####

```javascript
Number.parseFloat()	//把字符串参数解析成浮点数，和全局方法 parseFloat() 作用一致.

Number.parseInt()	//把字符串解析成特定基数对应的整型数字，和全局方法 parseInt() 作用一致.

Number.isFinite()	//判断传递的值是否为有限数字。

Number.isInteger()	//判断传递的值是否为整数。

Number.isNaN()	//判断传递的值是否为 NaN. More robust version of the original global isNaN().

Number.isSafeInteger()	//判断传递的值是否为安全整数。
```


- 转换数字: 

	    var  整型数字 = parseInt(字符串);
	    var 整型|浮点型 = new Number(字符串);

- 截取数字:

    	number.toFixed(小数点后保留位数);

#### 1.8.2 数学对象Math ####

```javascript
floor(), ceil()	//返回最大/最小整数小于/大于或等于参数
```

- 随机数:
  

		Math.random();  产生一个0-1的随机浮点型数字

- 四舍五入:

        Math.round(x);  将浮点数字 , 四舍五入为整型数字

#### 1.8.3 日期对象 ####

```java
var dateObjectName = new Date([parameters]);
```


```javascript
var today = new Date();

var endYear = new Date(1995, 11, 31, 23, 59, 59, 999); // 设置日和月，注意，月份是0-11

endYear.setFullYear(today.getFullYear()); // 把年设置为今年

var msPerDay = 24 * 60 * 60 * 1000; // 每天的毫秒数

var daysLeft = (endYear.getTime() - today.getTime()) / msPerDay;

var daysLeft = Math.round(daysLeft); //返回今年剩下的天数
```

### 1.9 字符串 ###

```javascript
charAt, charCodeAt, codePointAt	//返回字符串指定位置的字符或者字符编码。

indexOf, lastIndexOf	//分别返回字符串中指定子串的位置或最后位置。

startsWith, endsWith, includes	//返回字符串是否以指定字符串开始、结束或包含指定字符串。

concat	//连接两个字符串并返回新的字符串。

fromCharCode, fromCodePoint	//从指定的Unicode值序列构造一个字符串。这是一个String类方法，不是实例方法。

split	//通过将字符串分离成一个个子串来把一个String对象分裂到一个字符串数组中。

slice	//从一个字符串提取片段并作为新字符串返回。

substring, substr	//分别通过指定起始和结束位置，起始位置和长度来返回字符串的指定子集。

match, replace, search	//通过正则表达式来工作.

toLowerCase, toUpperCase	//分别返回字符串的小写表示和大写表示。

normalize	//按照指定的一种 Unicode 正规形式将当前字符串正规化。

repeat	//将字符串内容重复指定次数后返回。

trim	//去掉字符串开头和结尾的空白字符。
```

### 1.10 国际化 ###

**1、日期和时间格式化**

DateTimeFormat 对象在日期和时间的格式化方面很有用

**2、数字格式化**

NumberFormat 对象在数字的格式化方面很有用, 比如货币数量值.

### 1.11 正则表达式 ###

正则表达式可以被用于RegExp的exec和test方法以及 String的match、replace、search和split方法。
```javascript
exec	//一个在字符串中执行查找匹配的RegExp方法，它返回一个数组（未匹配到则返回null）。

test	//一个在字符串中测试是否匹配的RegExp方法，它返回true或false。

match	//一个在字符串中执行查找匹配的String方法，它返回一个数组或者在未匹配到时返回null。

search	//一个在字符串中测试匹配的String方法，它返回匹配到的位置索引，或者在失败时返回-1。

replace	//一个在字符串中执行查找匹配的String方法，并且使用替换字符串替换掉匹配到的子字符串。

split	//一个使用正则表达式或者一个固定字符串分隔一个字符串，并将分隔后的子字符串存储到数组中的String方法
```

步骤: 
    1.  创建正则表达式对象
        var 对象名 = /正则表达式字符串/g;
    2.  通过正则表达式对象的test方法, 验证数据是否匹配正则
        var flag = 正则对象.test(要验证的数据); 返回值为boolean类型

```javascript
案例: 
function testx(val){
    var reg = /^(13[0-9]|14[4789]|15[012356789]|17[0-9]|18[0-9])[0-9]{8}$/g;
    var flag = reg.test(val);
    if(flag){
        alert("用户名可用");
    }else{
        alert("很遗憾, 用户名不可用");
    }
}
```


### 1.12 数组对象 ###

**1、创建数组**
创建数组的方式：

```javascript
var arr = new Array(element0, element1, ..., elementN);
var arr = Array(element0, element1, ..., elementN);
var arr = [element0, element1, ..., elementN];

var arr = new Array(arrayLength);
var arr = Array(arrayLength);
```

**2、遍历数组**

```javascript
var colors = ['red', 'green', 'blue'];
for (var i = 0; i < colors.length; i++) {
  console.log(colors[i]);
}
```

foreach()方法：

```javascript
var colors = ['red', 'green', 'blue'];
colors.forEach(function(color) {
  console.log(color);
});
```

**3、数组的方法**

- concat() 连接两个数组并返回一个新的数组。

- join(deliminator = ',') 将数组的所有元素连接成一个字符串。
- push() 在数组末尾添加一个或多个元素，并返回数组操作后的长度。
- pop() 从数组移出最后一个元素，并返回该元素。
- shift() 从数组移出第一个元素，并返回该元素。
- sort() 给数组元素排序。
- indexOf(searchElement[, fromIndex]) 在数组中搜索searchElement 并返回第一个匹配的索引。
- lastIndexOf(searchElement[, fromIndex]) 和 indexOf 差不多，但这是从结尾开始，并且是反向搜索。
- forEach(callback[, thisObject]) 在数组每个元素项上执行callback。
- map(callback[, thisObject]) 在数组的每个单元项上执行callback函数，并把返回包含回调函数返回值的新数组（译者注：也就是遍历数组，并通过callback对数组元素进行操作，并将所有操作结果放入数组中并返回该数组）。
- filter(callback[, thisObject]) 返回一个包含所有在回调函数上返回为true的元素的新数组（译者注：callback在这里担任的是过滤器的角色，当元素符合条件，过滤器就返回true，而filter则会返回所有符合过滤条件的元素）。

### 1.13 集合 ###

**1、map对象**

你可以使用for...of循环来得到所有的[key, value]。

**2、set对象**

Set对象是一组值的集合，这些值是不重复的，可以按照添加顺序来遍历。

### 1.14 对象 ###

JavaScript每创建一个对象就会设置一个原型，指向它的原型对象

#### 1.14.1 对象初始化器 ####

除了构造函数容器创建对象之外，你也可以通过对象初始化容器创建对象

创建对象的语法如下：JSON对象的方式

```javascript
var obj = { property_1:   value_1,   // property_# 可以是一个标识符...
            2:            value_2,   // 或一个数字...
           ["property" +3]: value_3,  //  或一个可计算的key名... 
            // ...,
            "property n": value_n }; // 或一个字符串
```

#### 1.14.2 使用构造函数 ####

作为另一种方式，你可以通过两步来创建对象：

1. 通过创建一个构造函数来定义对象的类型。首字母大写是非常普遍而且很恰当的惯用法。
2. 通过 new 创建对象实例。

为了定义对象类型，为对象类型创建一个函数以声明类型的名称、属性和方法。
例如：创建一个Car对象，并且拥有make、model和year属性

```javascript
function Car(make,model,year){
	this.make=make;
	this.model=model;
	this.year=year;//使用this将传入函数的值赋值给对象的属性。
}

var myCar = new Car("china","china model",1983);
```

#### 1.14.3 使用Object.create方法创建 ####

对象也可以使用Object.create()方法创建。该方法实用之处在于它可以为创建的对象选择原型对象。而不用定义一个构造函数。
```javascript
// 使用初始化器创建一个Animal对象
var Animal = {
  type: "Invertebrates", // 设置属性的默认值
  displayType : function() {  // 创建一个displayType方法
    console.log(this.type);
  }
}

// 创建一个新对象指向Animal 
var animal1 = Object.create(Animal);
animal1.displayType(); // Output:Invertebrates

// 创建一个新对象改写type属性
var fish = Object.create(Animal);
fish.type = "Fishes";
fish.displayType(); // 输出Fishes
```

#### 1.14.4 继承 ####

所有的 JavaScript 对象继承于至少一个对象。被继承的对象被称作原型，并且继承的属性可通过构造函数的 prototype 对象找到

#### 1.14.5 为对象类型定义属性

你可以通过 prototype 属性为之前定义的对象类型增加属性。这为该类型的所有对象，而不是仅仅一个对象增加了一个属性。下面的代码为所有类型为 car 的对象增加了 color 属性，然后为对象 car1 的 color 属性赋值：

```javascript
Car.prototype.color=null;
car1.color = "block";
```

**1、定义方法**

	objectName.methodname = function_name;
	
	var myObj = {
	  myMethod: function(params) {
	    // ...do something
	  }
	  
	  // 或者 这样写也可以
	  
	  myOtherMethod(params) {
	    // ...do something else
	  }
	};

**2、this关键字**

类似于Java中this关键字主要指向的是本类的方法和属性

**3、定义getters和setters**

一个 getter 是一个获取某个特定属性的值的方法。一个  setter 是一个设定某个属性的值的方法。你可以为预定义的或用户定义的对象定义 getter 和 setter 以支持新增的属性。定义 getter 和 setter 的语法采用对象字面量语法。

可以在创建对象时，为属性设置set和get方法，只需要在set和get设置即可。

**4、删除属性**

使用delete删除一个不是定义而来的属性

### 1.15 对象模型细节 ###

#### 1.15.1 基于类的Java语言和基于原型的JavaScript对比 ####

|基于类的（Java）|	基于原型的（JavaScript）|
|-----|-----|
|类和实例是不同的事物。	|所有对象均为实例。|
|通过类定义来定义类；通过构造器方法来实例化类。|	通过构造器函数来定义和创建一组对象。|
|通过 new 操作符创建单个对象。	|相同。|
|通过类定义来定义现存类的子类，从而构建对象的层级结构。|	指定一个对象作为原型并且与构造函数一起构建对象的层级结构|
|遵循类链继承属性。|	遵循原型链继承属性。|
|类定义指定类的所有实例的所有属性。无法在运行时动态添加属性。|	构造器函数或原型指定初始的属性集。允许动态地向单个的对象或者整个对象集中添加或移除属性。|

#### 1.15.2 Employee层级结构（类似于Java中多层继承） ####

![](https://i.imgur.com/D6FGpgI.png)

JavaScript中不支持多重继承

1、简单的层级结构示例

2、灵活的层级结构示例

3、继承属性

[参照对象那个模型和细节](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Guide/Details_of_the_Object_Model)

此处代码较多内容较简单，建议使用编辑器编写实际代码示例

## 二 JQuery

![](https://i.imgur.com/rw261ct.png)
### Jquery的onload实现 ### 

	$(function(){
	    //当网页加载完毕时执行
	});

### 选择器 ###
#### 基本选择器 ####

-   id选择器

    	格式: 
    	var $obj = $("#id值");
-   类选择器

    	格式: 
    	var $obj = $(".class值");
-   标签名称选择器

    	格式: 
    	var $obj = $("标签名称");
    
#### 层级选择器 ####

-   子选择器        *
    	格式: 
        var $obj =$("父元素选择器>子选择器");
-   后代选择器   *
    	格式: 
        var $obj =$("父元素选择器 后代选择器");
-   后一个兄弟选择器
    	格式: 
        var $obj =$("选择器+兄弟选择器");   
-   后所有兄弟选择器
    	格式: 
        var $obj =$("选择器~兄弟选择器");
        
#### 筛选选择器 ####

: first:匹配第一个元素     
: last:匹配最后一个元素  
: not:过滤not条件:$(“div:not(.class)”);
: even:匹配下标偶数        
: odd:匹配下标奇数     
: eq:选择指定索引值 :$(“div:eq(1)”)
: gt:匹配索引值大于x的:$(“div:gt(5)”)    
: lt:匹配索引值小于x的:$(“div:lt(5)”)
		示例: 
  	  $(function(){
	        $("p:first").css({"color":"#090"});
	        $("p:not(#a)").css({"font-size":"10px"});
	        $("p:gt(2)").css({"font-size":"16px"});
	
	    });
### jQuery常用函数 ###
#### 获取和修改元素文本内容 ####

在原生JS的操作中, 我们使用元素对象的inenrHTML属性 来修改元素的文本内容. 

在Jquery中, 提供了两个方法,  可以用来获取/设置文本内容


	1.  $obj.html(文本) | var 文本 = $obj.html();
	    获取元素文档内容
	
	2.  var 获取文本 = $obj.text()
	    获取元素文本内容

html函数与text函数区别: 

	使用html函数获取文本时, 会获取到子标签 
	使用text函数获取文本时, 只能获取到不包含标签的文本内容

#### 获取和修改元素的属性值 ####

在JS代码中, 我们可以通过  dom对象.属性名 来操作一个元素的属性 !
在Jquery中可以通过attr函数 操作所有的属性: 
获取属性值:

   	var  value = $obj.attr("属性名");
设置属性值:

    $obj.attr("属性名","属性值");

删除属性值:

    $obj.removeAttr("属性名");
#### 修改元素样式 ####

css函数 两个方法的重载: 
1.  用于一次指定多个样式

  	 格式:  $obj.css(json格式的样式对象);
2.  用于一次指定一个样式

		 格式:  $obj.css("样式key","样式值");
	    案例: 
	    $("img").css("box-shadow","3px 3px 3px 3px #aaa");
	    
#### calss属性值的设置与获取 ####

添加class属性值
		$obj.addClass("值");
删除class属性值
		$obj.removeClass("值");
替换class属性值
		$obj.toggleClass("值");

#### 显示和隐藏函数 ####

- 基本效果

	    显示: $obj.show();
	    隐藏: $obj.hide();
	    切换: $obj.toggle();
- 左上折叠效果

	    显示: $obj.show(time毫秒);
	    隐藏: $obj.hide(time毫秒);
	    切换: $obj.toggle(time毫秒);
- 上下折叠效果

	    显示: $obj.slideDown(time毫秒);
	    隐藏: $obj.slideUp(time毫秒);
	    切换: $obj.slideToggle(time毫秒);
	    如果使用到图片上, 元素必须存在确定的宽度 ,才可以实现上下折叠
- 淡入淡出效果

	    显示: $obj.fadeIn(time毫秒);
	    隐藏: $obj.fadeOut(time毫秒);

#### 自定义动画 ####
		$obj.animate(参数1,参数2,[参数3]);
		参数1.    JSON对象, 描述动画执行结果的样式
		参数2.    从当前样式, 过渡到参数1样式的 毫秒时间
		参数3.    可选参数, function类型 , 动画执行完毕的监听函数 (当动画执行完毕, 自动执行此函数)

### JQuery事件 ###
#### 绑定和解绑事件 ####

- 绑定事件: 
    	$obj.bind("事件类型",处理函数);
- 解绑事件:
    	$obj.unbind("事件类型");
- 模拟事件触发: 
    	$obj.trigger("事件类型"); 

#### 事件函数  ####   

	$obj.click(fn)// 当点击
	$obj.dblclick(fn)//当双击
	$obj.blur(fn)//当失去焦点
	$obj.focus(fn)//当获取焦点
	$obj.change(fn)//当状态改变 ,常用于下拉选框
	$obj.keydown(fn)//当键盘按下
	$obj.keyup(fn)//当键盘弹起
	$obj.mouseover(fn)//当鼠标指针位于元素上
	$obj.mouseout(fn)//当鼠标指针从元素上滑出
	$obj.submit(fn)//表单提交事件, 等同于form元素的onsubmit事件, return false 可以阻止表单提交    *****
	$obj.load(fn)//当元素加载完毕
	$obj.unload(fn)//当元素销毁
#### 组合事件 ####

	hover(over,out);
	参数1.    function类型 , 鼠标移入时触发的事件代码
	参数2.    function类型 , 鼠标移出时触发的事件代码

#### 动态绑定事件 ####

	格式: 
	注意: 添加动态绑定事件的未来元素, 必须有一个已经存在的父元素 !
	
	$父元素对象.on("事件类型","寻找子元素的选择器",func处理函数);
### 文档函数 ###

-  创建元素
	    var $obj = $("html元素");
	    例如:
	        创建一个按钮: 
	        var $obj = $('<input type="button" value="示例按钮">');
-  将Jquery元素对象, 添加到网页中
	-   通过父元素 向父元素内部追加
	    格式: $父元素.append($新元素);
	-   通过父元素 向父元素内部前置
	    格式: $父元素.prepend($新元素);
	-   通过一个元素, 向这个元素的后面添加
	    格式: $obj.after($新元素);
	-   通过一个元素, 想这个元素的前面添加
    格式: $obj.before($新元素);
-  从网页中 删除一个节点
	    格式: 
	    $要删除的元素.remove();
-  清空一个节点
    	格式: 
        $要清空的元素.empty();
-  克隆一个节点
	-   var $新对象 = $原对象.clone();    //克隆元素, 不克隆元素事件;
	-   var $新对象 = $原对象.clone(true);    //克隆元素, 克隆元素事件;

### 文档查找 ###

	children(选择器) 查找子元素
	next() 查找后面的兄弟元素 
	prev() 查找前面的兄弟元素
	siblings() 查找兄弟元素
	find(选择器) 查找子元素及后代元素
	parent() 查找父元素
	parents(selector) 查找祖先元素
### 工具函数 ###

**$.each ** 遍历

可应用于 遍历对象 , 遍历数组

-   遍历对象
        遍历Jquery对象, 得到其中的每一个dom对象   
        格式:
            $obj.each(function(){
                //在这里this 表示每次遍历的dom对象
            }); 
-   遍历数组
        遍历一个JS数组, 得到每一个下标的数据
            $.each(数组,function(i,value){
                //i表示遍历时的数据下标
                //value表示遍历时的数据
            });
            

去除数组中重复元素 (此函数直接操作原数组, 无返回值)

		格式: $.unique(数组);
合并数组:  将两个数组合并 ( 将第二个数组中的所有内容,
追加到第一个数组中)

		格式: $.merge(数组1,数组2);
去除字符串中的前后空格: (不会对原字符串产生影响, 返回值为去除空格的文本)

	    格式: 
	        $.trim(字符串);