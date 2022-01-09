# StregnthPlus

## 介绍
一个简单轻量的java强化插件，通过为物品添加或删除lore来进行物品的强化，当玩家进行攻击的时候进行伤害判断。
如果存在强化等级则进行相应的加伤，反之进行相应的减伤操作。来实现物品强化的效果。如果物品超过8级，那么如果
强化并非为保护强化，那么该物品在强化失败的时候会破碎消失（这是不可逆转的，请注意！）。每级强化等级允许
配置自己想要的伤害加成和减免。如果防御溢出那么允许配置最低伤害值保证不完全无伤。

注意！伤害和防御数值请自行均衡，如果出现不平衡问题概不处理，本插件默认数值仅供参考。

### 软件架构
#### 1.本地文件
    一、ConfigFactory
    该插件使用了原生Spigot中的FileConfiguration类，自定义了一个configFactory进行本地配
    置文件的读取默认情况下，配置文件基本上是不存在错误的，如果存在，请删除配置文件按照论坛
    中的默认配置文件进行配置或者删除您的配置文件使用/sp reload 指令 重新加载配置文件。
    configFactory中定义了四个Bean对象，将需要读取的参数封装到了其bean中。
    使用StrengthExtra类进行整合。
#### 2.强化模块

    一、dao层：
        1.strengthDao
        strengthDao中实现了物品的强化方法，进行物品的lore增加和基础的强化强化功能，同时
        实现强化公告的播报功能。还实现了强化石物品的给与功能。
    二、service层：
        1.strengthService
        strengthService中实现复杂的逻辑判断，执行dao层方法，进行各种各样的判断和逻辑处
        理，将强化是否可行，强化石对玩家背包填充，强化物品检索等等功能，最终交由
        commandHander进行处理调用。
    三、CommandHander层：
        在此，进行全方位的命令整合，执行service中的各种方法，处理一些简单的问题所在。将
        结果传回到玩家，并将结果返回。
#### 3.伤害监听
    该插件会获取伤害参数进行监听，并加上物品获取到的物品等级来进行伤害参数的获取，如果
    等级获取的伤害数值低于防御数值，那么会按照最低伤害进行伤害附加，反之直接进行附加。
#### 4.默认utils
    在插件开发中，我封装了一些默认的开发Utils帮助进行二次开发，或者进行自己想要的修改
    来方便个人进行变更。都是一些简单的小工具，希望能帮到你们。
### 安装教程
1.  将插件移动到服务器的plugin文件夹下
2.  启动/重启服务器
3.  进行使用

### 使用说明
#### 1.插件权限
    strength.admin (用于使用各种管理员指令)
#### 2.插件指令
    /sp 用于展示强化menu信息
    /sp normal 进行普通物品强化
    /sp safe 进行保护强化，被保护强化的物品不会因为超过八级而破碎，需要保护强化石
    /sp success 必定成功的强化指令，需要必定成功强化石
    -------------------下面的指令需要strength.admin权限----------------------
    给与指令：（如果超出则会发送超出数值）
    /sp normalstone amount 给与普通强化石指令，amount为数量
    /sp safestone amount 给与保护强化石指令，amount为数量
    /sp successstone amount 给与必定成功强化石指令，amount为数量
    /sp admin 直接将物品强化到10级，注意，会发送广播公告，并且物品等级颜色不同于
    普通强化颜色。
    /sp reload 进行配置文件的重载
#### 3.配置文件范例
    #强化插件配置文件
    # 剑的每级伤害（近战）
    sword: 1.5
    # 弓箭的每级伤害
    bow: 1.5
    # 弩的每级伤害
    crossbow: 1.3
    # 护甲强化一级抵抗的伤害数值
    defence: 0.5
    # 当防御伤害高于攻击伤害（强化等级过高导致不掉血的刮痧伤害）
    min_damage: 0.2
    strength_stone:
    stone_normal:
    #配置物品类型
    item: SPONGE
    #配置强化石名字
    name: '&b强化石'
    #配置强化石lore，允许多行lore
    lore:
    - '&a用于强化武器和防具的好东西'
    stone_safe:
    # 允许填入物品类型
    item: PAPER
    name: '&c保护强化卷'
    # 配置是否允许保护石进行保护
    safe: true
    lore:
    - '&b用于保护超过&c[&a8&c]&b级之后的强化不导致武器破碎'
    stone_success:
    item: EMERALD
    name: '&e必定成功强化石'
    # 配置是否允许必定成功石成功
    success: true
    lore:
    - '&6必定成功的强化石，只要使用它，那么这次强化必定会成功'
    # 强化几率，总共十级，如果超出不会读取
    strength_chance:
    - 100
    - 90
    - 80
    - 70
    - 60
    - 50
    - 40
    - 30
    - 20
    - 10
    #强化名称，必须要对的上物品名
    itemName:
    - 'WOODEN_SWORD'
    - 'STONE_SWORD'
    - 'IRON_SWORD'
    - 'GOLDEN_SWORD'
    - 'DIAMOND_SWORD'
    - 'NETHERITE_SWORD'
    - 'WOODEN_AXE'
    - 'STONE_AXE'
    - 'IRON_AXE'
    - 'GOLDEN_AXE'
    - 'DIAMOND_AXE'
    - 'NETHERITE_AXE'
    - 'BOW'
    - 'CROSS_BOW'
    - 'DIAMOND_HELMET'
    - 'DIAMOND_CHESTPLATE'
    - 'DIAMOND_LEGGINGS'
    - 'DIAMOND_BOOTS'
    - 'NETHERITE_HELMET'
    - 'NETHERITE_CHESTPLATE'
    - 'NETHERITE_LEGGINGS'
    - 'NETHERITE_BOOTS'
    - 'IRON_HELMET'
    - 'IRON_CHESTPLATE'
    - 'IRON_LEGGINGS'
    - 'IRON_BOOTS'
    - 'GOLDEN_HELMET'
    - 'GOLDEN_CHESTPLATE'
    - 'GOLDEN_LEGGINGS'
    - 'GOLDEN_BOOTS'

### 参与贡献
Raven(独自开发了整个项目，并完善了代码)