create table script
(
    id            text primary key,
    account       text,
    content       text,
    date_created  bigint,
    date_modified bigint,
    language      int,
    name          text
)
    with caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
     and compaction = {'max_threshold': '32', 'min_threshold': '4', 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy'}
     and compression = {'class': 'org.apache.cassandra.io.compress.LZ4Compressor', 'chunk_length_in_kb': '64'}
     and dclocal_read_repair_chance = 0.1;

insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('6d0a2db2d17a0a20902b91ca57209a10', 01042001, 'fb33934d74db776c5664a1dafb0ecbd934e7751520ce8634a57047d58616409be3c2a30bdc72914de5985e0c3a6d9a95de9f90c2793aba57781f042dfc05fce420d9f4179f0bc8b019bc6005f321', 1603017224, 1603017594, 1, 'Tinh chieu cao');