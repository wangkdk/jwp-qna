package qna.common;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import qna.domain.AnswerRepository;
import qna.domain.DeleteHistoryRepository;
import qna.domain.QuestionRepository;
import qna.domain.UserRepository;

@Disabled
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class CommonRepositoryTest {
    @Autowired
    protected DeleteHistoryRepository deleteHistoryRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected QuestionRepository questionRepository;
    @Autowired
    protected AnswerRepository answerRepository;
}
