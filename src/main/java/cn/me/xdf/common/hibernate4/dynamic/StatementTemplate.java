package cn.me.xdf.common.hibernate4.dynamic;

import freemarker.template.Template;

public class StatementTemplate {

	public StatementTemplate(TYPE type, Template template) {
		this.type = type;
		this.template = template;
	}

	private Template template;

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	private TYPE type;

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	enum TYPE {
		HQL, SQL
	}

}
