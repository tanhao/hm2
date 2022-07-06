import * as types from '@/vuex/types'
import { MessageBox } from 'mint-ui' // 弹出式提示框(错误提示)
import Mint from 'mint-ui';
export default {
    state: {
      games:sessionStorage.getItem('games')?JSON.parse(sessionStorage.getItem('games')):{},
      gameMenu:[],
      markets:null,
      market:'A',
      gameId:null,
      pagePosition:[],
      gameInfoRefreshNum:0,
      whetherSwitch:false,
      modeId:10,
      game:{},
      gameInfo:{
        closeTime: 0,
        gameNo: '',
        lotteryId: 0,
        openTime: 0,
        prevGameNo: '',
        result: '',
        status: '',
        wl: 0,
        prevResult:[],
      },
      categoryList:[],
      marketMenu:null,
      currentMarket:null,
      odds:null,
      betState:false,
      playType:null,
      historyReturn:{},
      promptInformation:'',
      historyHomeReturn:{},
      profitlosReturn:{},
      longDragonList:[],
      pk10Odds:{
        lm:{'no1':[],'no2':[],'no3':[],'no4':[],'no5':[],'no6':[],'no7':[],'no8':[],'no9':[],'no10':[]},//两面盘
        firstAddSecond:[],//冠亚和
        oneToFive:[],//1-5,
        sixToTen:[],//6-10
        idcPk10Odds:[]
      },
      sscOdds:{
        lm:[],
        qiu1:[],
        qiu2:[],
        qiu3:[],
        qiu4:[],
        qiu5:[],
        onezzh:[],
        twozzh:{},
        threezzh:{},
        twozdw:[],
        threezdw:[],
        twozhs:{},
        threezhs:{},
        zxs:{},
        zxl:{},
        fszh:[],
        kd:{},
        ndsm:{},
      },
      kl10Odds:{
        integration:{},
        oneToEight:{},
        continuousCode:{},
        positiveCode:{}
      },
      pcddOdds:{
        baozi:[],
        hezhi:[],
        lm:[],
        sebo:[],
        tmbs:[],
        duizi:[],
        shunzi:[]
      },
      socket:null,
      userOddsCljps:{},
      userOddsCloses:{},
      userOddsJumps:{},
      userOddsNows:{},
      userOddss:{},
      resultStatus:false,
      serverTimer:null,
      socketResetStatus:false,
      gameStatus:false,
    }
    ,
    getters: {
      games:state=>state.games,
      game:state=>state.game,
      gameInfo:state=>state.gameInfo,
      gameId:state=>state.gameId,
      gameInfoRefreshNum:state=>state.gameInfoRefreshNum,
      longDragonList:state=>state.longDragonList,
      categoryList:state=>state.categoryList,
      betState:state=>state.betState,
      gameMenu:state=>state.gameMenu,
      modeId:state=>state.modeId,
      markets:state=>state.markets,
      market:state=>state.market,
      marketMenu:state=>state.marketMenu,
      currentMarket:state=>state.currentMarket,
      odds:state=>state.odds,
      sscOdds:state=>state.sscOdds,
      pk10Odds:state=>state.pk10Odds,
      pagePosition:state=>state.pagePosition,
      playType:state=>state.playType,
      fast3Odds:state=>state.fast3Odds,
      pcddOdds:state=>state.pcddOdds,
      historyReturn:state=>state.historyReturn,
      historyHomeReturn:state=>state.historyHomeReturn,
      profitlosReturn:state=>state.profitlosReturn,
      kl10Odds:state=>state.kl10Odds,
      gxkl10Odds:state=>state.gxkl10Odds,
      bjfast8Odds:state=>state.bjfast8Odds,
      gd11x5Odds:state=>state.gd11x5Odds,
      promptInformation:state=>state.promptInformation,
      whetherSwitch:state=>state.whetherSwitch,
      socket:state=>state.socket,
      userOddsCljps:state=>state.userOddsCljps,
      userOddsCloses:state=>state.userOddsCloses,
      userOddsJumps:state=>state.userOddsJumps,
      userOddsNows:state=>state.userOddsNows,
      userOddss:state=>state.userOddss,
      resultStatus:state=>state.resultStatus,
      serverTime:state=>state.serverTime,
      socketResetStatus:state=>state.socketResetStatus,
      gameStatus:state=>state.gameStatus
    },
    mutations: {
      [types.SET_GAMES](state,payload){
        state.games = payload;
        state.gameMenu=[];
        payload.forEach((item)=>{
          if(item.lotteryKey=='bjpk10' || item.lotteryKey=='xyft' || item.lotteryKey=='cqssc' || item.lotteryKey=='lucky5'
            || item.lotteryKey=='lucky10' || item.lotteryKey=='speed10' || item.lotteryKey=='sbspeed10' || item.lotteryKey=='jsssc'
            || item.lotteryKey=='bjk3' || item.lotteryKey=='jsk3' || item.lotteryKey=='jlk3' || item.lotteryKey=='gxk3'
            || item.lotteryKey=='speed3' || item.lotteryKey=='pcdd' || item.lotteryKey=='hmpk10' || item.lotteryKey=='gdkl10'
            || item.lotteryKey=='gxkl10' || item.lotteryKey=='bjfast8' || item.lotteryKey=='gd11x5' || item.lotteryKey=='epssm'
            || item.lotteryKey=='fhsc' || item.lotteryKey=='tycsm' || item.lotteryKey=='xjssc' || item.lotteryKey=='cjk5'
            || item.lotteryKey=='fhssc' || item.lotteryKey=='tycssc' || item.lotteryKey=='hmcqssc' || item.lotteryKey=='txffc'
            || item.lotteryKey=='txsfc' || item.lotteryKey=='speed8' || item.lotteryKey=='lucky20' || item.lotteryKey=='xync'
            || item.lotteryKey=='xyft3' || item.lotteryKey=='twssc'|| item.lotteryKey=='sgft' || item.lotteryKey=='xyft1396'
            || item.lotteryKey=='lucky8' || item.lotteryKey=='jnd28' || item.lotteryKey=='pcdd' || item.lotteryKey=='jsdd' ){
            state.gameMenu.push({'index':item.lotteryId+'','title':item.lotteryKey});
          }
        });
      },
      [types.SET_MODE_ID](state,payload){
        state.modeId = payload;
      },
      [types.SELECT_GAME](state,payload){
        state.gameId = payload;
        let nowGame = '';
        if(Object.keys(state.games).length>0) {
          nowGame = state.games.find((val)=>{
            return val.lotteryId+''==state.gameId;
          });
          if(nowGame){
            Object.assign(state.game,nowGame);
          }else{
            state.game = {lotteryId:0,lotteryKey:''};
          }
        }
        if(payload && state.socket.ws.readyState==1){
          let msg = {code:'odds_lottery', content:{lotteryId:state.gameId,lotteryKey:state.game.lotteryKey}};
          state.socket.send(JSON.stringify(msg))
        }
      },
      [types.SET_CURRENT_GAME](state,payload){
        /**
         * 新的陪率数据
         */
        let arr = [];
        state.marketMenu=[];
        state.odds = payload.odds;
        state.categoryList = [];
        //组装需要显示的陪率数据
        if (state.game.lotteryKey === 'bjpk10' || state.game.lotteryKey === 'xyft' ||
          state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10' ||
          state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10' ||
          state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc' || state.game.lotteryKey==='xyft3'
          || state.game.lotteryKey==='tycsm'|| state.game.lotteryKey==='sgft' || state.game.lotteryKey==='xyft1396') {
          state.pk10Odds.lm = {'gyh':[],'no1':[],'no2':[],'no3':[],'no4':[],'no5':[],'no6':[],'no7':[],'no8':[],'no9':[],'no10':[]};
          state.pk10Odds.firstAddSecond = [];
          state.pk10Odds.oneToFive = [[],[],[],[],[]];
          state.pk10Odds.sixToTen = [[],[],[],[],[]];
        } else if (state.game.lotteryKey === 'cqssc' || state.game.lotteryKey === 'lucky5' || state.game.lotteryKey==='jsssc'
          || state.game.lotteryKey==='xjssc' || state.game.lotteryKey==='cjk5' || state.game.lotteryKey==='fhssc'
          || state.game.lotteryKey==='tycssc' || state.game.lotteryKey==='hmcqssc' || state.game.lotteryKey==='txffc'
          || state.game.lotteryKey==='txsfc' || state.game.lotteryKey==='twssc') {
          state.sscOdds.lm = {'t_lhh':[],'qiu1':[],'qiu2':[],'qiu3':[],'qiu4':[],'qiu5':[],'top3':[],'mid3':[],'end3':[]};
          state.sscOdds.qiu1= [];
          state.sscOdds.qiu2= [];
          state.sscOdds.qiu3= [];
          state.sscOdds.qiu4= [];
          state.sscOdds.qiu5= [];
          state.sscOdds.onezzh= [];
          state.sscOdds.twozzh= {'2ztop3':[],'2zmid3':[],'2zend3':[]};
          state.sscOdds.threezzh= {
            '3ztop3':{
              'lingtou':[],
              'yitou':[],
              'ertou':[],
              'santou':[],
              'sitou':[],
              'wutou':[],
              'liutou':[],
              'qitou':[],
              'batou':[],
              'jiutou':[],

            },
            '3zmid3':{
              'lingtou':[],
              'yitou':[],
              'ertou':[],
              'santou':[],
              'sitou':[],
              'wutou':[],
              'liutou':[],
              'qitou':[],
              'batou':[],
              'jiutou':[],
            },
            '3zend3':{
              'lingtou':[],
              'yitou':[],
              'ertou':[],
              'santou':[],
              'sitou':[],
              'wutou':[],
              'liutou':[],
              'qitou':[],
              'batou':[],
              'jiutou':[],
            },

          };
          state.sscOdds.twozdw= [];
          state.sscOdds.threezdw= [];
          state.sscOdds.twozhs= {'2hsbg':[],'2hsbs':[],'2hsqb':[],'2hsqg':[],'2hsqs':[],'2hssg':[],'2hswb':[],
            '2hswg':[],'2hswq':[],'2hsws':[],'2hwsbg':[],'2hwsbs':[],'2hwsqb':[],'2hwsqg':[],'2hwsqs':[],
            '2hwssg':[],'2hwswb':[],'2hwswg':[],'2hwswq':[],'2hwsws':[]};
          state.sscOdds.threezhs= {'3hstop3':[],'3hsmid3':[],'3hsend3':[],'3hwstop3':[],'3hwsmid3':[],'3hwsend3':[]};
          state.sscOdds.zxs= {'zx3top3':[],'zx3mid3':[],'zx3end3':[]};
          state.sscOdds.zxl= {'zx6top3':[],'zx6mid3':[],'zx6end3':[]};
          state.sscOdds.fszh= [];
          state.sscOdds.kd= {'kdtop3':[],'kdmid3':[],'kdend3':[]};
          state.sscOdds.ndsm={'nn':[],'sh':[]};
        } else if (state.game.lotteryKey === 'jsk3' || state.game.lotteryKey === 'bjk3' || state.game.lotteryKey === 'jlk3' || state.game.lotteryKey === 'gxk3' || state.game.lotteryKey==='speed3') {
          state.fast3Odds.cangpai={};
          state.fast3Odds.dandian={};
          state.fast3Odds.dianshu={};
          state.fast3Odds.duanpai={};
          state.fast3Odds.heya={};
          state.fast3Odds.ou={};
          state.fast3Odds.quanwei={};
          state.fast3Odds.sanjun={};
          state.fast3Odds.weisai={};
          state.fast3Odds.zufang={};
        } else if (state.game.lotteryKey === 'pcdd' || state.game.lotteryKey === 'jnd28' || state.game.lotteryKey === 'jsdd') {
          state.pcddOdds.lm=[];
          state.pcddOdds.tmbs=[];
          state.pcddOdds.baozi=[];
          state.pcddOdds.hezhi=[];
          state.pcddOdds.sebo=[];
          state.pcddOdds.shunzi=[];
          state.pcddOdds.duizi=[];
        } else if (state.game.lotteryKey === 'gdkl10' || state.game.lotteryKey==='speed8' || state.game.lotteryKey==='xync'
          || state.game.lotteryKey==='lucky8') {
          state.kl10Odds.oneToEight={'qiu1':[],'qiu2':[],'qiu3':[],'qiu4':[],'qiu5':[],'qiu6':[],'qiu7':[],'qiu8':[]};
          state.kl10Odds.integration={'sum':[]};
          state.kl10Odds.positiveCode={'zm':[]};
          state.kl10Odds.continuousCode={'lianma':[]};
        }else if(state.game.lotteryKey==='gxkl10'){
          state.gxkl10Odds.lm={};
          state.gxkl10Odds.qiu={};
        }else if(state.game.lotteryKey==='gd11x5'){
          state.gd11x5Odds.lm = {};
          state.gd11x5Odds.dh = {};
          state.gd11x5Odds.lianma = {};
          state.gd11x5Odds.zhixuan = {};
        }else if(state.game.lotteryKey==='bjfast8' || state.game.lotteryKey==='lucky20'){
          state.bjfast8Odds.zh={};
          state.bjfast8Odds.zm = {};
        }
        //let arr = [state.fast3Odds.sjdx,state.fast3Odds.wsqs,state.fast3Odds.eth,state.fast3Odds.sbt,state.fast3Odds.pointT,state.fast3Odds.longCard,state.fast3Odds.shortCard];
        if(state.game.lotteryKey==='bjpk10' || state.game.lotteryKey==='xyft' ||
          state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10' ||
          state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10' ||
          state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc'
          || state.game.lotteryKey==='tycsm' || state.game.lotteryKey==='xyft3'|| state.game.lotteryKey==='sgft'
          || state.game.lotteryKey==='xyft1396'){
          state.categoryList=['yds','gyh','lm'];
        }else if(state.game.lotteryKey==='cqssc' || state.game.lotteryKey=='lucky5' || state.game.lotteryKey=='jsssc'
          || state.game.lotteryKey==='xjssc' || state.game.lotteryKey==='cjk5' || state.game.lotteryKey==='fhssc'
          || state.game.lotteryKey==='tycssc' || state.game.lotteryKey==='hmcqssc' || state.game.lotteryKey==='txffc'
          || state.game.lotteryKey==='txsfc' || state.game.lotteryKey==='twssc'){
          state.categoryList = ['ball','totalPenl','onezh','twozh','threezh','twodw','threedw','twohs','threehs','zxthree','zxsix','fszh','kd','ndsm'];
        }else if(state.game.lotteryKey === 'jsk3' || state.game.lotteryKey === 'bjk3' || state.game.lotteryKey === 'jlk3' || state.game.lotteryKey === 'gxk3' || state.game.lotteryKey==='speed3'){
          state.categoryList = ['sjdx','wsqw','ds','cp','dp','smz','yxxsb'];
        }else if(state.game.lotteryKey ==='pcdd' || state.game.lotteryKey ==='jnd28' || state.game.lotteryKey ==='jsdd'){
          state.categoryList = ['qbwf'];
        }else if(state.game.lotteryKey==='gdkl10' || state.game.lotteryKey==='speed8' || state.game.lotteryKey==='xync'
          || state.game.lotteryKey==='lucky8'){
          state.categoryList = ['oneToEight','integration','qiu1','qiu2','qiu3','qiu4','qiu5','qiu6','qiu7','qiu8','positiveCode','continuousCode'];
        }else if(state.game.lotteryKey==='gxkl10'){
          state.categoryList = ['lm','dhydw','qiu1','qiu2','qiu3','qiu4','qiu5'];

        }else if(state.game.lotteryKey==='bjfast8' || state.game.lotteryKey==='lucky20'){
          state.categoryList = ['zhengHe','zm'];
        }else if(state.game.lotteryKey==='gd11x5'){
          state.categoryList = ['lm','dh','lianma','zhixuan'];
        }
        if(state.whetherSwitch){
          state.categorySelect = state.categoryList[0];
          state.playType = state.categoryList[0];
        }
        if(!state.playType){
          state.playType = state.categoryList[0];
        }
        state.whetherSwitch = false;

        for(let marketP in payload.markets){
          if(payload.markets[marketP]){
            state.market = marketP;
          }
        }

        state.userOddsCljps = payload.userOddsCljps?payload.userOddsCljps:state.userOddsCljps;
        state.userOddsCloses = payload.userOddsCloses?payload.userOddsCloses:state.userOddsCloses;
        state.userOddsJumps = payload.userOddsJumps?payload.userOddsJumps:state.userOddsJumps;
        state.userOddsNows = payload.userOddsNows?payload.userOddsNows:state.userOddsJumps;
        state.userOddss = payload.userOddss?payload.userOddss:state.userOddss;
        //let selectOddsArr = rootGetters['bet/selectList'];
        payload.oddss.forEach(obj=>{
          obj.status = 0;
          for(let cateGroyUid in payload.categorys){
            if(obj.categoryId == cateGroyUid){

              Object.assign(obj,payload.categorys[cateGroyUid]);
            }
          }
          for(let playUid in payload.plays){
            if(obj.playId == playUid){
              Object.assign(obj,payload.plays[playUid]);


            }
          }
          /*for(let userOddsUid in payload.userOddss){
            if(obj.categoryId == userOddsUid){
              let userOdds = 0;
              payload.userOddss[userOddsUid].forEach(val=>{userOdds+=val});
              obj.odds = userOdds;
            }
          }*/

          if (state.game.lotteryKey === 'bjpk10' || state.game.lotteryKey === 'xyft' ||
            state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10'||
            state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10'||
            state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc'
            || state.game.lotteryKey==='tycsm' || state.game.lotteryKey==='xyft3'|| state.game.lotteryKey==='sgft'
            || state.game.lotteryKey==='xyft1396') {
            if(obj.playKey === "no1" || obj.playKey === "no2" || obj.playKey === "no3" || obj.playKey === "no4" || obj.playKey === "no5"){
              /*selectOddsArr.find(selOdds=>{
                if(selOdds.oddsId == obj.oddsId){
                  obj.choose = selOdds.choose;
                  obj.betAmt = selOdds.betAmt;
                }
              })*/
              state.pk10Odds.oneToFive[obj.playKey.split('no')[1]-1].push(obj);
            }else if(obj.playKey === "no6" || obj.playKey === "no7" || obj.playKey === "no8" || obj.playKey === "no9" || obj.playKey === "no10"){
              state.pk10Odds.sixToTen[obj.playKey.split('no')[1]-6].push(obj);
            }else if(obj.playKey.indexOf('gyh')!=-1){
              state.pk10Odds.firstAddSecond.push(obj);
            }
          }else if(state.game.lotteryKey==='cqssc' || state.game.lotteryKey=='lucky5' || state.game.lotteryKey=='jsssc'
            || state.game.lotteryKey==='xjssc' || state.game.lotteryKey==='cjk5' || state.game.lotteryKey==='fhssc'
            || state.game.lotteryKey==='tycssc' || state.game.lotteryKey==='hmcqssc' || state.game.lotteryKey==='txffc'
            || state.game.lotteryKey==='txsfc' || state.game.lotteryKey==='twssc'){
            if(obj.playKey==='t_lhh' || obj.playKey==='top3' || obj.playKey === 'mid3' || obj.playKey==='end3'){
              state.sscOdds.lm[obj.playKey].push(obj);
            }else if(obj.playKey==='qiu1' || obj.playKey==='qiu2' || obj.playKey==='qiu3' || obj.playKey==='qiu4' || obj.playKey==='qiu5'){
              if(obj.categoryKey=='lm'){
                state.sscOdds.lm[obj.playKey].push(obj);
              }
              state.sscOdds[obj.playKey].push(obj);
            }else if(obj.playKey.indexOf('1z')!=-1){
              state.sscOdds.onezzh.push(obj);
            }else if(obj.playKey==='2ztop3' || obj.playKey ==='2zmid3' || obj.playKey === '2zend3'){
              state.sscOdds.twozzh[obj.playKey].push(obj);
            }else if(obj.playKey==='3ztop3' || obj.playKey ==='3zmid3' || obj.playKey === '3zend3'){

              if(obj.oddsKey.substring(0,1)=='0'){
                state.sscOdds.threezzh[obj.playKey]['lingtou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='1'){
                state.sscOdds.threezzh[obj.playKey]['yitou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='2'){
                state.sscOdds.threezzh[obj.playKey]['ertou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='3'){
                state.sscOdds.threezzh[obj.playKey]['santou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='4'){
                state.sscOdds.threezzh[obj.playKey]['sitou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='5'){
                state.sscOdds.threezzh[obj.playKey]['wutou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='6'){
                state.sscOdds.threezzh[obj.playKey]['liutou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='7'){
                state.sscOdds.threezzh[obj.playKey]['qitou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='8'){
                state.sscOdds.threezzh[obj.playKey]['batou'].push(obj);
              }else if(obj.oddsKey.substring(0,1)=='9'){
                state.sscOdds.threezzh[obj.playKey]['jiutou'].push(obj);
              }

            }else if(obj.playKey==='2dw'){
              state.sscOdds.twozdw.push(obj);
            }else if(obj.playKey==='3dw'){
              state.sscOdds.threezdw.push(obj);
            }else if(0===obj.playKey.indexOf('2h')){
              state.sscOdds.twozhs[obj.playKey].push(obj);
            }else if(0===obj.playKey.indexOf('3h')){
              state.sscOdds.threezhs[obj.playKey].push(obj);
            }else if(0===obj.playKey.indexOf('zx3')){
              state.sscOdds.zxs[obj.playKey].push(obj);
            }else if(0===obj.playKey.indexOf('zx6')){
              state.sscOdds.zxl[obj.playKey].push(obj);
            }else if(obj.playKey==='fs'){
              state.sscOdds.fszh.push(obj);
            }else if(0===obj.playKey.indexOf('kd')){
              state.sscOdds.kd[obj.playKey].push(obj);
            }else if(obj.playKey==='nn' || obj.playKey ==='sh'){
              state.sscOdds.ndsm[obj.playKey].push(obj);
            }
          }else if(state.game.lotteryKey==='gdkl10' || state.game.lotteryKey==='speed8' || state.game.lotteryKey==='xync'
            || state.game.lotteryKey==='lucky8'){
            if(obj.playKey==='lianma'){
              state.kl10Odds.continuousCode[obj.playKey].push(obj);
            }else if(obj.playKey.indexOf('qiu')!=-1){
              state.kl10Odds.oneToEight[obj.playKey].push(obj);
            }else if(obj.playKey==='sum'){
              state.kl10Odds.integration[obj.playKey].push(obj);
            }else if(obj.playKey==='zm'){
              state.kl10Odds.positiveCode[obj.playKey].push(obj);
            }
          }else if(state.game.lotteryKey==='pcdd' || state.game.lotteryKey=='jnd28' || state.game.lotteryKey=='jsdd'){
            state.pcddOdds[obj.playKey].push(obj);
          }
        })
        if(state.game.lotteryKey==='bjpk10' || state.game.lotteryKey==='xyft' ||
          state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10' ||
          state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10'||
          state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc'
          || state.game.lotteryKey==='tycsm' || state.game.lotteryKey==='xyft3'|| state.game.lotteryKey==='sgft'
          || state.game.lotteryKey==='xyft1396') {
          let outLayerLength = state.pk10Odds.oneToFive.length;
          let innerLayerLength = 0;
          for (let j = 0; j < outLayerLength; j++) {
            if (innerLayerLength === 0) {
              innerLayerLength = state.pk10Odds.oneToFive[j].length;
            }
            for (let i = 0; i < innerLayerLength; i++) {

              let obj = state.pk10Odds.oneToFive[j][i];
              if (typeof obj != 'undefined' && obj.categoryKey === 'lm') {
                state.pk10Odds.lm[obj.playKey].push(obj);
              }
            }
          }
          outLayerLength = state.pk10Odds.sixToTen.length;
          innerLayerLength = 0;
          for(let i = 0;i<state.pk10Odds.firstAddSecond.length;i++){

            if(!Math.sign(state.pk10Odds.firstAddSecond[i].oddsKey)){
              state.pk10Odds.lm[state.pk10Odds.firstAddSecond[i].playKey].push(state.pk10Odds.firstAddSecond[i]);
            }
          }

          for (let j = 0; j < outLayerLength; j++) {
            if (innerLayerLength === 0) {
              innerLayerLength = state.pk10Odds.sixToTen[j].length;
            }
            for (let i = 0; i < innerLayerLength; i++) {
              let obj = state.pk10Odds.sixToTen[j][i];
              if (typeof obj != 'undefined' && obj.categoryKey === 'lm') {
                state.pk10Odds.lm[obj.playKey].push(obj);
              }
            }
          }
          for (let i = 0; i < state.pk10Odds.oneToFive.length; ++i) {
            for (let j = 0; j < state.pk10Odds.oneToFive.length - i - 1; ++j) {

              let obj = state.pk10Odds.oneToFive[j];
              let objTwo = state.pk10Odds.oneToFive[j + 1];
              if(obj.length>0 && objTwo.length>0){
                let arr = obj[0].playKey.split('no');
                let arrTwo = objTwo[0].playKey.split('no');
                if (parseInt(arr[1]) > parseInt(arrTwo[1])) {
                  let param = state.pk10Odds.oneToFive[j];
                  state.pk10Odds.oneToFive[j] = state.pk10Odds.oneToFive[j + 1];
                  state.pk10Odds.oneToFive[j + 1] = param;
                }
              }

            }
          }
          for (let i = 0; i < state.pk10Odds.sixToTen.length; ++i) {
            for (let j = 0; j < state.pk10Odds.sixToTen.length - i - 1; ++j) {
              let obj = state.pk10Odds.sixToTen[j];
              let objTwo = state.pk10Odds.sixToTen[j + 1];
              if(obj.length>0 && objTwo.length>0){
                let arr = obj[0].playKey.split('no');
                let arrTwo = objTwo[0].playKey.split('no');
                if (parseInt(arr[1]) > parseInt(arrTwo[1])) {
                  let param = state.pk10Odds.sixToTen[j];
                  state.pk10Odds.sixToTen[j] = state.pk10Odds.sixToTen[j + 1];
                  state.pk10Odds.sixToTen[j + 1] = param;
                }
              }

            }
          }
          let objs= [];
          for(let i = 0;i<state.pk10Odds.firstAddSecond.length;i++){
            if(state.pk10Odds.firstAddSecond[i].oddsKey=='odd' || state.pk10Odds.firstAddSecond[i].oddsKey=='even' ||
              state.pk10Odds.firstAddSecond[i].oddsKey=='under' || state.pk10Odds.firstAddSecond[i].oddsKey=='over'){
              objs.push(Object.assign({},state.pk10Odds.firstAddSecond[i]));
              state.pk10Odds.firstAddSecond.splice(i,1);
              i=-1;
            }
          }
          for(let i = 0;i<objs.length;i++){
            state.pk10Odds.firstAddSecond.push(objs[i]);
          }
        }else if(state.game.lotteryKey==='cqssc' || state.game.lotteryKey==='xjssc' || state.game.lotteryKey==='hmcqssc' ){
          for(let i = 1;i<=5;i++){
            let arr = state.sscOdds['qiu'+i].splice(0,4);
            state.sscOdds['qiu'+i] = [...state.sscOdds['qiu'+i],...arr];
            //state.sscOdds.lm['qiu'+i] = [...state.sscOdds.lm['qiu'+i],...arr];
          }
        }
      },
      [types.SET_LOTTERY_NOW](state,payload){
        clearInterval(state.serverTimer);
        state.serverTimer = null;
        if(payload.serverTime){
          if(payload.data.prevGameNo && payload.data.gameNo){
            state.gameInfo.gameNo = payload.data.gameNo;
          }
          state.gameInfo.serverTime = payload.serverTime;
        }else{
          if(payload.prevGameNo && payload.gameNo){
            state.gameInfo.gameNo = payload.gameNo;
          }
        }

        if(!state.serverTimer){
          state.serverTimer = setInterval(()=>{
            state.gameInfo.serverTime = state.gameInfo.serverTime + 1000;
          },1000);
        }

        let closeTime = 0;
        let arr ={};
        if(payload.serverTime && payload.data.openTime){
          closeTime = payload.data.actionTime - (state.gameInfo.serverTime/1000).toFixed(0) ;

          state.gameInfo.closeTime = state.gameInfo.serverTime/1000 + closeTime;

          state.gameInfo.closeTime -= payload.data.precedeClose;


          state.gameInfo.openTime = state.gameInfo.serverTime/1000 + closeTime;
          state.gameInfo.openTime += payload.data.delayOpen;
          state.gameInfo.gameNo = payload.data.gameNo;
          if(state.gameInfo.lotteryId == payload.data.lotteryId && payload.data.prevGameNo >= state.gameInfo.prevGameNo){
            state.gameInfo.prevGameNo = payload.data.prevGameNo;
            state.gameInfo.prevResult = [];
          }else if(state.gameInfo.lotteryId != payload.data.lotteryId){
            state.gameInfo.prevGameNo = payload.data.prevGameNo;
            state.gameInfo.prevResult = [];
          }
          state.gameInfo.precedeClose = payload.data.precedeClose;
          state.gameInfo.isOpenTime = payload.data.openTime?payload.data.openTime:state.gameInfo.isOpenTime;
          state.gameInfo.wl = payload.data.wl?payload.data.wl:state.gameInfo.wl;
          state.gameInfo.status = payload.data.isOpenSelf?payload.data.isOpenSelf:state.gameInfo.status;
          if(payload.data.prevLmcl){
            arr = JSON.parse(payload.data.prevLmcl);
          }
          state.gameInfo.lotteryKey = payload.data.lotteryKey;
          state.gameInfo.lotteryId = payload.data.lotteryId;
          if(payload.data.betAmtSum!=null && typeof payload.data.betAmtSum != "undefined"){
            state.gameInfo.betAmtSum = payload.data.betAmtSum;
          }
          payload.prevResult = payload.data.prevResult;
        }else{
          arr = JSON.parse(payload.stats).lmcl;
          state.gameInfo.lotteryKey = payload.lotteryKey;
          state.gameInfo.prevGameNo = payload.gameNo;
          state.gameInfo.prevResult = [];
          payload.prevResult = payload.result;
        }
        state.longDragonList = [];




        if(state.gameInfo.closeTime-(state.gameInfo.serverTime/1000) > 0 && state.gameInfo.status){
          state.betState = true;
        }else{
          state.betState = false;
        }

        if(!state.gameInfo.status){
          state.betState = false;
          Mint.MessageBox.close();
          Mint.MessageBox({$type:'alert',message:'游戏已封盘，下次开盘时间为:'+new Date(payload.data.openTime*1000).format('yyyy-MM-dd hh:mm:ss'),title:'提示',closeOnClickModal:false});

        }
        if(payload.serverTime && (((payload.data.lotteryId== 101 || payload.data.lotteryId== 201 || payload.data.lotteryId== 301 ||
          payload.data.lotteryId== 101 ||payload.data.lotteryId== 303 || payload.data.lotteryId == 204) && payload.data.actionTime - state.gameInfo.serverTime/1000 - state.gameInfo.precedeClose>1200) || ((payload.data.lotteryId == 102 ||
          payload.data.lotteryId == 104 || payload.data.lotteryId == 203 || payload.data.lotteryId == 304 || payload.data.lotteryId == 106  || payload.data.lotteryId == 205|| payload.data.lotteryId == 402) && payload.data.actionTime - state.gameInfo.serverTime/1000 - state.gameInfo.precedeClose>300)
        ||((payload.data.lotteryId == 103 ||
            payload.data.lotteryId == 105 || payload.data.lotteryId == 202 || payload.data.lotteryId == 302|| payload.data.lotteryId == 403) && payload.data.actionTime - state.gameInfo.serverTime/1000 - state.gameInfo.precedeClose>75)
        ||((payload.data.lotteryId == 401) && payload.data.actionTime - state.gameInfo.serverTime/1000 - state.gameInfo.precedeClose>210))){
          state.betState = false;
          Mint.MessageBox.close();
          Mint.MessageBox({$type:'alert',message:'游戏已封盘，下次开盘时间为:'+new Date(payload.data.openTime*1000).format('yyyy-MM-dd hh:mm:ss'),title:'提示',closeOnClickModal:false});

        }

        if(payload.serverTime){
          if(state.gameInfo.prevGameNo && state.gameInfo.lotteryId == payload.data.lotteryId){
            if(state.gameInfo.prevGameNo > payload.data.prevGameNo){

              return;
            }
          }
          if(state.gameInfo.lotteryId == payload.data.lotteryId && payload.data.gameNo< state.gameInfo.prevGameNo){
            return;
          }
          if(state.gameInfo.lotteryId == payload.data.lotteryId && payload.data.prevResult == state.gameInfo.prevResult){
            return;
          }
        }else{
          if(state.gameInfo.prevGameNo && state.gameInfo.lotteryId == payload.lotteryId){
            if(state.gameInfo.prevGameNo > payload.prevGameNo){

              return;
            }
          }
          if(state.gameInfo.lotteryId == payload.lotteryId && payload.gameNo< state.gameInfo.prevGameNo){
            return;
          }
          if(state.gameInfo.lotteryId == payload.lotteryId && payload.prevResult == state.gameInfo.prevResult){
            return;
          }
        }

        if(payload.prevResult){
          if(state.gameInfo.lotteryId == 101 || state.gameInfo.lotteryId == 102 ||
            state.gameInfo.lotteryId == 103 || state.gameInfo.lotteryId == 104 ||
            state.gameInfo.lotteryId == 105 || state.gameInfo.lotteryId == 106){
            payload.prevResult.split(',').forEach((val)=>{
              let obj = '';
              if(val.at(0)==='0'){
                obj = val.at(1);
              }else{
                obj = val;
              }
              state.gameInfo.prevResult.push(obj);
            });
          }else if(state.gameInfo.lotteryId == 201 || state.gameInfo.lotteryId == 202 ||state.gameInfo.lotteryId == 203
            ||state.gameInfo.lotteryId == 204 ||state.gameInfo.lotteryId == 205) {
            payload.prevResult.split(',').forEach((val)=>{
              state.gameInfo.prevResult.push(val);
            });
          }else if(state.gameInfo.lotteryId == 301 || state.gameInfo.lotteryId == 302 ||state.gameInfo.lotteryId == 303
            || state.gameInfo.lotteryId == 304){
            payload.prevResult.split(',').forEach((val)=>{
              let obj = '';
              if(val.at(0)==='0'){
                obj = val.at(1);
              }else{
                obj = val;
              }
              state.gameInfo.prevResult.push(obj);
            });
          }else if(state.gameInfo.lotteryId == 401 || state.gameInfo.lotteryId == 402 ||state.gameInfo.lotteryId == 403){
            payload.prevResult.split(',').forEach((val)=>{
              state.gameInfo.prevResult.push(val);
            });
          }


        }
        setTimeout(()=>{
          for (let obj in arr) {
            if(arr[obj]>1){
              let splitArr = obj.split('_');
              let param = '';
              if(splitArr.length==3){
                param = {'type': splitArr[0]+'_'+splitArr[1], 'oddsKey': splitArr[2], 'number': arr[obj]};
              }else{
                param = {'type': splitArr[0], 'oddsKey': splitArr[1], 'number': arr[obj]};
              }

              state.longDragonList.push(param);
            }
          }
          for(let i = 0;i<state.longDragonList.length;i++){
            for(let j = 0;j<state.longDragonList.length-i-1;j++){
              if(state.longDragonList[j].number < state.longDragonList[j+1].number){
                let obj = state.longDragonList[j];
                state.longDragonList[j] = state.longDragonList[j+1];
                state.longDragonList[j+1] = obj;
              }

            }
          }
        },1000)
      },
      [types.CHANGE_BET_STATE](state,payload){
        state.betState = payload;
      },
      [types.SET_MARKET](state,payload){
        state.markets = payload;
      },
      [types.SET_PAGE_POSITION](state,payload){
        if(payload){
          if(!state.pagePosition){
            state.pagePosition = [];
          }
          state.pagePosition.push(payload);
        }else{
          state.pagePosition.splice(state.pagePosition.length-1,1);
        }

      },
      [types.SET_PLAY_TYPE](state,payload){
        state.playType=payload;
      },
      [types.SET_HISTORY_RETURN](state,payload){
        state.historyReturn = payload;
      },
      [types.SET_PROFITLOS_RETURN](state,payload){
        state.profitlosReturn = payload;
      },
      [types.SET_HISTORY_HOME_RETURN](state,payload){

        state.historyHomeReturn = payload;
      },
      [types.SET_PROMPT_INFORMATION](state,payload){
        state.promptInformation = payload;
        if(state.promptInformation){
          Mint.MessageBox.close();
          Mint.MessageBox({$type:'alert',message:state.promptInformation,title:'提示',closeOnClickModal:false});
        }
      },
      [types.SET_WHETHER_SWITCH](state,payload){
        state.whetherSwitch = payload;
      },
      [types.SET_GAME_CLEAR](state,payload){
        state.whetherSwitch = false;
        state.games=sessionStorage.getItem('games')?JSON.parse(sessionStorage.getItem('games')):{};
        state.gameMenu=[];
        state.markets=null;
        state.gameId=null;
        state.pagePosition=[];
        state.modeId=10;
        state.game={};
        state.gameInfo={
          closeTime: 0,
            gameNo: '',
            lotteryId: 0,
            openTime: 0,
            prevGameNo: '',
            result: '',
            status: '',
            wl: 0,
            prevResult:[],
        };
        state.categoryList=[];
          state.marketMenu=null;
          state.currentMarket=null;
          state.odds=null;
          state.betState=false;
          state.playType=null;
          state.historyReturn={};
        state.promptInformation='';
          state.historyHomeReturn={};
        state.profitlosReturn={};
        state.longDragonList=[];
          state.pk10Odds={
          lm:{'no1':[],'no2':[],'no3':[],'no4':[],'no5':[],'no6':[],'no7':[],'no8':[],'no9':[],'no10':[]},//两面盘
          firstAddSecond:[],//冠亚和
            oneToFive:[],//1-5,
            sixToTen:[],//6-10
            idcPk10Odds:[]
        };
        state.sscOdds={
          lm:[],
            qiu1:[],
            qiu2:[],
            qiu3:[],
            qiu4:[],
            qiu5:[],
            onezzh:[],
            twozzh:{},
          threezzh:{},
          twozdw:[],
            threezdw:[],
            twozhs:{},
          threezhs:{},
          zxs:{},
          zxl:{},
          fszh:[],
            kd:{},
          ndsm:{},
        };
        state.kl10Odds={
          integration:{},
          oneToEight:{},
          continuousCode:{},
          positiveCode:{}
        };
      },
      [types.SET_SOCKET](state,payload){
        state.socket=payload;
      },
      [types.SET_USER_ODDS_CLJPS](state,payload){
        state.userOddsCljps = payload;
      },
      [types.SET_USER_ODDS_CLOSES](state,payload){
        state.userOddsCloses = payload;
      },
      [types.SET_USER_ODDS_JUMPS](state,payload){
        state.userOddsJumps = payload;
        if(!payload){
          state.userOddsJumps = {};
          /*let msg = {code:'odds_lottery', content:{lotteryId:state.gameId,lotteryKey:state.game.lotteryKey}};
          if(state.socket.ws.readyState == 1){
            state.socket.send(JSON.stringify(msg))
          }*/
        }
      },
      [types.SET_USER_ODDS_NOWS](state,payload){
        state.userOddsNows = payload;
      },
      [types.SET_USER_ODDSS](state,payload){
        state.userOddss = payload;
      },
      [types.SET_CLOSE_SOCKET](state,payload){
        state.socket.send('{"code":"odds_unlottery"}');
      },
      [types.SET_CLEAR_POSITION](state,payload){
        state.pagePosition = [];
      },
      [types.SET_RESULT_STATUS](state,payload){
        state.resultStatus = payload;
      },
      [types.SET_CLEAR_SERVER_TIME](state,payload){
        clearInterval(state.serverTime);
        state.serverTime = null;
      },
      [types.SET_SOCKET_RESET_STATUS](state,payload){
        state.socketResetStatus = payload;
      },
      [types.SET_GAME_STATUS](state,payload){
        state.gameStatus = payload;
      }
    },
    actions: {
      setGames({commit},payload){
        commit(types.SET_GAMES,payload);
      },
      setLotteryNow({commit},payload){
        commit(types.SET_LOTTERY_NOW,payload);//游戏数据
      },
      setCurrentGame({commit},payload){
        commit(types.SET_CURRENT_GAME,payload);//陪率数据
      },
      selectGame({commit},payload){
        commit(types.SELECT_GAME,payload);
        //commit(types.SET_LOTTERY_ID,payload);
      },
      setModeId({commit},payload){
        commit(types.SET_MODE_ID,payload);
      },
      changeBetState({commit},payload){
        commit(types.CHANGE_BET_STATE,payload);//修改下注状态
      },
      setMarket({commit},payload){
        commit(types.SET_MARKET,payload);
      },
      setPagePosition({commit},payload){
        commit(types.SET_PAGE_POSITION,payload);
      },
      setPlayType({commit},payload){
        commit(types.SET_PLAY_TYPE,payload);
      },
      setHistoryReturn({commit},payload){
        commit(types.SET_HISTORY_RETURN,payload);
      },
      setProfitlosReturn({commit},payload){
        commit(types.SET_PROFITLOS_RETURN,payload);
      },
      setHistoryHomeReturn({commit},payload){
        commit(types.SET_HISTORY_HOME_RETURN,payload);
      },
      setPromptInformation({commit},payload){
        commit(types.SET_PROMPT_INFORMATION,payload);
      },
      setGameClear({commit},payload){
        commit(types.SET_GAME_CLEAR,payload);
      },
      setWhetherSwitch({commit},payload){
        commit(types.SET_WHETHER_SWITCH,payload);
      },
      setSocket({commit},payload){
        commit(types.SET_SOCKET,payload);
      },
      setUserOddsCljps({commit},payload){
        commit(types.SET_USER_ODDS_CLJPS,payload);
      },
      setUserOddsCloses({commit},payload){
        commit(types.SET_USER_ODDS_CLOSES,payload);
      },
      setUserOddsJumps({commit},payload){
        commit(types.SET_USER_ODDS_JUMPS,payload);
      },
      setUserOddsNows({commit},payload){
        commit(types.SET_USER_ODDS_NOWS,payload);
      },
      setUserOddss({commit},payload){
        commit(types.SET_USER_ODDSS,payload);
      },
      setCloseSocket({commit},payload){
        commit(types.SET_CLOSE_SOCKET,payload);
      },
      setClearPosition({commit},payload){
        commit(types.SET_CLEAR_POSITION,payload);
      },
      setResultStatus({commit},payload){
        commit(types.SET_RESULT_STATUS,payload);
      },
      setClearServerTime({commit},payload){
        commit(types.SET_CLEAR_SERVER_TIME,payload);
      },
      setSocketResetStatus({commit},payload){
        commit(types.SET_SOCKET_RESET_STATUS,payload);
      },
      setGameStatus({commit},payload){
        commit(types.SET_GAME_STATUS,payload);
      }
    }
}

