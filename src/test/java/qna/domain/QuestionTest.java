package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static qna.exception.ExceptionMessage.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import qna.exception.CannotDeleteException;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1", UserTest.JAVAJIGI);

    private Question question;
    private List<Answer> answerList;
    private User writer;

    @BeforeEach
    void setUp() {
        writer = new User("writer", "123", "writer", "writer@mail.com");
        question = createQuestion("question", "contents", writer);
        answerList = Arrays.asList(
            AnswerTest.createAnswer(writer, question),
            AnswerTest.createAnswer(writer, question));
    }

    @DisplayName("로그인 사용자와 질문한 사람이 같은 경우에만 삭제가 가능하다")
    @Test
    void validateQuestionOwner() {
        assertDoesNotThrow(() -> Q1.validateQuestionOwner(UserTest.JAVAJIGI));
        assertThatThrownBy(() -> Q1.validateQuestionOwner(UserTest.SANJIGI))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage(CANNOT_DELETE_QUESTION_MESSAGE.getMessage());
    }

    @DisplayName("질문을 삭제하면 답변들도 함께 삭제 된다")
    @Test
    void delete() {
        // when
        question.delete(writer);

        // then
        assertTrue(question.isDeleted());
        assertThat(answerList).extracting("deleted").containsOnly(true);
    }

    public static Question createQuestion(String title, String contents, User writer) {
        return new Question(title, contents, writer);
    }
}
