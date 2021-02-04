## Docker容器化和Kubernetes平台支持

### Docker容器化
![](http://nepxion.gitee.io/docs/icon-doc/information.png) Spring 2.3.x支持Docker分层部署，步骤也更简单，请参考Polaris【北极星】企业级云原生微服务框架里的介绍

① 搭建Windows10操作系统或者Linux操作系统下的Docker环境

- Windows10环境下，具体步骤参考[Docker安装步骤](https://github.com/Nepxion/Thunder/blob/master/thunder-spring-boot-docker-example/README.md)
- Linux环境请自行研究

② 需要在4个工程下的pom.xml里增加spring-boot-maven-plugin和docker-maven-plugin
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
        <mainClass>com.nepxion.discovery.guide.gateway.DiscoveryGuideGateway</mainClass>
        <layout>JAR</layout>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
            <configuration>
                <attach>false</attach>
            </configuration>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <configuration>
        <imageName>${ImageName}</imageName>
        <baseImage>openjdk:8-jre-alpine</baseImage>
        <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
        <exposes>
            <expose>${ExposePort}</expose>
        </exposes>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
```
③ 拷贝discovery-guide-docker目录下的所有脚本文件到根目录下

④ 所有脚本文件下的MIDDLEWARE_HOST=10.0.75.1改成使用者本地物理IP地址（Docker是不能去连接容器外地址为localhost的中间件服务器）

⑤ 全自动部署和运行Docker化的服务。在根目录下

- 一键运行install-docker-gateway.bat或者.sh，把Spring Cloud Gateway网关全自动部署且运行起来
- 一键运行install-docker-zuul.bat或者.sh，把Zuul网关全自动部署且运行起来
- 一键运行install-docker-service-xx.bat或者.sh，把微服务全自动部署且运行起来。需要注意，必须依次运行，即等上一个部署完毕后才能执行下一个
- 一键运行install-docker-console.bat或者.sh，把控制平台全自动部署且运行起来
- 一键运行install-docker-admin.bat或者.sh，把监控平台全自动部署且运行起来	

上述步骤为演示步骤，和DevOps平台结合在一起，更为完美

⑥ Docker运行效果

- Docker Desktop

![](http://nepxion.gitee.io/docs/discovery-doc/Docker.jpg)

- Docker Windows

![](http://nepxion.gitee.io/docs/polaris-doc/DockerWindows.jpg)

- Docker Linux

![](http://nepxion.gitee.io/docs/polaris-doc/DockerLinux.jpg)

### Kubernetes平台支持
请自行研究

## 自动化测试
自动化测试，基于Spring Boot/Spring Cloud的自动化测试框架，包括普通调用测试、蓝绿灰度调用测试和扩展调用测试（例如：支持阿里巴巴的Sentinel，FF4j的功能开关等）。通过注解形式，跟Spring Boot内置的测试机制集成，使用简单方便。该自动化测试框架的现实意义，可以把服务注册发现中心、远程配置中心、负载均衡、蓝绿灰度发布、熔断降级限流、功能开关、Feign或者RestTemplate调用等中间件或者组件，一条龙组合起来进行自动化测试

自动化测试代码参考[指南示例自动化测试](https://github.com/Nepxion/DiscoveryGuide/tree/master/discovery-guide-test-automation)

### 架构设计
通过Matrix Aop框架，实现TestAutoScanProxy和TestInterceptor拦截测试用例，实现配置内容的自动化推送

### 启动控制台
运行[指南示例](https://github.com/Nepxion/DiscoveryGuide)下的DiscoveryGuideConsole.java控制台服务，它是连接服务注册发现中心、远程配置中心和服务的纽带，自动化测试利用控制台实现配置的自动更新和清除

### 配置文件
```
# 自动化测试框架内置配置
# 测试用例类的扫描路径
spring.application.test.scan.packages=com.nepxion.discovery.guide.test
# 测试用例的配置内容推送时，是否打印配置日志。缺失则默认为true
spring.application.test.config.print.enabled=true
# 测试用例的配置内容推送后，等待生效的时间。推送远程配置中心后，再通知各服务更新自身的配置缓存，需要一定的时间，缺失则默认为3000
spring.application.test.config.operation.await.time=5000
# 测试用例的配置内容推送的控制台地址。控制台是连接服务注册发现中心、远程配置中心和服务的纽带
spring.application.test.console.url=http://localhost:6001/

# 业务测试配置
# Spring Cloud Gateway网关配置
gateway.group=discovery-guide-group
gateway.service.id=discovery-guide-gateway
gateway.test.url=http://localhost:5001/discovery-guide-service-a/invoke/gateway

# Zuul网关配置
zuul.group=discovery-guide-group
zuul.service.id=discovery-guide-zuul
zuul.test.url=http://localhost:5002/discovery-guide-service-a/invoke/zuul

# 每个测试用例执行循环次数
testcase.loop.times=1

# 测试用例的灰度权重测试开关。由于权重测试需要大量采样调用，会造成整个自动化测试时间很长，可以通过下面开关开启和关闭。缺失则默认为true
gray.weight.testcases.enabled=true
# 测试用例的灰度权重采样总数。采样总数越大，灰度权重准确率越高，但耗费时间越长
gray.weight.testcase.sample.count=1500
# 测试用例的灰度权重准确率偏离值。采样总数越大，灰度权重准确率偏离值越小
gray.weight.testcase.result.offset=5
```

### 测试用例

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，当使用Eureka注册中心的时候，因为Spring Cloud内嵌了Eureka可用区亲和性功能，会自动开启该策略，则导致某些自动化测试用例失败。需要把所有服务实例的元数据zone值改成相同或者也可以把该行元数据删除，然后进行自动化测试

#### 测试包引入
```xml
<dependencies>
    <dependency>
        <groupId>com.nepxion</groupId>
        <artifactId>discovery-plugin-test-starter</artifactId>
        <version>${discovery.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <compilerArgs>
                    <arg>-parameters</arg>
                </compilerArgs>
                <encoding>${project.build.sourceEncoding}</encoding>
                <source>${java.version}</source>
                <target>${java.version}</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

![](http://nepxion.gitee.io/docs/icon-doc/warning.png) 需要注意，对于带有注解@DTestConfig的测试用例，要用到Spring的Spel语法格式（即group = "#group", serviceId = "#serviceId"），需要引入Java8的带"-parameters"编译方式，见上面的<compilerArgs>参数设置

在IDE环境里需要设置"-parameters"的Compiler Argument

- Eclipse加"-parameters"参数：https://www.concretepage.com/java/jdk-8/java-8-reflection-access-to-parameter-names-of-method-and-constructor-with-maven-gradle-and-eclipse-using-parameters-compiler-argument
- Idea加"-parameters"参数：http://blog.csdn.net/royal_lr/article/details/52279993

#### 测试入口程序
结合Spring Boot Junit，TestApplication.class为测试框架内置应用启动程序，DiscoveryGuideTestConfiguration用于初始化所有测试用例类。在测试方法上面加入JUnit的@Test注解
```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestApplication.class, DiscoveryGuideTestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiscoveryGuideTest {
    @Autowired
    private DiscoveryGuideTestCases discoveryGuideTestCases;

    private static long startTime;

    @BeforeClass
    public static void beforeTest() {
        startTime = System.currentTimeMillis();
    }

    @AfterClass
    public static void afterTest() {
        LOG.info("* Finished automation test in {} seconds", (System.currentTimeMillis() - startTime) / 1000);
    }

    @Test
    public void testNoGray() throws Exception {
        discoveryGuideTestCases.testNoGray(gatewayTestUrl);
        discoveryGuideTestCases.testNoGray(zuulTestUrl);
    }

    @Test
    public void testVersionStrategyGray() throws Exception {
        discoveryGuideTestCases.testVersionStrategyGray1(gatewayGroup, gatewayServiceId, gatewayTestUrl);
        discoveryGuideTestCases.testVersionStrategyGray1(zuulGroup, zuulServiceId, zuulTestUrl);
    }
}
```

```java
@Configuration
public class DiscoveryGuideTestConfiguration {
    @Bean
    public DiscoveryGuideTestCases discoveryGuideTestCases() {
        return new DiscoveryGuideTestCases();
    }
}
```

#### 普通调用测试
在测试方法上面增加注解@DTest，通过断言Assert来判断测试结果。注解@DTest内容如下
```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTest {

}
```

代码如下
```java
public class DiscoveryGuideTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTest
    public void testNoGray(String testUrl) {
        int noRepeatCount = 0;
        List<String> resultList = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (!resultList.contains(result)) {
                noRepeatCount++;
            }
            resultList.add(result);
        }

        Assert.assertEquals(noRepeatCount, 4);
    }
}
```

#### 蓝绿灰度调用测试
在测试方法上面增加注解@DTestConfig，通过断言Assert来判断测试结果。注解DTestConfig注解内容如下
```java
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DTestConfig {
    // 组名
    String group();

    // 服务名
    String serviceId();

    // 组名-服务名组合键值的前缀
    String prefix() default StringUtils.EMPTY;

    // 组名-服务名组合键值的后缀
    String suffix() default StringUtils.EMPTY;

    // 执行配置的文件路径。测试用例运行前，会把该文件里的内容推送到远程配置中心或者服务
    String executePath();

    // 重置配置的文件路径。测试用例运行后，会把该文件里的内容推送到远程配置中心或者服务。该文件内容是最初的默认配置
    // 如果该注解属性为空，则直接删除从配置中心删除组名-服务名组合键值
    String resetPath() default StringUtils.EMPTY;
}
```

代码如下

```java
public class DiscoveryGuideTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "#group", serviceId = "#serviceId", executePath = "gray-strategy-version-1.xml", resetPath = "gray-default.xml")
    public void testVersionStrategyGray(String group, String serviceId, String testUrl) {
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.getForEntity(testUrl, String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            int index = result.indexOf("[V=1.0]");
            int lastIndex = result.lastIndexOf("[V=1.0]");

            Assert.assertNotEquals(index, -1);
            Assert.assertNotEquals(lastIndex, -1);
            Assert.assertNotEquals(index, lastIndex);
        }
    }
}
```

蓝绿灰度配置文件gray-strategy-version-1.xml的内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>
    <strategy>
        <version>1.0</version>
    </strategy>
</rule>
```

蓝绿灰度配置文件gray-default.xml的内容如下
```xml
<?xml version="1.0" encoding="UTF-8"?>
<rule>

</rule>
```

#### 扩展调用测试
除了支持蓝绿灰度自动化测试外，使用者可扩展出以远程配置中心内容做变更的自动化测试。以阿里巴巴的Sentinel的权限功能为例子，参考PolarisGuide，测试实现方式如下

① 远程配置中心约定

- Nacos的Key格式

```
Group为DEFAULT_GROUP，Data ID为sentinel-authority-${spring.application.name}。每个服务都专享自己的Sentinel规则
```

- Apollo的Key格式

```
namespace为application，Key为sentinel-authority。每个服务都专享自己的Sentinel规则
```

② 执行测试用例前，把执行限流降级熔断等逻辑的内容（executePath = "sentinel-authority-2.json"）推送到远程配置中心

③ 执行测试用例，通过断言Assert来判断测试结果

④ 执行测试用例后，把修改过的内容（resetPath = "sentinel-authority-1.json"）复原，再推送一次到远程配置中心

```java
public class PolarisTestCases {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @DTestConfig(group = "DEFAULT_GROUP", serviceId = "sentinel-authority-polaris-service-b", executePath = "sentinel-authority-2.json", resetPath = "sentinel-authority-1.json")
    public void testSentinelAuthority1(String testUrl) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            String result = testRestTemplate.postForEntity(testUrl, "gateway", String.class).getBody();

            LOG.info("Result{} : {}", i + 1, result);

            if (result.contains("AuthorityRule")) {
                count++;
            }
        }

        Assert.assertEquals(count, 4);
    }
}
```

### 测试报告
- 路由策略测试报告样例

```
---------- Run automation testcase :: testNoGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testEnabledStrategyGray1() ----------
Header : [mobile:"138"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionStrategyGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=90%, 1.1 version weight=10%
B service desired : 1.0 version weight=20%, 1.1 version weight=80%
Result : A service 1.0 version weight=89.6%
Result : A service 1.1 version weight=10.4%
Result : B service 1.0 version weight=20.1333%
Result : B service 1.1 version weight=79.8667%
* Passed
---------- Run automation testcase :: testRegionWeightStrategyGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=85%, qa region weight=15%
B service desired : dev region weight=85%, qa region weight=15%
Result : A service dev region weight=83.7667%
Result : A service qa region weight=16.2333%
Result : B service dev region weight=86.2%
Result : B service qa region weight=13.8%
* Passed
```

- 路由规则测试报告样例

```
---------- Run automation testcase :: testStrategyCustomizationGray() ----------
Header : [a:"1", b:"2"]
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testRegionRuleGray() ----------
Result1 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result2 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
Result3 : gateway -> discovery-guide-service-a[192.168.0.107:3002][V=1.1][R=qa][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4001][V=1.0][R=qa][G=discovery-guide-group]
Result4 : gateway -> discovery-guide-service-a[192.168.0.107:3001][V=1.0][R=dev][G=discovery-guide-group] -> discovery-guide-service-b[192.168.0.107:4002][V=1.1][R=dev][G=discovery-guide-group]
* Passed
---------- Run automation testcase :: testVersionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=75%, 1.1 version weight=25%
B service desired : 1.0 version weight=35%, 1.1 version weight=65%
Result : A service 1.0 version weight=75.2667%
Result : A service 1.1 version weight=24.7333%
Result : B service 1.0 version weight=35.1667%
Result : B service 1.1 version weight=64.8333%
* Passed
---------- Run automation testcase :: testRegionWeightRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : dev region weight=95%, qa region weight=5%
B service desired : dev region weight=95%, qa region weight=5%
Result : A service dev region weight=94.9333%
Result : A service qa region weight=5.0667%
Result : B service dev region weight=95.0667%
Result : B service qa region weight=4.9333%
* Passed
---------- Run automation testcase :: testVersionCompositeRuleGray() ----------
Sample count=3000
Weight result offset desired=5%
A service desired : 1.0 version weight=40%, 1.1 version weight=60%
Route desired : A Service 1.0 version -> B Service 1.0 version, A Service 1.1 version -> B Service 1.1 version
Result : A service 1.0 version weight=39.8333%
A service 1.1 version weight=60.1667%
* Passed
```

## 压力测试
压力测试，基于WRK的异步压力测试框架，能用很少的线程压测出很大的并发量，使用简单方便

### 测试环境
① 准备两台机器部署Spring Cloud应用

② 准备一台机器部署网关（Spring Cloud或者Zuul）

③ 准备一台机器部署压测工具

| 服务 | 配置 | 数目 |
| --- | --- | --- |
| Spring Cloud Gateway | 16C 32G | 1 |
| Zuul 1.x | 16C 32G | 1 |
| Service | 4C 8G | 2 |

④ 优化方式

- Spring Cloud Gateway，不需要优化
- Zuul 1.x，优化如下

```
zuul.host.max-per-route-connections=1000
zuul.host.max-total-connections=1000
zuul.semaphore.max-semaphores=5000
```

### 测试介绍
- 使用WRK脚本进行性能测试，WRK脚本参考post.lua（位于discovery-guide-test-automation目录下），不带参数运行
- 使用WRK详细说明参考[https://github.com/wg/wrk](https://github.com/wg/wrk)

### 测试步骤
- 登录到WRK的机器，进入WRK目录
- 运行命令 wrk -t64 -c2000 -d30s -H "id: 123" -H "token: abc" --timeout=2s --latency --script=post.lua http://localhost:5001/discovery-guide-service-a/invoke/gateway

```
使用方法: wrk <选项> <被测HTTP服务的URL>
  Options:
    -c, --connections 跟服务器建立并保持的TCP连接数量
    -d, --duration    压测时间。例如：2s，2m，2h
    -t, --threads     使用多少个线程进行压测
    -s, --script      指定Lua脚本路径
    -H, --header      为每一个HTTP请求添加HTTP头。例如：-H "id: 123" -H "token: abc"，冒号后面要带空格
        --latency     在压测结束后，打印延迟统计信息
        --timeout     超时时间
```

- 等待结果，Requests/sec 表示每秒处理的请求数

基于WRK极限压测，报告如下

| 服务 | 性质 | 线程数 | 连接数 | 每秒最大请求数 | 资源耗费 |
| --- | --- | --- | --- | --- | --- |
| Spring Cloud Gateway为起始的调用链 | 原生框架 | 5000 | 20000 | 28100左右 | CPU占用率42% |
| Spring Cloud Gateway为起始的调用链 | 本框架 | 5000 | 20000 | 27800左右 | CPU占用率42.3% |
| Zuul 1.x为起始的调用链 | 原生框架 | 5000 | 20000 | 24050左右 | CPU占用率56% |
| Zuul 1.x为起始的调用链 | 本框架 | 5000 | 20000 | 23500左右 | CPU占用率56.5% |

## 附录

### 中间件服务器下载地址
![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 注册中心

① Nacos

- Nacos服务器版本，推荐用最新版本，从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取
- 功能界面主页，[http://localhost:8848/nacos/index.html](http://localhost:8848/nacos/index.html)

② Consul

- Consul服务器版本不限制，推荐用最新版本，从[https://releases.hashicorp.com/consul/](https://releases.hashicorp.com/consul/)获取
- 功能界面主页，[http://localhost:8500](http://localhost:8500)

③ Eureka

- 跟Spring Cloud版本保持一致，自行搭建服务器
- 功能界面主页，[http://localhost:9528](http://localhost:9528)

④ Zookeeper

- Spring Cloud F版或以上，必须采用Zookeeper服务器的3.5.x服务器版本（或者更高），从[http://zookeeper.apache.org/releases.html#download](http://zookeeper.apache.org/releases.html#download)获取
- Spring Cloud E版，Zookeeper服务器版本不限制

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 配置中心

① Nacos

- Nacos服务器版本，推荐用最新版本，从[https://github.com/alibaba/nacos/releases](https://github.com/alibaba/nacos/releases)获取
- 功能界面主页，[http://localhost:8848/nacos/index.html](http://localhost:8848/nacos/index.html)

② Apollo

- Apollo服务器版本，推荐用最新版本，从[https://github.com/ctripcorp/apollo/releases](https://github.com/ctripcorp/apollo/releases)获取
- 功能界面主页，[http://localhost:8088](http://localhost:8088)

③ Redis

- Redis服务器版本，推荐用最新版本，从[https://redis.io](https://redis.io)获取

④ Etcd

- Etcd服务器版本，推荐用最新版本，从[https://github.com/etcd-io/etcd/releases](https://github.com/etcd-io/etcd/releases)获取

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 限流熔断

① Sentinel

- Sentinel服务器版本，推荐用最新版本，从[https://github.com/alibaba/Sentinel/releases](https://github.com/alibaba/Sentinel/releases)获取
- 功能界面主页，[http://localhost:8075/#/dashboard](http://localhost:8075/#/dashboard)

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 调用链监控

① Jaeger

- Jaeger服务器版本，推荐用最新版本，从[https://github.com/jaegertracing/jaeger/releases](https://github.com/jaegertracing/jaeger/releases)获取
- 功能界面主页，[http://localhost:16686](http://localhost:16686)

② SkyWalking

- SkyWalking服务器版本，推荐用最新版本，从[http://skywalking.apache.org/downloads](http://skywalking.apache.org/downloads)获取
- 功能界面主页，[http://127.0.0.1:8080/](http://127.0.0.1:8080/)

③ Zipkin

- Zipkin服务器版本，推荐用最新版本，从[https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec](https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec)获取
- 功能界面主页，[http://localhost:9411/zipkin](http://localhost:9411/zipkin)

![](http://nepxion.gitee.io/docs/icon-doc/information_message.png) 指标监控

① Prometheus

- Prometheus服务器版本，推荐用最新版本，从[https://github.com/prometheus/prometheus/releases](https://github.com/prometheus/prometheus/releases)获取
- 功能界面主页，[http://localhost:9090](http://localhost:9090)

② Grafana

- Grafana服务器版本，推荐用最新版本，从[https://grafana.com/grafana/download?platform=windows](https://grafana.com/grafana/download?platform=windows)获取
- 功能界面主页，[http://localhost:3000](http://localhost:3000)

③ Spring Boot Admin

- 跟Spring Boot版本保持一致，自行搭建服务器。从[https://github.com/codecentric/spring-boot-admin](https://github.com/codecentric/spring-boot-admin)获取
- 功能界面主页，[http://localhost:6002](http://localhost:6002)

## Star走势图
[![Stargazers over time](https://starchart.cc/Nepxion/Discovery.svg)](https://starchart.cc/Nepxion/Discovery)