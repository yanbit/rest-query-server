package util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 10:49
 */
public class JdbcUtils {

    public static Connection getConnection() {
        HikariConfig config = new HikariConfig("/hikari.properties");
        HikariDataSource ds = new HikariDataSource(config);
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void shutdownConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Gson json = null;

    public static Gson getGson() {
        if (json == null) {
            json = new Gson();
        }
        return json;
    }


    public static Map map;

    static {
        map = Maps.newHashMap();
        setQueryString();
    }

    private static void setQueryString() {
        //successpayorder
        map.put("successpayorderall",
                "select DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as sd,count(1) as c " +
                        "from zz_member m join zz_yundb y on(m.mid = y.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY sd " +
                        "ORDER BY c ");
        map.put("successpayordernew",
                "select DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as sd,count(1) as c " +
                        "from zz_member m join zz_yundb y on(m.mid = y.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY sd " +
                        "ORDER BY c ");
        //payorderconvrate
        map.put("payorderconvrateall",
                "select T2.cd,ROUND(T1.c/T2.cTotal*100,2) as r " +
                        "from " +
                        "(select DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as dd,count(1) as c " +
                        "from zz_yundb as y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY dd) T1," +
                        "(select DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as cd,count(1) as cTotal " +
                        "from zz_yuncart as y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY cd) T2 " +
                        "ORDER BY r ");
        map.put("payorderconvratenew",
                "select T2.cd,ROUND(T1.c/T2.cTotal*100,2) as r " +
                        "from " +
                        "(select DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as dd,count(1) as c " +
                        "from zz_yundb as y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY dd) T1," +
                        "(select DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as cd,count(1) as cTotal " +
                        "from zz_yuncart as y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY cd) T2 " +
                        "ORDER BY r ");
        //addcart
        map.put("addcartall",
                "select DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as d,SUM(y.qty) as s " +
                        "from zz_yuncart AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY s ");
        map.put("addcartnew",
                "select DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as d,SUM(y.qty) as s " +
                        "from zz_yuncart AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY s ");

        //successpurchase
        map.put("successpurchaseall",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as d,count(1) as c " +
                        "from zz_yundb y where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY c ");
        map.put("successpurchasenew",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as d,count(1) as c " +
                        "from zz_yundb AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY c ");

        //avgpay
        map.put("avgpayall",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as d,ROUND(AVG(y.qty),2) as r " +
                        "from zz_yundb AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY r ");
        map.put("avgpaynew",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as d,ROUND(AVG(y.qty),2) as r " +
                        "from zz_yundb AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY r ");
        //purchaseuser
        map.put("purchaseuserall",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') as d,count(1) as c " +
                        "from zz_yundb AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY c ");
        map.put("purchaseusernew",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') as d,count(1) as c " +
                        "from zz_yundb AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY c ");
        //userprice
        map.put("userpriceall",
                "SELECT username,ROUND(AVG(y.qty),2) as r " +
                        "from zz_yundb AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ? " +
                        "GROUP BY mid " +
                        "ORDER BY r ");
        map.put("userpricenew",
                "SELECT y.username,count(1) as c " +
                        "from zz_yundb AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY y.mid " +
                        "ORDER BY c ");
        //avgpurchase
        map.put("avgpurchaseall",
                "SELECT username,count(1) as c " +
                        "from zz_yundb AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(y.add_time),'%Y-%m-%d') < ?  " +
                        "GROUP BY mid " +
                        "ORDER BY c ");
        map.put("avgpurchasenew",
                "SELECT y.username,count(1) as c from zz_yundb AS y JOIN zz_member m on (y.mid=m.mid) " +
                        "where DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') > ?  and DATE_FORMAT(FROM_UNIXTIME(m.c_time),'%Y-%m-%d') < ? " +
                        "GROUP BY y.mid " +
                        "ORDER BY c ");
        //completedaily
        map.put("completedaily",
                "SELECT DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') as d,count(1) as c " +
                        "from zz_yunbuy AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') < ? " +
                        "GROUP BY d " +
                        "ORDER BY c ");
        //luckbaby
        map.put("luckbaby",
                "SELECT member_name,DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') as d " +
                        "from zz_yunbuy AS y " +
                        "where DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') > ? and DATE_FORMAT(FROM_UNIXTIME(end_time),'%Y-%m-%d') < ? " +
                        "ORDER BY d ");
    }
}
