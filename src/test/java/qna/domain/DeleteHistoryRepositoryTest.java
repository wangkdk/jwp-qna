package qna.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import qna.common.CommonRepositoryTest;

class DeleteHistoryRepositoryTest extends CommonRepositoryTest {
    @Autowired
    private DeleteHistoryRepository deleteHistoryRepository;
    private User writer;
    private Question question;
    private Answer answer;

    @BeforeEach
    void setUp() {
        writer = new User("questionWriter", "123", "questionWriter", "writer@email.com");
        question = new Question("question", "questionContents");
        question.setWriter(writer);
        answer = new Answer(writer, question, "answerContents");
    }

    @DisplayName("qna 삭제 히스토리를 저장한다")
    @Test
    void save() {
        // given
        DeleteHistory deleteHistory = createDeleteHistory(ContentType.QUESTION, question.getId(), writer);

        // when
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(deleteHistory);

        // then
        assertEquals(deleteHistory, savedDeleteHistory);
    }

    @DisplayName("Contents type 별 히스토리를 가져온다")
    @Test
    void findByIdAndContentType() {
        // given
        DeleteHistory savedDeleteHistory = deleteHistoryRepository.save(
            createDeleteHistory(ContentType.ANSWER, answer.getId(), writer));
        deleteHistoryRepository.save(
            createDeleteHistory(ContentType.QUESTION, question.getId(), writer));

        // when
        DeleteHistory findDeleteHistory = deleteHistoryRepository.findByIdAndContentType(
            savedDeleteHistory.getId(),
            savedDeleteHistory.getContentType()).orElse(null);

        // then
        assertNotNull(findDeleteHistory);
        assertEquals(savedDeleteHistory, findDeleteHistory);
    }

    private DeleteHistory createDeleteHistory(ContentType contentType, Long contentId, User deletedBy) {
        return new DeleteHistory(contentType, contentId,
            deletedBy, LocalDateTime.now());
    }
}
