package aceplus.survey2.objects;

import java.util.ArrayList;

/**
 * Created by PhyoKyawSwar on 6/14/17.
 */

public class QuestionAndAnswerId {

    String questionsID;
    String questionText;
    ArrayList<Answer> answer;


    public String getQuestionsID() {
        return questionsID;
    }

    public void setQuestionsID(String questionsID) {
        this.questionsID = questionsID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Answer> answer) {
        this.answer = answer;
    }
}
