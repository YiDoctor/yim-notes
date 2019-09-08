# deepin&Manjaro #

## 一  使用前的基本设置

### 1.1 初次启动卡死开机画面

原因是因为笔记本双显卡的问题，开机E 设置quiet后加 nouveau.modeset=0 禁用独显 F10保存

进入之后设置开机启动文件/boot/grub/grub.cfg 永久修改

### 1.2 设置常用网站DNS加速

http://tool.chinaz.com/dns?type=1&host=github.com&ip=

在上面网站中查找需要加速的网站的DNS地址:

​	如 github.com

并在windows或Linux下的hosts文件中这是DNS加速地址:

windows下地址: Windows/system32/drivers/etc/hosts

Linux下地址：etc/hosts

### 1.3 设置su免密

```sh
sudo vim /etc/pam.d/su
```

直接将如下两行内容取消注释

```sh
auth           sufficient      pam_wheel.so trust use_uid
auth           required        pam_wheel.so use_uid
```

deepin中没有wheel群组需要创建

```sh
id user_name

sudo  groupadd wheel #如果不存在wheel群组,新增

sudo usermod  -g wheel yim  #变更当前用户所在群组
```

### 1.4 设置sudo免密

```
sudo vim /etc/sudoers
```

在文档的最后一行

```sh
#manjaro中
%user_name ALL=(ALL) NOPASSWD: ALL
#deepin中
user_name ALL=(ALL) NOPASSWD: ALL
```

## 二 配置国内镜像源(manjaro)

#### 2.1 选择国内源 ####

```sh
sudo  pacman-mirrors -i -c China -m rank  # 勾选ustc科大源
```

#### 2.2 添加archlinuxcn库和antergos库 ####

```sh
$ sudo vim /etc/pacman.conf
# 在文件末尾添加以下两行
[archlinuxcn]
SigLevel = TrustAll
Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch

[antergos]
SigLevel = TrustAll
Server = https://mirrors.ustc.edu.cn/antergos/$repo/$arch
```

或者使用echo提取字符串，使用管道符将上一个命令的输出作为输入（tee指令会从标准输入设备读取数据，将其内容输出到标准输出设备，同时保存成文件 参数 -a 为追加的意思 ）

```sh
echo -e "\n[archlinuxcn]\nSigLevel = TrustAll\nServer = https://mirrors.ustc.edu.cn/archlinuxcn/\$arch\n\n[antergos]\nSigLevel = TrustAll\nServer = https://mirrors.ustc.edu.cn/antergos/\$repo/\$arch\n"|sudo tee -a /etc/pacman.conf
```

#### 2.3 安装archlinuxcn antergos签名钥匙 ####

```sh
sudo pacman -Sy --noconfirm archlinuxcn-keyring antergos-keyring
```

## 三  软件安装(manjaro) ##

#### 3.1 安装yay(兼容pacman，yaourt 推荐使用yay安装软件，安装时不使用sudo) ####

```sh
sudo pacman -S --noconfirm yay
```

#### 3.2 安装yaourt ####

manjaro有自己的图形化包管理器,pamac，当然也可以命令行使用archlinux系的，还有AUR助手 yaourt 更方便。Yaourt可用于查找软件包(包括[core] [extra] [community] AUR的软件包，pacman只能查找非AUR的软件包)

```sh
sudo pacman -S --noconfirm yaourt
```

### 3.3 家目录改为英文

```sh
sudo pacman -S xdg-user-dirs-gtk

export LANG=en_US

xdg-user-dirs-gtk-update

#然后会有个窗口提示语言更改，更新名称即可

export LANG=zh_CN.UTF-8
```

### 3.4 pacman命令

Pacman是Arch Linux 的包管理器。它将一个简单的二进制包格式和易用的构建系统结合了起来。不管软件包是来自官方的 Arch 库还是用户自己创建，Pacman 都能方便得管理。

更新系统
在 Archlinux 中，使用一条命令即可对整个系统进行更新：

```
pacman -Syu
```

如果你已经使用pacman -Sy将本地的包数据库与远程的仓库进行了同步，也可以只执行：pacman -Su

**安装包**
➔ pacman -S 包名：例如，执行 pacman -S firefox 将安装 Firefox。你也可以同时安装多个包，
只需以空格分隔包名即可。
➔ pacman -Sy 包名：与上面命令不同的是，该命令将在同步包数据库后再执行安装。
➔ pacman -Sv 包名：在显示一些操作信息后执行安装。
➔ pacman -U：安装本地包，其扩展名为 pkg.tar.gz。
➔ pacman -U http://www.example.com/repo/example.pkg.tar.xz 安装一个远程包（不在 pacman 配置的源里面）

**删除包**
➔ pacman -R 包名：该命令将只删除包，保留其全部已经安装的依赖关系
➔ pacman -Rs 包名：在删除包的同时，删除其所有没有被其他已安装软件包使用的依赖关系
➔ pacman -Rsc 包名：在删除包的同时，删除所有依赖这个软件包的程序
➔ pacman -Rd 包名：在删除包时不检查依赖。

**搜索包**
➔ pacman -Ss 关键字：在仓库中搜索含关键字的包。
➔ pacman -Qs 关键字： 搜索已安装的包。
➔ pacman -Qi 包名：查看有关包的详尽信息。
➔ pacman -Ql 包名：列出该包的文件。

**其他用法**
➔ pacman -Sw 包名：只下载包，不安装。
➔ pacman -Sc：清理未安装的包文件，包文件位于 /var/cache/pacman/pkg/ 目录。
➔ pacman -Scc：清理所有的缓存文件。

## 四 通用安装 ##

### 4.1 安装zsh

```sh
yay -S --noconfirm zsh

sh -c "$(curl -fsSL https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh)" # 下载并配置ohmyzsh

chsh -s /bin/zsh # 更改默认的shell
```

### 4.2 安装VMware

VMware官网提供了Linux版本,下载好之后只需要下面两部即可安装完成:

```sh
# 1.获取可执行权限
sudo chmod +x VMware-Workstation-Full-15.0.2-10952284.x86_64.bundle 
# 2.执行安装
sudo ./VMware-Workstation-Full-15.0.2-10952284.x86_64.bundle 
```

### 4.3 其他实用安装

```sh
netease-cloud-music #网易云音乐

youdao-dict #网易有道词典

google-chrome #谷歌浏览器

masterpdfeditor #对linux用户免费的PDF浏览及编辑器，支持实时预览

Typora  #简介高效的 Markdown 编辑器

uget #媲美迅雷的下载工具

filezilla #强大的FTP工具

catfish #基于GTK+的非常快速，轻量级的文件搜索工具

peek #屏幕录像工具，小巧玲珑，可保存录像为gif动图和兼容于html5的webm视频

subversion  git # SVN git

eclipse-jee # eclipse
```

## 五 deepinN卡降频

修改启动配置文件：/etc/default/grub

```sh
# Written by com.deepin.daemon.Grub2
DEEPIN_GFXMODE_ADJUSTED=1
# 修改项
GRUB_CMDLINE_LINUX="splash quiet"
# 修改项
GRUB_CMDLINE_LINUX_DEFAULT="intel_pstate=disable"
GRUB_DEFAULT=2
GRUB_DISTRIBUTOR="`/usr/bin/lsb_release -d -s 2>/dev/null || echo Deepin`"
GRUB_GFXMODE=1920x1080
GRUB_THEME="/boot/grub/themes/deepin/theme.txt"
GRUB_TIMEOUT=5
```

