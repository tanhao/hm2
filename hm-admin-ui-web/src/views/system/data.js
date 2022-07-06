let groups = [{
    "groupId": 100,
    "groupKey": "carship",
    "groupName": "赛车飞艇组",
    "isChecked": false,
    "lotteryId": 101,
    "lotterys": []
}, {
    "groupId": 200,
    "groupKey": "ssc",
    "groupName": "时时彩组",
    "isChecked": false,
    "lotteryId": 201,
    "lotterys": []
}, {
    "groupId": 300,
    "groupKey": "happy10",
    "groupName": "广东快乐10组",
    "isChecked": false,
    "lotteryId": 301,
    "lotterys": []
}
];

let lotterys = [
    { "groupId": 100, "lotteryId": 101, "lotteryKey": "bjpk10", "lotteryName": "北京赛车" },
    { "groupId": 100, "lotteryId": 102, "lotteryKey": "xyft", "lotteryName": "幸运飞艇" },
    { "groupId": 100, "lotteryId": 103, "lotteryKey": "speed10", "lotteryName": "极速赛车" },
    { "groupId": 100, "lotteryId": 104, "lotteryKey": "lucky10", "lotteryName": "澳洲幸运10" },
    { "groupId": 100, "lotteryId": 105, "lotteryKey": "sbspeed10", "lotteryName": "极速飞艇" },
    { "groupId": 100, "lotteryId": 106, "lotteryKey": "epssm", "lotteryName": "EPS赛马" },
    { "groupId": 100, "lotteryId": 107, "lotteryKey": "fhsc", "lotteryName": "凤凰赛车" },
    { "groupId": 100, "lotteryId": 108, "lotteryKey": "tycsm", "lotteryName": "太阳城赛马" },
    { "groupId": 100, "lotteryId": 109, "lotteryKey": "xyft3", "lotteryName": "幸运飞艇(3)" },
    { "groupId": 100, "lotteryId": 110, "lotteryKey": "sgft", "lotteryName": "SG飞艇" },
    { "groupId": 200, "lotteryId": 201, "lotteryKey": "cqssc", "lotteryName": "重庆时时彩" },
    { "groupId": 200, "lotteryId": 202, "lotteryKey": "jsssc", "lotteryName": "极速时时彩" },
    { "groupId": 200, "lotteryId": 203, "lotteryKey": "lucky5", "lotteryName": "澳洲幸运5" },
    { "groupId": 200, "lotteryId": 204, "lotteryKey": "fhssc", "lotteryName": "凤凰时时彩" },
    { "groupId": 200, "lotteryId": 205, "lotteryKey": "tycssc", "lotteryName": "太阳城时时彩" },
    { "groupId": 200, "lotteryId": 206, "lotteryKey": "txffc", "lotteryName": "腾讯分分彩" },
    { "groupId": 200, "lotteryId": 207, "lotteryKey": "txsfc", "lotteryName": "腾讯三分彩" },
    { "groupId": 200, "lotteryId": 208, "lotteryKey": "twssc", "lotteryName": "台湾时时彩" },
    { "groupId": 300, "lotteryId": 301, "lotteryKey": "gdkl10", "lotteryName": "广东快乐十分" },
    { "groupId": 300, "lotteryId": 302, "lotteryKey": "speed8", "lotteryName": "极速快乐十分" },
    { "groupId": 300, "lotteryId": 303, "lotteryKey": "xync", "lotteryName": "幸运农场" },
    { "groupId": 300, "lotteryId": 304, "lotteryKey": "lucky8", "lotteryName": "澳洲幸运8" }
];

