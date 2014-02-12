package cn.me.xdf.model.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import cn.me.xdf.common.utils.Identities;

/**
 * 统一定义id的entity基类
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. Oracle需要每个Entity独立定义id的SEQUCENCE时
 * 
 * @author xiaobin
 * 
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -977696724792206680L;

	protected String fdId;

	protected Integer version;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	@Column(length = 32)
	public String getFdId() {
		if (StringUtils.isBlank(fdId))
			fdId = Identities.generateID();
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	@Column(nullable = true)
	public Integer getVersion() {
		if (version == null) {
			version = 0;
		}
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
