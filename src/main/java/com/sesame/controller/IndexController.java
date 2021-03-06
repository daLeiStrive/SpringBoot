package com.sesame.controller;

import com.github.kevinsawicki.http.HttpRequest;
import com.sesame.framework.bean.GMap;
import com.sesame.framework.utils.StringUtil;
import com.sesame.framework.web.core.GStateData;
import com.sesame.framework.web.interceptor.annotation.AuthNonCheckRequired;
import com.sesame.framework.web.interceptor.annotation.LoginNonCheckRequired;
import lombok.extern.apachecommons.CommonsLog;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author johnny
 * @date 2017-06-10 13:10
 * @classpath com.sesame.controller
 * @Description:
 */
@CommonsLog
@Controller
@RequestMapping("/index")
public class IndexController {

    /**
     * 加载页面
     */
    @LoginNonCheckRequired
    @AuthNonCheckRequired
    @RequestMapping("/{uriPath}")
    public String index(@PathVariable("uriPath") String uriPath) {
        log.info("进入了page 界面>>>>" + uriPath);
        return uriPath;
    }

    @LoginNonCheckRequired
    @AuthNonCheckRequired
    @RequestMapping("/page404")
    public String page404() {

        return "error/404";
    }

    @LoginNonCheckRequired
    @AuthNonCheckRequired
    @RequestMapping("/page500")
    public String page500() {
        return "error/500";
    }

    /**
     * 注销登录
     */
    @LoginNonCheckRequired
    @AuthNonCheckRequired
    @RequestMapping("/cleanlogin")
    public String cleanlogin(HttpServletRequest request) {
        request.getSession().invalidate(); // 运用invalidate()比较好，退出时使session失效
        return "login";
    }

    /**
     * 请求转发
     */
    @LoginNonCheckRequired
    @RequestMapping("/init")
    @ResponseBody
    public String init(HttpServletRequest request, ModelMap model) {

        String method = request.getMethod();
        String formUrl = "";
        String formService = "";

        GMap params = GMap.newMap();

        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if (paraName.equals("formUrl")) {
                formUrl = request.getParameter(paraName);
            } else if (paraName.equals("formService")) {
                formService = request.getParameter(paraName);
            } else {
                params.putAction(paraName, request.getParameter(paraName));
            }
        }
        String url = formService + formUrl;
        String apiResult = "";
        if (StringUtil.equals("POST", method)) {
            // apiResult = HttpXmlClient.post(url, params);
            apiResult = HttpRequest.post(url).form(params).body();
        } else if (StringUtil.equals("GET", method)) {
            // apiResult = HttpXmlClient.get(url, params);
            apiResult = HttpRequest.get(url).form(params).body();
        }
        return apiResult;
    }


    @RequestMapping("/getSysmenu")
    @ResponseBody
    public List<GMap> getSysmenu(HttpServletRequest request) {
        List<GMap> list_GMap = new ArrayList<GMap>();
        List<GMap> children = null;

        List<GMap> list = (List<GMap>) request.getSession().getAttribute(GStateData.SESSION.MENU);

        GMap gMap = null;
        for (GMap sm1 : list) {
            if (sm1.getInteger("grade").equals(1)) {
                gMap = GMap.newMap();
                gMap.putAction("title", sm1.getString("name"));
                gMap.putAction("icon", sm1.getString("icon") == null ? "&#xe620;" : sm1.getString("icon"));
                gMap.putAction("spread", false);

                children = new ArrayList<>();
                for (GMap sm2 : list) {
                    if (sm2.getInteger("grade").equals(2) && sm2.getInteger("pid").equals(sm1.getInteger("id"))) {

                        GMap params = GMap.newMap();
                        params.putAction("title", sm2.getString("name"));
                        params.putAction("icon", sm2.getString("icon") == null ? "&#xe609;" : sm2.getString("icon"));
                        params.putAction("href", request.getContextPath() + "/" + sm2.getString("url"));

                        children.add(params);
                    }
                }
                gMap.putAction("children", children);

                list_GMap.add(gMap);
            }
        }

        return list_GMap;
    }

    @LoginNonCheckRequired
    @RequestMapping("/getTest")
    @ResponseBody
    public List<GMap> getTest(HttpServletRequest request,HttpServletResponse response) throws IOException {

        List<GMap> list = new ArrayList<>();
        GMap m1 = new GMap();
        m1.put("name","汤剂");
        m1.put("sex","男");
        m1.put("age","女");

        GMap m2 = new GMap();
        m2.put("name","汤剂");
        m2.put("sex","男");
        m2.put("age","女");

        list.add(m1);
        list.add(m2);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JSONObject resultJSON = JSONObject.fromObject(m2); //根据需要拼装json
        String jsonpCallback = request.getParameter("jsonpCallback");//客户端请求参数
        out.println(jsonpCallback+"("+resultJSON.toString(1,1)+")");//返回jsonp格式数据
        out.flush();
        out.close();
        return list;
    }

}