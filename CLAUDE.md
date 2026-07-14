# CLAUDE.md

## 项目概述

这是一个基于 Spring Boot 3.x 的秒杀系统后端项目（miaosha-v1），使用 Maven 构建。

## 技术栈

- **框架**: Spring Boot 3.2.0
- **JDK**: Java 17
- **数据库**: MySQL 8.x
- **持久层**: MyBatis 3.0.4
- **缓存**: Redis (Jedis 3.7.0)
- **消息队列**: RabbitMQ
- **API文档**: SpringDoc OpenAPI (Swagger) 2.3.0
- **验证**: Jakarta Bean Validation (spring-boot-starter-validation)

## 项目结构

```
src/main/java/com/ccc/miaoshav1/
├── controller/          # 控制器层
├── dao/                 # 数据访问层 (MyBatis Mapper接口)
├── domin/               # 实体类 (domain拼写错误但沿用)
├── vo/                  # 视图对象/请求参数对象
├── result/              # 统一响应结果封装
├── validator/           # 自定义校验注解
├── config/              # 配置类
└── mybatis/controller/  # MyBatis测试控制器
```

## 关键配置

### 数据库
- 数据库名: `miaosha`
- 地址: `localhost:3306`
- 用户名/密码: 见 `application.yml`

### 服务端口
- HTTP端口: `8080`

### MyBatis配置
- 类型别名包: `com.ccc.miaoshav1.domin,com.ccc.miaoshav1.vo`
- 驼峰映射: 开启 `map-underscore-to-camel-case`

## 常用命令

```bash
# 启动项目
./mvnw spring-boot:run

# 打包
./mvnw clean package

# 运行测试
./mvnw test
```

## 开发规范

### API 响应格式
所有接口统一返回 `Result<T>` 类型：
- `code`: 状态码
- `msg`: 消息
- `data`: 数据

### 参数校验
- 在 VO 对象字段上使用校验注解 (`@NotNull`, `@PasswordLength` 等)
- 控制器方法参数必须添加 `@Valid` 注解才能触发校验
- 自定义校验注解位于 `validator` 包

### Swagger 文档
- 访问地址: `http://localhost:8080/swagger-ui.html`
- 控制器使用 `@Tag` 注解分组
- 接口使用 `@Operation` 和 `@ApiResponse` 描述

## 注意事项

1. **校验依赖**: Spring Boot 3.x 需要显式添加 `spring-boot-starter-validation` 依赖才能使用参数校验
2. **包名拼写**: `domin` 是拼写错误，实际应为 `domain`，但项目已沿用此命名
3. **MyBatis**: 使用注解方式（非 XML），Mapper 接口位于 `dao` 包
