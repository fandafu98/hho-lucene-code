# hho-lucene-code

18269766046 郑延康的代码题

---

# 项目简易结构

> code-web        web接口层

> code-service 业务层所在

> code-framework 主要存放lucene等组件的配置
---

# 大致流程

1.项目启动时，先通过``com.hho.framework.runner.InitIndexDataRunner``类，自动初始化100条数据

2.业务接口层：``com.hho.service.LuceneDemoManager`` 包含了如下接口

- 向外提供一个函数，以基于id增量批量更新索引；
- 提供一个函数，用于分页检索满足条件的doc；
- 提供一个函数，用于查询满足条件的文档数；

也可以通过 ``com.hho.web.api.rest.SearchApi`` 进行web端的调用测试

