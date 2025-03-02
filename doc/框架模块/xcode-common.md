# xcode-common为功能框架模块，其他业务模块或者框架模块都会引入
## 1. 数据脱敏功能
提供非数据库的typeHandler形式的数据脱敏功能，使用jackson包下的两个注解实现
分别是@JacksonAnnotationsInside以及@JsonSerialize
其中@JacksonAnnotationsInside是jackson的内置注解，用于组合其他注解以供满足我们个性化的序列化需求，
在common中我们使用@JsonSerialize注解，使用JacksonDataDesensitized实现的自定义json序列化，也就是脱敏功能，将它与
@JacksonAnnotationsInside配合使用，达到脱敏效果。

## 2. 枚举类校验
通过StringEnumValueToArray或者IntEnumValueToArray接口，再配合spring的校验框架实现枚举类的校验
枚举类只需要实现此接口，实现其中将某一个属性转为数据的方法即可，然后使用spring校验框架的注解，将实现了
StringEnumValueToArray或者IntEnumValueToArray接口的枚举类当作参数方剂去就行了

## 3. 提供代理功能 也就是aop模块
通过springcblib jdk以及原生态cglib代理的形式，提供自定义的aop功能
## 4. 提供spring 注解框架国际化
## 5. 提供异常处理框架，以及统一的异常处理器，异常工具类
## 6. 提供统一返回类 分页返回 分页参数业务类
## 7. 提供各种工具类 如集合工具类，文件工具 http工具类等。

还有一个重要的功能就是引入了knife4j依赖，用于管理业务框架的api接口调用，快速生产接口文档