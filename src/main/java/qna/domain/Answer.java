package qna.domain;

import static qna.exception.ExceptionMessage.*;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;
import qna.exception.UnAuthorizedException;

@Entity
public class Answer extends BaseEntityTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Embedded
    private Contents contents;

    private boolean deleted = false;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.contents = new Contents(contents);
        changeQuestion(question);
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public Contents getContents() {
        return contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void changeQuestion(Question question) {
        if (Objects.nonNull(this.question)) {
            this.question.getAnswers().remove(this);
        }
        this.question = question;
        question.getAnswers().add(this);
    }

    public void validateAnswerOwner(User owner) {
        if (!isOwner(owner)) {
            throw new CannotDeleteException(CANNOT_DELETE_ANSWER_DIFFERENT_USER_MESSAGE.getMessage());
        }
    }

    private boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + id +
            ", writer=" + writer +
            ", question=" + question +
            ", contents='" + contents + '\'' +
            ", deleted=" + deleted +
            '}';
    }
}
