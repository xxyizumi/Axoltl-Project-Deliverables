package com.example.mybatisadvanced.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.jdbc.SQL;

import com.example.mybatisadvanced.entity.Product;

@Mapper
public interface ProductMapper {
    /**
     * 全商品を取得
     */
    @Select("SELECT id, name, price, stock, description, created_at, updated_at " +
            "FROM products ORDER BY id")
    @Results(id = "ProductResult", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "name", column = "name"),
        @Result(property = "price", column = "price"),
        @Result(property = "stock", column = "stock"),
        @Result(property = "description", column = "description"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<Product> findAll();
    
    /**
     * IDで商品を取得
     */
    @Select("SELECT * FROM products WHERE id = #{id}")
    @ResultMap("ProductResult")
    Product findById(@Param("id") Long id);
    
    /**
     * 名前で商品を検索（部分一致）
     */
    @Select("SELECT * FROM products WHERE name LIKE CONCAT('%', #{keyword}, '%')")
    List<Product> findByNameLike(@Param("keyword") String keyword);

    /**
     * 商品を登録
     * 
     * @Options(useGeneratedKeys=true, keyProperty="id")で
     * 自動生成されたIDをエンティティに設定
     */
    @Insert("INSERT INTO products (name, price, stock, description, created_at, updated_at) " +
            "VALUES (#{name}, #{price}, #{stock}, #{description}, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    /**
     * 商品を更新
     */
    @Update("UPDATE products SET name = #{name}, price = #{price}, stock = #{stock}, " +
            "description = #{description}, updated_at = CURRENT_TIMESTAMP WHERE id = #{id}")
    int update(Product product);

    /**
     * 在庫を更新
     */
    @Update("UPDATE products SET stock = #{stock}, updated_at = CURRENT_TIMESTAMP WHERE id = #{id}")
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);

    /**
     * 商品を削除
     */
    @Delete("DELETE FROM products WHERE id = #{id}")
    int delete(@Param("id") Long id);

    @SelectProvider(type = ProductSqlProvider.class, method = "findByCondition")
    List<Product> findByCondition(@Param("name") String name, @Param("minPrice") Integer minPrice);

    // SQLプロバイダークラス
    public class ProductSqlProvider {
        public String findByCondition(@Param("name") String name, @Param("minPrice") Integer minPrice) {
            return new SQL() {{
                SELECT("*");
                FROM("products");
                if (name != null) {
                    WHERE("name LIKE CONCAT('%', #{name}, '%')");
                }
                if (minPrice != null) {
                    WHERE("price >= #{minPrice}");
                }
            }}.toString();
        }
    }
}
