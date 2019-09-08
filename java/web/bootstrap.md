# bootstrap完全学习手册 #
bootstrap前端框架,用于构建响应式 移动设备优先的网站

## bootstrap组件内容部分 ##

### 使用引入一个css和三个js文件 ###

BootCDN 提供的免费 CDN 加速服务

```html
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
```

### 布局 ###

container是Bootstrap最基本的布局元素,在我们使用默认网格系统时是必需的,响应断点(意味着宽始终是100%)

```html
<div class="container">
  <!-- Content here -->
</div>

.container-fluid设置全宽的容器,跨越了整个视口
```

### 响应式断点 ###

还有媒体查询和mixin，用于使用最小和最大断点宽度来定位单个屏幕尺寸段。

```css
// Extra small devices (portrait phones, less than 576px)
@media (max-width: 575.98px) { ... }

// Small devices (landscape phones, 576px and up)
@media (min-width: 576px) and (max-width: 767.98px) { ... }

// Medium devices (tablets, 768px and up)
@media (min-width: 768px) and (max-width: 991.98px) { ... }

// Large devices (desktops, 992px and up)
@media (min-width: 992px) and (max-width: 1199.98px) { ... }

// Extra large devices (large desktops, 1200px and up)
@media (min-width: 1200px) { ... }
```

### 栅格系统 ###

|尺寸| 超小 | 小 | 中等 | 大 | 超大 |
|----| ---- | ----- | ----- | ----- | ----- |
|尺寸描述 |<576px|>=576px|>=768px|>=992px|>=1200px|
|最大容器宽度 |自动(无)|540px|720px|960px|1140px|
|类前缀|.col-|.col-sm-|.col-md-|.col-lg-|.col-xl-|
|列数|12
|嵌套|是
|列排序|是
|Gutter width|30px (15px on each side of a column)

### 媒体对象 ###

媒体对象有助于建立复杂的和重复的成分

    .media
    .media-body
    
    <div class="media">
      <img class="align-self-center mr-3" src="..." alt="Generic placeholder image">
      <div class="media-body">
        <h5 class="mt-0">Center-aligned media</h5>
        <p>Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.</p>
        <p class="mb-0">Donec sed odio dui. Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.</p>
      </div>
    </div>

### 页面默认值 ###

    box-sizing
    font-family
    line-height
    text-align
    <body>有一个声明background-color 默认#fff

### table表格 ###

    .table
    .table-dark 黑色
    .table-striped 条纹
表头

    .thead-light 浅灰色
    .thead-dark 深灰色
    
    .table-bordered 添加边框
    .table-hover 添加悬停状态
    .table-sm 使表格更加紧凑
    .table-responsive 响应表
    .table-responsive{-sm|-md|-lg|-xl} 添加具体的断点
给表单元格进行着色或者添加背景色

### figure ###

    <figure class="figure">
      <img src="..." class="figure-img img-fluid rounded" alt="A generic square placeholder image with rounded corners in a figure.">
      <figcaption class="figure-caption text-right">A caption for the above image.</figcaption>
    </figure>

### 警报 ###

    <div class="alert alert-primary" role="alert">
      This is a primary alert—check it out!
    </div>
    <div class="alert alert-secondary" role="alert">
      This is a secondary alert—check it out!
    </div>
    <div class="alert alert-success" role="alert">
      This is a success alert—check it out!
    </div>
    <div class="alert alert-danger" role="alert">
      This is a danger alert—check it out!
    </div>
    <div class="alert alert-warning" role="alert">
      This is a warning alert—check it out!
    </div>
    <div class="alert alert-info" role="alert">
      This is a info alert—check it out!
    </div>
    <div class="alert alert-light" role="alert">
      This is a light alert—check it out!
    </div>
    <div class="alert alert-dark" role="alert">
      This is a dark alert—check it out!
    </div>

链接警报

    <div class="alert alert-primary" role="alert">
      This is a primary alert with <a href="#" class="alert-link">an example link</a>. Give it a click if you like.
    </div>

关闭警报的方式JS

    $('.alert').alert()
或者添加 

    data-dismiss="alert" 属性

### 徽章 ###

徽章可以用作链接或按钮的一部分来提供计数器。更改徽章外观

    <span class="badge badge-primary">Primary</span>
    <span class="badge badge-secondary">Secondary</span>
    <span class="badge badge-success">Success</span>
    <span class="badge badge-danger">Danger</span>
    <span class="badge badge-warning">Warning</span>
    <span class="badge badge-info">Info</span>
    <span class="badge badge-light">Light</span>
    <span class="badge badge-dark">Dark</span>

