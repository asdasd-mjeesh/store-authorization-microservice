package com.access_security.model.common;

public enum SizeEnum {
    L(SizeType.CLOTHES), M(SizeType.CLOTHES), XL(SizeType.CLOTHES), XXL(SizeType.CLOTHES),
    S_35(SizeType.SHOES), S_36(SizeType.SHOES), S_37(SizeType.SHOES), S_38(SizeType.SHOES),
    S_39(SizeType.SHOES), S_40(SizeType.SHOES), S_41(SizeType.SHOES), S_42(SizeType.SHOES),
    S_43(SizeType.SHOES), S_44(SizeType.SHOES), S_45(SizeType.SHOES);

    private final SizeType type;

    SizeEnum(SizeType type) {
        this.type = type;
    }

    public SizeType getType() {
        return type;
    }
}
