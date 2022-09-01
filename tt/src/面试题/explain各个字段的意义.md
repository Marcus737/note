# id
SQL查询中的序列号。id列数字越大越先执行，如果说数字一样大，那么就从上往下依次执行。
# select_type
查询的类型
- SIMPLE 简单SELECT(不使用UNION或子查询)
- PRIMARY 	最外层的SELECT 
# table
查询的表名。不一定是实际存在的表名。
# type
显示查询使用了何种类型。从最好到最差的连接类型依次为：
system，const，eq_ref，ref，fulltext，ref_or_null，index_merge，unique_subquery，index_subquery，range，index，ALL
# partitions
该列显示的为分区表命中的分区情况。非分区表该字段为空（null）。
# possible_keys
查询可能使用到的索引都会在这里列出来
# key
查询真正使用到的索引。
# key_len
查询用到的索引长度（字节数）
#  ref
如果是使用的常数等值查询，这里会显示const，如果是连接查询，被驱动表的执行计划这里会显示驱动表的关联字段，如果是条件使用了表达式或者函数，或者条件列发生了内部隐式转换，这里可能显示为func
# rows（重要）
rows 也是一个重要的字段。 这是mysql估算的需要扫描的行数（不是精确值）。
这个值非常直观显示 SQL 的效率好坏, 原则上 rows 越少越好
# filtered
这个字段表示存储引擎返回的数据在server层过滤后，剩下多少满足查询的记录数量的比例，注意是百分比，不是具体记录数。这个字段不重要
# extra
- distinct 在select部分使用了distinc关键字
- Using filesort 当 Extra 中有 Using filesort 时, 表示 MySQL 需额外的排序操作, 不能通过索引顺序达到排序效果. 一般有 Using filesort, 都建议优化去掉, 因为这样的查询 CPU 资源消耗大
- Using index “覆盖索引扫描”, 表示查询在索引树中就可查找所需数据, 不用扫描表数据文件, 往往说明性能不错
- Using temporary 查询有使用临时表, 一般出现于排序, 分组和多表 join 的情况, 查询效率不高, 建议优化.
