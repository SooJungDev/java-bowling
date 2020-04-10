package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import qna.CannotDeleteException;

class AnswersTest {
    private Answers answers;
    private DeleteHistories deleteHistories;

    @BeforeEach
    void setUp() {
        deleteHistories = new DeleteHistories();
        answers = new Answers(Stream.of(AnswerTest.A1, AnswerTest.A2).collect(Collectors.toList()));
    }

    @DisplayName("답변들 중에 다른사람이 쓴 답변이 있다면 삭제시킬 수 없다")
    @Test
    void checkAnswers() {
        assertThatThrownBy(() -> {
            answers.deleteAnswers(UserTest.JAVAJIGI, deleteHistories);
        }).isInstanceOf(CannotDeleteException.class);
    }

    @DisplayName("답변들 중에 삭제 시킬 수 있는 것을 삭제를 지시한다.")
    @Test
    void deleteAnswer() throws CannotDeleteException {
        answers = new Answers(Stream.of(AnswerTest.A1).collect(Collectors.toList()));
        List<Answer> answerList = answers.deleteAnswers(UserTest.JAVAJIGI, deleteHistories);
        assertThat(answerList.get(0).isDeleted()).isTrue();
    }
}
