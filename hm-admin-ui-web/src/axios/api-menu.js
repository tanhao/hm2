import HttpRequest from './index'
const axios = new HttpRequest();

export default {
    getMenuList(pId) {
        return axios.requestGet('/v2/ucenter/menu/menuList/'+pId);
    },
    addMenu(params){
        return axios.requestPost("/v2/ucenter/menu/add",params)
    },
    delMenu(menuId){
        return axios.requestGet("/v2/ucenter/menu/delete/"+menuId)
    },
    updateMenu(params){
        return axios.requestPost("/v2/ucenter/menu/update",params)
    },
    getSysTree(roleId) {
        return axios.requestGet('/v2/ucenter/menu/tree/' + roleId);
    },
    addRoleMenu(params) {
        return axios.requestPost('/v2/ucenter/menu/addRoleMenu', params);
    },
    delRoleMenu(params) {
        return axios.requestDelete('/v2/ucenter/menu/delRoleMenu', params);
    },

    /*************角色*************/
    getRoleList() {
        return axios.requestGet('/v2/ucenter/role/roleList', null);
    },
    delRole(roleId){
        return axios.requestGet("/v2/ucenter/role/delete/"+roleId)
    },
    addRole(params){
        return axios.requestPost("/v2/ucenter/role/add",params)
    },
    updateRole(params){
        return axios.requestPost("/v2/ucenter/role/update",params)
    },



};