let kinds = [
    { "kindId": 101, "groupId": 100, "quickSettingId0": 1, "kindKey": "yds", "kindName": "1-10车号" },
    { "kindId": 102, "groupId": 100, "quickSettingId0": 2, "kindKey": "lm", "kindName": "两面" },
    { "kindId": 103, "groupId": 100, "quickSettingId0": 2, "kindKey": "gyhou", "kindName": "冠亚和大双" },
    { "kindId": 104, "groupId": 100, "quickSettingId0": 2, "kindKey": "gyhoe", "kindName": "冠亚和小单" },
    { "kindId": 105, "groupId": 100, "quickSettingId0": 5, "kindKey": "gyh", "kindName": "冠亚和" },
    { "kindId": 201, "groupId": 200, "quickSettingId0": 1, "kindKey": "ydw", "kindName": "1-5球号" },
    { "kindId": 202, "groupId": 200, "quickSettingId0": 2, "kindKey": "lm", "kindName": "两面" },
    { "kindId": 203, "groupId": 200, "quickSettingId0": 2, "kindKey": "lhh", "kindName": "龙虎和" },
    { "kindId": 204, "groupId": 200, "quickSettingId0": 5, "kindKey": "ts", "kindName": "前中后三" },
    { "kindId": 205, "groupId": 200, "quickSettingId0": 1, "kindKey": "1z", "kindName": "一字组合" },
    { "kindId": 206, "groupId": 200, "quickSettingId0": 1, "kindKey": "2z", "kindName": "二字组合" },
    { "kindId": 207, "groupId": 200, "quickSettingId0": 1, "kindKey": "3z", "kindName": "三字组合" },
    { "kindId": 208, "groupId": 200, "quickSettingId0": 1, "kindKey": "2dw", "kindName": "二字定位" },
    { "kindId": 209, "groupId": 200, "quickSettingId0": 1, "kindKey": "3dw", "kindName": "三字定位" },
    { "kindId": 210, "groupId": 200, "quickSettingId0": 5, "kindKey": "2hs", "kindName": "二字和数" },
    { "kindId": 211, "groupId": 200, "quickSettingId0": 5, "kindKey": "hws", "kindName": "和尾数" },
    { "kindId": 212, "groupId": 200, "quickSettingId0": 5, "kindKey": "3hs", "kindName": "三字和数" },
    { "kindId": 213, "groupId": 200, "quickSettingId0": 4, "kindKey": "zx3", "kindName": "组选三" },
    { "kindId": 214, "groupId": 200, "quickSettingId0": 4, "kindKey": "zx6", "kindName": "组选六" },
    { "kindId": 215, "groupId": 200, "quickSettingId0": 5, "kindKey": "fs", "kindName": "复式" },
    { "kindId": 216, "groupId": 200, "quickSettingId0": 5, "kindKey": "kd", "kindName": "跨度" },
    { "kindId": 217, "groupId": 200, "quickSettingId0": 5, "kindKey": "nn", "kindName": "牛牛" },
    { "kindId": 218, "groupId": 200, "quickSettingId0": 2, "kindKey": "nnlm", "kindName": "牛牛两面" },
    { "kindId": 219, "groupId": 200, "quickSettingId0": 5, "kindKey": "sh", "kindName": "梭哈" },
    { "kindId": 301, "groupId": 300, "quickSettingId0": 1, "kindKey": "ydb", "kindName": "1~8球号" },
    { "kindId": 302, "groupId": 300, "quickSettingId0": 2, "kindKey": "lm", "kindName": "两面" },
    { "kindId": 303, "groupId": 300, "quickSettingId0": 3, "kindKey": "fwei", "kindName": "1~8方位" },
    { "kindId": 304, "groupId": 300, "quickSettingId0": 3, "kindKey": "zfb", "kindName": "1~8中发白" },
    { "kindId": 305, "groupId": 300, "quickSettingId0": 1, "kindKey": "zm", "kindName": "正码" },
    { "kindId": 306, "groupId": 300, "quickSettingId0": 2, "kindKey": "sumoe", "kindName": "总和单双" },
    { "kindId": 307, "groupId": 300, "quickSettingId0": 2, "kindKey": "sumwou", "kindName": "总和尾数大小" },
    { "kindId": 308, "groupId": 300, "quickSettingId0": 4, "kindKey": "lianma", "kindName": "连码" }
]

