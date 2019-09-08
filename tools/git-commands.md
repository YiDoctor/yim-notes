# 【快速入门Git命令】 #

工作中用不到git，一直都是用的SVN，但是其实我接住git的时间要比SVN要早许多，那是为了用HEXO框免费架搭建自己的个人博客，才接触的，git和github，这个快速手册基本上也是够自己用的了。

创建本地仓

    git init
查看本地仓状态

    git status
添加

    git add 命令是为了让文件添加到Git暂存区
    git add -A  提交所有变化
    git add -u  提交被修改(modified)和被删除(deleted)文件，不包括新文件(new)
    git add .  提交新文件(new)和被修改(modified)文件，不包括被删除(deleted)文件
提交(快照)

    git commit -m "你的注释"  将暂存区的的文件提交到当前分支


远程仓

    本地Git仓库和GitHub仓库之间的传输是通过SSH加密的pull & push

上传本地仓库内容至远程 

    git push -u origin master 
    第一次推送master分支时，加上了-u参数，在以后的推送或者拉取时就可以简化命令。

拉取远程仓库中别人的修改 

    git pull origin master
查看差异

    查看在最后一次提交之后的所有变更git differ HEAD     
    查看添加（add）而并未提交（commit）的变更 git differ --staged
撤销

    git reset
分支操作

    创建分支clean_up： git branch clean_up 
    跳转分支clean_up：git checkout clean_up 
    删除分支中所有.txt类型文件：git rm '*.txt' 

在分支进行删除操作依旧需要提交快照：

    git commit -m "Remove all the cats"

合并（merge）
    
    跳转回master分支，发现*.txt类型文件依旧存在：git branch master
    
    执行合并，将在clean_up分支上的修改合并到master上：git merge clean_up 
    删除分支clan_up：git branch -d clean_up
    完成远程仓库git push  

克隆远程仓库内容    
    
    git clone "路径地址"
