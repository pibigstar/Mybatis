# 手写Mybatis框架，实现其核心功能

> 1.读取xml文件，建立连接
从图中可以看出，MyConfiguration负责与人交互。待读取xml后，将属性和连接数据库的操作封装在MyConfiguration对象中供后面的组件调用。本文将使用dom4j来读取xml文件，它具有性能优异和非常方便使用的特点。

> 2.创建SqlSession，搭建Configuration和Executor之间的桥梁
我们经常在使用框架时看到Session，Session到底是什么呢？一个Session仅拥有一个对应的数据库连接。类似于一个前段请求Request，它可以直接调用exec(SQL)来执行SQL语句。从流程图中的箭头可以看出，MySqlSession的成员变量中必须得有MyExecutor和MyConfiguration去集中做调配，箭头就像是一种关联关系。我们自己的MySqlSession将有一个getMapper方法，然后使用动态代理生成对象后，就可以做数据库的操作了。

> 3.创建Executor，封装JDBC操作数据库
Executor是一个执行器，负责SQL语句的生成和查询缓存（缓存还没完成）的维护，也就是jdbc的代码将在这里完成，不过本文只实现了单表，有兴趣的同学可以尝试完成多表。

> 4.创建MapperProxy，使用动态代理生成Mapper对象
 我们只是希望对指定的接口生成一个对象，使得执行它的时候能运行一句sql罢了，而接口无法直接调用方法，所以这里使用动态代理生成对象，在执行时还是回到MySqlSession中调用查询，最终由MyExecutor做JDBC查询。这样设计是为了单一职责，可扩展性更强。