使用.badge-pill修饰符类可以使徽章更圆润

### Breadcrumb ###

    <nav aria-label="breadcrumb">
      <ol class="breadcrumb">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="#">Library</a></li>
        <li class="breadcrumb-item active" aria-current="page">Data</li>
      </ol>
    </nav>

### 按钮button ###

按钮样式

    <button type="button" class="btn btn-primary">Primary</button> 主要蓝

大纲按钮

    <button type="button" class="btn btn-outline-primary">Primary</button>
    
    class="btn btn-secondary btn-lg" 大按钮
    class="btn btn-secondary btn-sm" 小按钮
    
    .btn-block 按钮块
    .active
    .disabled

切换状态

    添加data-toggle="button"以切换按钮的active状态。如果你预先切换按钮，你必须手动添加.active类和 aria-pressed="true"到<button>。
    
    <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off">
      Single toggle
    </button>

单选按钮和复选按钮


    <div class="btn-group-toggle" data-toggle="buttons">
      <label class="btn btn-secondary active">
        <input type="checkbox" checked autocomplete="off"> Checked
      </label>
    </div>


    <div class="btn-group btn-group-toggle" data-toggle="buttons">
      <label class="btn btn-secondary active">
        <input type="radio" name="options" id="option1" autocomplete="off" checked> Active
      </label>
      <label class="btn btn-secondary">
        <input type="radio" name="options" id="option2" autocomplete="off"> Radio
      </label>
      <label class="btn btn-secondary">
        <input type="radio" name="options" id="option3" autocomplete="off"> Radio
      </label>
    </div>

JS方法
    $().button('toggle')	切换推送状态。为按钮提供已激活的外观。
    $().button('dispose')	销毁元素的按钮。

### 按钮组 ###

    <div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups">
      <div class="btn-group mr-2" role="group" aria-label="First group">
        <button type="button" class="btn btn-secondary">1</button>
        <button type="button" class="btn btn-secondary">2</button>
        <button type="button" class="btn btn-secondary">3</button>
        <button type="button" class="btn btn-secondary">4</button>
      </div>
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text" id="btnGroupAddon">@</div>
        </div>
        <input type="text" class="form-control" placeholder="Input group example" aria-label="Input group example" aria-describedby="btnGroupAddon">
      </div>
    </div>
    
    role属性配合使用


### card提供灵活且可扩展的内容容器 ###

