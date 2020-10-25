create table account
(
    id           text primary key,
    birth_year   bigint,
    date_created bigint,
    email        text,
    frist_name   text,
    last_name    text,
    password     text,
    "token"      text
)
    with comment = 'Account'
     and caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
     and compaction = {'max_threshold': '32', 'min_threshold': '4', 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy'}
     and compression = {'class': 'org.apache.cassandra.io.compress.LZ4Compressor', 'chunk_length_in_kb': '64'}
     and dclocal_read_repair_chance = 0.1;

insert into runme.account (id, birth_year, date_created, email, frist_name, last_name, password, token) values (01042001, 2001, null, 'duyanh@nguyen.com', 'Duy Anh', 'Nguyen', '202cb962ac59075b964b07152d234b70', '8249218621e2872c7363758e4661dada');
