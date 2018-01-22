### Query VS Filter

> query:计算每个document相对于搜索条件的相关度并按相关度排序

> filter:仅仅只是根据搜索条件过滤出匹配的数据,不计算相关度


range可以放在query里面(会影响相关度),需要放在constant_score下面,也可以放在filter里面(不影响相关度).--50讲