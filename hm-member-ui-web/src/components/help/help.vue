<template>
    <div class="help">
      <select id="lottery" v-model="paramsLotteryKey" name="lottery">
        <!--开始-->
        <template v-for="item in gameMenu">
          <option  :value="item.lotteryKey" :key="item.lotteryKey">{{$t(item.lotteryKey)}}</option>
        </template>
      </select>
      <!--所有彩中这里都一样-->
      <h2>重要声明</h2>
      <ul>
        <li>客户一经在本公司开户或投注，即被视为已接受以下规则。</li>
      </ul>
      <ol>
        <li>1.为避免出现争议，请您务必在下注之后查看「下注状况」。</li>
        <li>2.任何投诉必须在开奖之前提出，公司不接受任何开奖之后的投诉。</li>
        <li>3.若公布赔率时出现任何打字错误或非故意人为失误，所有（相关）注单一律不算。</li>
        <li>4.公布之所有赔率为浮动赔率，下注时请确认当前赔率及金额，下注确认后一律不能修改。</li>
        <li>5.开奖后接受的投注，一律视为投机行为，[本局注单一律不返还本金及盈利]，敬请会员遵守游戏规则。</li>
        <li>6.若本后台发现客户以不正当的手法投注或投注注单不正常，后台将有权「取消」相应之注单，客户不得有任何异议。</li>
        <li>7.如因软件或线路问题导致交易内容或其他与账号设定不符合的情形，请在开奖前立即与本后台联络反映问题，否则本后台将以资料库中的数据为准。</li>
        <li>8.倘若发生遭黑客入侵破坏行为或不可抗拒之灾害导致网站故障或资料损坏、数据丢失等情况，后台将以资料库数据为依据。如发现玩家使用不正当程序上获得利益，本公司有权要求取消所获得之不当得利。</li>
        <li>9.各级管理人员及客户必须对本系统各项功能进行了解及熟悉，任何违反正常使用的操作，后台概不负责。</li>
        <li>10.请认真了解各款彩票游戏规则。</li>
        <li>11.如果会员信用额度超额或者为负数引起的争议，一律以公司处理为准。</li>
        <li>12.客户有责任确保自己的账户及密码保密，如果因客户的账户、密码简单，或因泄露导致被盗用，造成的损失将由客户本人承担；同时应立即通知本公司，并更改其个人详细资料。</li>
        <li>13.若官方福彩中心开奖错误导致本系统采集数据同时出错情况下，当期错误的所有注单以福彩中心官方当日最新数据为标准重新结算！在此特别声明，客户不得有任何异议。</li>
        <li>14.本公司将不承担任何因客戶违反当地相关交易法令，所引起之任何责任。</li>
        <li>15.本系统所有投注皆含本金。</li>
      </ol>
      <ul>
        <li>本公司有权保留一切争议事项及最后的决策权。</li>
      </ul>
      <!--公用部分结束-->
      <!--彩种开始-->
      <div class="div_rules ui-content" v-show="paramsLotteryKey=='bjpk10' ||
            paramsLotteryKey==='lucky10' || paramsLotteryKey==='speed10'||paramsLotteryKey==='hmpk10' ||
            paramsLotteryKey==='epssm' || paramsLotteryKey==='fhsc' || paramsLotteryKey==='tycsm' || paramsLotteryKey==='xyft3' || paramsLotteryKey==='sgft'
