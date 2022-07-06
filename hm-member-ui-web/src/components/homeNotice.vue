<template>
  <div>
    <div v-show="!noticeShow" class="user_win">
      <div class="agree_win">
        <div class="user_logo"></div>
        <div style="margin: 0 auto;width: 771px;">
          <ul>
            <li class="user_wintitle">用户协议</li>
            <li class="user_winmain">
              <div class="win_info">
                <ul>
                  <li> 01. 为避免出现争议，请您务必在下注之后查看「下注状况」。</li>
                  <li> 02. 任何投诉必须在开奖之前，本系统不接受任何开奖之后的投诉。</li>
                  <li> 03. 公布赔率时出现的任何打字错误或非故意人为失误，所有（相关）注单一律不算。</li>
                  <li> 04. 公布之所有赔率为浮动赔率，下注时请确认当前赔率及金额，下注确认后一律不能修改。</li>
                  <li> 05. 开奖后接受的投注，一律视为投机漏洞，[本局注单一律不返还本金及盈利]，敬请会员遵守游戏规则。</li>
                  <li> 06. 若本后台发现客户以不正当的手法投注或投注注单不正常，后台将有权「取消」相应之注单，客户不得有任何异议。</li>
                  <li> 07. 如因软件或线路问题导致交易内容或其他与账号设定不符合的情形，请在开奖前立即与本后台联络反映问题，否则本后台将以资料库中的数据为准。</li>
                  <li> 08. 倘若发生遭黑客入侵破坏行为或不可抗拒之灾害致网站故障或资料损坏、数据丢失等情况，后台将以资料库数据为依据。</li>
                  <li> 09. 各级管理人员及客户必须对本系统各项功能进行了解及熟悉，任何违反正常使用的操作，后台概不负责。</li>
                  <li> 10. 请认真了解各款彩票游戏规则。</li>
                  <li> 11. 如果会员信用额度超额或者为负数引起的争议，一律以公司处理为准。</li>
                  <li> 12. 客户有责任确保自己的账户及密码保密，如果因客户的账户、密码简单，或因泄露导致被盗用，造成的损失由客户本人承担；同时应立即通知本公司，并更改其个人详细资料。</li>
                  <li> 13. 若官方福彩中心开奖错误导致本系统采集数据同时出错情况下当期错误的所有注单以福彩中心官方网站更改后的数据为标准重新结算！在此特别声明，客户不得有任何异议。</li>
                  <li> 14.任何因网絡卡頓延迟造成無法下注,本公司概不負責</li>
                  <li>以上协议解释权归本系统所有。</li>
