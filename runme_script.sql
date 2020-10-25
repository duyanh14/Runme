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

insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('9c11c937ac92ff30b24c8f2dddb66a7c', 01042001, '', 1602997101, 1602997101, 0, 'g');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('68b577e4356b15361163461cbe8a483a', 01042001, '', 1602996230, 1602996230, 0, 'hhg');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('d06758739910a3d26debbabbe9dc628c', 01042001, '', 1602997141, 1602997141, 0, 'h');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('4465e01372418c571d2f62beb47eed43', 01042001, '', 1602996300, 1602996300, 0, 'hhg');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('6d0a2db2d17a0a20902b91ca57209a10', 01042001, 'fb33934d74db776c5664a1dafb0ecbd934e7751520ce8634a57047d58616409be3c2a30bdc72914de5985e0c3a6d9a95de9f90c2793aba57781f042dfc05fce420d9f4179f0bc8b019bc6005f321', 1603017224, 1603017594, 1, 'Tinh chieu cao');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('ace27b20f58c3d2340a956640b8f89a5', 01042001, '', 1602996926, 1602996926, 0, 'b');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('a93e030a80067f90d4404760cb45dabd', 01042001, '', 1602996884, 1602996884, 0, 'g');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('77c691ef3679f2a5ea6a6af57cef2686', 01042001, '', 1602996299, 1602996299, 0, 'hhg');
insert into runme.script (id, account, content, date_created, date_modified, language, name) values ('e5bb4f1c9f1cec57f2438b4ec6ebf874', 01042001, '', 1602996297, 1602996297, 0, 'hhg');
