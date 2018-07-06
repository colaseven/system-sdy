package com.sdy.modular.system.warpper;

import com.sdy.core.base.warpper.BaseControllerWarpper;
import com.sdy.core.common.constant.factory.ConstantFactory;
import com.sdy.core.util.ToolUtil;
import com.sdy.modular.system.model.Dict;

import java.util.List;
import java.util.Map;

/**
 * 字典列表的包装
 */
public class DictWarpper extends BaseControllerWarpper {

    public DictWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        StringBuffer detail = new StringBuffer();
        Integer id = (Integer) map.get("id");
        List<Dict> dicts = ConstantFactory.me().findInDict(id);
        if (dicts != null) {
            for (Dict dict : dicts) {
                detail.append(dict.getNum() + ":" + dict.getName() + ",");
            }
            map.put("detail", ToolUtil.removeSuffix(detail.toString(), ","));
        }
    }

}
