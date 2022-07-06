<template>
    <div class="bodybox">
        <MyHeader></MyHeader>
        <!--主采种开始-->
        <PKtop></PKtop>
        <!--龙虎-->
        <div class="zhlzbox margt20">
            <div class="listhead">
                <div class="listheadl">
                    <span class="lmms"><i>3</i>龙虎路珠</span>
                </div>
                <div class="listheadr">
                    <div class="listheadrl">
                        <span id="today" class="checked">今天</span>
                        <span id="yesterday">昨天</span>
                        <span id="qianday">前天</span>
                    </div>
                    <div class="listheadrr">
                        <div class="rightime">
                            <div id="dateframe">
                                <input type="text" id="date" class="date" placeholder="">
                                <div id="datebox" class="calendar calendar-modal calendar-d"
                                     style="width: 219px; height: 269px; left: 1310.5px; top: 498.5px; z-index: 999; display: none;">
                                    <div class="calendar-inner" style="width: 219px;">
                                        <div class="calendar-views">
                                            <div class="view view-date" style="width: 219px;">
                                                <div class="calendar-hd"><a href="javascript:;"
                                                                            data-calendar-display-date=""
                                                                            class="calendar-display">2020/<span
                                                    class="m">10</span></a>
                                                    <div class="calendar-arrow"><span class="prev" title="上一月"
                                                                                      data-calendar-arrow-date="">&lt;</span><span
                                                        class="next" title="下一月" data-calendar-arrow-date="">&gt;</span>
                                                    </div>
                                                </div>
                                                <div class="calendar-ct" style="width: 219px; height: 219px;">
                                                    <ol class="week">
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            日
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            一
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            二
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            三
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            四
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            五
                                                        </li>
                                                        <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px">
                                                            六
                                                        </li>
                                                    </ol>
                                                    <ul class="date-items">
                                                        <li style="width: 219px;">
                                                            <ol class="days">
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">31
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">5
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">6
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">7
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">8
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">9
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">10
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">11
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">12
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">13
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">14
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">15
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">16
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">17
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">18
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">19
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">20
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">21
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">22
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">23
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">24
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">25
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">26
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">27
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">28
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">29
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">5
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">6
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">7
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">8
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">9
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">10
                                                                </li>
                                                            </ol>
                                                        </li>
                                                        <li style="width: 219px;">
                                                            <ol class="days">
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">27
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">28
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">29
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">5
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">6
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">7
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">8
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">9
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">10
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">11
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">12
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">13
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">14
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">15
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">16
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">17
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">18
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">19
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">20
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">21
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">22
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">23
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">24
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">25
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">26
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">27
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class=" now" data-calendar-day="">28
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">29
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">31
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">5
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">6
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">7
                                                                </li>
                                                            </ol>
                                                        </li>
                                                        <li style="width: 219px;">
                                                            <ol class="days">
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">25
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">26
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">27
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old now" data-calendar-day="">28
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">29
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="old" data-calendar-day="">31
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">5
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">6
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">7
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">8
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">9
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">10
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">11
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">12
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">13
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">14
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">15
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">16
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">17
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">18
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">19
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">20
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">21
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">22
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">23
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">24
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">25
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">26
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">27
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">28
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">29
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="" data-calendar-day="">30
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">1
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">2
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">3
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">4
                                                                </li>
                                                                <li style="width:31.285714285714285px;height:31.285714285714285px;line-height:31.285714285714285px"
                                                                    class="new" data-calendar-day="">5
                                                                </li>
                                                            </ol>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="view view-month" style="width: 219px;">
                                                <div class="calendar-hd"><a href="javascript:;"
                                                                            data-calendar-display-month=""
                                                                            class="calendar-display">2020</a>
                                                    <div class="calendar-arrow"><span class="prev" title="上一年"
                                                                                      data-calendar-arrow-month="">&lt;</span><span
                                                        class="next" title="下一年"
                                                        data-calendar-arrow-month="">&gt;</span></div>
                                                </div>
                                                <ol class="calendar-ct month-items"
                                                    style="width: 219px; height: 219px;">
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">1月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">2月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">3月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">4月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">5月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">6月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">7月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">8月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">9月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="" class="now">10月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">11月
                                                    </li>
                                                    <li style="width:54.75px;height:54.75px;line-height:54.75px"
                                                        data-calendar-month="">12月
                                                    </li>
                                                </ol>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="calendar-label"><p>HelloWorld</p><i></i></div>
                                </div>
                                <i class="dropicond"></i>
                            </div>
                        </div>
                        <div>选择日期&nbsp;</div>
                    </div>
                </div>
            </div>
        </div>
        <MyFoot></MyFoot>
    </div>
</template>
<script>
    import MyHeader from '@/components/layout/head'
    import MyFoot from '@/components/layout/foot'
    import PKtop from '@/components/lottery/pk10/top'

    export default {
        data() {
            return {}
        },
        components: {
            MyHeader,
            PKtop,
            MyFoot,
        },
        methods: {},
    }
</script>
<style scoped>

</style>