let categorys = [
    { "categoryId": 10101, "kindId": 101, "categoryKey": "yds", "categoryName": "" },
    { "categoryId": 10201, "kindId": 102, "categoryKey": "lm", "categoryName": "" },
    { "categoryId": 10301, "kindId": 103, "categoryKey": "gyhou", "categoryName": "大双" },
    { "categoryId": 10401, "kindId": 104, "categoryKey": "gyhoe", "categoryName": "小单" },
    { "categoryId": 10501, "kindId": 105, "categoryKey": "gyhthree", "categoryName": "3,4,18,19" },
    { "categoryId": 10502, "kindId": 105, "categoryKey": "gyhfive", "categoryName": "5,6,16,17" },
    { "categoryId": 10503, "kindId": 105, "categoryKey": "gyhseven", "categoryName": "7,8,14,15" },
    { "categoryId": 10504, "kindId": 105, "categoryKey": "gyhnine", "categoryName": "9,10,12,13" },
    { "categoryId": 10505, "kindId": 105, "categoryKey": "gyheleven", "categoryName": "11" },
    { "categoryId": 20101, "kindId": 201, "categoryKey": "ydw", "categoryName": "" },
    { "categoryId": 20201, "kindId": 202, "categoryKey": "lm", "categoryName": "" },
    { "categoryId": 20301, "kindId": 203, "categoryKey": "lh", "categoryName": "龙虎" },
    { "categoryId": 20302, "kindId": 203, "categoryKey": "he", "categoryName": "和" },
    { "categoryId": 20401, "kindId": 204, "categoryKey": "baozi", "categoryName": "豹子" },
    { "categoryId": 20402, "kindId": 204, "categoryKey": "sunzi", "categoryName": "顺子" },
    { "categoryId": 20403, "kindId": 204, "categoryKey": "duizi", "categoryName": "对子" },
    { "categoryId": 20404, "kindId": 204, "categoryKey": "bansun", "categoryName": "半顺" },
    { "categoryId": 20405, "kindId": 204, "categoryKey": "zaliu", "categoryName": "杂六" },
    { "categoryId": 20501, "kindId": 205, "categoryKey": "top3", "categoryName": "前三" },
    { "categoryId": 20502, "kindId": 205, "categoryKey": "mid3", "categoryName": "中三" },
    { "categoryId": 20503, "kindId": 205, "categoryKey": "end3", "categoryName": "后三" },
    { "categoryId": 20504, "kindId": 205, "categoryKey": "all5", "categoryName": "全五" },
    { "categoryId": 20601, "kindId": 206, "categoryKey": "duizi", "categoryName": "对子" },
    { "categoryId": 20602, "kindId": 206, "categoryKey": "others", "categoryName": "其它" },
    { "categoryId": 20701, "kindId": 207, "categoryKey": "baozi", "categoryName": "豹子" },
    { "categoryId": 20702, "kindId": 207, "categoryKey": "sunzi", "categoryName": "顺子" },
    { "categoryId": 20703, "kindId": 207, "categoryKey": "duizi", "categoryName": "对子" },
    { "categoryId": 20704, "kindId": 207, "categoryKey": "bansun", "categoryName": "半顺" },
    { "categoryId": 20705, "kindId": 207, "categoryKey": "zaliu", "categoryName": "杂六" },
    { "categoryId": 20801, "kindId": 208, "categoryKey": "2dw", "categoryName": "" },
    { "categoryId": 20902, "kindId": 209, "categoryKey": "3dw", "categoryName": "" },
    { "categoryId": 21001, "kindId": 210, "categoryKey": "2hs0", "categoryName": "0~4,14~18" },
    { "categoryId": 21002, "kindId": 210, "categoryKey": "2hs5", "categoryName": "5,13" },
    { "categoryId": 21003, "kindId": 210, "categoryKey": "2hs6", "categoryName": "6,12" },
    { "categoryId": 21004, "kindId": 210, "categoryKey": "2hs7", "categoryName": "7,11" },
    { "categoryId": 21005, "kindId": 210, "categoryKey": "2hs8", "categoryName": "8,10" },
    { "categoryId": 21006, "kindId": 210, "categoryKey": "2hs9", "categoryName": "9" },
    { "categoryId": 21101, "kindId": 211, "categoryKey": "hws", "categoryName": "" },
    { "categoryId": 21201, "kindId": 212, "categoryKey": "3hs0", "categoryName": "0~6,21~27" },
    { "categoryId": 21202, "kindId": 212, "categoryKey": "3hs7", "categoryName": "7,20" },
    { "categoryId": 21203, "kindId": 212, "categoryKey": "3hs8", "categoryName": "8,19" },
    { "categoryId": 21204, "kindId": 212, "categoryKey": "3hs9", "categoryName": "9,18" },
    { "categoryId": 21205, "kindId": 212, "categoryKey": "3hs10", "categoryName": "10,17" },
    { "categoryId": 21206, "kindId": 212, "categoryKey": "3hs11", "categoryName": "11,16" },
    { "categoryId": 21207, "kindId": 212, "categoryKey": "3hs12", "categoryName": "12,15" },
    { "categoryId": 21208, "kindId": 212, "categoryKey": "3hs13", "categoryName": "13,14" },
    { "categoryId": 21301, "kindId": 213, "categoryKey": "x5", "categoryName": "选5" },
    { "categoryId": 21302, "kindId": 213, "categoryKey": "x6", "categoryName": "选6" },
    { "categoryId": 21303, "kindId": 213, "categoryKey": "x7", "categoryName": "选7" },
    { "categoryId": 21304, "kindId": 213, "categoryKey": "x8", "categoryName": "选8" },
    { "categoryId": 21305, "kindId": 213, "categoryKey": "x9", "categoryName": "选9" },
    { "categoryId": 21306, "kindId": 213, "categoryKey": "x10", "categoryName": "选10" },
    { "categoryId": 21401, "kindId": 214, "categoryKey": "x4", "categoryName": "选4" },
    { "categoryId": 21402, "kindId": 214, "categoryKey": "x5", "categoryName": "选5" },
    { "categoryId": 21403, "kindId": 214, "categoryKey": "x6", "categoryName": "选6" },
    { "categoryId": 21404, "kindId": 214, "categoryKey": "x7", "categoryName": "选7" },
    { "categoryId": 21405, "kindId": 214, "categoryKey": "x8", "categoryName": "选8" },
    { "categoryId": 21501, "kindId": 215, "categoryKey": "fs", "categoryName": "" },
    { "categoryId": 21601, "kindId": 216, "categoryKey": "kd0", "categoryName": "0" },
    { "categoryId": 21602, "kindId": 216, "categoryKey": "kd1", "categoryName": "1,9" },
    { "categoryId": 21603, "kindId": 216, "categoryKey": "kd2", "categoryName": "2,8" },
    { "categoryId": 21604, "kindId": 216, "categoryKey": "kd3", "categoryName": "3,7" },
    { "categoryId": 21605, "kindId": 216, "categoryKey": "kd4", "categoryName": "4,6" },
    { "categoryId": 21606, "kindId": 216, "categoryKey": "kd5", "categoryName": "5" },
    { "categoryId": 21701, "kindId": 217, "categoryKey": "n0", "categoryName": "无牛" },
    { "categoryId": 21702, "kindId": 217, "categoryKey": "n1", "categoryName": "牛1" },
    { "categoryId": 21703, "kindId": 217, "categoryKey": "n2", "categoryName": "牛2" },
    { "categoryId": 21704, "kindId": 217, "categoryKey": "n3", "categoryName": "牛3" },
    { "categoryId": 21705, "kindId": 217, "categoryKey": "n4", "categoryName": "牛4" },
    { "categoryId": 21706, "kindId": 217, "categoryKey": "n5", "categoryName": "牛5" },
    { "categoryId": 21707, "kindId": 217, "categoryKey": "n6", "categoryName": "牛6" },
    { "categoryId": 21708, "kindId": 217, "categoryKey": "n7", "categoryName": "牛7" },
    { "categoryId": 21709, "kindId": 217, "categoryKey": "n8", "categoryName": "牛8" },
    { "categoryId": 21710, "kindId": 217, "categoryKey": "n9", "categoryName": "牛9" },
    { "categoryId": 21711, "kindId": 217, "categoryKey": "nn", "categoryName": "牛牛" },
    { "categoryId": 21801, "kindId": 218, "categoryKey": "nnlm", "categoryName": "" },
    { "categoryId": 21901, "kindId": 219, "categoryKey": "5mei", "categoryName": "五梅" },
    { "categoryId": 21902, "kindId": 219, "categoryKey": "4d1", "categoryName": "四带一" },
    { "categoryId": 21903, "kindId": 219, "categoryKey": "hulu", "categoryName": "葫芦" },
    { "categoryId": 21904, "kindId": 219, "categoryKey": "3tiao", "categoryName": "三条" },
    { "categoryId": 21905, "kindId": 219, "categoryKey": "sunzi", "categoryName": "顺子" },
    { "categoryId": 21906, "kindId": 219, "categoryKey": "2pair", "categoryName": "两对" },
    { "categoryId": 21907, "kindId": 219, "categoryKey": "1pair", "categoryName": "单对" },
    { "categoryId": 21908, "kindId": 219, "categoryKey": "5bukao", "categoryName": "五不管" },
    { "categoryId": 21909, "kindId": 219, "categoryKey": "sanhao", "categoryName": "散号" },
    { "categoryId": 30101, "kindId": 301, "categoryKey": "ydb", "categoryName": "" },
    { "categoryId": 30201, "kindId": 302, "categoryKey": "lm", "categoryName": "" },
    { "categoryId": 30301, "kindId": 303, "categoryKey": "fwei", "categoryName": "" },
    { "categoryId": 30401, "kindId": 304, "categoryKey": "zf", "categoryName": "中发" },
    { "categoryId": 30402, "kindId": 304, "categoryKey": "bai", "categoryName": "白" },
    { "categoryId": 30501, "kindId": 305, "categoryKey": "zm", "categoryName": "" },
    { "categoryId": 30601, "kindId": 306, "categoryKey": "sumodd", "categoryName": "总单" },
    { "categoryId": 30602, "kindId": 306, "categoryKey": "sumeven", "categoryName": "总双" },
    { "categoryId": 30701, "kindId": 307, "categoryKey": "heweiover", "categoryName": "总尾大" },
    { "categoryId": 30702, "kindId": 307, "categoryKey": "heweiunder", "categoryName": "总尾小" },
    { "categoryId": 30801, "kindId": 308, "categoryKey": "x2", "categoryName": "任选二" },
    { "categoryId": 30802, "kindId": 308, "categoryKey": "x2lz", "categoryName": "选二连直" },
    { "categoryId": 30803, "kindId": 308, "categoryKey": "x2lg", "categoryName": "选二连组" },
    { "categoryId": 30804, "kindId": 308, "categoryKey": "x3", "categoryName": "任选三" },
    { "categoryId": 30805, "kindId": 308, "categoryKey": "x3topz", "categoryName": "选三前直" },
    { "categoryId": 30806, "kindId": 308, "categoryKey": "x3topg", "categoryName": "选三前组" },
    { "categoryId": 30807, "kindId": 308, "categoryKey": "x4", "categoryName": "任选四" },
    { "categoryId": 30808, "kindId": 308, "categoryKey": "x5", "categoryName": "任选五" },
    { "categoryId": 40101, "kindId": 401, "categoryKey": "dx", "categoryName": "" },
    { "categoryId": 40202, "kindId": 402, "categoryKey": "sj", "categoryName": "" },
    { "categoryId": 40303, "kindId": 403, "categoryKey": "ws", "categoryName": "" },
    { "categoryId": 40404, "kindId": 404, "categoryKey": "qw", "categoryName": "" },
    { "categoryId": 40501, "kindId": 405, "categoryKey": "ds4", "categoryName": "4,17" },
    { "categoryId": 40502, "kindId": 405, "categoryKey": "ds5", "categoryName": "5,16" },
    { "categoryId": 40503, "kindId": 405, "categoryKey": "ds6", "categoryName": "6,15" },
    { "categoryId": 40504, "kindId": 405, "categoryKey": "ds7", "categoryName": "7,14" },
    { "categoryId": 40505, "kindId": 405, "categoryKey": "ds8", "categoryName": "8,13" },
    { "categoryId": 40506, "kindId": 405, "categoryKey": "ds9", "categoryName": "9,12" },
    { "categoryId": 40507, "kindId": 405, "categoryKey": "ds10", "categoryName": "10,11" },
    { "categoryId": 40601, "kindId": 406, "categoryKey": "cp", "categoryName": "" },
    { "categoryId": 40702, "kindId": 407, "categoryKey": "dp", "categoryName": "" },
    { "categoryId": 40803, "kindId": 408, "categoryKey": "dd", "categoryName": "" },
    { "categoryId": 40901, "kindId": 409, "categoryKey": "hy", "categoryName": "" },
    { "categoryId": 41001, "kindId": 410, "categoryKey": "zf", "categoryName": "" },
    { "categoryId": 50101, "kindId": 501, "categoryKey": "zm", "categoryName": "1-80" },
    { "categoryId": 50201, "kindId": 502, "categoryKey": "lm", "categoryName": "两面" },
    { "categoryId": 50301, "kindId": 503, "categoryKey": "sumhj", "categoryName": "总和和局" },
    { "categoryId": 50401, "kindId": 504, "categoryKey": "sumgg", "categoryName": "总和过关" },
    { "categoryId": 50501, "kindId": 505, "categoryKey": "teheTE", "categoryName": "前(多)后(多)" },
    { "categoryId": 50502, "kindId": 505, "categoryKey": "teheHe", "categoryName": "前后和" },
    { "categoryId": 50607, "kindId": 506, "categoryKey": "oeheOE", "categoryName": "单(多)双(多)" },
    { "categoryId": 50608, "kindId": 506, "categoryKey": "oeheHe", "categoryName": "单双和" },
    { "categoryId": 50709, "kindId": 507, "categoryKey": "wxjt", "categoryName": "金,土" },
    { "categoryId": 50710, "kindId": 507, "categoryKey": "wxmh", "categoryName": "木,火" },
    { "categoryId": 50711, "kindId": 507, "categoryKey": "wxs", "categoryName": "水" },
    { "categoryId": 60101, "kindId": 601, "categoryKey": "ydw", "categoryName": "" },
    { "categoryId": 60201, "kindId": 602, "categoryKey": "lm", "categoryName": "" },
    { "categoryId": 60301, "kindId": 603, "categoryKey": "sumodd", "categoryName": "总和单" },
    { "categoryId": 60302, "kindId": 603, "categoryKey": "sumeven", "categoryName": "总和双" },
    { "categoryId": 60401, "kindId": 604, "categoryKey": "sumover", "categoryName": "总尾大" },
    { "categoryId": 60402, "kindId": 604, "categoryKey": "sumunder", "categoryName": "总尾小" },
    { "categoryId": 60501, "kindId": 605, "categoryKey": "yzy", "categoryName": "一中一" },
    { "categoryId": 60601, "kindId": 606, "categoryKey": "rx2", "categoryName": "任选二" },
    { "categoryId": 60602, "kindId": 606, "categoryKey": "rx3", "categoryName": "任选三" },
    { "categoryId": 60603, "kindId": 606, "categoryKey": "q3zx", "categoryName": "前三直选" },
    { "categoryId": 60604, "kindId": 606, "categoryKey": "rx4", "categoryName": "任选四" },
    { "categoryId": 60605, "kindId": 606, "categoryKey": "rx5", "categoryName": "任选五" },
    { "categoryId": 60606, "kindId": 606, "categoryKey": "rx6", "categoryName": "任选六" },
    { "categoryId": 60607, "kindId": 606, "categoryKey": "rx7", "categoryName": "任选七" },
    { "categoryId": 60608, "kindId": 606, "categoryKey": "rx8", "categoryName": "任选八" },
    { "categoryId": 60609, "kindId": 606, "categoryKey": "q2gx", "categoryName": "前二组选" },
    { "categoryId": 60610, "kindId": 606, "categoryKey": "q3gx", "categoryName": "前三组选" },
    { "categoryId": 60611, "kindId": 606, "categoryKey": "q2zx", "categoryName": "前二直选" },
    { "categoryId": 70101, "kindId": 701, "categoryKey": "ydb", "categoryName": "" },
    { "categoryId": 70201, "kindId": 702, "categoryKey": "lm", "categoryName": "" },
    { "categoryId": 70301, "kindId": 703, "categoryKey": "sumodd", "categoryName": "总和单" },
    { "categoryId": 70302, "kindId": 703, "categoryKey": "sumeven", "categoryName": "总和双" },
    { "categoryId": 70401, "kindId": 704, "categoryKey": "sumover", "categoryName": "总尾大" },
    { "categoryId": 70402, "kindId": 704, "categoryKey": "sumunder", "categoryName": "总尾小" },
    { "categoryId": 70501, "kindId": 705, "categoryKey": "ydwsb", "categoryName": "1-5色波" },
    { "categoryId": 70601, "kindId": 706, "categoryKey": "ydwflsx", "categoryName": "1-5福禄寿喜" },
    { "categoryId": 80101, "kindId": 801, "categoryKey": "dx", "categoryName": "大小" },
    { "categoryId": 80201, "kindId": 802, "categoryKey": "danshuang", "categoryName": "单双" },
    { "categoryId": 80301, "kindId": 803, "categoryKey": "gzdx", "categoryName": "极值大小" },
    { "categoryId": 80401, "kindId": 804, "categoryKey": "dxds", "categoryName": "大小单双" },
    { "categoryId": 80501, "kindId": 805, "categoryKey": "hezhi0", "categoryName": "0,27" },
    { "categoryId": 80502, "kindId": 805, "categoryKey": "hezhi1", "categoryName": "1,26" },
    { "categoryId": 80503, "kindId": 805, "categoryKey": "hezhi2", "categoryName": "2,25" },
    { "categoryId": 80504, "kindId": 805, "categoryKey": "hezhi3", "categoryName": "3,24" },
    { "categoryId": 80505, "kindId": 805, "categoryKey": "hezhi4", "categoryName": "4,23" },
    { "categoryId": 80506, "kindId": 805, "categoryKey": "hezhi5", "categoryName": "5,22" },
    { "categoryId": 80507, "kindId": 805, "categoryKey": "hezhi6", "categoryName": "6-21" },
    { "categoryId": 80601, "kindId": 806, "categoryKey": "sebo", "categoryName": "色波" },
    { "categoryId": 80701, "kindId": 807, "categoryKey": "baozi", "categoryName": "豹子" },
    { "categoryId": 80801, "kindId": 808, "categoryKey": "tmbs", "categoryName": "特码包三" }]



let categoryMap = new Map();
categorys.map(item => {
    let kindId = item.kindId;
    if (categoryMap.has(kindId)) {
        categoryMap.get(kindId).push(item);
    } else {
        categoryMap.set(kindId, [item])
    }
})

let classMap = new Map();
kinds.forEach(item => {
    let { kindId, groupId } = item;
    item.categorys = categoryMap.get(kindId);
    if (classMap.has(groupId)) {
        classMap.get(groupId).push(item);
    } else {
        classMap.set(groupId, [item])
    }
})

let lotteryMap = new Map();
lotterys.forEach(item => {
    let { lotteryId, groupId } = item;
    item.isChecked = false;
    item.kinds = classMap.get(groupId);
    if (lotteryMap.has(groupId)) {
        lotteryMap.get(groupId).push(item);
    } else {
        lotteryMap.set(groupId, [item])
    }
})

groups.forEach(item => {
    item.isChecked = false;
    item.lotterys = lotteryMap.get(item.groupId);
    item.lotterys[0].isChecked = true;
})




export default { groups, lotterys, kinds, categorys };
