package com.youe.yc.spiderservice.core.request;


import com.youe.yc.spiderclient.template.entity.Template;
import us.codecraft.webmagic.Request;

public class TypeRequest extends Request implements Type{

	private static final String KEY_TEMPLATE_ID = "KEY_TEMPLATE_ID";
	private static final String KEY_TASK_ID = "KEY_TASK_ID";
	private static final String KEY_DEPTH = "KEY_DEPTH";
	private static final String KEY_TEMPLATE_DATA = "KEY_TEMPLATE_DATA";
	
	private RequestType requestType;
	
	private TypeRequest(TypeRequestBuilder builder){
		super(builder.url);
		
		this.requestType = builder.requestType;
		setTemplateId(builder.templateId);
		setTemplateData(builder.template);
		setTaskId(builder.taskId);
		setDepth(builder.depth);
	}

	public void setTemplateId(String templateId) {
		put(KEY_TEMPLATE_ID,templateId);
	}
	
	public String getTemplateId(){
		return (String) getExtra(KEY_TEMPLATE_ID);
	}
	
	public void setTemplateData(Template template){
		if(template != null){
			putExtra(KEY_TEMPLATE_DATA, template);
			if(getTemplateId() == null){
				setTemplateId(template.getId());
			}
		}
	}
	
	public Template getTemplateData(){
		return (Template) getExtra(KEY_TEMPLATE_DATA);
	}
	
	public void setTaskId(String taskId) {
		put(KEY_TASK_ID, taskId);
	}
	
	public String getTaskId(){
		return (String) getExtra(KEY_TASK_ID);
	}
	
	public void setDepth(Integer depth) {
		put(KEY_DEPTH, depth);
	}
	
	public Integer getDepth(){
		return (Integer) getExtra(KEY_DEPTH);
	}

	@Override
	public RequestType getType() {
		return requestType;
	}
	
	private void put(String key, Object value) {
		if(value != null){
			putExtra(key, value);		
		}
	}
	
	public static class TypeRequestBuilder{
		private String url;
		private RequestType requestType;
		private String taskId;
		private String templateId;
		private Template template;
		private Integer depth;
		
		public TypeRequestBuilder(String url,RequestType requestType){
			this.url = url;
			this.requestType = requestType;
		}
		
		public TypeRequestBuilder taskId(String taskId){
			this.taskId = taskId;
			return this;
		}
		
		public TypeRequestBuilder templateId(String templateId){
			this.templateId = templateId;
			return this;
		}
		
		public TypeRequestBuilder template(Template template){
			this.template = template;
			return this;
		}
		
		public TypeRequestBuilder depth(Integer depth){
			this.depth = depth;
			return this;
		} 
		
		public TypeRequest build(){
			return new TypeRequest(this);
		}
	}
}
