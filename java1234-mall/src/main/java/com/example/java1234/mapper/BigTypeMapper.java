package com.example.java1234.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.java1234.entity.BigType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BigTypeMapper extends BaseMapper<BigType>{
    /**
     * 商品大类Mapper接口
     */

        /**
         * 根据id查询商品大类
         * @param id
         * @return
         */
        public BigType findById(Integer id);
}
