# vuejs简单入门 #

## 主要内容

- VueJS入门
- 常用系统指令
- 生命周期
- VueJS AJAX
- 综合案例

## 一 VueJS 概述与快速入门

Vue.js是一个构建数据驱动的 web 界面的渐进式框架。Vue.js 的目标是通过尽可能简单的 API 实现响应的数据绑定和组合的视图组件。它不仅易于上手，还便于与第三方库或既有项目整合。

官网:https://cn.vuejs.org/

### 1.1 MVVM模式

MVVM是Model-View-ViewModel的简写。它本质上就是MVC 的改进版。MVVM 就是将其中的View 的状态和行为
抽象化，让我们将视图 UI 和业务逻辑分开

MVVM模式和MVC模式一样，主要目的是分离视图（View）和模型（Model）

Vue.js 是一个提供了 MVVM 风格的双向数据绑定的 Javascript 库，专注于View 层。它的核心是 MVVM 中的VM，

也就是 ViewModel。 ViewModel负责连接 View 和 Model，保证视图和数据的一致性，这种轻量级的架构让前端
开发更加高效、便捷

### 1.2 快速入门

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>vue快速入门</title>
<script src="js/vue/vue.min.js"></script>
</head>
<body>
    <div id="HelloVue">
        {{message}}
    </div>
    <script>
        new Vue({
            el:'#HelloVue', //表示当前vue对象接管了div区域
            data:{
                message:'hello world' //注意不要写分号结尾
            }
        });
    </script>
</body>
</html>
```

### 1.3 插值表达式

数据绑定最常见的形式就是使用“Mustache”语法 (双大括号) 的文本插值，Mustache 标签将会被替代为对应数据对象上属性的值。无论何时，绑定的数据对象上属性发生了改变，插值处的内容都会更新。

Vue.js 都提供了完全的 JavaScript 表达式支持。

```javascript
{{ number + 1 }}

{{ ok ? 'YES' : 'NO' }}
```

这些表达式会在所属 Vue 实例的数据作用域下作为 JavaScript 被解析。有个限制就是，每个绑定都只能包含单个
表达式，所以下面的例子都不会生效。

```javascript
<!-- 这是语句，不是表达式 -->
{{ var a = 1 }}
<!-- 流控制也不会生效，请使用三元表达式 -->
{{ if (ok) { return message } }}
```

## 二 VueJS 常用系统指令

### 2.1 v-on

可以用  v-on 指令监听 DOM 事件，并在触发时运行一些 JavaScript 代码

- v-on:click
- v-on:keydown
- v-on:mouseover
- ......

Vue.js 为 v-on 提供了事件修饰符来处理 DOM 事件细节，如：event.preventDefault() 或
event.stopPropagation()。

Vue.js通过由点(.)表示的指令后缀来调用修饰符。