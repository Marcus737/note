# INNODB
- mysql的默认引擎，支持事务
- 处理大量短期事务时应用此引擎
# MyISM引擎
- mysql5.1之前的版本，默认的引擎
- 不支持事务
- 不支持行级锁，对整张表加锁，会出现性能问题

# Memory引擎
- 比MyISM快一个数量级
- 其结构重启后会保存，但数据会丢失

# Archive引擎
- 支持select和insert操作
- 会将使用数据用zlib压缩，减少磁盘io操作
- 每次select需要全表扫描

# CSV引擎
- 将普通的CSV文件作为MYSQL的表来处理
- 不支持索引