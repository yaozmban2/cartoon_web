package cn.yu.cartoon.cartoon_web.pojo.dto;

/**
 * cartoon_type_relation表的映射对象
 *
 * @author Yu
 * @version 1.0
 * @date 2019/3/16 0:05
 **/
public class CartoonTypeRelation {

    private Integer id;

    private Integer cartoonId;

    private Integer typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCartoonId() {
        return cartoonId;
    }

    public void setCartoonId(Integer cartoonId) {
        this.cartoonId = cartoonId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "CartoonTypeRelation{" +
                "id=" + id +
                ", cartoonId=" + cartoonId +
                ", typeId=" + typeId +
                '}';
    }
}
