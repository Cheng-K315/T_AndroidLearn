### 温馨提示
1.初始会创建一个管理员账号，用户名：admin，密码：admin<br>
2.用户需注册才能使用


# 项目介绍

基于android的理财小助手app，开发采用了增量式软件开发模型，采用轻量级数据库SQLite存储。主要应用了listView、button、editText、textView、spinner、dialog、CalendarView、menu等Android原生控件，以及自定义View控件ShanView(扇形图)、CircleProgressBar(环形进度条);采用了分层式类似MVC的结构，分为Activity(View)、JavaBean(Model)、Service（Control）,另外有Util包（工具类,包含字符串处理类、时间格式转换类、数据库操作类等）。以达到在模块层次上减小耦合、提高内聚的设计目标。

# 目录结构

![java代码](https://img-blog.csdnimg.cn/20210226150635101.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzgyNzM3Ng==,size_16,color_FFFFFF,t_70)

<br>上图是src下的java代码，编写的类比较多，有三十多个，大部分是一个页面对应一个java类。<br><br>

![布局代码](https://img-blog.csdnimg.cn/20210226152015177.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzgyNzM3Ng==,size_16,color_FFFFFF,t_70)

<br>上图是src下的布局文件，分为登录注册页、主菜单页、新增支出页、新增收入页、我的支出/收入展示页、数据管理页、系统设置页、收支便签展示页等等，角色分为用户和管理员（内置，用于管理多个普通账户）
<br>

# 总体设计

![总体设计](https://img-blog.csdnimg.cn/20210226151313405.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzgyNzM3Ng==,size_16,color_FFFFFF,t_70)

# 效果展示

下面是部分页面的具体效果。

(1)登录注册页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0620/235926_35ce8331_7634285.png "1.png")<br><br><br>


(2)管理员后台页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0620/235936_435064e4_7634285.png "2.png")<br><br><br>

(3)主菜单页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0620/235945_2b6c7922_7634285.png "3.png")<br><br><br>

(4)我的支出页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0620/235953_aa7eec4b_7634285.png "4.png")<br><br><br>

(5)数据管理页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000002_e593ea04_7634285.png "5.png")<br><br><br>

(6)季节收入页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000155_39e2b43e_7634285.png "6.png")<br><br><br>

(7)新增支出页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000207_68c061d4_7634285.png "7.png")<br><br><br>

(8)数据管理页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000213_7e958655_7634285.png "8.png")<br><br><br>

(9)系统设置页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000242_70522351_7634285.png "9.png")<br><br><br>

(10)便签管理页<br>
![输入图片说明](https://images.gitee.com/uploads/images/2021/0621/000316_df1d7c4f_7634285.png "10.png")<br><br><br>


# 项目总结

这个项目收获挺多的，独立完成一个小软件的开发。趁着大三寒假空闲之余就整理了一下，适合安卓新手入门。

