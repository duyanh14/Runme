insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x0408401BD499312E4A789654D40F4162', null, 'SELECT function_name AS name,
       argument_types,
       argument_names,
       body,
       called_on_null_input,
       language,
       return_type
FROM system_schema.functions
/* FROM system.schema_functions
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x876C27B9DFC2D41641F10E9006B6BE75', 'projecta', 'SELECT type_name AS name, field_names, field_types
FROM system_schema.types
/* FROM system.schema_usertypes
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xD9F348FBC3FD2BF7BE5C3420192A06A6', 'puinit', 'SELECT function_name AS name,
       argument_types,
       argument_names,
       body,
       called_on_null_input,
       language,
       return_type
FROM system_schema.functions
/* FROM system.schema_functions
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xB72AD6BCCFF7BCD55F5090B02279666E', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''duyanh'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x7EC3D770917447403029A50F61AEA220', 'projecta', 'SELECT keyspace_name, durable_writes, toJson(replication) as replication
FROM system_schema.keyspaces');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xD203957CE92C0B9D6F06239C9FEF7BCD', 'puinit', 'SELECT role AS name, can_login, is_superuser
FROM system_auth.roles');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x19FC22EDB268F5F94CBFACDF4EBC8CFC', 'puinit', 'SELECT keyspace_name, durable_writes, toJson(replication) as replication
FROM system_schema.keyspaces');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x2BDE165DE2F24928EEA38EBAC3556425', 'projecta', 'SELECT function_name AS name,
       argument_types,
       argument_names,
       body,
       called_on_null_input,
       language,
       return_type
FROM system_schema.functions
/* FROM system.schema_functions
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xD29FCE68807BB65CC4DA6EAEF2C6D83D', 'puinit', 'SELECT aggregate_name AS name,
       argument_types as signature,
       /* signature,
       */
       final_func,
       return_type,
       state_func,
       state_type
FROM system_schema.aggregates
/* FROM system.schema_aggregates
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xAE4B8705A186DFE4A67AF02DD1014F7D', null, 'SELECT * from system.local;');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x4270E770868E0FDCDDABAD5935B74305', null, 'SELECT aggregate_name AS name,
       argument_types as signature,
       /* signature,
       */
       final_func,
       return_type,
       state_func,
       state_type
FROM system_schema.aggregates
/* FROM system.schema_aggregates
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x5E0538B6C11E4A99DB205EAC3C695766', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''keyspace_name'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x937750BA2AF751539A301642F07A332B', 'projecta', 'SELECT column_name as name, table_name, type, kind, clustering_order, position
FROM system_schema.columns
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x6444E1D1880EBE22CD5FD7FD310435BB', null, 'SELECT role AS name, can_login, is_superuser
FROM system_auth.roles');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x3F89076F7EB6EE63E223B65CE3559285', null, 'SELECT table_name AS name,
       comment,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance
FROM system_schema.tables
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xFF989E3AF830F4B89EDD463127B20C23', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''system_schema'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x7EBF3B7FAAFCD5A1C183197B84D64C7D', 'puinit', 'SELECT column_name as name, table_name, type, kind, clustering_order, position
FROM system_schema.columns
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xCDAC633D2DE4CE77B916C9DF668C182F', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''system_traces'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xFCA1C4BC9E4BFA333AB6D8A2EC57DD3F', 'runme', 'UPDATE runme.script SET language = ? WHERE id = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x24FCFEF249CE6440811DDA723E0824BF', 'puinit', 'SELECT trigger_name AS name,
       table_name,
       toJson(options) as options
       /* columnfamily_name as table_name,
       trigger_options as options
       */
