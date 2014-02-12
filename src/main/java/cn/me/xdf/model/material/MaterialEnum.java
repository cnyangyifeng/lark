package cn.me.xdf.model.material;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-10-31
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 资源枚举类
 * 素材类型：
 * 01视频 attmain
 * 02音频 attmain  不管，没这个需求
 * 03图片 attmain  不管，没这个需求
 * 04文档 attmain
 * 05幻灯片  attmain
 * 06网页(链接)不管，没这个需求
 * 07富文本   不管，没这个需求
 * 08测试    questions
 * 09测评   不管，没这个需求
 * 10作业 work
 * 11日程  不管，没这个需求
 */
public enum MaterialEnum {

    m_01 {  //视频

        @Override
        public String getValue() {
            return "01";
        }

        @Override
        public String getBean() {
            return "materialAttmainService";
        }

        @Override
        public String getView() {
            return "video";
        }
    }, m_04 {
        @Override
        public String getValue() {
            return "04";
        }

        @Override
        public String getBean() {
            return "materialAttmainService";
        }

        @Override
        public String getView() {
            return "doc";
        }
    }, m_05 {
        @Override
        public String getValue() {
            return "05";
        }

        @Override
        public String getBean() {
            return "materialAttmainService";
        }

        @Override
        public String getView() {
            return "ppt";
        }
    }, m_07 {
        @Override
        public String getValue() {
            return "07";
        }

        @Override
        public String getBean() {
            return "materialAttmainService";
        }

        @Override
        public String getView() {
            return "txt";
        }
    }, m_08 {
        @Override
        public String getValue() {
            return "08";
        }

        @Override
        public String getBean() {
            return "materialQuestionsService";
        }

        @Override
        public String getView() {
            return "question";
        }
    }, m_10 {
        @Override
        public String getValue() {
            return "10";
        }

        @Override
        public String getBean() {
            return "materialTaskService";
        }

        @Override
        public String getView() {
            return "task";
        }
    };

    /**
     * 资源枚举字段值
     *
     * @return
     */
    public abstract String getValue();


    /**
     * bean 名称
     * return
     */
    public abstract String getBean();

    /**
     * 资源跳转页面
     *
     * @return
     */
    public abstract String getView();

}
