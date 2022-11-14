package lotto.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import camp.nextstep.edu.missionutils.Randoms;

public class Lotto {
    private static final int LOTTO_NUMBERS_LENGTH = 6;
    private static final String LOTTO_NUMBERS_LENGTH_EXCEPTION_MESSAGE = "로또 번호는 " + LOTTO_NUMBERS_LENGTH + "개입니다";
    private static final String DUPLICATE_EXCEPTION_MESSAGE = "로또 번호에 중복이 있습니다";

    private final List<LottoNumber> numbers;

    public Lotto(Integer... numbers) {
        this(List.of(numbers));
    }

    public Lotto(List<Integer> numbers) {
        validateLength(numbers);
        validateNoDuplicate(numbers);
        this.numbers = numbers.stream()
                .map(LottoNumber::new)
                .collect(Collectors.toList());
    }

    public static Lotto createRandomized() {
        return new Lotto(Randoms.pickUniqueNumbersInRange(LottoNumber.RANGE_MINIMUM, LottoNumber.RANGE_MAXIMUM,
                LOTTO_NUMBERS_LENGTH));
    }

    private void validateLength(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBERS_LENGTH) {
            throw new IllegalArgumentException(LOTTO_NUMBERS_LENGTH_EXCEPTION_MESSAGE);
        }
    }

    private void validateNoDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException(DUPLICATE_EXCEPTION_MESSAGE);
        }
    }

    public long countMatchesWith(Lotto winningLotto) {
        return numbers.stream()
                .filter(winningLotto::contains)
                .count();
    }

    public boolean contains(LottoNumber number) {
        return numbers.contains(number);
    }
}
