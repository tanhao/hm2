import * as types from '@/vuex/types'
import Mint from 'mint-ui';
export default {
    state: {
      games:sessionStorage.getItem('games')?JSON.parse(sessionStorage.getItem('games')):{},
      gameMenu:[],
      markets:null,
      market:null,
      gameId:'',
      pagePosition:'',
      whetherSwitch:false,
      game:{},
      gameInfoRefreshNum:0,
      gameInfo:{
        closeTime: 0,
        gameNo: '',
        lotteryId: 0,
        openTime: 0,
        prevGameNo: '',
        betAmtSum:0,
        result: '',
        status: '',
        wl: 0,
        prevResult:[],
        numHe:0
      },
      categoryList:[],
      categorySelect:'',
      longDragonList:[],
      promptInformation:'',
      showGameMenu:{'menuFirst':[],'menuLast':[]},
      odds:null,
      betState:false,
      playType:null,
      historyReturn:{},
      profitlosReturn:{},
      kjlistK3:[],
      k3YxxCss:'k3',
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
      serverTimer:null,
      plays:null
    }
    ,
    getters: {
      games:state=>state.games,
      game:state=>state.game,
      gameInfo:state=>state.gameInfo,
      showGameMenu:state=>state.showGameMenu,
      gameId:state=>state.gameId,
      gameInfoRefreshNum:state=>state.gameInfoRefreshNum,
      promptInformation:state=>state.promptInformation,
      categoryList:state=>state.categoryList,
      categorySelect:state=>state.categorySelect,
      betState:state=>state.betState,
      gameMenu:state=>state.gameMenu,
      markets:state=>state.markets,
      market:state=>state.market,
      odds:state=>state.odds,
      sscOdds:state=>state.sscOdds,
      pk10Odds:state=>state.pk10Odds,
      pcddOdds:state=>state.pcddOdds,
      pagePosition:state=>state.pagePosition,
      playType:state=>state.playType,
      historyReturn:state=>state.historyReturn,
      profitlosReturn:state=>state.profitlosReturn,
      kl10Odds:state=>state.kl10Odds,
      longDragonList:state=>state.longDragonList,
      kjlistK3:state=>state.kjlistK3,
      k3YxxCss:state=>state.k3YxxCss,
      whetherSwitch:state=>state.whetherSwitch,
      socket:state=>state.socket,
      userOddsCljps:state=>state.userOddsCljps,
      userOddsCloses:state=>state.userOddsCloses,
      userOddsJumps:state=>state.userOddsJumps,
      userOddsNows:state=>state.userOddsNows,
      userOddss:state=>state.userOddss,
      plays:state=>state.plays
    },
    mutations: {
      [types.SET_GAMES](state,payload){
        state.games = payload;
        state.dragMenu=[];
        state.gameMenu = [];
        state.showGameMenu.menuFirst=[];
        state.showGameMenu.menuLast = [];

        /*if(state.gameMenu.length==0){*/
          payload.forEach((item)=>{
            if(!item.lotteryId){
              item.lotteryId = item.id;
            }
            if(item.lotteryKey=='bjpk10' || item.lotteryKey=='xyft' || item.lotteryKey=='speed10' ||
              item.lotteryKey=='lucky10' || item.lotteryKey=='sbspeed10' || item.lotteryKey=='hmpk10' || item.lotteryKey=='epssm' ||
              item.lotteryKey=='fhsc' || item.lotteryKey=='tycsm' || item.lotteryKey=='xyft3' || item.lotteryKey=='sgft' || item.lotteryKey=='cqssc'||
              item.lotteryKey=='jsssc' || item.lotteryKey=='lucky5'|| item.lotteryKey=='xjssc'|| item.lotteryKey=='cjk5'|| item.lotteryKey=='fhssc'|| item.lotteryKey=='tycssc'
              || item.lotteryKey=='hmcqssc'|| item.lotteryKey=='txffc'|| item.lotteryKey=='txsfc'|| item.lotteryKey=='twssc' || item.lotteryKey=='gdkl10'
              || item.lotteryKey=='speed8'|| item.lotteryKey=='xync' || item.lotteryKey=='bjk3' || item.lotteryKey=='jsk3'
              || item.lotteryKey=='jlk3' || item.lotteryKey=='gxk3' || item.lotteryKey=='speed3' || item.lotteryKey=='bjfast8'
              || item.lotteryKey=='lucky20' || item.lotteryKey=='gd11x5' || item.lotteryKey=='gxkl10' || item.lotteryKey=='pcdd'
            || item.lotteryKey=='xyft1396' || item.lotteryKey=='lucky8' || item.lotteryKey=='jnd28' || item.lotteryKey=='pcdd' || item.lotteryKey=='jsdd' ){
              state.gameMenu.push({'id':item.lotteryId+'','lotteryKey':item.lotteryKey,'disPlay':true});
              if(state.showGameMenu.menuFirst.length<8){
                state.showGameMenu.menuFirst.push({'id':item.lotteryId+'','lotteryKey':item.lotteryKey,'disPlay':true});
              }else{
                state.showGameMenu.menuLast.push({'id':item.lotteryId+'','lotteryKey':item.lotteryKey,'disPlay':true});
              }
            }
          });
          if(!state.gameId){
            state.gameId = state.gameMenu[0].id;
          }
      },
      [types.SET_MODE_ID](state,payload){
        state.modeId = payload;
      },
      [types.SELECT_GAME](state,payload){
        state.gameId = payload;
        if(state.gameId==101 || state.gameId==201 || state.gameId==204  || state.gameId==301
          || state.gameId==401 || state.gameId==402 || state.gameId==403 || state.gameId==404
          || state.gameId==601 || state.gameId==701 ){
          state.gameInfoRefreshNum = 10;
        }else if(state.gameId==102 || state.gameId==104 || state.gameId==106 || state.gameId==109 || state.gameId==111
          || state.gameId==112 || state.gameId==203 || state.gameId==207 || state.gameId==211 || state.gameId==304
          || state.gameId==501 || state.gameId==502 || state.gameId==801 || state.gameId==901 || state.gameId==107
          || state.gameId==108 || state.gameId==110 || state.gameId==205 || state.gameId==206 || state.gameId==210){
          state.gameInfoRefreshNum = 5;
        }else if(state.gameId==103 || state.gameId==105 || state.gameId==202 || state.gameId==208
          || state.gameId==102 || state.gameId==209 || state.gameId==302 || state.gameId==405){
          state.gameInfoRefreshNum = 3;
        }else{
          state.gameInfoRefreshNum = 10;
        }
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
        if(payload){

                let msg = {code:'odds_lottery', content:{lotteryId:parseInt(state.gameId),lotteryKey:state.game.lotteryKey}};
                state.socket.send(JSON.stringify(msg));


        }
      },
      [types.SET_CURRENT_GAME](state,payload){
        /**
         * 新的陪率数据
         */
        state.categoryList = [];
        //组装需要显示的陪率数据
        if (state.game.lotteryKey === 'bjpk10' || state.game.lotteryKey === 'xyft' ||
          state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10' ||
          state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10' ||
          state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc' || state.game.lotteryKey==='xyft3'
          || state.game.lotteryKey==='tycsm'|| state.game.lotteryKey==='sgft' || state.game.lotteryKey==='xyft1396') {
          state.categoryList=['ydsxz','gyjzh','lm'];
          state.pk10Odds.lm = {'gyh':[],'no1':[],'no2':[],'no3':[],'no4':[],'no5':[],'no6':[],'no7':[],'no8':[],'no9':[],'no10':[]};
          state.pk10Odds.firstAddSecond = [];
          state.pk10Odds.oneToFive = [[],[],[],[],[]];
          state.pk10Odds.sixToTen = [[],[],[],[],[]];
        } else if (state.game.lotteryKey === 'cqssc' || state.game.lotteryKey === 'lucky5' || state.game.lotteryKey==='jsssc'
          || state.game.lotteryKey==='xjssc' || state.game.lotteryKey==='cjk5' || state.game.lotteryKey==='fhssc'
          || state.game.lotteryKey==='tycssc' || state.game.lotteryKey==='hmcqssc' || state.game.lotteryKey==='txffc'
          || state.game.lotteryKey==='txsfc' || state.game.lotteryKey==='twssc') {
          state.categoryList = ['ball','nnp','onezh','twozh','threezh','twodw','threedw','twohs','threehs','zxthree','zxsix','fszh','kd','ndsm'];
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
        }else if (state.game.lotteryKey === 'gdkl10' || state.game.lotteryKey==='speed8' || state.game.lotteryKey==='xync'
          || state.game.lotteryKey==='lucky8') {
          state.categoryList = ['oneToEight','integration','qiu1','qiu2','qiu3','qiu4','qiu5','qiu6','qiu7','qiu8','positiveCode','continuousCode'];
          state.kl10Odds.oneToEight={'qiu1':[],'qiu2':[],'qiu3':[],'qiu4':[],'qiu5':[],'qiu6':[],'qiu7':[],'qiu8':[]};
          state.kl10Odds.integration={'sum':[]};
          state.kl10Odds.positiveCode={'zm':[]};
          state.kl10Odds.continuousCode={'lianma':[]};
        } else if (state.game.lotteryKey === 'pcdd' || state.game.lotteryKey === 'jsdd' || state.game.lotteryKey === 'jnd28') {
          state.pcddOdds.lm=[];
          state.pcddOdds.tmbs=[];
          state.pcddOdds.baozi=[];
          state.pcddOdds.hezhi=[];
          state.pcddOdds.sebo=[];
          state.pcddOdds.shunzi=[];
          state.pcddOdds.duizi=[];
        }
        if(state.whetherSwitch){
          state.categorySelect = state.categoryList[0];
        }
        if(!state.plays){
          state.plays = payload.plays;
        }
        for(let key in state.plays){
          if(key && key.substring(0,3)!=state.gameId){
            state.plays = payload.plays;
          }
        }
        state.userOddsCljps = payload.userOddsCljps?payload.userOddsCljps:state.userOddsCljps;
        state.userOddsCloses = payload.userOddsCloses?payload.userOddsCloses:state.userOddsCloses;
        state.userOddsJumps = payload.userOddsJumps?payload.userOddsJumps:state.userOddsJumps;
        state.userOddsNows = payload.userOddsNows?payload.userOddsNows:state.userOddsJumps;
        state.userOddss = payload.userOddss?payload.userOddss:state.userOddss;
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

          for(let marketP in payload.markets){
            if(payload.markets[marketP]){
              state.market = marketP;
            }
          }

          for(let userOddsUid in payload.userOddss){
            if(obj.categoryId == userOddsUid){
              let userOdds = 0;
              payload.userOddss[userOddsUid].forEach(val=>{userOdds+=val});
              obj.odds = userOdds;
            }
          }
          if (state.game.lotteryKey === 'bjpk10' || state.game.lotteryKey === 'xyft' ||
            state.game.lotteryKey==='lucky10' || state.game.lotteryKey==='speed10'||
            state.game.lotteryKey==='sbspeed10' || state.game.lotteryKey==='hmpk10'||
            state.game.lotteryKey==='epssm' || state.game.lotteryKey==='fhsc'
            || state.game.lotteryKey==='tycsm' || state.game.lotteryKey==='xyft3'|| state.game.lotteryKey==='sgft'
            || state.game.lotteryKey==='xyft1396') {
            if(obj.playKey === "no1" || obj.playKey === "no2" || obj.playKey === "no3" || obj.playKey === "no4" || obj.playKey === "no5"){
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
          }else if(state.game.lotteryKey==='pcdd'  || state.game.lotteryKey=='jnd28' || state.game.lotteryKey=='jsdd'){
            console.log('kkd',state.pcddOdds,obj);
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

        if(state.game.groupId ==200){
          state.sscOdds.threezzh['3ztop3'] = [...state.sscOdds.threezzh['3ztop3']['lingtou'],...state.sscOdds.threezzh['3ztop3']['yitou'],...state.sscOdds.threezzh['3ztop3']['ertou'],
            ...state.sscOdds.threezzh['3ztop3']['santou'],...state.sscOdds.threezzh['3ztop3']['sitou'],
            ...state.sscOdds.threezzh['3ztop3']['wutou'],...state.sscOdds.threezzh['3ztop3']['liutou'],
            ...state.sscOdds.threezzh['3ztop3']['qitou'],...state.sscOdds.threezzh['3ztop3']['batou'],
            ...state.sscOdds.threezzh['3ztop3']['jiutou']];
          state.sscOdds.threezzh['3zmid3'] = [...state.sscOdds.threezzh['3zmid3']['lingtou'],...state.sscOdds.threezzh['3zmid3']['yitou'],...state.sscOdds.threezzh['3zmid3']['ertou'],
            ...state.sscOdds.threezzh['3zmid3']['santou'],...state.sscOdds.threezzh['3zmid3']['sitou'],
            ...state.sscOdds.threezzh['3zmid3']['wutou'],...state.sscOdds.threezzh['3zmid3']['liutou'],
            ...state.sscOdds.threezzh['3zmid3']['qitou'],...state.sscOdds.threezzh['3zmid3']['batou'],
            ...state.sscOdds.threezzh['3zmid3']['jiutou']];
          state.sscOdds.threezzh['3zend3'] = [...state.sscOdds.threezzh['3zend3']['lingtou'],...state.sscOdds.threezzh['3zend3']['yitou'],...state.sscOdds.threezzh['3zend3']['ertou'],
            ...state.sscOdds.threezzh['3zend3']['santou'],...state.sscOdds.threezzh['3zend3']['sitou'],
            ...state.sscOdds.threezzh['3zend3']['wutou'],...state.sscOdds.threezzh['3zend3']['liutou'],
            ...state.sscOdds.threezzh['3zend3']['qitou'],...state.sscOdds.threezzh['3zend3']['batou'],
            ...state.sscOdds.threezzh['3zend3']['jiutou']];
        }
        console.log('pcdd',state.pcddOdds,state.game);


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
        ||((payload.data.lotteryId == 103 ||
            payload.data.lotteryId == 401) && payload.data.actionTime - state.gameInfo.serverTime/1000 - state.gameInfo.precedeClose>210))){
          state.betState = false;
          Mint.MessageBox.close();
          Mint.MessageBox({$type:'alert',message:'游戏已封盘，下次开盘时间为:'+new Date(payload.data.openTime*1000).format('yyyy-MM-dd hh:mm:ss'),title:'提示',closeOnClickModal:false});

        }

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

          let resultArr = payload.prevResult.split(',');
          let arr = [];
          if(state.gameInfo.lotteryId == 101 || state.gameInfo.lotteryId == 102 ||
            state.gameInfo.lotteryId == 103 || state.gameInfo.lotteryId == 104 ||
            state.gameInfo.lotteryId == 105 || state.gameInfo.lotteryId == 106){
            let gyh = parseInt(resultArr[0])+parseInt(resultArr[1]);
            let gyhOU = gyh>11?'OVER':'UNDER';
            let gyhOE = (gyh%2===1)?'ODD':'EVEN';
            let no1DT = parseInt(resultArr[0])>parseInt(resultArr[9])?'DRAGON':'TIGER';
            let no2DT = parseInt(resultArr[1])>parseInt(resultArr[8])?'DRAGON':'TIGER';
            let no3DT = parseInt(resultArr[2])>parseInt(resultArr[7])?'DRAGON':'TIGER';
            let no4DT = parseInt(resultArr[3])>parseInt(resultArr[6])?'DRAGON':'TIGER';
            let no5DT = parseInt(resultArr[4])>parseInt(resultArr[5])?'DRAGON':'TIGER';
            arr = [gyh+'',gyhOU,gyhOE,no1DT,no2DT,no3DT,no4DT,no5DT];
            payload.prevResult.split(',').forEach((val)=>{
              let obj = '';
              if(val.substring(0,1)==='0'){
                obj = val.substring(1,2);
              }else{
                obj = val;
              }
              state.gameInfo.prevResult.push(obj);
            });
            state.gameInfo.result = arr;
          }else if(state.gameInfo.lotteryId == 201 || state.gameInfo.lotteryId == 202 ||state.gameInfo.lotteryId == 203 ||state.gameInfo.lotteryId == 204 ||state.gameInfo.lotteryId == 205) {
            payload.prevResult.split(',').forEach((val)=>{
              state.gameInfo.prevResult.push(val);
            });
          }else if(state.gameInfo.lotteryId == 301 || state.gameInfo.lotteryId == 302 ||state.gameInfo.lotteryId == 303
            || state.gameInfo.lotteryId == 304){
            payload.prevResult.split(',').forEach((val)=>{
              let obj = '';
              if(val.substring(0,1)==='0'){
                obj = val.substring(1,2);
              }else{
                obj = val;
              }
              state.gameInfo.prevResult.push(obj);
            });
          }else if(state.gameInfo.lotteryId == 401 || state.gameInfo.lotteryId == 402 ||state.gameInfo.lotteryId == 403){
            state.gameInfo.numHe = 0;
            payload.prevResult.split(',').forEach((val)=>{
              state.gameInfo.numHe+=parseInt(val);
              state.gameInfo.prevResult.push(val);
            });
          }

        }

      },
      [types.CHANGE_BET_STATE](state,payload){
        state.betState = payload;
      },
      [types.SET_MARKET](state,payload){
        state.markets = payload;
      },
      [types.SET_PAGE_POSITION](state,payload){
        state.pagePosition = payload;
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
      [types.SET_CATEGORY_SELECT](state,payload){
        state.categorySelect = payload;
      },
      [types.SET_LONG_DRAGONLIST](state,payload){
        state.longDragonList = payload;
      },
      [types.SET_KJLIST_K3](state,payload){
        state.kjlistK3= [];
        payload.findIndex((resultItem,index,arrList)=>{
          if(resultItem && resultItem.result) {
            let param = [];
            let he = 0;
            let arr = [];
            resultItem.result.split(',').forEach((obj) => {
              he += Number.parseInt(obj);
              param.push(obj);
            });
            let ou = he > 10 ? 'OVER' : 'UNDER';
            arr = [he, ou];
            resultItem.special = arr;
            resultItem.result = param;
            if(state.kjlistK3.length<20){
              state.kjlistK3.push(resultItem);
            }

          }

        });


      },
      [types.SET_K3_YXX_CSS](state,payload){
        state.k3YxxCss = payload;
      },
      [types.SET_GAME_MENU](state,payload){
        state.gameMenu = payload;
      },
      [types.SET_PROMPT_INFORMATION](state,payload){
        state.promptInformation = payload;

        if(state.promptInformation){
          Mint.MessageBox.close();
          Mint.MessageBox({$type:'alert',title: '提示', message: state.promptInformation,closeOnClickModal:false, showCancelButton: false});
        }
      },
      [types.SET_WHETHER_SWITCH](state,payload){
        state.whetherSwitch = payload;
      },
      [types.SET_GAME_CLEAR](state,payload){
        console.log('清空了game');
        state.games=sessionStorage.getItem('games')?JSON.parse(sessionStorage.getItem('games')):{};
        state.gameMenu=[];
        state.markets=null;
        state.gameId='';
        state.pagePosition='';
        state.game={};
        state.gameInfo={
          closeTime: 0,
            gameNo: '',
            lotteryId: 0,
            openTime: 0,
            prevGameNo: '',
            betAmtSum:0,
            result: '',
            status: '',
            wl: 0,
            prevResult:[],
            numHe:0
        };
        state.categoryList=[];
        state.categorySelect='';
        state.longDragonList=[];
        state.promptInformation='';
        state.showGameMenu={'menuFirst':[],'menuLast':[]};
        state.odds=null;
        state.betState=false;
        state.playType=null;
        state.historyReturn={};
        state.profitlosReturn={};
        state.kjlistK3=[];
        state.k3YxxCss='k3';
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
        state.fast3Odds={
          cangpai:{},
          dandian:{},
          dianshu:{},
          duanpai:{},
          heya:{},
          ou:{},
          quanwei:{},
          sanjun:{},
          weisai:{},
          zufang:{}
        };
        state.pcddOdds={
          baozi:[],
          hezhi:[],
          lm:[],
          sebo:[],
          tmbs:[],
          duizi:[],
          shunzi:[]
        };
        state.kl10Odds={
          integration:{},
          oneToEight:{},
          continuousCode:{},
          positiveCode:{}
        };
        state.gxkl10Odds={
          lm:{},
          qiu:{}
        };
        state.bjfast8Odds={
          zh:{},
          zm:{}
        };
        state.gd11x5Odds={
          lm:{},
          dh:{},
          lianma:{},
          zhixuan:{}
        };
      },
      [types.SET_SOCKET](state,payload){
        state.socket=payload;
      },
      [types.SET_USER_ODDS_CLJPS](state,payload){
        state.userOddsCljps = null;
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
      }

    },
    actions: {
      setGames({commit},payload){
        commit(types.SET_GAMES,payload);
      },
      setLotteryNow({commit,dispatch},payload){
        commit(types.SET_LOTTERY_NOW,payload);//游戏数据


      },
      setCurrentGame({commit},payload){
        commit(types.SET_CURRENT_GAME,payload);//陪率数据
        commit(types.SET_MARKET,payload.market);
      },
      selectGame({commit},payload){
        commit(types.SELECT_GAME,payload);
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
      setCategorySelect({commit},payload){
        commit(types.SET_CATEGORY_SELECT,payload);
      },
      setLongDragonList({commit},payload){
        commit(types.SET_LONG_DRAGONLIST,payload);
      },
      setKjlistK3({commit},payload){
        commit(types.SET_KJLIST_K3,payload);
      },
      setK3YxxCss({commit},payload){
        commit(types.SET_K3_YXX_CSS,payload);
      },
      setGameMenu({commit},payload){
        commit(types.SET_GAME_MENU,payload);
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
      }
    }
}

