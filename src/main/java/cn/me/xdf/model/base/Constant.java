package cn.me.xdf.model.base;

public class Constant {
	/**
	 * 素材类型：
	 * 01视频
	 */
	public static final String MATERIAL_TYPE_VIDEO = "01";
	/**
	 * 素材类型：
	 * 02音频
	 */
	public static final String MATERIAL_TYPE_AUDIO = "02";
	/**
	 * 素材类型：
	 * 03图片
	 */
	public static final String MATERIAL_TYPE_PIC = "03";
	/**
	 * 素材类型：
	 * 04文档
	 */
	public static final String MATERIAL_TYPE_DOC = "04";
	/**
	 * 素材类型：
	 * 05幻灯片
	 */
	public static final String MATERIAL_TYPE_PPT = "05";
	/**
	 * 素材类型：
	 * 06网页(链接)
	 */
	public static final String MATERIAL_TYPE_HTML = "06";
	/**
	 * 素材类型：
	 * 07富文本
	 */
	public static final String MATERIAL_TYPE_RICHTEXT  = "07";
	/**
	 * 素材类型：
	 * 08测试
	 */
	public static final String MATERIAL_TYPE_TEST = "08";
	/**
	 * 素材类型：
	 * 09测评
	 */
	public static final String MATERIAL_TYPE_ASSESSMENT = "09";
	/**
	 * 素材类型：
	 * 10作业包
	 */
	public static final String MATERIAL_TYPE_JOBPACKAGE = "10";
	/**
	 * 素材类型：
	 * 11日程
	 */
	public static final String MATERIAL_TYPE_SCHEDULE = "11";
	
	/**
	 * 试题类型：
	 * 1单选
	 */
	public static final Integer EXAM_QUESTION_SINGLE_SELECTION = 1;
	
	/**
	 * 试题类型：
	 * 2多选
	 */
	public static final Integer EXAM_QUESTION_MULTIPLE_SELECTION = 2;
	
	/**
	 * 试题类型：
	 * 3填空
	 */
	public static final Integer EXAM_QUESTION_CLOZE = 3;
	
	/**
	 * 存储方式：
	 * 01数据库
	 */
	public static final String STORE_TYPE_DB = "01";
	
	/**
	 * 存储方式：
	 * 02磁盘
	 */
	public static final String STORE_TYPE_DISK = "02";
	
	/**
	 * 消息类型：01评论
	 */
	public static final String MESSAGE_TYPE_REVIEW = "01";
	
	/**
	 * 消息类型：02备课心情
	 */
	public static final String MESSAGE_TYPE_MOOD = "02";
	
	/**
	 * 消息类型：03系统消息
	 */
	public static final String MESSAGE_TYPE_SYS = "03";
	
	/**
	 * 消息类型：04评论回复
	 */
	public static final String MESSAGE_TYPE_REPLY = "04";
	
	/**
	 * 课程模板状态：00草稿
	 */
	public static final String COURSE_TEMPLATE_STATUS_DRAFT = "00";
	
	/**
	 * 课程模板状态：01发布
	 */
	public static final String COURSE_TEMPLATE_STATUS_RELEASE = "01";
	
	/**
	 * 章节类型：0表示章
	 */
	public static final Integer CATALOG_TYPE_CHAPTER = 0;
	
	/**
	 * 章节类型：1表示节
	 */
	public static final Integer CATALOG_TYPE_LECTURE = 1;
	
	/**
	 * 素材综述类型：01表示下载
	 */
	public static final String MATERIALDISCUSSINFO_TYPE_DOWNLOAD = "01";
	
	/**
	 * 素材综述类型：02表示播放
	 */
	public static final String MATERIALDISCUSSINFO_TYPE_PLAY = "02";
	
	/**
	 * 素材综述类型：03表示攒
	 */
	public static final String MATERIALDISCUSSINFO_TYPE_LAUD = "03";
	
	/**
	 * 作业作答方式：
	 * 01上传作业
	 */
	public static final String TASK_TYPE_UPLOAD = "01";
	
	/**
	 * 作业作答方式：
	 * 02在线作答
	 */
	public static final String TASK_TYPE_ONLINE = "02";
	/**
	 * 查询方式:
	 * 01课程模版管理
	 */
	public static final String COUSER_TEMPLATE_MANAGE ="01";
	/**
	 * 查询方式:
	 * 02课程授权管理
	 */
	public static final String COUSER_AUTH_MANAGE ="02";
	
	/**
	 * 作业交互方式：
	 * 00未答
	 */
	public static final String TASK_STATUS_UNFINISH = "00";
	
	/**
	 * 作业交互方式：
	 * 01答完
	 */
	public static final String TASK_STATUS_FINISH = "01";
	
	/**
	 * 作业交互方式：
	 * 02驳回
	 */
	public static final String TASK_STATUS_REJECT = "02";
	
	/**
	 * 作业审批（代表当前记录已被导师审批）
	 */
	public static final String TASK_STATUS_CHECK = "05";
	
	/**
	 * 作业交互方式：
	 * 03未通过
	 */
	public static final String TASK_STATUS_FAIL = "03";
	
	/**
	 * 作业交互方式：
	 * 04通过
	 */
	public static final String TASK_STATUS_PASS = "04";
	
	/**
	 * 数据库操作方式：
	 * 01修改
	 */
	public static final String DB_UPDATE = "01";
	
	/**
	 * 数据库操作方式：
	 * 02删除
	 */
	public static final String DB_DELETE = "02";
	
	/**
	 * 数据库操作方式：
	 * 03插入
	 */
	public static final String DB_INSERT = "03";
	
	
	
	
}
