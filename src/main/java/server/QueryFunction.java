package server;

import bean.QueryParam;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.restlet.Request;
import org.restlet.data.Form;
import util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 15:54
 */
public class QueryFunction {

    private Gson json = JdbcUtils.getGson();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public String handlerParam(Request request) {
        Form form = request.getResourceRef().getQueryAsForm();
        String subject = form.getValues("subject");
        String user = form.getValues("user");
        String sdate = form.getValues("startdate");
        String edate = form.getValues("enddate");
        String order = form.getValues("order");

        Calendar calendar = Calendar.getInstance();

        if (Strings.isNullOrEmpty(subject)) {
            return json.toJson("error subject");
        }
        if (Strings.isNullOrEmpty(user)) {
            user = "all";
        }
        if (Strings.isNullOrEmpty(sdate) || Strings.isNullOrEmpty(edate)) {
            edate = format.format(calendar.getTime());
            calendar.add(Calendar.MONTH, -3);
            sdate = format.format(calendar.getTime());
        }
        if (Strings.isNullOrEmpty(order)) {
            order = "desc";
        }

        QueryParam param = new QueryParam();
        param.setSubject(subject);
        param.setSdate(sdate);
        param.setEdate(edate);
        param.setOrder(order);
        param.setUser(user);
        System.out.println("=======Request param======= " + param);
        Map map = JdbcUtils.map;
        String queryString;
        String sub;
        if (!"completedaily".equals(subject)&&!"luckbaby".equals(subject)){
            sub=subject+user;
            queryString = (String) map.get(sub)+param.getOrder();
        }else {
            sub=subject;
            queryString=(String) map.get(sub)+param.getOrder();
        }


        return query(queryString, param,sub);
    }


    private String query(String queryString, QueryParam param ,String subject) {
        System.out.println("=======QueryString param======= "+queryString);
        Connection conn = JdbcUtils.getConnection();
        Map<String, String> jmap = Maps.newLinkedHashMap();
        try {
            PreparedStatement pstmt = conn.prepareStatement(queryString);

            if (!param.getSubject().contains("payorderconvrate")) {
//                if ("avgpurchasenew".equals(subject) || "userpricenew".equals(subject)){
//                    pstmt.setString(1, param.getSdate());
//                }else{
                    pstmt.setString(1, param.getSdate());
                    pstmt.setString(2, param.getEdate());
//                }

            } else {
                pstmt.setString(1, param.getSdate());
                pstmt.setString(2, param.getEdate());
                pstmt.setString(3, param.getSdate());
                pstmt.setString(4, param.getEdate());
            }
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                jmap.put(result.getString(1), result.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return json.toJson("error query");
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return json.toJson(jmap);
    }
}