<!--                  <li>01. 使用本公司網站的客戶，請留意閣下所在的國家或居住地的相關法律規定，如有疑問應就相關問題，尋求當地法律意見。</li>-->
<!--                  <li>02.-->
<!--                    若發生遭駭客入侵破壞行為或不可抗拒之災害導致網站故障或資料損壞、資料丟失等情況，我們將以本公司之後備資料為最後處理依據；為確保各方利益，請各會員投注後列印資料。本公司不會接受沒有列印資料的投訴。-->
<!--                  </li>-->
<!--                  <li>03.-->
<!--                    為避免糾紛，各會員在投注之後，務必進入下注狀況檢查及列印資料。若發現任何異常，請立即與代理商聯繫查證，一切投注將以本公司資料庫的資料為准，不得異議。如出现特殊网络情况或线路不稳定导致不能下注或下注失败。本公司概不负责。-->
<!--                  </li>-->
<!--                  <li>04. 單一注單最高派彩上限為一百萬。</li>-->
<!--                  <li>05. 開獎結果以官方公佈的結果為准。</li>-->
<!--                  <li>06. 我們將竭力提供準確而可靠的開獎統計等資料，但並不保證資料絕對無誤，統計資料只供參考，並非是對客戶行為的指引，本公司也不接受關於統計數據產生錯誤而引起的相關投訴。</li>-->
<!--                  <li>07.-->
<!--                    本公司擁有一切判決及註消任何涉嫌以非正常方式下註之權利，在進行更深入調查期間將停止發放與其有關之任何彩金。客戶有責任確保自己的帳戶及密碼保密，如果客戶懷疑自己的資料被盜用，應立即通知本公司，並須更改其個人詳細資料。所有被盜用帳號之損失將由客戶自行負責。在某種特殊情況下，客人之信用額可能會出現透支。-->
<!--                  </li>-->
                  <!--                <li>08. 倘若发生遭黑客入侵破坏行为或不可抗拒之灾害致网站故障或资料损坏、数据丢失等情况，后台将以资料库数据为依据。</li>-->
                  <!--                <li>09. 各级管理人员及客户必须对本系统各项功能进行了解及熟悉，任何违反正常使用的操作，后台概不负责。</li>-->
                  <!--                <li>10. 请认真了解各款彩票游戏规则。</li>-->
                  <!--                <li class="ftcolor_red">● 11. 如果会员信用额度超额或者为负数引起的争议，一律以公司处理为准。</li>-->
                  <!--                <li>● 12. 客户有责任确保自己的账户及密码保密，如果因客户的账户、密码简单，或因泄露导致被盗用，造成的损失由客户本人承担；同时应立即通知本公司，并更改其个人详细资料。</li>-->
                  <!--                <li>● 13. 若官方福彩中心开奖错误导致本系统采集数据同时出错情况下当期错误的所有注单以福彩中心官方网站更改后的数据为标准重新结算！在此特别声明，客户不得有任何异议。</li>-->
                  <li>我瞭解以及同意下註列明的協定和規則。</li>
                  <li class="user_winbu">
                    <div><span><a class="no" @click="onCl">不同意</a><a class="yes" @click="onOk">同意</a></span></div>
                  </li>
                </ul>
              </div>
            </li>
            <li class="user_winfooter"></li>
          </ul>
        </div>
      </div>
    </div>

    <!--用户弹出公告-->
    <div v-show="noticeShow" style="display:none ">
      <div class="mod-dialog-bg" style="display: block;"></div>
      <div class="mod-dialog" style="left: calc(50% - 350px); top: 25%; display: block;width: 700px">
        <div class="dialog-nav">
          <span class="dialog-title">用户公告</span>
            <a class="dialog-close" @click="noticeClose"></a>
        </div>
        <div class="dialog-main">
          <div class="dialog-content">
            <ol style="margin: 0;padding: 10px;">
              <li v-for="(item,index) in noticeArr">{{item.content}}</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapGetters, mapActions} from 'vuex'

  export default {
    components: {},
    data() {
      return {
        noticeArr:[],
        noticeShow:false,
        lotteryPageFlag:false
      }
    },
    computed: {},
    methods: {
      ...mapActions(['setLogot', 'setPromptInformation']),
      onOk() {
        let self = this;

        self.$api.Member.getNotice({}).then(resNotice => {
          let alertNoticeList = [];
          if (resNotice.data && resNotice.data.notices.length > 0) {
            for (let i = 0; i < resNotice.data.notices.length; i++) {
              if (resNotice.data.notices[i].isAlert) {
                alertNoticeList.push(Object.assign({}, resNotice.data.notices[i]));
              }
            }

            /*let alertContent = '';
            for (let i = 0; i < alertNoticeList.length; i++) {
              alertContent += '<div class="popnotice">' + (i + 1) + '.' + alertNoticeList[i].content + '</div>' + '</br>';
            }
            self.setPromptInformation(alertContent).then().catch(error => {

            });*/
          }
        self.noticeArr = alertNoticeList;
        this.noticeOk();
        })


      },
      noticeOk(){
        console.log(this.noticeArr);
        if(this.noticeArr.length>0){
          this.noticeShow = true;
        }else{
          this.noticeClose();
        }
      },
      noticeClose(){
        this.$router.push('/home/');
      },
      onCl() {
        let self = this;
        this.$api.Member.logout().then(val => {
          if (val) {
            if (val.code === 10000) {
              //self.setPromptInformation('注销成功！');
              //self.socket.send('{"code":"odds_unlottery"}');
              window.location.href = '/';
              self.setLogout();

            }
          }
        })
      }
    }
  }
</script>
<style scoped>
  a {
    cursor: pointer;
  }
</style>
