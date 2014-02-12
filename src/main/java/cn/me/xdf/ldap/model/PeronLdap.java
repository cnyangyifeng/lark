package cn.me.xdf.ldap.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaobin268
 * Date: 13-12-13
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public class PeronLdap extends BaseModel {

    private static final long serialVersionUID = 3398977021142608598L;
    /**设备ID     guid*/
    private String userName;
    /**设备面膜  userPassword*/
    private String passWord;
    /**设备名称<deviceType>_<GUID>_<roomNumber>   cn*/
    private String deviceName;
    private String roomId;
    /**设备所在学校       o*/
    private String schoolCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSchoolCode() {
        return schoolCode;
    }
    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("objectclass","xdf-person");
        map.put("name_attribute", this.getUserName());

        return map;
    }

    @Override
    public void setMap(Map<String, String> map) {
        for(String mkey:map.keySet()){
            if (mkey.equals("cn")) {
                this.setDeviceName(map.get(mkey));

            } else if (mkey.equals("name_attribute")) {
                this.setUserName(map.get(mkey));

            }
        }
    }

    @Override
    public String getDN() {
        //自定义的组织dn
        return "cn=users";
    }
}
