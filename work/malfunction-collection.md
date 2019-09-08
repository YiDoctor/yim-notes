# Java故障收集处理手册

## 一 方法论

1. 故障现象
2. 导致原因
3. 解决方案
4. 优化建议

## 二 并发修改异常

 `ConcurrentModificationException`：并发修改异常

### 2.1 故障现象

`java.util.ConcurrentModificationException`

### 2.2 导致原因

多线程环境先，使用集合类，如`ArrayList`类

### 2.3  解决方案

1. 使用线程安全的集合类代替线程不安全的工具类，如：Vector替换`ArrayList`

2. 使用`Collections`集合工具类

   ```java
   List<String> strings = Collections.synchronizedList(new ArrayList<String>());
   ```

3. ```
   new CopyOnWriteArrayList<>();
   ```