package com.xzy.enums;

public enum EUserType {
    ADMIN(0), GROUP(1), COMPANY(2), AGENT1(3), AGENT2(4), AGENT3(5), AGENT4(6), AGENT5(7), AGENT6(8), AGENT7(9),
    AGENT8(10), AGENT9(11), AGENT10(12);

    private int level;

    private EUserType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public static EUserType valueOf(int value) { // 手写的从int到enum的转换函数
        switch (value) {
            case 0:
                return ADMIN;
            case 1:
                return GROUP;
            case 2:
                return COMPANY;
            case 3:
                return AGENT1;
            case 4:
                return AGENT2;
            case 5:
                return AGENT3;
            case 6:
                return AGENT4;
            case 7:
                return AGENT5;
            case 8:
                return AGENT6;
            case 9:
                return AGENT7;
            case 10:
                return AGENT8;
            case 11:
                return AGENT9;
            case 12:
                return AGENT10;
            default:
                return null;
        }
    }
}
