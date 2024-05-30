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


# 接口调用测试
>  localhost:8080/search/get-docs-count   分页检索满足条件的doc

入参

``
{"title": "苹果",
"startTime":"2024-06-03",
"endTime":"2024-07-08",
"isDesc":true,
"statusList":["2","5"]}
``

返参

``
{
"success": true,
"code": 200,
"msg": "operate successfully",
"data": 100
}
``

---
>  localhost:8080/search/page-query   分页检索满足条件的doc

入参

``
{
"pageNum": 1,
"pageSize": 10,
"query": {
"title": "苹果",
"startTime":"2024-06-03",
"endTime":"2024-07-08",
"isDesc":true,
"statusList":["2","5"]
}
}
``

返参
``
{
"success": true,
"code": 200,
"msg": "operate successfully",
"data": {
"records": [
{
"id": 24,
"title": "苹果手机小屏类型再曝光：3000万主摄、8英寸屏幕",
"status": "5",
"time": "2024-06-23"
},
{
"id": 20,
"title": "苹果手机曲面屏类型再曝光：3000万主摄、6英寸屏幕",
"status": "5",
"time": "2024-06-19"
}
],
"total": 2,
"current": 1,
"size": 10
}
}
``
---


>  localhost:8080/search/update-batch   基于id增量批量更新索引

入参

``
[
{
"id":"104",
"title":"橘子当午饭吃可以吗",
"status":"1"
}
]
``

返参
``
{
"success": true,
"code": 200,
"msg": "operate successfully",
"data": null
}
``



