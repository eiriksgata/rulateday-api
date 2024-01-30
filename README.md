## Rulateday-api

**本项目是为mirai-rulateday-dice项目提供云端服务的API**
前端的开源项目为 rulateday-api-vue-client

### 主要用途

1. 提供一个用户提交数据的平台服务。
2. 用于收集机器人抛出的特殊异常错误。
3. 用于处理收录消息情况。
4. API平台上的查询数据会更为强大，其中DND5e将会加入更多的拓展包内容查询。
5. 作为指令逻辑业务处理平台。
6. 根据RBAC设计，控制访问权限
7. 一些工具集成（FF14的幻卡模拟器等）。

**拓展内容：**

- [ ] 更多的法术列表
- [ ] 拓展职业
- [ ] 拓展魔法物品
- [ ] 拓展种族
- [ ] xge物品
- [ ] EGtW内容
- [ ] 一些module物品

### 技术

- SpringBoot 2.x - derivation(Web|Security|Other....)
- jdk8 (因为Heroku机器java version为1.8)
- SQLite + Mybatis plus | other..

