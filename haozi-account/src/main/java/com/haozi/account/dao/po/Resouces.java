package com.haozi.account.dao.po;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author haozi
 * @since 2022-04-16
 */
@Getter
@Setter
public class Resouces implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer resourcesId;

    /**
     * 资源名
     */
    private String resourceName;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 类型
     */
    @TableField("`type`")
    private Integer type;

    /**
     * 删除标识
     */
    private Integer isDel;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 资源标识
     */
    private String resourceFlag;


}
