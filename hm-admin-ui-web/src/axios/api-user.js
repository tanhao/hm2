import HttpRequest from './index'
const axios = new HttpRequest()

export default {
    getCaptcha(){
        return axios.requestGet('/v2/auth/captcha');
    },
    login(data) {
        return axios.requestPost('/v2/auth/login', data);
    },
    logout() {/*登出*/
        return axios.requestGet('/v2/ucenter/user/userlogout');
    },
    getRouters(son) {
        return axios.requestGet('/v2/ucenter/user/routers/'+son);
    },
    getUserList(data) {
        return axios.requestGet('/v2/ucenter/user/findList', data);
    },
    updateUserInfo(data){
        return axios.requestPost('/v2/ucenter/user/update', data);
    },
    findPrePt(userId){
        return axios.requestGet('/v2/ucenter/user/pre/pt/'+userId);
    },
    findMemberPrePt(userId){
        return axios.requestGet('/v2/ucenter/user/pre/membetPt/'+userId);
    },
    findActualPt(userId){
        return axios.requestGet('/v2/ucenter/user/actual/pt/'+userId);
    },
    putUserPt(params) {
        return axios.requestPut('/v2/ucenter/user/edit/pt', params);
    },
    putUserPre(params){
        return axios.requestPut('/v2/ucenter/user/edit/pre', params);
    },
    findUserBasic(userId){
        return axios.requestGet('/v2/ucenter/user/basic/'+userId);
    },
    saveUserBasic(data){
        return axios.requestPost('/v2/ucenter/user/add/',data);
    },
    getAddUser(id){
        return axios.requestGet('/v2/ucenter/user/add/user/' + id);
    },
    delUserId(userId){
        return axios.requestDelete('/v2/ucenter/user/delUserId/'+userId);
    },
    getSitename(){
        return axios.requestGet('/v2/ucenter/website/sitename');
    },
    putBalance(params) {
        return axios.requestPut('/v2/ucenter/user/balance', params);
    },
    allBalance(params){
        return axios.requestPut('/v2/ucenter/user/allBalance',params)
    },
    nullMemBalance(params){
        return axios.requestPut("/v2/ucenter/user/nullMemBalance",params)
    },
    updatePwd(params){
        return axios.requestPost('/v2/ucenter/user/updatePwd',params)
    },
    updatePwdNotoken(params){
        return axios.requestPost('/v2/ucenter/user/updatePwd/notoken',params)
    },
    updateSecondary(params){
        return axios.requestPost("/v2/ucenter/user/updateSecondary",params)
    },

    selUserNameList(params){/* 按日期和用户名查询 */
        return axios.requestGet("/v2/ucenter/login/selUserNameList",params)
    },
    selIPList(params){/* 按IP查询 */
        return axios.requestGet("/v2/ucenter/login/selIPList",params)
    },
    selUserIdList(params){/* 按ID查询 */
        return axios.requestGet("/v2/ucenter/login/selUserIdList",params)
    },
    selUserIdByList(params){/* 按传进去的ID查询 */
        return axios.requestGet("/v2/ucenter/login/selUserIdByList",params)
    },
    selUserRunning(params){/* 按传进去的ID查询余额变动日志 */
        return axios.requestGet("/v2/ucenter/user/userRunning",params)
    },
    getUserLottery(userId){/* 按传进去的ID查询彩种 */
        return axios.requestGet("/v2/ucenter/user/userLottery/"+userId)
    },
    updateUserIdLottery(params){/* 按传进去的ID修改彩种 */
        return axios.requestGet("/v2/ucenter/user/updateUserIdLottery",params)
    },
    updateGroupIdLottery(params){/* 按传进去的组ID修改彩种 */
        return axios.requestGet("/v2/ucenter/user/updateGroupIdLottery",params)
    },

    countOrder(userId){/*查询下线是否有注单*/
        return axios.requestGet("/v2/ucenter/user/countOrder/"+userId);
    },
    toStartTime(userId){
        return axios.requestGet("/v2/ucenter/website/toStartTime/"+userId);
    },
    toUpdateTime(data){
        return axios.requestPut("/v2/ucenter/website/updateTime",data);
    },
    resetPwd(username){
        return axios.requestPost('/v2/ucenter/user/reset/password/'+username);
    },
    relation(data){
        return axios.requestGet('/v2/ucenter/user/relation',data);
    },
    lockList(){
        return axios.requestGet('/v2/ucenter/user/login/error');
    },
    lockInfo(username,type){
        return axios.requestGet('/v2/ucenter/user/login/error/'+username+'/'+type);
    },

};
