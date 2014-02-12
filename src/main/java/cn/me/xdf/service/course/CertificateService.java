package cn.me.xdf.service.course;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.me.xdf.service.BaseService;
import cn.me.xdf.service.bam.BamCourseService;
import cn.me.xdf.service.message.MessageService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 证书service
 * 
 * @author zhaoq
 * 
 */
@Service
@Transactional(readOnly = false)
public class CertificateService extends BaseService implements InitializingBean {

	private static final Logger LOGER = LoggerFactory
			.getLogger(CertificateService.class);

	/**
	 * 模板缓存
	 */
	protected Map<String, Template> templateCache;

	protected CertificateBuilder certificateBuilder;

	@Autowired
	public void setCertificateBuilder(CertificateBuilder certificateBuilder) {
		this.certificateBuilder = certificateBuilder;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		templateCache = new ConcurrentHashMap<String, Template>();
		certificateBuilder.init();
		Map<String, String> message = certificateBuilder.getMessages();
		Configuration configuration = new Configuration();
		configuration.setNumberFormat("#");
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		for (Entry<String, String> entry : message.entrySet()) {
			stringLoader.putTemplate(entry.getKey(), entry.getValue());
			templateCache.put(entry.getKey(), new Template(entry.getKey(),
					new StringReader(entry.getValue()), configuration));
		}
		configuration.setTemplateLoader(stringLoader);

	}

	protected String processTemplate(Template template,
			Map<String, ?> parameters) {
		StringWriter stringWriter = new StringWriter();
		try {
			template.process(parameters, stringWriter);
		} catch (Exception e) {
			LOGER.error("处理系统消息参数模板时发生错误：{}", e.toString());
			throw new RuntimeException(e);
		}
		return stringWriter.toString();
	}
	
	/**
	 * 得到证书Html
	 */
	public String getCertificateHtml(final String name,final Map<String, ?> parameters){
		Template template = templateCache.get(name);
        return processTemplate(template, parameters);
	}

	@Override
	public <T> Class<T> getEntityClass() {
		return null;
	}
}
