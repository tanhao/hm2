
package com.xzy.pojo.ctrl.vo;

import com.xzy.pojo.ctrl.CategoryDO;
import com.xzy.pojo.ctrl.KindDO;
import com.xzy.pojo.ctrl.UserCategoryDO;
import com.xzy.pojo.ctrl.UserKindDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
public class CategoryVO extends KindVO {
    protected Map<Integer, List<CategoryDO>> categorys;
    protected  Map<Integer,? extends Map<Integer, UserCategoryDO>> userCategorys;
}