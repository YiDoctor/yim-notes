# Java故障收集处理手册

**方法论**：

1. **故障现象**
2. **导致原因**
3. **解决方案**
4. **优化建议**

## 一 一般异常

### 1.1 类没有找到错误

`java.lang.NoClassDefFoundError:`

1. **故障现象**

   `java.lang.NoClassDefFoundError:具体的类`

   编译时可以加载的类，在运行时无法加载，JVM 抛出错误异常。

2. **导致原因**

   1. ClassPath下找不到对应的jar包
   2. Maven项目具体问题具体分析

   本次错误原因：微服务环境下使用`EnableFeignClients`注解，找不到导入其它依赖触发的异常。

3. **解决方案**

   [异常解决方法参考](https://blog.csdn.net/alinyua/article/details/80070890)

## 二 并发异常

###  2.1 并发修改异常

`ConcurrentModificationException`：并发修改异常

1. **故障现象**

   `java.util.ConcurrentModificationException`

2. **导致原因**

   多线程环境下，使用集合类，如`ArrayList`类

3. **解决方案**
   1. 使用线程安全的集合类代替线程不安全的工具类，如：Vector替换`ArrayList`

   2. 使用`Collections`集合工具类

   ```java
   List<String> strings = Collections.synchronizedList(new ArrayList<String>());
   ```

   3. `new CopyOnWriteArrayList<>();`
