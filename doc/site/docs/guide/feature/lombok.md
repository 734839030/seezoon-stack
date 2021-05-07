# Lombok

推荐的常用注解，**建议不要使用过于复杂的功能**。

## [@Getter/@Setter](https://projectlombok.org/features/GetterSetter)

可以默认生成`getter/setter`，手写的`getter/setter` 优先级更高。

## [@ToString](https://projectlombok.org/features/ToString)

可以为类的字段生成toString方法，拼接所有字段名和值。

## [@Data](https://projectlombok.org/features/Data)

基本包含@Getter/@Setter/@ToString 等，不建议使用。

## [@Builder](https://projectlombok.org/features/Builder)

为类提供建造者模式的链式赋值，有时候很方便，**不过需要注意，这个不会包含父类属性**。

## [@Log](https://projectlombok.org/features/log)

提供sl4j的log实例，可以选择性的使用，在基础的Controller和Service中已经包含，通常只需要继承即可以。

## [@NoArgsConstructor](https://projectlombok.org/features/constructor)

无参构造函数。

## [@RequiredArgsConstructor](https://projectlombok.org/features/constructor)

针对final字段，用的比较多，这种特别适合自动注入[**Spring Bean**](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#using-boot-spring-beans-and-dependency-injection)的注入。

## [@AllArgsConstructor](https://projectlombok.org/features/constructor)
全部字段构造函数。