package lotto.controller;

import java.util.List;

import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Money;
import lotto.domain.Picker;
import lotto.domain.Rank;
import lotto.domain.Seller;
import lotto.view.InputView;
import lotto.view.OutputView;

public class Game {

    public void play() {
        Money capital = new Money(InputView.promptAmount());
        List<Lotto> lottos = issueLottosWith(capital);

        List<Rank> ranks = getPicker().pick(lottos);

        Money profit = sumProfitsOf(ranks);
        double profitRate = capital.calculateProfitRateOf(profit);
        OutputView.printStatistics(ranks, profitRate);
    }

    private Money sumProfitsOf(List<Rank> ranks) {
        return ranks.stream()
                .map(Rank::getPrize)
                .reduce(Money::add)
                .orElse(new Money(0));
    }

    private Picker getPicker() {
        Lotto winningLotto = new Lotto(InputView.promptWinningNumbers());
        LottoNumber bonusNumber = new LottoNumber(InputView.promptBonusNumber());
        return new Picker(winningLotto, bonusNumber);
    }

    private List<Lotto> issueLottosWith(Money capital) {
        List<Lotto> lottos = new Seller().issueLottos(capital);

        OutputView.printPurchase(lottos);
        OutputView.printIssuedLottos(lottos);
        return lottos;
    }
}
