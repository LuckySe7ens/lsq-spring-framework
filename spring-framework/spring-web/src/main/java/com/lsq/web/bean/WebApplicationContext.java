package com.lsq.web.bean;

import com.lsq.context.annotation.Autowired;
import com.lsq.context.annotation.Component;
import com.lsq.context.annotation.Controller;
import com.lsq.context.annotation.Service;
import com.lsq.context.bean.*;
import com.lsq.context.utils.StringUtils;
import com.lsq.web.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liushiquan on 2019/1/7.
 */
public class WebApplicationContext extends HttpServlet implements AbstractApplicationContext {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7218104769658143907L;

	/**
     * bean瀹瑰櫒
     */
    private Map<String,Object> beanMap = new ConcurrentHashMap<String, Object>(16);

    private Map<String,Method> methodMap = new ConcurrentHashMap<String, Method>(16);

    private static Environment env;

    private static String baseScanPackage;

    private static String BASE_SCAN_PACKAGE_KEY = "base.scan.package";

    private static String base_scan_path = null;

    private WebApplicationContext context;

    public WebApplicationContext() {
    }

    public void initEnv(String appProPath) throws Exception {
        env = new DefaultEnvironment();
        env.initEnvironment(appProPath);

        baseScanPackage = env.getString(BASE_SCAN_PACKAGE_KEY);

        if(baseScanPackage == null || "".equals(baseScanPackage.trim())) {
            throw new Exception("娌℃湁閰嶇疆鎴栭厤缃殑 " + BASE_SCAN_PACKAGE_KEY + "闈炴硶");
        }
    }

    public void initBean() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("");
        String path = resource.getPath();
        base_scan_path = path + baseScanPackage.replaceAll("\\.", "/");
        File file = new File(base_scan_path);
        loadingBean(path, file);
    }

    public void loadingBean(String path, File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if(files == null){
                return;
            }
            for (File f : files) {
                loadingBean(path,f);
            }
        }
        else if(file.getName().endsWith(".class")) {
            String path1 = file.getAbsolutePath();
            path1 = path1.substring(path.length() - 1).replace(".class","");
            path1 = path1.replaceAll("\\\\",".");
            Class<?> clazz = Class.forName(path1);
            if(clazz.isAnnotationPresent(Controller.class)) {
                Controller component = clazz.getAnnotation(Controller.class);
                String key = component.value();
                Object instance = clazz.newInstance();
                if("".equals(key)) {
                    beanMap.put(StringUtils.firstlower(clazz.getSimpleName()),instance);
                } else {
                    beanMap.put(key,instance);
                }
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                String baseUrl = "";
                if(requestMapping != null) {
                    baseUrl = requestMapping.value();
                }

                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
                        methodMap.put((baseUrl + "\\" + reqMapping.value()).replaceAll("\\\\+","/"),method);
                    }
                }
            } else if(clazz.isAnnotationPresent(Service.class)) {
                Service component = clazz.getAnnotation(Service.class);
                String key = component.value();
                Object instance = clazz.newInstance();
                if("".equals(key)) {
                    beanMap.put(StringUtils.firstlower(clazz.getSimpleName()),instance);
                } else {
                    beanMap.put(key,instance);
                }
            } else if(clazz.isAnnotationPresent(Component.class)) {
                Component component = clazz.getAnnotation(Component.class);
                String key = component.value();
                Object instance = clazz.newInstance();
                if("".equals(key)) {
                    beanMap.put(StringUtils.firstlower(clazz.getSimpleName()),instance);
                } else {
                    beanMap.put(key,instance);
                }
            }
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            context = new WebApplicationContext();
            context.initEnv(AbstractEnvironment.DEFAULT_APP_PROPERTIES_FILENAME);
            context.initBean();
            context.initField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initField() throws Exception {
        for (Map.Entry entry : beanMap.entrySet()) {
            Object o = entry.getValue();
            Class<?> clazz = o.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String s = autowired.value();
                    if("".equals(s)) {
                        field.set(o,beanMap.get(StringUtils.firstlower(field.getType().getSimpleName())));
                    } else {
                        field.set(o,beanMap.get(s));
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    public Object getBean(Class<?> clazz) {
        return beanMap.get(StringUtils.firstlower(clazz.getSimpleName()));
    }

    public List<Object> getBeans(Class<?> clazz) {
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contextPath = req.getContextPath();
        StringBuffer url = req.getRequestURL();

        String reqUrl = url.substring(url.indexOf(contextPath) + contextPath.length() + 1);
        Method method = context.getMethodMap().get(reqUrl);
        if(method == null) {
            resp.getWriter().append("page not found");
            resp.setStatus(404);
            resp.flushBuffer();
            return;
        }
        Map<String, String[]> parameterMap = req.getParameterMap();

        try {
        	Class<?>[] types = method.getParameterTypes();
        	Object result = null;
        	if(types == null || types.length == 0) {
        		 result = method.invoke(context.getBeanMap().get(StringUtils.firstlower(method.getDeclaringClass().getSimpleName())));
        	} else {
        		 result = method.invoke(context.getBeanMap().get(StringUtils.firstlower(method.getDeclaringClass().getSimpleName())), parameterMap.values().toArray());
        	}
        	resp.getWriter().append(result.toString());
        	resp.setStatus(200);
        	resp.flushBuffer();
        	return;
        } catch (Exception e) {
            resp.getWriter().append("server internal error\n\r" + e);
            resp.setStatus(500);
            resp.flushBuffer();
            return;
        }
    }

	public Map<String, Object> getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(Map<String, Object> beanMap) {
		this.beanMap = beanMap;
	}

	public Map<String, Method> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(Map<String, Method> methodMap) {
		this.methodMap = methodMap;
	}
    
    
}
