package aceplus.survey2.objects;

/**
 * Created by PhyoKyawSwar on 6/6/17.
 */

public class Questions {

    String Id;
    String Text;
    String QoT;
    int QoC;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getQoT() {
        return QoT;
    }

    public void setQoT(String qoT) {
        QoT = qoT;
    }

    public int getQoC() {
        return QoC;
    }

    public void setQoC(int qoC) {
        QoC = qoC;
    }
}
