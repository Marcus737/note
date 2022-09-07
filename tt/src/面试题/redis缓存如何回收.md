# 为啥要回收？
触发了最大内存的限制，根据策略进行回收
# 回收策略
- noeviction 不删除，直接返回错误
- volatile-ttl 在时间限制的键里回收存活时间短的过期键
- allkeys-lru 回收最少使用的键
- volatile-lru 在过期键里回收最少使用的键
- allkeys-random 随机回收
- volatile-random 过期的键里随机回收
- allkeys-lfu 回收使用频率最少的键
- volatile-lfu 在过期键里回收使用频率最少的键
