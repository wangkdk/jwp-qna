package qna.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static qna.exception.ExceptionMessage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import qna.exception.CannotDeleteException;

class AnswersTest {
    private Answers answers;
    private List<Answer> answerList;
    private Answer firstAnswer;
    private Question question;

    @BeforeEach
    void setUp() {
        User writer = UserTest.JAVAJIGI;
        question = createQuestion("question", "contents", writer);
        firstAnswer = createAnswer(writer, question);
        answerList = Arrays.asList(
            firstAnswer,
            createAnswer(writer, question),
            createAnswer(writer, question)
        );

        this.answers = new Answers(new ArrayList<>(answerList));
    }

    @DisplayName("질문자와 답변들의 답변자가 같은경우 예외가 발생하지 않는다")
    @Test
    void validateAnswersOwner() {
        // given
        User questionWriter = UserTest.JAVAJIGI;
        Question question = createQuestion("question1", "questionContents1", questionWriter);
        Answers answers = new Answers(Arrays.asList(
            createAnswer(questionWriter, question),
            createAnswer(questionWriter, question)
        ));

        // when && then
        assertDoesNotThrow(() -> answers.validateAnswersOwner(questionWriter));
    }

    @DisplayName("질문자와 답변들의 답변자가 다른경우 예외가 발생한다")
    @Test
    void validateAnswersOwnerException() {
        // given
        User questionWriter = UserTest.JAVAJIGI;
        Question question = createQuestion("question1", "questionContents1", questionWriter);
        Answers answers = new Answers(Arrays.asList(
            createAnswer(questionWriter, question),
            createAnswer(UserTest.SANJIGI, question)
        ));

        // when & then
        assertThatThrownBy(() -> answers.validateAnswersOwner(questionWriter))
            .isInstanceOf(CannotDeleteException.class)
            .hasMessage(CANNOT_DELETE_ANSWER_DIFFERENT_USER_MESSAGE.getMessage());
    }

    @DisplayName("첫번째 질문을 삭제한다")
    @Test
    void remove() {
        answers.remove(firstAnswer);
        assertEquals(2, answers.size());
    }

    @DisplayName("질문을 추가한다")
    @Test
    void add() {
        answers.add(createAnswer(UserTest.JAVAJIGI, question));
        answers.add(createAnswer(UserTest.JAVAJIGI, question));
        assertEquals(5, answers.size());
    }

    @DisplayName("질문을 전체 삭제한다")
    @Test
    void deleteAll() {
        // when
        answers.deleteAll();

        // then
        assertThat(answerList).extracting("deleted")
            .containsOnly(true);
    }

    @DisplayName("질문들의 이력 정보를 가져온다")
    @Test
    void createDeleteHistories() {
        List<DeleteHistory> deleteHistories = answers.createDeleteHistories();
        assertEquals(3, deleteHistories.size());
        assertThat(deleteHistories).extracting("contentType").containsOnly(ContentType.ANSWER);
    }

    @DisplayName("삭제되지 않은 답변만 추출한다")
    @Test
    void updateDeletedFalseAnswers() {
        // given
        Answer deletedTrueAnswer = createAnswer(UserTest.JAVAJIGI, question);
        deletedTrueAnswer.setDeleted(true);
        question.addAnswer(deletedTrueAnswer);

        // when
        question.getAnswers().updateDeletedFalseAnswers();

        // then
        assertEquals(3, question.getAnswers().size());
    }

    private Answer createAnswer(User writer, Question question) {
        return new Answer(writer, question, "contents");
    }

    private Question createQuestion(String title, String contents, User writer) {
        return new Question(title, contents, writer);
    }
}
