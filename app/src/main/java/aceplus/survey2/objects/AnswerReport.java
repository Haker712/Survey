package aceplus.survey2.objects;

/**
 * Created by PhyoKyawSwar on 6/19/17.
 */

public class AnswerReport {

    String Id;
    String Template_Id;
    String Customer_Id;
    String User_Id;
    String Date;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTemplate_Id() {
        return Template_Id;
    }

    public void setTemplate_Id(String template_Id) {
        Template_Id = template_Id;
    }

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
