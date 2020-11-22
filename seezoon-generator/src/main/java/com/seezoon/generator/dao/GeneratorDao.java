package com.seezoon.generator.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.seezoon.generator.dto.db.DbTable;
import com.seezoon.generator.dto.db.DbTableColumn;

/**
 * 获取表信息DAO
 *
 * 在web端配置需要引入该模块，所以没有把sql写在xml中，减少一个冗余配置
 *
 * @author hdf
 */
@Mapper
@Repository
public interface GeneratorDao {
    // @formatter:off
    @Select({"<script>",
            "select table_name name, table_comment comment from information_schema.tables",
            "<where>",
            "table_schema = (select database())",
            "<if test='tableName != null'>",
            "and table_name = upper(#{tableName})",
            "</if>",
            "</where>",
            "order by create_time desc",
            "</script>"})
    public List<DbTable> findTable(String tableName);

    @Select({"<script>",
            "select",
            "t.column_name name,",
            "if(t.is_nullable = 'yes' , true , false) nullable,",
            "(t.ordinal_position * 10) sort ,",
            "t.column_comment comment ,",
            "t.data_type dataType ,",
            "t.character_maximum_length maxLength,",
            "t.column_type columnType,",
            "t.column_key columnKey,",
            "t.extra extra",
            "from",
            "information_schema.`columns` t",
            "where table_name = upper(#{tableName}) and t.table_schema = (select database())",
            "order by t.ordinal_position asc",
            "</script>"})
    public List<DbTableColumn> findColumnByTableName(String tableName);

    @Select({"<script>",
            "select t.data_type dataType",
            "from information_schema.`columns` t",
            "where table_name = upper(#{tableName}) and t.table_schema = (select database()) and t.column_key = 'PRI'",
            "order by t.ordinal_position asc limit 0,1",
            "</script>"})
    public String findPkType(String tableName);
    // @formatter:on
}
