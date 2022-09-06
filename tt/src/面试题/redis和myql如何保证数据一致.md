# 先更新mysql，再更新redis
- 若redis更新失败，数据可能不一致
# 先删redis，更新mysql
- 高并发下性能较低
- 数据不一致，线程1删除缓存，在更新mysql时，线程2把旧数据读到缓存，数据会不一致
# 延时双删
先删除redis，再去更新mysql，延时几百毫秒后再删除redis的数据