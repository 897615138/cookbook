# 关于后端的某些原则


## v 1.0
用户类(bean):
      命名: xxxInfo .
      类中注意: @Data @ToString  每个实现Serializable接口.

面板(controller):
      每一方面对应一个Controller,具体哪个方面可以看接口来确定.
      面板中写的是对于该方面的各种方法包括单表的增删改查 多表查询.
      每个面板中 涉及到 可能会发生数据库错误(比如插入的时候主键唯一 之类的) 使用try catch捕捉.
      返回值类型比如select可以直接返回list 返回到Servlet层处理,也可以用方法调整来完成具体的需求.


数据持久层与xml(dao与 mapper):
      按照现在的先进行,由于目前的多字段更新,采用的是前端后端结合前端采用隐藏域的方式进行,所以测试的时候直接全表更新.
      **dao接口的方法里 参数必须是 类的对象 主要是为了动态代理的需要 这里与老师的不同 请注意!**


工具类(utils):
     BeanUtil:在Servlet中使用req生成指定的对象,传入Controller层的方法,最后调用Controller函数.
     Const: **各种约定 关于Class 类 状态的返回也放在这里.**
     ResultUtil: 用于携带数据与前端交互.
     ControllerUtil: 用于生成mapper
     连接池组件: DBConfig jdbcConnPoolV2 
     异常工具类: AssertUtil

Servlets:
      用于写各种Servlet.
      请求命名 /方面/xxx
      其他参考目前工程.

测试(test):
        预留给收藏之类的目前不好实现的测试之用.


测试工具: 


** postman 批量插入大数据**

共享的collection集连接: https://www.getpostman.com/collections/75efec9326d888ccb719


导入教程: https://www.jianshu.com/p/91a5d7fd5fce


* 注意xml 中sql语句的写法: 
    1. #{}中使用实体类名 比如:  
    //上课老师说的原子性注册插入问题 
    insert into user_info(user_name,user_password,user_email,user_job)
        values(#{userName},#{userPassword},#{userEmail},#{userJob})
    2. 表中唯一的字段全部改写成使用上例的样子. 减少数据库的负担.
  
    4. 涉及到主键的地方 比如用户id  dish id 等 目前统一使用uuid 进行.
    **5. 为了减轻数据库的负担,数据库中除了主键以外的约束全部使用sql语句 或者后端java代码逻辑完成(如1)**
  


其他: 

1.所有需要后端的操作的提示信息，不要在前端有魔法值的输出，统一写在util.CONST 里配置，通过ResultUtil的msg传输到前端进行显示
2.所有的insert操作，前端获取用户提供的数据后，传至后端，后端将其他属性全部补齐insert（数据库里没有not null或default限制了。所有的关于外键之类的约束条件都需要后端自己判断）
3.所有有是否删除属性的表，在做任意操作的时候要判断是否已经逻辑删除
4.注册：用户名不能出现@符号（防止与邮箱重复），8-12位  密码8-12位 邮箱按照基本邮箱的标准
5.登录：邮箱/用户名登录 多条件登录

待处理
1.菜谱展示页 相关菜系菜谱
2.菜谱评论
3.第一个使用的人加载过慢