|| paramsLotteryKey==='xyft1396'">
        <div v-show="paramsLotteryKey==='bjpk10'">
          <h2 class="h2_font">北京赛车(原名：<a href="#" target="_blank">北京福彩“PK拾”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的投注时间、开奖时间和开奖号码与“北京PK拾”完全同步，营业时间为北京时间(GMT+8)每天上午09:10到晚上23:50，每20分钟开奖一次，每天开奖44期。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='speed10'">
          <h2 class="h2_font">极速赛车(原名：<a href="#" target="_blank">“Speed10”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  “极速赛车”为引进欧洲博彩公司开奖设备，由菲律宾博彩执照公司审核监控，联合开发的彩票游戏，营业时间为北京时间(GMT+8)每天上午07:30:30到凌晨05:59:15，每75秒开奖一次，每天开奖1079期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='lucky10'">
          <h2 class="h2_font">澳洲幸运10(原名：<a href="#" target="_blank">“Lucky Ball 10”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的投注时间、开奖时间和开奖号码与“澳洲幸运10”完全同步，营业时间为北京时间(GMT+8)每天上午07:34到凌晨05:59，每5分钟开奖一次，每天开奖269期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='epssm'">
          <h2 class="h2_font">EPS赛马规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的营业时间为北京时间(GMT+8)每天上午09:02到凌晨04:00，每2分钟开奖一次，每天开奖570期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='xyft3'">
          <h2 class="h2_font">幸运飞艇(3)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的营业时间为北京时间(GMT+8)每天上午09:00到凌晨04:00，每3分钟开奖一次，每天开奖380期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='xyft1396'">
          <h2 class="h2_font">国际幸运飞艇规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的营业时间为北京时间(GMT+8)每天下午13:04到凌晨04:04，每5分钟开奖一次，每天开奖180期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='hmpk10'">
          <h2 class="h2_font">老北京赛车规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>  该游戏的营业时间为北京时间(GMT+8)每天上午07:34到凌晨05:59，每5分钟开奖一次，每天开奖269期。 </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='fhsc'">
          <h2 class="h2_font">凤凰赛车规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每150秒开一次奖。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='tycsm'">
          <h2 class="h2_font">太阳城赛马规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每300秒开一次奖。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='sgft'">
          <h2 class="h2_font">SG飞艇规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每300秒开一次奖。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">1~10两面：</span>指单、双；大、小。</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">单、双：</span>号码为双数叫双，如4、8；号码为单数叫单，如5、9</li>
            <li><span class="thickening font_in_bold">大、小：</span>开出之号码大于或等于6为大，小于或等于5为小</li>
            <li><span class="thickening font_in_bold">第一名～第十名 车号指定：</span>每一个车号为一投注组合，开奖结果“投注车号”对应所投名次视为中奖，其余情形视为不中奖</li>
          </ul>
          <li><span class="thickening font_in_bold">1~5龙虎</span><span style="color:red;">【注意规则】</span>为：</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening" style="color:red;">冠  军 龙/虎：“第一名”车号大于“第十名”车号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">亚  军 龙/虎：“第二名”车号大于“第九名”车号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第三名 龙/虎：“第三名”车号大于“第八名”车号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第四名 龙/虎：“第四名”车号大于“第七名”车号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第五名 龙/虎：“第五名”车号大于“第六名”车号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
          </ul>
          <li><span class="thickening font_in_bold">冠军车号+亚军车号=冠亚和值(为3到19)</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">冠亚和单双：</span>“<span class="font_in_bold">冠亚和值</span>”为单视为投注“单”的注单视为中奖，为双视为投注“双”的注单视为中奖，其余视为不中奖。</li>
            <li><span class="thickening font_in_bold">冠亚和大小：</span>“<span class="font_in_bold">冠亚和值</span>”大于11时投注“大”的注单视为中奖，小于或等于11时投注“小”的注单视为中奖，其余视为不中奖。</li>
            <li><span class="thickening font_in_bold">冠亚和指定：</span>“<span class="font_in_bold">冠亚和值</span>”可能出现的结果为3～19， 投中对应“<span class="font_in_bold">冠亚和值</span>”数字的视为中奖，其余视为不中奖。</li>
          </ul>
        </ol>
      </div>
      <div v-show="paramsLotteryKey==='xyft' || paramsLotteryKey==='sbspeed10'">
        <div v-show="paramsLotteryKey==='xyft'">
          <h2 class="h2_font">幸运飞艇规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>营业时间为北京时间(GMT+8)每天下午13:04到凌晨04:04，每5分钟开奖一次，每天开奖180期。</p>
          </div>
          <h2 class="h2_font">具体游戏规则如下：</h2>
        </div>
        <div v-show="paramsLotteryKey==='sbspeed10'">
          <h2 class="h2_font">极速飞艇(原名：<a href="#" target="_blank">“SB Speed10”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p> “极速飞艇”为引进欧洲博彩公司开奖设备，由菲律宾博彩执照公司审核监控，联合开发的彩票游戏，营业时间为北京时间(GMT+8)每天上午07:31:00到凌晨05:59:45，每75秒开奖一次，每天开奖1079期。 </p>
          </div>
          <h2 class="h2_font">具体游戏规则如下：</h2>
        </div>
        <ol>
          <li><span class="thickening font_in_bold">1~10两面：</span>指单、双；大、小。</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">单、双：</span>号码为双数叫双，如4、8；号码为单数叫单，如5、9</li>
            <li><span class="thickening font_in_bold">大、小：</span>开出之号码大于或等于6为大，小于或等于5为小</li>
            <li><span class="thickening font_in_bold">第一名～第十名 船号指定：</span>每一个船号为一投注组合，开奖结果“投注船号”对应所投名次视为中奖，其余情形视为不中奖</li>
          </ul>
          <li><span class="thickening font_in_bold">1~5龙虎</span><span style="color:red;">【注意规则】</span>为：</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening" style="color:red;">冠  军 龙/虎：“第一名”船号大于“第十名”船号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">亚  军 龙/虎：“第二名”船号大于“第九名”船号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第三名 龙/虎：“第三名”船号大于“第八名”船号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第四名 龙/虎：“第四名”船号大于“第七名”船号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
            <li><span class="thickening" style="color:red;">第五名 龙/虎：“第五名”船号大于“第六名”船号视为【龙】中奖、反之小于视为【虎】中奖，其余情形视为不中奖。</span></li>
          </ul>
          <li><span class="thickening font_in_bold">冠军船号+亚军船号=冠亚和值(为3到19)</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">冠亚和单双：</span>“<span class="font_in_bold">冠亚和值</span>”为单视为投注“单”的注单视为中奖，为双视为投注“双”的注单视为中奖，其余视为不中奖。</li>
            <li><span class="thickening font_in_bold">冠亚和大小：</span>“<span class="font_in_bold">冠亚和值</span>”大于11时投注“大”的注单视为中奖，小于或等于11时投注“小”的注单视为中奖，其余视为不中奖。</li>
            <li><span class="thickening font_in_bold">冠亚和指定：</span>“<span class="font_in_bold">冠亚和值</span>”可能出现的结果为3～19， 投中对应“<span class="font_in_bold">冠亚和值</span>”数字的视为中奖，其余视为不中奖。</li>
          </ul>
        </ol>
      </div>

      <div v-show="paramsLotteryKey==='cqssc' || paramsLotteryKey==='lucky5' || paramsLotteryKey==='jsssc'
            || paramsLotteryKey==='xjssc' || paramsLotteryKey==='cjk5'|| paramsLotteryKey==='hmcqssc'
            ||paramsLotteryKey==='fhssc' || paramsLotteryKey==='tycssc' || paramsLotteryKey==='txffc' || paramsLotteryKey==='txsfc'
            || paramsLotteryKey==='twssc'">
        <div v-show="paramsLotteryKey==='cqssc'">
          <h2 class="h2_font">重庆时时彩(原名：<a href="#" target="_blank">重庆福彩“时时彩”)</a>规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“重庆时时彩”完全同步，营业时间如下：
              早場-每天上午07：10到晚上23:50，每20分钟开奖一次，共50期。
              晚場-凌晨00:10到凌晨03:10，每20分钟开奖一次，共9期，全日共59期。
              每日23：50到隔日00：10暫停投注。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='jsssc'">
          <h2 class="h2_font">极速时时彩(原名：<a href="#" target="_blank">“Speed5”)</a>规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>“极速时时彩”为引进欧洲博彩公司开奖设备，由菲律宾博彩执照公司审核监控，联合开发的彩票游戏，营业时间为北京时间(GMT+8)每天上午07:30:30到凌晨05:59:15，每75秒开奖一次，每天开奖1079期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='lucky5'">
          <h2 class="h2_font">澳洲幸运5(原名：<a href="#" target="_blank">“Lucky Ball 5”)</a>规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“澳洲幸运5”完全同步，营业时间为北京时间(GMT+8)每天上午07:34到凌晨05:59，每5分钟开奖一次，每天开奖269期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='xjssc'">
          <h2 class="h2_font">新疆时时彩(原名：<a href="#" target="_blank">新疆福彩“时时彩”)</a>规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“新疆时时彩”完全同步，营业时间为北京时间(GMT+8)每天上午10:00到凌晨02:00，每20分钟开奖一次，每天开奖48期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='cjk5'">
          <h2 class="h2_font">超级快5规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的营业时间为北京时间(GMT+8)每天上午09:00到凌晨03:59，每2分钟开奖一次，每天开奖570期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='twssc'">
          <h2 class="h2_font">台湾时时彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的营业时间为北京时间(GMT+8)每天上午07:00到凌晨23:55，每5分钟开奖一次，每天开奖203期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='hmcqssc'">
          <h2 class="h2_font">老重庆时时彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的营业时间为全天，每75秒开奖一次。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='fhssc'">
          <h2 class="h2_font">凤凰时时彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每150秒开一次奖。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='tycssc'">
          <h2 class="h2_font">太阳城时时彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每300秒开一次奖。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='txffc'">
          <h2 class="h2_font">腾讯分分彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每60秒开一次奖。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='txsfc'">
          <h2 class="h2_font">腾讯三分彩规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>北京时间（GMT+8）上午09:00开到凌晨04:00，每180秒开一次奖。
            </p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">第一球~第五球：</span>第一球特、第二球特、第三球特、第四球特、第五球特：指下注的每一球特与开出之号码其开奖顺序及开奖号码相同，视为中奖，如第一球开出号码8，下注第一球为8者视为中奖，其余情形视为不中奖。</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">单双大小：</span>根据相应单项投注第一球特 ~ 第五球特开出的球号，判断胜负。</li>
            <li><span class="thickening font_in_bold">大小：</span>根据相应单项投注的第一球特 ~ 第五球特开出的球号大於或等於5为特码大，小於或等於4为特码小。</li>
            <li><span class="thickening font_in_bold">单双：</span>根据相应单项投注的第一球特 ~ 第五球特开出的球号为双数叫特双，如2、6；特码为单数叫特单，如1、3。</li>
          </ul>
          <li><span class="thickening font_in_bold">总和单双大小：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">大小：</span>根据相应单项投注的第一球特 ~ 第五球特开出的球号大於或等於23为特码大，小於或等於22为特码小。</li>
            <li><span class="thickening font_in_bold">单双：</span>根据相应单项投注的第一球特 ~ 第五球特开出的球号数字总和值是双数为总和双，数字总和值是单数为总和单。</li>
          </ul>
          <li><span class="thickening font_in_bold">前三特殊玩法： 豹子 > 顺子 > 对子 > 半顺 > 杂六 。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">豹子：</span>中奖号码的万位千位百位数字都相同。----如中奖号码为000、111、999等，中奖号码的万位千位百位数字相同，则投注豹子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">顺子：</span>中奖号码的万位千位百位数字都相连，不分顺序。（数字9、0、1相连）----如中奖号码为123、901、321、546等，中奖号码万位千位百位数字相连，则投注顺子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">对子：</span>中奖号码的万位千位百位任意两位数字相同。（不包括豹子）----如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。如果开奖号码为豹子,则对子视为不中奖。如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">半顺：</span>中奖号码的万位千位百位任意两位数字相连，不分顺序。（不包括顺子、对子。）----如中奖号码为125、540、390、706，中奖号码有两位数字相连，则投注半顺者视为中奖，其它视为不中奖。如果开奖号码为顺子、对子,则半顺视为不中奖。--如中奖号码为123、901、556、233，视为不中奖。</li>
            <li><span class="thickening font_in_bold">杂六：</span>不包括豹子、对子、顺子、半顺的所有中奖号码。----如中奖号码为157，中奖号码位数之间无关联性，则投注杂六者视为中奖，其它视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">中三特殊玩法： 豹子 > 顺子 > 对子 > 半顺 > 杂六 。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">豹子：</span>中奖号码的拾位佰位千位数字都相同。----如中奖号码为000、111、999等，中奖号码的拾位佰位千位数字相同，则投注豹子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">顺子：</span>中奖号码的拾位佰位千位数字都相连，不分顺序。（数字9、0、1相连）----如中奖号码为123、901、321、546等，中奖号码拾位佰位千位数字相连，则投注顺子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">对子：</span>中奖号码的拾位佰位千位任意两位数字相同。（不包括豹子）----如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。如果开奖号码为豹子,则对子视为不中奖。如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">半顺：</span>中奖号码的拾位佰位千位任意两位数字相连，不分顺序。（不包括顺子、对子。）----如中奖号码为125、540、390、706，中奖号码有两位数字相连，则投注半顺者视为中奖，其它视为不中奖。如果开奖号码为顺子、对子,则半顺视为不中奖。--如中奖号码为123、901、556、233，视为不中奖。</li>
            <li><span class="thickening font_in_bold">杂六：</span>不包括豹子、对子、顺子、半顺的所有中奖号码。----如中奖号码为157，中奖号码位数之间无关联性，则投注杂六者视为中奖，其它视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">后三特殊玩法： 豹子 > 顺子 > 对子 > 半顺 > 杂六 。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">豹子：</span>中奖号码的百位十位个位数字都相同。----如中奖号码为000、111、999等，中奖号码的百位十位个位数字相同，则投注豹子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">顺子：</span>中奖号码的百位十位个位数字都相连，不分顺序。（数字9、0、1相连）----如中奖号码为123、901、321、546等，中奖号码百位十位个位数字相连，则投注顺子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">对子：</span>中奖号码的百位十位个位任意两位数字相同。（不包括豹子）----如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。如果开奖号码为豹子,则对子视为不中奖。如中奖号码为001，112、696，中奖号码有两位数字相同，则投注对子者视为中奖，其它视为不中奖。</li>
            <li><span class="thickening font_in_bold">半顺：</span>中奖号码的百位十位个位任意两位数字相连，不分顺序。（不包括顺子、对子。）----如中奖号码为125、540、390、706，中奖号码有两位数字相连，则投注半顺者视为中奖，其它视为不中奖。如果开奖号码为顺子、对子,则半顺视为不中奖。--如中奖号码为123、901、556、233，视为不中奖。</li>
            <li><span class="thickening font_in_bold">杂六：</span>不包括豹子、对子、顺子、半顺的所有中奖号码。----如中奖号码为157，中奖号码位数之间无关联性，则投注杂六者视为中奖，其它视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">龙虎和特殊玩法： 龙 > 虎 > 和 （0为最小，9为最大）。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">龙：</span>开出之号码第一球（万位）的中奖号码大于第五球（个位）的中奖号码，如出和局为打和。如 第一球开出4 第五球开出2；第一球开出9 第五球开出8；第一球开出5 第五球开出1...中奖为龙。</li>
            <li><span class="thickening font_in_bold">虎：</span>开出之号码第一球（万位）的中奖号码小于第五球（个位）的中奖号码，如出和局为打和。如 第一球开出7 第五球开出9；第一球开出3 第五球开出5；第一球开出5 第五球开出8...中奖为虎。</li>
            <li><span class="thickening font_in_bold">和：</span>开出之号码第一球（万位）的中奖号码等于第五球（个位）的中奖号码，例如开出结果：2***2则投注和局者视为中奖，其它视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">一字组合</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>在0~9中选择一个号码下注。如下注号码在开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）中任何一位出现则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>在0~9中选择一个号码下注。如下注号码在开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）中任何一位出现则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>在0~9中选择一个号码下注。如下注号码在开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）中任何一位出现则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">全五：</span>在0~9中选择一个号码下注。如下注号码在开出之号码的任何一位中出现则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">二字组合</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>在0~9中选择二个号码下注（不分顺序）。如下注号码在开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>在0~9中选择二个号码下注（不分顺序）。如下注号码在开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>在0~9中选择二个号码下注（不分顺序）。如下注号码在开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">二字定位</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">万千：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第一球（万位）及第二球（千位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万佰：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第一球（万位）及第三球（佰位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万拾：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第一球（万位）及第四球（拾位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万个：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第一球（万位）及第五球（个位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千佰：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第二球（千位）及第三球（佰位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千拾：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第二球（千位）及第四球（拾位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千个：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第二球（千位）及第五球（个位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰拾：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第三球（佰位）及第四球（拾位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰个：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第三球（佰位）及第五球（个位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">拾个：</span>在0~9中按顺序选择二个号码下注。如下注号码与开出之号码的第四球（拾位）及第五球（个位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">二字和数</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">万千：</span>如开出之号码的第一球（万位）及第二球（千位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万佰：</span>如开出之号码的第一球（万位）及第三球（佰位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万拾：</span>如开出之号码的第一球（万位）及第四球（拾位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万个：</span>如开出之号码的第一球（万位）及第五球（个位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千佰：</span>如开出之号码的第二球（千位）及第三球（佰位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千拾：</span>如开出之号码的第二球（千位）及第四球（拾位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千个：</span>如开出之号码的第二球（千位）及第五球（个位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰拾：</span>如开出之号码的第三球（佰位）及第四球（拾位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰个：</span>如开出之号码的第三球（佰位）及第五球（个位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">拾个：</span>如开出之号码的第四球（拾位）及第五球（个位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">二字和数尾数</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">万千：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第一球（万位）及第二球（千位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万佰：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第一球（万位）及第三球（佰位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万拾：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第一球（万位）及第四球（拾位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">万个：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第一球（万位）及第五球（个位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千佰：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第二球（千位）及第三球（佰位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千拾：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第二球（千位）及第四球（拾位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">千个：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第二球（千位）及第五球（个位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰拾：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第三球（佰位）及第四球（拾位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">佰个：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第三球（佰位）及第五球（个位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">拾个：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第四球（拾位）及第五球（个位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">三字组合</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>在0~9中选择三个号码下注（不分顺序）。如下注号码在开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>在0~9中选择三个号码下注（不分顺序）。如下注号码在开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>在0~9中选择三个号码下注（不分顺序）。如下注号码在开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）中出现（不分顺序）则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">三字定位</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>在0~9中按顺序选择三个号码下注。如下注号码与开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>在0~9中按顺序选择三个号码下注。如下注号码与开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>在0~9中按顺序选择三个号码下注。如下注号码与开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）相同且顺序一至则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">三字和数</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>如开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>如开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>如开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）相加的和数与下注选择的范围相同，则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">三字和数尾数</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">前三：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第一球（万位）、第二球（千位）及第三球（佰位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">中三：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第二球（千位）、第三球（佰位）及第四球（拾位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
            <li><span class="thickening font_in_bold">后三：</span>在0~9中选择一个号码下注。如下注号码与开出之号码的第三球（佰位）、第四球（拾位）及第五球（个位）相加的和数之尾数相同，则视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">组选三</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>在前三球、中三球、后三球中可以挑选5~10个号码，当开奖结果中有且只有两个号码重复，则视为中奖。挑选不同个数号码有其相对应的赔率。如果是选择 1、2、3、4、5中的任何两个号码，且其中有一个号码重复则中奖。</li>
            <li>举例：112、344，若是开出豹子则不算中奖。</li>
            <li>【附注】："豹子"为三字同号，例如：111、222。</li>
          </ul>
          <li><span class="thickening font_in_bold">组选六</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>在前三球、中三球、后三球中可以挑选择4~8个号码，当开奖结果都出现在所下注的号码中且没有任何号码重复，则视为中奖。挑选不同的号码有其相对应的赔率，中奖赔率以所选号码中的最小赔率计算彩派。</li>
            <li>举例：如果是选择(1、2、3、4)，例如：开奖结果为123、124、134、234都中奖。例如：112、133、145、444等都是不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">复式组合</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>在前三球、中三球、后三球中分别选择号码组合。例如在后三选择3x2x4，即佰位数选3个号码，拾位数选2个号码，个位数选4个号码或选择3x3x3，如每个位数所选择的号码都对应出现在开奖结果中，则为中奖。 此游戏选号有以下限制：</li>
            <li>每一位数最少要选择1个号码，最多选10个号码。</li>
            <li>三个位数共选取号码数量，最少需选择9个号，最多选择21个号码。</li>
          </ul>
          <li><span class="thickening font_in_bold">跨度</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>以开奖三个号码（前三球、中三球、后三球）的最大差距(跨度)，作为中奖的依据。会员可以选择0~9的任一跨度。</li>
            <li>举例：开奖结果为3，4，8。中奖的跨度为5。(最大号码 8减最小号码 3 = 5)。若开奖结果三号码都相同，称作豹子，投注注豹子者中奖，其馀为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">牛牛</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>以开出之五个号码为基础,组合任三个号码成0或10的倍数取剩余两个号码之和为点数(大于10时减10作为对奖基准,例如:00017为8点,02818为9点,99219.88400皆为10点俗称牛牛;01234,12589因任三个数都无法组合成0或10的倍数此局视为无点，当五个号码相同时，例：11111…99999视为「无点」，00000则视为「牛牛」。</li>
          </ul>
          <li><span class="thickening font_in_bold">牛牛双面</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>**牛大(6、7、8、9、10) ，牛小 (1、2、3、4、5 )** ：若开出的点数在大或小区则中奖。无点(无牛)则视为不中奖。 </li>
            <li>**牛单(1、3、5、7、9 ) ，牛双 (2、4、6、8、10)** ：若开出的点数在单或双区则中奖。无点(无牛)则视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">梭哈</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">**五梅**：</span>开出的五个号码全部相同,例如:00000,11111,22222。</li>
            <li><span class="thickening font_in_bold">**四带一**：</span>开出的五个号码中四个相同，例如:10000,11110,22221,33331,44442,55552。</li>
            <li><span class="thickening font_in_bold">**葫芦**：</span>开出的五个号码中三个相同(三条)及两个号码相同(一对)，例如:11222,22333,33444,11000。</li>
            <li><span class="thickening font_in_bold">**三条**：</span>开出的五个号码中三个相同及剩下的两个号码不相同，例如:12333,01222,34555,66678。</li>
            <li><span class="thickening font_in_bold">**顺子**：</span>开出的五个号码从小到大排列为01234,12345,23456,34567,45678,56789,06789,01789,01289,01239。</li>
            <li><span class="thickening font_in_bold">**两对**：</span>开出的五个号码中有两组相同，例如:00112,11223,33445,66779。</li>
            <li><span class="thickening font_in_bold">**单对**：</span>开出的五个号码中有且只有一组相同,例如:00123,00789,55678。</li>
            <li><span class="thickening font_in_bold">**五不靠**：</span>开出的五个号码从小到大排列为13579,02468。</li>
            <li><span class="thickening font_in_bold">**散号**：</span>开出的五个号码没有任何相同或相连关系的，例如:12356,01345,34678,12579。</li>
          </ul>
        </ol>
      </div>

      <div v-show="paramsLotteryKey==='gdkl10' || paramsLotteryKey==='speed8' || paramsLotteryKey==='xync' || paramsLotteryKey==='lucky8'">
        <div v-show="paramsLotteryKey==='gdkl10'">
          <h2 class="h2_font">广东快乐十分(原名：<a href="#" target="_blank">广东福彩“快乐十分”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“广东快乐十分”完全同步，营业时间为北京时间(GMT+8)每天上午09:00到晚上23:00，每20分钟开奖一次，每天开奖42期。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='speed8'">
          <h2 class="h2_font">极速快乐十分(原名：<a href="#" target="_blank"> “Speed8”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>“极速快乐十分”为引进欧洲博彩公司开奖设备，由菲律宾博彩执照公司审核监控，联合开发的彩票游戏，营业时间为北京时间(GMT+8)每天上午07:30:30到凌晨03:59:15，每75秒开奖一次，每天开奖983期。</p>
          </div>
        </div>

        <div v-show="paramsLotteryKey==='xync'">
          <h2 class="h2_font"> 重庆幸运农场(原名：<a href="#" target="_blank"> 重庆福彩“幸运农场”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“重庆时时彩”完全同步，营业时间如下：
              早場-每天上午07：00到晚上23:40，每20分钟开奖一次，共50期。
              晚場-凌晨00:00到凌晨03:00，每20分钟开奖一次，共9期，全日共59期。
              每日23：40到隔日00：00暫停投注。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='lucky8'">
          <h2 class="h2_font">澳洲幸运8(原名：<a href="#" target="_blank">“Lucky Ball 8”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“澳洲幸运20”完全同步，营业时间为北京时间(GMT+8)每天上午07:34到凌晨05:59，每5分钟开奖一次，每天开奖269期。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">两面：</span>指单、双；大、小、尾大、尾小。</li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">单、双：</span>号码为双数叫双，如8、16；号码为单数叫单，如19、5。</li>
            <li><span class="thickening font_in_bold">大、小：</span>开出之号码大于或等于11为大，小于或等于10为小。</li>
            <li><span class="thickening font_in_bold">尾大、尾小：</span>开出之尾数大于或等于5为尾大，小于或等于4为尾小。</li>
            <li>每一个号码为一投注组合，假如投注号码为开奖号码并在所投的球位置，视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">中发白</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">中：</span>开出之号码为01、02、03、04、05、06、07</li>
            <li><span class="thickening font_in_bold">发：</span>开出之号码为08、09、10、11、12、13、14</li>
            <li><span class="thickening font_in_bold">白：</span>开出之号码为15、16、17、18、19、20</li>
          </ul>
          <li><span class="thickening font_in_bold">方位</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">东：</span>开出之号码为01、05、09、13、17</li>
            <li><span class="thickening font_in_bold">南：</span>开出之号码为02、06、10、14、18</li>
            <li><span class="thickening font_in_bold">西：</span>开出之号码为03、07、11、15、19</li>
            <li><span class="thickening font_in_bold">北：</span>开出之号码为04、08、12、16、20</li>
          </ul>
          <li><span class="thickening font_in_bold">龙虎以第一球的中奖号码和第八球的中奖号码做为对奖号码</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">龙：</span>开出之号码第一球的中奖号码大于第八球的中奖号码。如 第一球开出14 第八球开出09；第一球开出17 第八球开出08；第一球开出05 第八球开出01...中奖为龙。</li>
            <li><span class="thickening font_in_bold">虎：</span>开出之号码第一球的中奖号码小于第八球的中奖号码。如 第一球开出14 第八球开出16；第一球开出13 第八球开出18；第一球开出05 第八球开出08...中奖为虎。</li>
          </ul>
          <li><span class="thickening font_in_bold">正码：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>从01至20中任意选择1个号码进行投注， 投注号码与开奖号码中任意1个位置的号码相符，即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">总和单双：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>所有8个开奖号码的数字总和值是单数为总和单，如数字总和值是31、51；所有8个开奖号码的数字总和值是双数为总和双，如数字总和是42、80；假如投注组合符合中奖结果，视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">总和大小：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>所有8个开奖号码的数字总和值85到132为总大；所有8个开奖号码的数字总和值36到83为总分小；所有8个开奖号码的数字总和值为84打和；如开奖号码为01、20、02、08、17、09、11，数字总和是68，则总分小。假如投注组合符合中奖结果，视为中奖，其余情形视为不中奖，打和不计算结果。</li>
          </ul>
          <li><span class="thickening font_in_bold">选二任选：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择2个号码对开奖号码中任意2个位置的投注。 投注号码与开奖号码中任意2个位置的号码相符，即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">选二连组：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择2个号码对开奖号码中按开奖顺序出现的2个连续位置的投注。 投注号码与开奖号码中按开奖顺序出现的2个连续位置的号码相符（顺序不限），即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">选三任选：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择3个号码对开奖号码中任意3个位置的投注。 投注号码与开奖号码中任意3个位置的号码相符，即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">选三前组：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择3个号码对开奖号码中按开奖顺序出现的前3个连续位置的投注。 投注号码与开奖号码中按开奖顺序出现的前3个位置的号码相符（顺序不限），即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">选四任选：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择4个号码，对开奖号码中任意4个位置的投注。 投注号码与开奖号码中任意4个位置的号码相符，即中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">选五任选：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>指从01至20中任意选择5个号码，对开奖号码中任意5个位置的投注。 投注号码与开奖号码中任意5个位置的号码相符，即中奖。</li>
          </ul>
        </ol>
      </div>


      <div v-show="paramsLotteryKey==='bjk3' || paramsLotteryKey==='jsk3' || paramsLotteryKey==='jlk3' || paramsLotteryKey==='gxk3' || paramsLotteryKey==='speed3'">
        <div v-show="paramsLotteryKey==='speed3'">
          <h2 class="h2_font">极速快3(原名：<a href="#" target="_blank"> “Speed3”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>“极速快3”为引进欧洲博彩公司开奖设备，由菲律宾博彩执照公司审核监控，联合开发的彩票游戏，营业时间为北京时间(GMT+8)每天上午07:30:30到凌晨05:59:15，每75秒开奖一次，每天开奖983期。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='bjk3'">
          <h2 class="h2_font">北京快3规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>“北京快3”营业时间为北京时间(GMT+8)每天上午09:00:00到晚上23:40:00，每20分钟开奖一次，每天开奖44期。</p>
          </div>
        </div>

        <div v-show="paramsLotteryKey==='jsk3'">
          <h2 class="h2_font">江苏快3规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>“江苏快3”营业时间为北京时间(GMT+8)每天上午08:30:00到晚上22:10:00，每20分钟开奖一次，每天开奖41期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='gxk3'">
          <h2 class="h2_font">广西快3(原名：<a href="#" target="_blank">广西福彩“快3”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“广西快3”完全同步，营业时间为北京时间(GMT+8)每天上午09:10到晚上22:30，每20分钟开奖一次，每天开奖40期。
            </p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='jlk3'">
          <h2 class="h2_font">吉林快3(原名：<a href="#" target="_blank">吉林福彩“快3”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“广西快3”完全同步，营业时间为北京时间(GMT+8)每天上午09:10到晚上22:30，每20分钟开奖一次，每天开奖40期。
            </p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">骰宝</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">小：</span>三粒骰子之点数总和由4点至10点。</li>
            <li><span class="thickening font_in_bold">大：</span>三粒骰子之点数总和由11点至17点；注：若三粒骰子平面点数相同，通吃「大」、「小」各注。</li>
            <li><span class="thickening font_in_bold">三军/鱼虾蟹：</span>任何一粒骰子出现选定之平面点数。</li>
            <li><span class="thickening font_in_bold">围骰：</span>三粒骰子平面与选定点数相同。</li>
            <li><span class="thickening font_in_bold">全骰：</span>在一点至六点内，三粒骰子平面点数相同。</li>
            <li><span class="thickening font_in_bold">点数：</span>由4点至17点，三粒骰子平面点数之总和。</li>
            <li><span class="thickening font_in_bold">长牌：</span>任两粒骰子之平面点数。</li>
            <li><span class="thickening font_in_bold">短牌：</span>选定两粒骰子之平面点数。</li>

          </ul>
          <li><span class="thickening font_in_bold">四门子 单点 玩法：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">基生: </span>三粒骰子之点数总和为5,9,13,17。</li>
            <li><span class="thickening font_in_bold">老虎: </span>三粒骰子之点数总和为6,10,14,18。</li>
            <li><span class="thickening font_in_bold">出门: </span>三粒骰子之点数总和为3,7,11,15。</li>
            <li><span class="thickening font_in_bold">青龙: </span>三粒骰子之点数总和为4,8,12,16。</li>
          </ul>
          <li><span class="thickening font_in_bold">四门子 合押 玩法：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">基生和老虎: </span>三粒骰子之点数总和为 基生或者老虎 时就视为中奖。</li>
            <li><span class="thickening font_in_bold">出门和青龙: </span>三粒骰子之点数总和为 出门或者青龙 时就视为中奖。</li>
            <li><span class="thickening font_in_bold">基生和出门: </span>三粒骰子之点数总和为 基生或者出门 时就视为中奖。</li>
            <li><span class="thickening font_in_bold">老虎和青龙: </span>三粒骰子之点数总和为 老虎或者青龙 时就视为中奖。</li>
            <li><span class="thickening font_in_bold">基生和青龙: </span>三粒骰子之点数总和为 基生或者青龙 时就视为中奖。</li>
            <li><span class="thickening font_in_bold">老虎和出门: </span>三粒骰子之点数总和为 老虎或者出门 时就视为中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">四门子 主防 玩法：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">基生防老虎:</span> 三粒骰子之点数总和为 基生 时就视为中奖,为 老虎 时就回本；<li/>
            <li><span class="thickening font_in_bold">基生防出门:</span> 三粒骰子之点数总和为 基生 时就视为中奖,为 出门 时就回本；<li/>
            <li><span class="thickening font_in_bold">基生防青龙:</span> 三粒骰子之点数总和为 基生 时就视为中奖,为 青龙 时就回本；<li/>
            <li><span class="thickening font_in_bold">老虎防基生:</span> 三粒骰子之点数总和为 老虎 时就视为中奖,为 基生 时就回本；<li/>
            <li><span class="thickening font_in_bold">老虎防出门:</span> 三粒骰子之点数总和为 老虎 时就视为中奖,为 出门 时就回本；<li/>
            <li><span class="thickening font_in_bold">老虎防青龙:</span> 三粒骰子之点数总和为 老虎 时就视为中奖,为 青龙 时就回本；<li/>
            <li><span class="thickening font_in_bold">出门防基生:</span> 三粒骰子之点数总和为 出门 时就视为中奖,为 基生 时就回本；<li/>
            <li><span class="thickening font_in_bold">出门防老虎:</span> 三粒骰子之点数总和为 出门 时就视为中奖,为 老虎 时就回本；<li/>
            <li><span class="thickening font_in_bold">出门防青龙:</span> 三粒骰子之点数总和为 出门 时就视为中奖,为 青龙 时就回本；<li/>
            <li><span class="thickening font_in_bold">青龙防基生:</span> 三粒骰子之点数总和为 青龙 时就视为中奖,为 基生 时就回本；<li/>
            <li><span class="thickening font_in_bold">青龙防老虎:</span> 三粒骰子之点数总和为 青龙 时就视为中奖,为 老虎 时就回本；<li/>
            <li><span class="thickening font_in_bold">青龙防出门:</span> 三粒骰子之点数总和为 青龙 时就视为中奖,为 出门 时就回本；<li/>
          </ul>
        </ol>
      </div>


      <div v-show="paramsLotteryKey==='bjfast8' || paramsLotteryKey==='lucky20'">
        <div v-show="paramsLotteryKey==='lucky20'">
          <h2 class="h2_font">澳洲幸运20(原名：<a href="#" target="_blank">“Lucky Ball 20”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“澳洲幸运20”完全同步，营业时间为北京时间(GMT+8)每天上午07:34到凌晨05:59，每5分钟开奖一次，每天开奖269期。</p>
          </div>
        </div>
        <div v-show="paramsLotteryKey==='bjfast8'">
          <h2 class="h2_font">北京快乐8(原名：<a href="#" target="_blank">北京福彩“快乐8”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“北京快乐8”完全同步，营业时间为北京时间(GMT+8)每天上午09:00到晚上23:55，每5分钟开奖一次，每天开奖179期。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">正码</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>投注的1个号码与当期摇出的20个号码中的任1个号码相同，则中奖</li>
          </ul>
          <li><span class="thickening font_in_bold">总和：以所有开出的全部20个号码加起来的和值来判定</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">总和大/小：</span>20个号码加总的和值大于810，为和大；20个号码加总的和值小于810，则为和小。</li>
            <li><span class="thickening font_in_bold">总和单/双：</span>20个号码加总的和值为单，叫做和单；20个号码加总的和值为双，叫做和双。</li>
            <li><span class="thickening font_in_bold">总和810：</span>20个号码加总的和值等于810，叫和值810。(当和值等于810,则大小退回本金)举例：开奖号码为1，2，3，4，5，6，7，8，9，10，11，12，13，14，15，16，17，18，19，20；那么此20个开奖号码的和值总和为210，则为小，为双。则投注小和双者中奖。投注大、单、和值810者不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">总和过关：开出的20个号码的总和的游戏，分为「大单」，「小单」，「大双」和「小双」。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>总和大于810为「总数大」,小于810为「总数小」。<li/>
            <li>总和为双数叫「双」，单数叫「单」。<li/>
            <li>通过大小和单双组合产生「大单」，「小单」，「大双」和「小双」四种结果。<li/>
            <li>总和等于810，则视为和局。<li/>
            <li><span class="thickening font_in_bold">举例：</span> 开奖号码为01、04、05、10、11、13、20、27、30、32、33、36、40、47、54、59、61、64、67、79，总和是693，总和小于810，并且是单数，则为「小单」。下注「小单」为赢，反之则输。<li/>
          </ul>
          <li><span class="thickening font_in_bold">前后和：开奖号码1至40为前盘号码，41至80为后盘号码。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>开出的20个号码中，前盘号码（1-40）在此局开出号码数目占多数时，此局为：前(多)。<li/>
            <li>开出的20个号码中，后盘号码（41-80）在此局开出号码数目占多数时，此局为:后(多)。<li/>
            <li>开出的20个号码中，前盘号码（1－40）和后盘号码（41-80）在此局开出的数目相同时（各10个数字），此局为：前后(和)。且投注前（多）或后（多）视为不中奖，不返回投注金额。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此局开出1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20. 此局为：前(多)。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此局开出41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60 此局为：后(多)。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此局开出 1,2,3,4,5,6,7,8,9,10,41,42,43,44,45,46,47,48,49,50 此局为：前后(和)。<li/>
          </ul>
          <li><span class="thickening font_in_bold">单双和：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>开奖号码中1，3，5，7，…，75，77，79为单数号码，2，4，6，8，……，76，78，80为双数号码。当期开出的20个中奖号码中，如单数号码数目占多数时（超过10个），则为单(多)，投注单(多)中奖；双数号码占多数时（超过10个），则为双(多)，投注双(多)中奖；如果单数和双数号码数目相同时（均为 10个），则为单双(和)，投注单双(和)者中奖。且投注单（多）或双（多）视为不中奖，不返回下注金额。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此期开出1，3，5，7，9，11，13，15，17，19，21，22，24，26，28，30，32，34，46，68， 其中单数11个双数9个，此期为：单(多)。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此期开出2，4，6，8，10，12，14，16，44，48，66，68，25，27，31，35，37，39，41，55， 其中双数12个单数8个，此期为：双(多)。<li/>
            <li><span class="thickening font_in_bold">举例：</span>此期开出2，4，6，8，10，12，14，16，18，20，41，43，45，47，49，51，53，55，57，59， 其中单数10个双数10个，此期为：单双(和)。<li/>
          </ul>

          <li><span class="thickening font_in_bold">五行：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li>开出的20个号码的总和分在5个段，以金、木、水、火、土命名：金（210～695）、木（696～763）、水（764～855）、火（856～923）和土（924～1410）。<li/>
            <li><span class="thickening font_in_bold">举例：</span>开奖号码为01、04、05、10、11、13、20、27、30、32、33、36、40、47、54、59、61、64、67、79，总和是693，<li/>
            <li>则总分数在210－695段内，则开出的是「金」。下注「金」为赢，反之则输。<li/>
          </ul>
        </ol>
      </div>


      <div v-show="paramsLotteryKey==='gd11x5'">
        <div v-show="paramsLotteryKey==='gd11x5'">
          <h2 class="h2_font">广东11选5(原名：<a href="#" target="_blank">广东体彩“11选5”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“广东11选5”完全同步，营业时间为北京时间(GMT+8)每天上午09:10到晚上23:10，每20分钟开奖一次，每天开奖42期。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">【单码】- 指第一球、第二球、第三球、第四球、第五球出现的顺序与号码为派彩依据。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">单号：</span>如现场滚球第一个开奖号码为10号，投注第一球为10号则视为中奖，其它号码视为不中奖 大小：开出的号码大于或等于6为大，小于或等于5为小，开出11为和 (不计算输赢)</li>
            <li><span class="thickening font_in_bold">单双：</span>号码为双数叫双，如2、8；号码为单数叫单，如5、9；开出11为和 (不计算输赢)。</li>
          </ul>
          <li><span class="thickening font_in_bold">【总和】-以全部开出的5个号码，加起来的总和来判定。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">总和大小：</span>所有开奖号码数字加总值大于30为和大；总和值小于30为和小；若总和值等于30为和 (不计算输赢)。</li>
            <li><span class="thickening font_in_bold">总和单双：</span>所有开奖号码数字加总值为单数叫和单，如11、31；加总值为双数叫和双，如42、30。</li>
            <li><span class="thickening font_in_bold">总和尾数大小：</span>所有开奖号码数字加总值的尾数，大于或等于5为尾大，小于或等于4为尾小。</li>
          </ul>
          <li><span class="thickening font_in_bold">【龙虎】</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">龙：</span>第一球开奖号码大于第五球开奖号码，如第一球开出10，第五球开出7。<li/>
            <li><span class="thickening font_in_bold">虎：</span>第一球开奖号码小于第五球开奖号码，如第一球开出3，第五球开出7。<li/>
          </ul>
          <li><span class="thickening font_in_bold">选号- 选号玩法是由1~11号中，选出1~5个号码为一投注组合来进行投注。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">一中一: </span>投注1个号码与当期开奖的5个号码中任1个号码相同，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选二: </span>投注2个号码与当期开奖的5个号码中任2个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选三: </span>投注3个号码与当期开奖的5个号码中任3个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选四: </span>投注4个号码与当期开奖的5个号码中任4个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选五: </span>投注5个号码与当期开奖的5个号码中5个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选六: </span>投注6个号码中任5个号码与当期开奖的5个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选七: </span>投注7个号码中任5个号码与当期开奖的5个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">任选八: </span>投注8个号码中任5个号码与当期开奖的5个号码相同(顺序不限)，视为中奖。</li>
            <li><span class="thickening font_in_bold">组选前二: </span>投注的2个号码与当期顺序开出的5个号码中的前2个号码相同，视为中奖。</li>
            <li><span class="thickening font_in_bold">组选前三: </span>投注的3个号码与当期顺序开出的5个号码中的前3个号码相同，视为中奖。</li>
            <li><span class="thickening font_in_bold">直选前二: </span>投注的2个号码与当期顺序开出的5个号码中的前2个号码相同且顺序一致，视为中奖。</li>
            <li><span class="thickening font_in_bold">直选前三: </span>投注的3个号码与当期顺序开出的5个号码中的前3个号码相同且顺序一致，视为中奖。</li>
          </ul>
        </ol>
      </div>


      <div v-show="paramsLotteryKey==='gxkl10'">
        <div v-show="paramsLotteryKey==='gxkl10'">
          <h2 class="h2_font">广西快乐十分(原名：<a href="#" target="_blank">广西福彩“快乐十分”</a>)规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>该游戏的投注时间、开奖时间和开奖号码与“广西快乐十分”完全同步，营业时间为北京时间(GMT+8)每天上午09:00到晚上22:20，每20分钟开奖一次，每天开奖40期。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">假设快乐十分开奖结果为1、2、3、4、5，那么正码是1、2、3、4，特码是5。（每个位数在开奖时，从1 ~ 21中摇出一个结果）选号玩法。</span></li>
          <li><span class="thickening font_in_bold">单双大小：根据正码一~正码四及特码开出的球号判断胜负。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">大小：</span>开出号码01~10为小，11~20为大，21为和。</li>
            <li><span class="thickening font_in_bold">单双：</span>开出号码21为和。</li>
          </ul>
          <li><span class="thickening font_in_bold">尾大、小：开出之特码尾数大于或等于5为尾大，小于或等于4为尾小，出21为和。</span></li>
          <li><span class="thickening font_in_bold">单号1~5：从01至21中任意选择一个号码进行投注，投注号码与开奖号码相符，即中奖。</span></li>
          <li><span class="thickening font_in_bold">福禄寿喜：依号码顺序将21除外的20个号码分为四种类（如下），再以四种类下注。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">福：</span>01、02、03、04、05</li>
            <li><span class="thickening font_in_bold">禄：</span>06、07、08、09、10</li>
            <li><span class="thickening font_in_bold">寿：</span>11、12、13、14、15</li>
            <li><span class="thickening font_in_bold">喜：</span>16、17、18、19、20</li>
            <li>若当期号码在相对应的球位落在下注三种类范围内，视为中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">色波：依特码、正码1~4将21个号码分为三种类，分别为红、蓝、绿（如下），再依三种类下注。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">红：</span>1、4、7、10、13、16、19</li>
            <li><span class="thickening font_in_bold">蓝：</span>2、5、8、11、14、17、20</li>
            <li><span class="thickening font_in_bold">绿：</span>3、6、9、12、15、18、21</li>
            <li>若当期号码在相对应的球位落在下注三种类范围内，视为中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">合数单双：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">合单：</span>指开出球号的个位数加上十位数之和为“单数”，如1、14、18。</li>
            <li><span class="thickening font_in_bold">合双：</span>指开出球号的个位数加上十位数之和为“双数”，如11、15、19。</li>
            <li>开出21为和。</li>
          </ul>
          <li><span class="thickening font_in_bold">总和：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">总大小：</span>数字总和值56~95为总大，数字总和值15~54为总小，所有5个开奖号码的总和值为55打和。</li>
            <li><span class="thickening font_in_bold">总单双：</span>数字总和值是双数为总和双，数字总和值是单数为总和单。</li>
            <li><span class="thickening font_in_bold">总尾大小：</span>所有5个开奖号码的数字总和数值的个位数大于或等于5为总尾大，小于或等于4为总尾小假如投注组合符合中奖结果，视为中奖，其余情形视为不中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">龙虎：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">龙：</span>第一球开奖号码大于第五球开奖号码，如第一球开出10，第五球开出7。</li>
            <li><span class="thickening font_in_bold">虎：</span>第一球开奖号码小于第五球开奖号码，如第一球开出3，第五球开出7。</li>
          </ul>
        </ol>
      </div>

      <div v-show="paramsLotteryKey==='pcdd'">
        <div v-show="paramsLotteryKey==='pcdd'">
          <h2 class="h2_font">PC蛋蛋玩法规则说明</h2>
          <div style="list-style-type: disc; padding-left: 20px;">
            <p>PC蛋蛋开奖结果从上午09:00到晚上23:55，每5分钟开奖一次，每天开奖179期。PC蛋蛋每期开奖共开出20个数字，PC蛋蛋将这20个开奖号码按照由小到大的顺序依次排列；取其1-6位开奖号码相加，和值的末位数作为PC蛋蛋开奖第一个数值； 取其7-12位开奖号码相加，和值的末位数作为PC蛋蛋开奖第二个数值，取其13-18位开奖号码相加，和值的末位数作为PC 蛋蛋开奖第三个数值；三个数值相加即为PC蛋蛋最终的开奖结果。</p>
          </div>
        </div>
        <h2 class="h2_font">具体游戏规则如下：</h2>
        <ol>
          <li><span class="thickening font_in_bold">单双大小：根据正码一~正码四及特码开出的球号判断胜负。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">大小：</span>数字14-27为大 数字0-13为小。</li>
            <li><span class="thickening font_in_bold">单双：</span>数字1，3，5，~27为单 数字0，2，4~26为双。</li>
            <li><span class="thickening font_in_bold">极小：</span>[极小0-5]。</li>
            <li><span class="thickening font_in_bold">极大：</span>[极大22-27]。</li>
            <li><span class="thickening font_in_bold">大小双：</span>数字14，16，~26为大双 数字0，2，4，~12为小双。</li>
            <li><span class="thickening font_in_bold">大小单：</span>数字15，17，~27为大单 数字1，3，5，~13为小单。</li>
          </ul>
          <li><span class="thickening font_in_bold">色波：将27个号码分为三种类，分别为红、蓝、绿（如下），再依三种类下注。</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">红：</span>3、6、9、12、15、18、21、24</li>
            <li><span class="thickening font_in_bold">蓝：</span>2、5、8、11、17、20、23、26</li>
            <li><span class="thickening font_in_bold">绿：</span>1、4、7、10、16、19、22、25</li>
            <li><span class="thickening font_in_bold"></span>出现赛果号码为0、13、14、27则色波全不中。</li>
            <li><span class="thickening font_in_bold">豹子：</span>开出的三个号码全部相等视为中奖。</li>
            <li>若当期号码在相对应的球位落在下注三种类范围内，视为中奖。</li>
          </ul>
          <li><span class="thickening font_in_bold">特码包三：</span></li>
          <ul style="list-style-type: disc; padding-left: 20px;">
            <li><span class="thickening font_in_bold">特码包三：</span>选三个号码，有一个中则视为中将。</li>
          </ul>
        </ol>
      </div>
    </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'
    export default {
        name: "help",
      data(){
          return{
            paramsLotteryKey:'bjpk10'
          }
      },
      computed:{
        ...mapGetters(['gameMenu'])
      },
      methods:{
        rulesSwitch(lotteryKey){
          this.paramsLotteryKey = lotteryKey;
        }
      },
      mounted(){
        if(this.$route.query.lotteryKey){
          this.paramsLotteryKey = this.$route.query.lotteryKey;
        }else{
          this.paramsLotteryKey = 'bjpk10';
        }
      }
    }
</script>

<style scoped>
  .help{
    color: #000000;
    font-size: 13px;
    background-color: #ffffff;
  }
  .help h2 {
      font-size: 15px;
    }
  .help ul {
      margin-top: 25px;
    }
  .help  li {
    list-style: none;
    width: 700px;
    margin-top: 8px;
    line-height: 20px;
  }
  .help li img {
    width: 97%;
    height: 97%;
  }
  .help p {
    margin: 0;
  }

  .help a {
    color: #2836F4;
    text-decoration: none;
  }
  .help a:hover {
    text-decoration: none;
  }

  .help .stress {
    margin-left: 2em;
  }

  .help .title{
    font-weight: bold;
  }

  .help .label {
    font-size: 13px;
    color: red;
  }



</style>
