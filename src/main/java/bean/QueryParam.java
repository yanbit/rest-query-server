package bean;

/**
 * User: yanbit
 * Date: 2016/2/3
 * Time: 16:22
 */
public class QueryParam {
    private String subject;
    private String user;
    private String sdate;
    private String edate;
    private String order;

    @Override
    public String toString() {
        return "QueryParam{" +
                "subject='" + subject + '\'' +
                ", user='" + user + '\'' +
                ", sdate='" + sdate + '\'' +
                ", edate='" + edate + '\'' +
                ", order='" + order + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
