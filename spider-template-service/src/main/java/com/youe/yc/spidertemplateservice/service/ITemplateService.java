package com.youe.yc.spidertemplateservice.service;


import com.youe.yc.spidertemplateservice.entity.Template;

public interface ITemplateService {
	
	/**
	 * 获取模板id
	 * @param id
	 * @return
	 */
	Template getTemplate(String id);
	
	/**
	 * 保存模板信息
	 * @param template
	 */
	void save(Template template);

	/**
	 * 更新模板信息
	 * @param template
	 */
//	void update(Template template);
}