["此模块内容较多涵盖内容较为齐全,适合内容页面,点击查看文档"](https://v4.bootcss.com/docs/4.0/components/card/)

### 幻灯片组件 ###

[幻灯片一般用在主页做循环展示,点击查看文档](https://v4.bootcss.com/docs/4.0/components/carousel/)

### collapse ###

使用几个类和JS插件切换项目中内容的可见性

    .collapse 隐藏内容
    .collapsing 在转换期间应用
    .collapse  .show 显示内容

[点击查看项目中的案例](F:\GitProject\FrontH5CSS\collapse.html)

    data-toggle="collapse"和data-target 到元件自动分配一个或多个可折叠的元件的控制
    aria-expanded 明确地将与控件绑定的可折叠元素的当前状态传达给屏幕阅读器和类似的辅助技术

### 下拉菜单 ###

    .dropdown 
    .dropup
    .dropleft
    .dropright
    
     <div class="dropdown-divider"></div>菜单分割线    

### 表单form ###

    .form-group 表单组
    .form-check 检查
    .form-control 控制
    .form-control-file 文件上传
    .form-control-lg 大
    .form-check-inline 内联
    .form-row 表格行
    使用网格系统创建复杂布局
表单验证

	HTML表单验证通过CSS的两个伪类应用，:invalid以及:valid。它适用于<input>，<select>和<textarea>元素。
	Bootstrap将:invalid和:valid样式范围限定为父.was-validated类，通常应用于<form>。否则，没有值的任何必填字段在页面加载时显示为无效。这样，您可以选择何时激活它们（通常在尝试提交表单之后）。
	作为备用，.is-invalid并且.is-valid类别可以用来代替伪类用于服务器端验证。他们不需要.was-validated父类。

### 超大屏幕Jumbotron ###

	.jumbotron jumbotron-fluid
	.lead

### 列表组 ###

	<ul class="list-group">
	  <li class="list-group-item disabled">Cras justo odio</li>
	  <li class="list-group-item">Dapibus ac facilisis in</li>
	  <li class="list-group-item">Morbi leo risus</li>
	  <li class="list-group-item">Porta ac consectetur ac</li>
	  <li class="list-group-item">Vestibulum at eros</li>
	</ul>
		
	链接形式的列表
	按钮形式的列表
	
	有徽章的表示计数所用的
	.list-group-flush 红晕效果

### Modal ###

使用Bootstrap的JavaScript模式插件为您的站点添加灯箱，用户通知或完全自定义内容的对话框
[弹出层 对话框 提示框](https://v4.bootcss.com/docs/4.0/components/modal/)

### 导航 ###

[疑问参照文档](https://v4.bootcss.com/docs/4.0/components/navs/)

示例

	<ul class="nav">
	  <li class="nav-item">
	    <a class="nav-link active" href="#">Active</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled" href="#">Disabled</a>
	  </li>
	</ul>

对齐

	以.justify-content-center：为中心
	右对齐.justify-content-end：
	
	.flex-column实用程序更改弹性项目方向来堆叠导航

标签
	
	从上面获取基本导航并添加.nav-tabs类以生成选项卡式界面。使用它们可以使用我们的选项卡JavaScript插件创建可列表区域
	.nav-pills
	.nav-fill
	对于等宽元素，请使用.nav-justified
### 分页 ###

	<nav aria-label="Page navigation example">
	  <ul class="pagination">
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	        <span class="sr-only">Previous</span>
	      </a>
	    </li>
	    <li class="page-item"><a class="page-link" href="#">1</a></li>
	    <li class="page-item"><a class="page-link" href="#">2</a></li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li>
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	        <span class="sr-only">Next</span>
	      </a>
	    </li>
	  </ul>
	</nav>

### Popovers弹出窗口 ###
[参照文档使用](https://v4.bootcss.com/docs/4.0/components/popovers/)
依靠第三方脚本Popper.js

[也可参照layer.js的使用文档 参照使用](https://www.layui.com/doc/modules/layer.html)

### 进度条 ###

	我们使用.progressas作为包装器来指示进度条的最大值。
	我们使用内部.progress-bar来指示到目前为止的进展。
	该.progress-bar要求的内嵌样式，实用类，或自定义CSS来设置其宽度。
	该.progress-bar还需要一些role与aria属性，使其访问。

### 滚动效果 ###
它必须在Bootstrap 导航组件或列表组上使用。
[若使用可参照](https://v4.bootcss.com/docs/4.0/components/scrollspy/#item-3-1)
### 提示 ###

依赖Popper.js
[参照文档 提示](https://v4.bootcss.com/docs/4.0/components/tooltips/)

## bootstrap通用内容部分 ##
### 边框 ###
**添加边框** 

	<span class="border"></span>
	<span class="border-top"></span>
	<span class="border-right"></span>
	<span class="border-bottom"></span>
	<span class="border-left"></span>

**去除边框** 
	
	<span class="border-0"></span>
	<span class="border-top-0"></span>
	<span class="border-right-0"></span>
	<span class="border-bottom-0"></span>
	<span class="border-left-0"></span>

**边框颜色**

	<span class="border border-primary"></span>
	<span class="border border-secondary"></span>
	<span class="border border-success"></span>
	<span class="border border-danger"></span>
	<span class="border border-warning"></span>
	<span class="border border-info"></span>
	<span class="border border-light"></span>
	<span class="border border-dark"></span>
	<span class="border border-white"></span>

**边界半径**

	<img src="..." alt="..." class="rounded">
	<img src="..." alt="..." class="rounded-top">
	<img src="..." alt="..." class="rounded-right">
	<img src="..." alt="..." class="rounded-bottom">
	<img src="..." alt="..." class="rounded-left">
	<img src="..." alt="..." class="rounded-circle">
	<img src="..." alt="..." class="rounded-0">

### Clearfix  ###

通过添加clearfix实用程序,可以快速清楚容器中的浮动内容\

示例

	<div class="bg-info clearfix">
	  <button type="button" class="btn btn-secondary float-left">Example Button floated left</button>
	  <button type="button" class="btn btn-secondary float-right">Example Button floated right</button>
	</div>

### 关闭图标 ###
通过添加aria-label属性实现

	<button type="button" class="close" aria-label="Close">
	  <span aria-hidden="true">&times;</span>
	</button>

### 颜色 ###

文本颜色 text-primary
链接文本颜色  bg-primary 
按钮颜色
背景颜色
外联线颜色

背景渐变效果

	<div class="p-3 mb-2 bg-gradient-primary text-white">.bg-gradient-primary</div>
	<div class="p-3 mb-2 bg-gradient-secondary text-white">.bg-gradient-secondary</div>
	<div class="p-3 mb-2 bg-gradient-success text-white">.bg-gradient-success</div>
	<div class="p-3 mb-2 bg-gradient-danger text-white">.bg-gradient-danger</div>
	<div class="p-3 mb-2 bg-gradient-warning text-dark">.bg-gradient-warning</div>
	<div class="p-3 mb-2 bg-gradient-info text-white">.bg-gradient-info</div>
	<div class="p-3 mb-2 bg-gradient-light text-dark">.bg-gradient-light</div>
	<div class="p-3 mb-2 bg-gradient-dark text-white">.bg-gradient-dark</div>

### 显示属性 ###

- .d-{value} 对于 xs
- .d-{breakpoint}-{value}为sm，md，lg，和xl。

当值是一个：

- none
- inline
- inline-block
- block
- table
- table-cell
- table-row
- flex
- inline-flex

媒体查询具有给定断点或更大的屏幕宽度。例如，.d-lg-none将display: none;两个lg和xl屏幕。
示例

	<div class="d-inline p-2 bg-primary text-white">d-inline</div>
	<span class="d-block p-2 bg-dark text-white">d-block</span>

**隐藏元素**

要隐藏元素，只需使用.d-none类或其中一个.d-{sm,md,lg,xl}-none类进行任何响应式屏幕变化。

中型大型设备除外

|屏幕尺寸|类|
|----|-----|
|隐藏在所有人身上|.d-none|
|只在xs上隐藏|.d-none .d-sm-block|
|只隐藏在sm上|.d-sm-none .d-md-block|
|只在md上隐藏|	.d-md-none .d-lg-block|
|隐藏在lg上|	.d-lg-none .d-xl-block|
|只在xl上隐藏|	.d-xl-none|
|所有人都可以看到|	.d-block|
|仅在xs上可见|	.d-block .d-sm-none|
|仅在sm上可见	|.d-none .d-sm-block .d-md-none|
|仅在md上可见	|.d-none .d-md-block .d-lg-none|
|仅在lg上可见	|.d-none .d-lg-block .d-xl-none|
|仅在xl上可见	|.d-none .d-xl-block|

**显示在打印中**

	.d-print-none
	.d-print-inline
	.d-print-inline-block
	.d-print-block
	.d-print-table
	.d-print-table-row
	.d-print-table-cell
	.d-print-flex
	.d-print-inline-flex

### 嵌入功能 ###

通过创建可在任何设备上缩放的固有比率，根据父级的宽度创建响应式视频或幻灯片嵌入。

	规则被直接施加到<iframe>，<embed>，<video>，和<object>元件; .embed-responsive-item如果要匹配其他属性的样式
	
	<!-- 16by9纵横比 -->
	<div class="embed-responsive embed-responsive-16by9">
	  <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/zpOULjyy-n8?rel=0" allowfullscreen></iframe>
	</div>

### Flex ###

使用全套响应式Flexbox实用程序快速管理网格列，导航，组件等的布局，对齐和大小调整。对于更复杂的实现，可能需要自定义CSS。

	<div class="d-flex p-2">flexbox容器</div>
	
	<div class="d-inline-flex p-2">内联的flexbox容器</div>
	
	.d-flex
	.d-inline-flex
	.d-sm-flex
	.d-sm-inline-flex
	.d-md-flex
	.d-md-inline-flex
	.d-lg-flex
	.d-lg-inline-flex
	.d-xl-flex
	.d-xl-inline-flex

**设置方向**

	使用.flex-row设置在水平方向（在浏览器默认值），或者.flex-row-reverse开始从相对侧上的水平方向上
	使用.flex-column设定垂直方向，或者.flex-column-reverse 开始从相对侧上的垂直方向

也存在响应变化flex-direction。

	.flex-row
	.flex-row-reverse
	.flex-column
	.flex-column-reverse
	.flex-sm-row
	.flex-sm-row-reverse
	.flex-sm-column
	.flex-sm-column-reverse
	.flex-md-row
	.flex-md-row-reverse
	.flex-md-column
	.flex-md-column-reverse
	.flex-lg-row
	.flex-lg-row-reverse
	.flex-lg-column
	.flex-lg-column-reverse
	.flex-xl-row
	.flex-xl-row-reverse
	.flex-xl-column
	.flex-xl-column-reverse

**Justify content**
	
	<div class="d-flex justify-content-start">...</div>(浏览器默认)
	<div class="d-flex justify-content-end">...</div>
	<div class="d-flex justify-content-center">...</div>
	<div class="d-flex justify-content-between">...</div>
	<div class="d-flex justify-content-around">...</div>

也存在响应变化justify-content。

	.justify-content-start
	.justify-content-end
	.justify-content-center
	.justify-content-between
	.justify-content-around
	.justify-content-sm-start
	...
	.justify-content-md-start
	...
	.justify-content-lg-start
	...
	.justify-content-xl-start
	....

**对齐项目**

	<div class="d-flex align-items-start">...</div>
	<div class="d-flex align-items-end">...</div>
	<div class="d-flex align-items-center">...</div>
	<div class="d-flex align-items-baseline">...</div>
	<div class="d-flex align-items-stretch">...</div>(默认浏览器)
	
	响应式变化
	.align-items-sm-start
	.........

**自我对齐**

	<div class="align-self-start">Aligned flex item</div>
	<div class="align-self-end">Aligned flex item</div>
	<div class="align-self-center">Aligned flex item</div>
	<div class="align-self-baseline">Aligned flex item</div>
	<div class="align-self-stretch">Aligned flex item</div>(默认浏览器)
	
	响应式变化
	.align-self-sm-start
	......

**自动**

当您将弹性对齐与自动边距混合时，Flexbox可以做一些非常棒的事情。下面显示的是通过自动边距控制弹性项目的三个示例：默认（无自动边距），向右推两个项目（.mr-auto），向左推动两个项目（.ml-auto）。

	<div class="d-flex">
	  <div class="p-2">Flex item</div>
	  <div class="p-2">Flex item</div>
	  <div class="p-2">Flex item</div>
	</div>
	
	<div class="d-flex">
	  <div class="mr-auto p-2">Flex item</div>
	  <div class="p-2">Flex item</div>
	  <div class="p-2">Flex item</div>
	</div>
	
	<div class="d-flex">
	  <div class="p-2">Flex item</div>
	  <div class="p-2">Flex item</div>
	  <div class="ml-auto p-2">Flex item</div>
	</div>

**包裹**

更改Flex项目在Flex容器中的换行方式。选择完全没有包装（浏览器默认设置）.flex-nowrap，包装.flex-wrap，或反向包装.flex-wrap-reverse。

也存在响应变化flex-wrap。

	.flex-nowrap
	.flex-wrap
	.flex-wrap-reverse
	.flex-sm-nowrap
	.flex-sm-wrap
	.flex-sm-wrap-reverse
	.flex-md-nowrap
	.flex-md-wrap
	.flex-md-wrap-reverse
	.flex-lg-nowrap
	.flex-lg-wrap
	.flex-lg-wrap-reverse
	.flex-xl-nowrap
	.flex-xl-wrap
	.flex-xl-wrap-reverse

**order排序**

	<div class="d-flex flex-nowrap">
	  <div class="order-3 p-2">First flex item</div>
	  <div class="order-2 p-2">Second flex item</div>
	  <div class="order-1 p-2">Third flex item</div>
	</div>
	
	也存在响应变化order 0~12

**对齐内容**

使用align-content上Flexbox的容器公用事业柔性物品排列在一起的横轴。选择start（浏览器的默认）， ，end，center，between，around或stretch。为了演示这些实用程序，我们强制flex-wrap: wrap并增加了flex项的数量。

也存在响应变化align-content。

	.align-content-start
	.align-content-end
	.align-content-center
	.align-content-around
	.align-content-stretch
	.align-content-sm-start
	......

### 浮动float ###
使用我们的响应式float实用程序，在任何断点上切换浮动任何元素。

	<div class="float-left">在所有视口向左浮动</div><br>
	<div class="float-right">在所有视口向右浮动</div><br>
	<div class="float-none">禁止在所有视口浮动</div>

每个float值也存在响应变化。
	
	.float-left
	.float-right
	.float-none
	.float-sm-left
	.float-sm-right
	.float-sm-none
	.float-md-left
	.float-md-right
	.float-md-none
	.float-lg-left
	.float-lg-right
	.float-lg-none
	.float-xl-left
	.float-xl-right
	.float-xl-none

### 图像替换 ###

使用图像替换类交换背景图像的文本

示例

	<h1 class="text-hide" style="background-image: url('/assets/brand/bootstrap-solid.svg'); width: 50px; height: 50px;">Bootstrap</h1>

### 定位 ###

	<div class="position-static">...</div>
	<div class="position-relative">...</div>
	<div class="position-absolute">...</div>
	<div class="position-fixed">...</div>
	<div class="position-sticky">...</div>

固定顶部

	<div class="fixed-top">...</div>

固定底部

	<div class="fixed-bottom">...</div>

粘性顶部

	<div class="sticky-top">...</div>

### screenreader屏幕阅读器 ###

隐藏所有设备的元素，除了屏幕阅读器使用.sr-only。结合.sr-only使用.sr-only-focusable时可以再次显示元素（例如，仅限键盘用户）。也可以用作mixins。

	<a class="sr-only sr-only-focusable" href="#content">Skip to main content</a>
	
	// Usage as a mixin
	.skip-navigation {
	  @include sr-only;
	  @include sr-only-focusable;
	}

### 设置元素的宽高 ###


	<div class="w-25 p-3" style="background-color: #eee;">宽度比例 25%</div>
	<div class="w-50 p-3" style="background-color: #eee;">Width 50%</div>
	<div class="w-75 p-3" style="background-color: #eee;">Width 75%</div>
	<div class="w-100 p-3" style="background-color: #eee;">Width 100%</div>
	
	<div style="height: 100px; background-color: rgba(255,0,0,0.1);">
	  <div class="h-25 d-inline-block" style="width: 120px; background-color: rgba(0,0,255,.1)">高度比例 25%</div>
	  <div class="h-50 d-inline-block" style="width: 120px; background-color: rgba(0,0,255,.1)">Height 50%</div>
	  <div class="h-75 d-inline-block" style="width: 120px; background-color: rgba(0,0,255,.1)">Height 75%</div>
	  <div class="h-100 d-inline-block" style="width: 120px; background-color: rgba(0,0,255,.1)">Height 100%</div>
	</div>

最大宽高max

	<div style="height: 100px; background-color: rgba(255,0,0,0.1);">
	  <div class="mh-100" style="width: 100px; height: 200px; background-color: rgba(0,0,255,0.1);">Max-height 100%</div>
	</div>

### Spacing ###

- m - 对于设置的类 margin
- p - 对于设置的类 padding

方位

- t- 对于设置margin-top或的类padding-top
- b- 对于设置margin-bottom或的类padding-bottom
- l- 对于设置margin-left或的类padding-left
- r- 对于设置margin-right或的类padding-right
- x- 对于设置*-left和的类*-right
- y- 对于设置*-top和的类*-bottom
- 空白 - 用于设置元素的所有4个边margin或padding在其上的类

大小

- 0- 对于消除margin或padding通过设置它的类0
- 1- （默认情况下）的类时，设置margin或padding以$spacer * .25
- 2- （默认情况下）的类时，设置margin或padding以$spacer * .5
- 3- （默认情况下）的类时，设置margin或padding以$spacer
- 4- （默认情况下）的类时，设置margin或padding以$spacer * 1.5
- 5- （默认情况下）的类时，设置margin或padding以$spacer * 3
- auto- 对于设置margin为auto的类


**水平居中**

	<div class="mx-auto" style="width: 200px;">
	  Centered element
	</div>

内容display: block和width设定由水平边缘设置auto。


### 文本 ###

文本对齐

	.text-justify 
	.text-left
	.text-right
	.text-center

文字包装和溢出

	.text-truncate类以使用省略号截断文本

文字转换

	<p class="text-lowercase">小写转换</p>
	<p class="text-uppercase">大写转换</p>
	<p class="text-capitalize">首字母大写</p>

字体粗细和斜体

	<p class="font-weight-bold">字体加粗</p>
	<p class="font-weight-normal">正常字体</p>
	<p class="font-weight-light">重量轻的文字</p>
	<p class="font-italic">斜体文字</p>


### 垂直对齐 ###

轻松更改内联，内联块，内联表和表格单元格的垂直对齐方式

	选择.align-baseline，.align-top，.align-middle，.align-bottom，.align-text-bottom，和.align-text-top需要

### 能见度 ###

	<div class="visible">...</div>
	<div class="invisible">...</div>