FROM system_schema.triggers
/* FROM system.schema_triggers
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x4D32DE15E35C5A8D7AA6DD93FCCEF47B', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''projecta'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xA6CC412E558ECD437BFF5CB4CAE20921', 'projecta', 'SELECT table_name AS name,
       comment,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance
FROM system_schema.tables
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xA931B9455DC4715DEF83B5055D4BF9B1', 'projecta', 'SELECT view_name AS name,
       comment,
       base_table_name,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance,
       where_clause
FROM system_schema.views
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x9C36B713E0454B9B6F95D24A31818F6F', 'puinit', 'SELECT view_name AS name,
       comment,
       base_table_name,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance,
       where_clause
FROM system_schema.views
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x8099DEF384FD0757398C4EC6BD1D3C70', 'runme', 'INSERT INTO runme.script (id, account, content, date_created, date_modified, language, name) VALUES (?, ?, ?, ?, ?, ?, ?)');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x7628F4447397FA6D777EA07712831184', 'puinit', 'SELECT type_name AS name, field_names, field_types
FROM system_schema.types
/* FROM system.schema_usertypes
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xC2FAF7D12A7F8DD8425575BAD9430E70', 'projecta', 'SELECT index_name AS name, table_name, kind, toJson(options) as options
FROM system_schema.indexes
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x22E5C373298F286560BD62A397C92203', 'projecta', 'select release_version from system.local');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x6E027226E19FCBC6F72C75FC78AE72F2', null, 'SELECT column_name as name, table_name, type, kind, clustering_order, position
FROM system_schema.columns
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x81B67767210EDF7E7E1806F3C748A3A4', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''system_auth'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x8A7B41FFC467AD96C7114928CDC7BF3C', 'projecta', 'SELECT trigger_name AS name,
       table_name,
       toJson(options) as options
       /* columnfamily_name as table_name,
       trigger_options as options
       */
FROM system_schema.triggers
/* FROM system.schema_triggers
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x2D12C4F11EFAD48949C5F591B29A1B8B', 'projecta', 'SELECT aggregate_name AS name,
       argument_types as signature,
       /* signature,
       */
       final_func,
       return_type,
       state_func,
       state_type
FROM system_schema.aggregates
/* FROM system.schema_aggregates
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xC5E949A24AA06E72628EFDAA33AF4D46', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''system_distributed'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x873D02245082E4FD1C09690E1875A69B', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''system'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x8E86869BFC932F8816BEE2ABCEF5C485', 'projecta', 'SELECT role AS name, can_login, is_superuser
FROM system_auth.roles');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x7F17A4BBB0F7721EE6C28A01666E378A', null, 'select release_version from system.local');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x7B0C834C4B76487B4B1F9A45EF8E7B9E', null, 'SELECT index_name AS name, table_name, kind, toJson(options) as options
FROM system_schema.indexes
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x554B6B9E18A0A19E0602096D9B1627A9', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''ssssss'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xE2D3F9CED5C14E8F0E53E0ACBF535DC5', null, 'SELECT keyspace_name, durable_writes, toJson(replication) as replication
FROM system_schema.keyspaces');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x4F5182AD64A13CBE04E0C72E41BC28A6', null, 'SELECT view_name AS name,
       comment,
       base_table_name,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance,
       where_clause
FROM system_schema.views
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xB83DE12568BADED8DDDC6CA7E2323C92', null, 'SELECT trigger_name AS name,
       table_name,
       toJson(options) as options
       /* columnfamily_name as table_name,
       trigger_options as options
       */
FROM system_schema.triggers
/* FROM system.schema_triggers
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x2745C2BEF02E5DED350E0CDB797CDAF4', 'puinit', 'SELECT index_name AS name, table_name, kind, toJson(options) as options
FROM system_schema.indexes
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xC2621E7AED384E0A4F5C93B9EAECBEF6', null, 'SELECT type_name AS name, field_names, field_types
FROM system_schema.types
/* FROM system.schema_usertypes
*/
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x54E99802B64A548462F3A7B9486A70E2', 'runme', 'UPDATE runme.script SET content = ? WHERE id = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x2C3E86FF85F4BF3CCFD00134E9FF9494', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''puinit'';');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x69AD0BDBD087EE01F86A76FB9569F1E2', 'puinit', 'select release_version from system.local');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x8ED7C8E478449C0F7531CAC3A49EBC87', 'puinit', 'SELECT table_name AS name,
       comment,
       bloom_filter_fp_chance,
       toJson(caching) as caching,
       cdc, 
       toJson(compaction) as compaction,
       toJson(compression) as compression,
       crc_check_chance,
       dclocal_read_repair_chance,
       default_time_to_live,
       speculative_retry,
       gc_grace_seconds,
       max_index_interval,
       memtable_flush_period_in_ms,
       min_index_interval,
       read_repair_chance
FROM system_schema.tables
WHERE keyspace_name = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0xF8FDA4C35D33A69C175CAD97FE00AA13', 'runme', 'UPDATE runme.account SET email = ? WHERE id = ?');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x431E34DBBD8E1A1BDB1217DA63B6307E', null, 'SELECT * FROM system_schema.keyspaces;');
insert into system.prepared_statements (prepared_id, logged_keyspace, query_string) values ('0x6A49465FC0D07A767178B5B899749CAB', null, 'SELECT * FROM system_schema.tables WHERE keyspace_name = ''runme'';');
