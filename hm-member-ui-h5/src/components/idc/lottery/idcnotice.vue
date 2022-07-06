<template>
  <div id="idc">
    <div v-show="!noticeShow" class="home">
      <div class="sc-bZQynM OQRyf">
        <div class="sc-gzVnrw flXAPw">
          <my-header maintop="true" userxy="true" back="false"></my-header>
          <div class="scroll-wrapper-home" style="height: 100% !important;">
            <div class="win_info">
              <ul class="decimal">
                <li>1.使用本公司網站的客戶，請留意閣下所在的國家或居住地的相關法律規定，如有疑問應就相關問題，尋求當地法律意見。</li>
                <li>2.
                  若發生遭駭客入侵破壞行為或不可抗拒之災害導致網站故障或資料損壞、資料丟失等情況，我們將以本公司之後備資料為最後處理依據；為確保各方利益，請各會員投注後列印資料。本公司不會接受沒有列印資料的投訴。
                </li>
                <li>3.
                  為避免糾紛，各會員在投注之後，務必進入下注狀況檢查及列印資料。若發現任何異常，請立即與代理商聯繫查證，一切投注將以本公司資料庫的資料為准，不得異議。如出现特殊网络情况或线路不稳定导致不能下注或下注失败。本公司概不负责。
                </li>
                <li>4.單一注單最高派彩上限為一百萬。</li>
                <li>5.開獎結果以官方公佈的結果為准。</li>
                <li>6.我們將竭力提供準確而可靠的開獎統計等資料，但並不保證資料絕對無誤，統計資料只供參考，並非是對客戶行為的指引，本公司也不接受關於統計數據產生錯誤而引起的相關投訴。</li>
                <li>7.本公司擁有一切判決及註消任何涉嫌以非正常方式下註之權利，在進行更深入調查期間將停止發放與其有關之任何彩金。客戶有責任確保自己的帳戶及密碼保密，如果客戶懷疑自己的資料被盜用，應立即通知本公司，並須更改其個人詳細資料。所有被盜用帳號之損失將由客戶自行負責。在某種特殊情況下，客人之信用額可能會出現透支。
                </li>
                <li>我瞭解以及同意下註列明的協定和規則。</li>
                <div style="width: 100%;text-align: center;padding-top: 20px;padding-bottom: 20px"><span class="btn btn-success btnred" @click="onOk">同意</span></div>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--用户弹出公告-->
    <div v-show="noticeShow" >
      <div class="home">
        <div class="sc-bZQynM OQRyf">
          <div class="sc-gzVnrw flXAPw">
            <my-header maintop="true" usergg="true"></my-header>
            <div class="scroll-wrapper-home scroll-xy">
              <div style="width: 100%;height: 100%;display: table">
                <ol>
                  <li style="list-style-type: decimal;margin-bottom: 10px;;" v-for="(item,index) in noticeArr">{{item.content}}</li>
                </ol>
                <div style="width: 90%;text-align: center;padding-top: 20px;margin: 0 auto" @click="noticeOk"><span class="btn btn-success">关闭</span></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import {mapGetters, mapActions} from 'vuex'
  import MyHeader from '@/components/idc/layout/header'
  import member from '@/axios/api-mem.js'
  export default {
    components: {
      MyHeader,
    },
    data() {
      return {
        noticeArr:[],
        noticeShow:false
      }
    },
    computed: {

    },
    methods: {
      ...mapActions(['setPromptInformation']),
      onOk(){
        let self = this;
        member.getNotice({}).then(resNotice=>{
          let alertNoticeList = [];
          if (resNotice.data && resNotice.data.notices.length > 0) {
            for (let i = 0; i < resNotice.data.notices.length; i++) {
              if (resNotice.data.notices[i].isAlert) {
                alertNoticeList.push(Object.assign({}, resNotice.data.notices[i]));
              }
            }
            self.noticeArr = alertNoticeList;

          }
        }).finally(()=>{
          if(self.noticeArr.length>0){
            self.noticeShow = true;
          }else{
            this.$router.push('/idc/main/')
          }
        })
      },
      noticeOk(){
        this.$router.push('/idc/main/')
      },
    },
    mounted() {

    }
  }
</script>

