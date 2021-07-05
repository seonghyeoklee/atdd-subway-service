package nextstep.subway.fare.policy.discount;

import nextstep.subway.fare.policy.extra.ExtraFarePolicy;

public class ChildPolicy extends DiscountPolicy {
    private static final double DISCOUNT_PERCENT = 50.0 / 100;
    private static final int BASIC_RATE = 350;
    private static final int CHILD_MINIMUM_AGE = 6;
    private static final int CHILD_MAXIMUM_AGE = 13;


    public ChildPolicy(int age, ExtraFarePolicy... extraFarePolicies) {
        super(age, extraFarePolicies);
    }

    @Override
    protected int getDiscountAmount(int fare) {
        return (int) ((fare - BASIC_RATE) * DISCOUNT_PERCENT);
    }

    @Override
    protected boolean validateAge(int age) {
        return age >= CHILD_MINIMUM_AGE && age < CHILD_MAXIMUM_AGE;
    }

}
